package com.techfox.buildup.service;

import org.jobrunr.jobs.JobId;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.jobs.context.JobContext;
import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.spring.annotations.Recurring;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BatchJobService {

    final private JobScheduler jobScheduler;

    public BatchJobService(JobScheduler jobScheduler) {
        this.jobScheduler = jobScheduler;
    }

    public JobId enqueueJob() {
        return jobScheduler.enqueue(UUID.randomUUID(),
                () -> runScheduledJob("name", JobContext.Null)
        );
    }

    public void runScheduledJob(String id, JobContext jobContext) {

    }

    @Recurring(id="sampleJob", cron = "* * * * *")
    @Job(name = "sampleJob")
    public void testCurlData(){

    }
}
