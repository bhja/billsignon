package com.cloudflare.billonsigndate.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableBatchProcessing
public class BatchSchedule {

  @Autowired
  private JobLauncher jobLauncher;
  @Autowired
  private Job job;


  @Scheduled(cron = "${com.cloudflare.cronExpression}")
  public void billOnSignDate() {
    JobParameters jobParameters = new JobParametersBuilder()
        .addLong("startAt", System.currentTimeMillis()).toJobParameters();
    try {
      jobLauncher.run(job, jobParameters);

    } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
             JobParametersInvalidException e) {
      e.printStackTrace();
    }

  }
}
