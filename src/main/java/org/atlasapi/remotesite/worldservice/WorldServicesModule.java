package org.atlasapi.remotesite.worldservice;

import javax.annotation.PostConstruct;

import org.atlasapi.persistence.content.ContentResolver;
import org.atlasapi.persistence.content.ContentWriter;
import org.atlasapi.persistence.logging.AdapterLog;
import org.jets3t.service.security.AWSCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.metabroadcast.common.scheduling.RepetitionRules;
import com.metabroadcast.common.scheduling.SimpleScheduler;

@Configuration
public class WorldServicesModule {
    
    @Autowired private ContentResolver resolver; 
    @Autowired private ContentWriter writer;
    @Autowired private AdapterLog log;
    @Autowired private SimpleScheduler scheduler;
    
    private @Value("${s3.access}") String s3access;
    private @Value("${s3.secret}") String s3secret;
    private @Value("${s3.worldservice.bucket}") String s3bucket;
    
    @Bean public WsDataStore wsDataStore() {
        return new S3WsDataStore(new AWSCredentials(s3access, s3secret), s3bucket, log);
    }
    
    @Bean public WsProgrammeUpdater wsProgrammeUpdater() {
        return new WsProgrammeUpdater(wsDataStore(), new DefaultWsSeriesHandler(resolver, writer, log), new DefaultWsProgrammeHandler(resolver, writer, log), log);
    }
    
    @PostConstruct
    public void schedule() {
        scheduler.schedule(wsProgrammeUpdater(), RepetitionRules.NEVER);
    }
    
}
