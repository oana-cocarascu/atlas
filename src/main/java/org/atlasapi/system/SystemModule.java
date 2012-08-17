package org.atlasapi.system;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.google.common.collect.ImmutableList;
import com.metabroadcast.common.health.HealthProbe;
import com.metabroadcast.common.health.probes.DiskSpaceProbe;
import com.metabroadcast.common.health.probes.MemoryInfoProbe;
import com.metabroadcast.common.persistence.mongo.health.MongoConnectionPoolProbe;
import com.metabroadcast.common.webapp.health.HealthController;
import org.atlasapi.messaging.producers.MessageReplayer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SystemModule {
	
	private final ImmutableList<HealthProbe> systemProbes = ImmutableList.<HealthProbe>of(new MemoryInfoProbe(), new DiskSpaceProbe(), new MongoConnectionPoolProbe());
	
	private @Autowired Collection<HealthProbe> probes;
	private @Autowired HealthController healthController;
    private @Autowired MessageReplayer messageReplayer;

	public @Bean HealthController healthController() {
		return new HealthController(systemProbes);
	}
	
	public @Bean org.atlasapi.system.HealthController threadController() {
		return new org.atlasapi.system.HealthController();
	}
    
    public @Bean ReplayController replayController() {
        return new ReplayController(messageReplayer);
    }
	
	@PostConstruct
	public void addProbes() {
		healthController.addProbes(probes);
	}
}