package org.atlasapi.remotesite.metabroadcast;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.atlasapi.remotesite.redux.UpdateProgress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.metabroadcast.common.scheduling.ScheduledTask;
import com.metabroadcast.common.time.Timestamp;

public class MagpieUpdaterTask extends ScheduledTask{
    
    private static final Logger log = LoggerFactory.getLogger(MagpieUpdaterTask.class);

    private final MetaBroadcastMagpieUpdater magpieUpdater;
    private final RemoteMagpieResultsSource resultsSource;
    private final SchedulingStore schedulingStore;
    
    private final AtomicReference<Timestamp> lastModified;

    public MagpieUpdaterTask(RemoteMagpieResultsSource resultsSource, MetaBroadcastMagpieUpdater magpieUpdater, SchedulingStore schedulingStore) {
        this.resultsSource = resultsSource;
        this.magpieUpdater = magpieUpdater;
        this.schedulingStore = schedulingStore;
        this.lastModified = new AtomicReference<Timestamp>();
    }

    @Override
    protected void runTask() {
        
        lastModified.set(toTimestamp(getPersistedState()));
        Iterable<RemoteMagpieResults> resultsChangedSince = resultsSource.resultsChangeSince(lastModified.get());
        Iterator<RemoteMagpieResults> resultsIter = resultsChangedSince.iterator();
        
        UpdateProgress progress = UpdateProgress.START;
        
        while(resultsIter.hasNext() && shouldContinue()) {
            RemoteMagpieResults results = resultsIter.next();
            if (results.retrieved()) {
                UpdateProgress resultProgress = magpieUpdater.updateTopics(results.getResults());
                progress = progress.reduce(resultProgress);
                reportStatus(progress.toString());
                
                lastModified.set(results.getTimestamp());
                saveState();
            } else {
                log.warn(results.context(), results.reason());
            }
        }

        reportStatus(progress.toString());
    }

    private Timestamp toTimestamp(Optional<Map<String, Object>> possibleState) {
        if (possibleState.isPresent()) {
            Map<String, Object> state = possibleState.get();
            return Timestamp.of((Long)state.get("lastModifiedTime"));
        }
        return Timestamp.of(0);
    }

    private void saveState() {
        schedulingStore.storeState(getName(), getRecordableState());
    }

    private Map<String, Object> getRecordableState() {
        return ImmutableMap.<String,Object>of("lastModifiedTime", lastModified.get().millis());
    }

    private Optional<Map<String, Object>> getPersistedState() {
        return schedulingStore.retrieveState(getName());
    }

}