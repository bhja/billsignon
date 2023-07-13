package com.cloudflare.billonsigndate.config;


import com.cloudflare.billonsigndate.batch.processor.CustomerItemProcessor;
import com.cloudflare.billonsigndate.batch.reader.CustomerItemReader;
import com.cloudflare.billonsigndate.batch.writer.NoOpItemWriter;
import com.cloudflare.billonsigndate.entity.CustomerInfo;
import com.cloudflare.billonsigndate.utils.HttpUtility;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import java.util.concurrent.CompletableFuture;

@Configuration
@AllArgsConstructor
public class SpringBatchConfig {


  @Autowired
  AppInfo appInfo;
  @Autowired
  HttpUtility httpUtility;

  private StepBuilderFactory stepBuilderFactory;

  @Bean
  public Step initialBatchProcess(CustomerItemReader reader, CustomerItemProcessor processor) {
    return stepBuilderFactory.get("bill-on-SignDate").<CustomerInfo, CompletableFuture<String>>chunk(appInfo.getChunkSize())
                             .reader(reader)
                             .processor(processor)
                             .writer(new NoOpItemWriter())
                             .taskExecutor(taskExecutor())
                             .build();
  }

  @Bean
  public Job runJob(JobRepository repository, CustomerItemReader reader, CustomerItemProcessor processor) {
    return new JobBuilder("importCustomer").repository(repository).
                                           flow(initialBatchProcess(reader, processor)).end().build();

  }

  @Bean
  public TaskExecutor taskExecutor() {
    SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
    asyncTaskExecutor.setConcurrencyLimit(appInfo.getAsyncPoolSize());
    return asyncTaskExecutor;
  }


}
