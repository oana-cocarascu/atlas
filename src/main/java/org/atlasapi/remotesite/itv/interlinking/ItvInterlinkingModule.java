package org.atlasapi.remotesite.itv.interlinking;

import javax.annotation.PostConstruct;

import org.atlasapi.persistence.content.ContentResolver;
import org.atlasapi.persistence.content.ContentWriter;
import org.atlasapi.persistence.logging.AdapterLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.metabroadcast.common.scheduling.RepetitionRules;
import com.metabroadcast.common.scheduling.SimpleScheduler;

@Configuration
public class ItvInterlinkingModule {
    
    @Autowired
    private ContentWriter contentWriter;
    
    @Autowired
    private ContentResolver contentResolver;
    
    @Autowired
    private SimpleScheduler scheduler;
    
    @Autowired
    private AdapterLog log;
    
    @PostConstruct
    public void setup() {
        scheduler.schedule(itvInterlinkingTodayUpdater().withName("ITV Interlinking today updater"), RepetitionRules.NEVER);
        scheduler.schedule(itvInterlinkingUpdater().withName("ITV Interlinking 30 day updater"), RepetitionRules.NEVER);
    }
    
    @Bean
    public ItvInterlinkingTodayUpdater itvInterlinkingTodayUpdater() {
        return new ItvInterlinkingTodayUpdater(itvInterlinkingSingleFileUpdater(), log);
    }
    
    @Bean
    public ItvInterlinkingUpdater itvInterlinkingUpdater() {
        return new ItvInterlinkingUpdater(itvInterlinkingSingleFileUpdater(), log, 30);
    }
    
    @Bean ItvInterlinkingSingleFileUpdater itvInterlinkingSingleFileUpdater() {
        return new ItvInterlinkingSingleFileUpdater(contentWriter, itvInterlinkingContentExtractor());
    }
    
    @Bean
    public ItvInterlinkingEntryProcessor itvInterlinkingEntryProcessor() {
        return new ItvInterlinkingEntryProcessor(contentWriter, itvInterlinkingContentExtractor());
    }
    
    @Bean
    public ItvInterlinkingContentExtractor itvInterlinkingContentExtractor() {
        return new ItvInterlinkingContentExtractor(contentResolver);
    }
}
