package com.techfox.buildup.config;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.val;
import org.jobrunr.jobs.mappers.JobMapper;
import org.jobrunr.server.BackgroundJobServer;
import org.jobrunr.server.BackgroundJobServerConfiguration;
import org.jobrunr.server.JobActivator;
import org.jobrunr.storage.InMemoryStorageProvider;
import org.jobrunr.storage.StorageProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.jobrunr.storage.nosql.mongo.MongoDBStorageProvider;

@Configuration
public class JobRunrConfig {
    private MongoClient mongoClient;

    @Bean
    public StorageProvider storageProvider(JobMapper jobMapper) {
        StorageProvider storageProvider = MongoDBStorageProvider(mongoClient);
        storageProvider.setJobMapper(jobMapper);

        return storageProvider;
    }

    @Bean
    public BackgroundJobServer backgroundJobServer(
            StorageProvider storageProvider,
            @Qualifier("jobRunrJsonMapper") JsonMapper jsonMapper,
            JobActivator jobActivator
    ) {

        BackgroundJobServer backgroundJobServer = new BackgroundJobServer(
                storageProvider, (org.jobrunr.utils.mapper.JsonMapper) jsonMapper);
        backgroundJobServer.start();

        return backgroundJobServer;
    }
}
