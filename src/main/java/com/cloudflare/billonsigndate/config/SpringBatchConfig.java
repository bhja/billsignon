package com.cloudflare.billonsigndate.config;


import com.cloudflare.billonsigndate.batch.processor.CustomerItemProcessor;
import com.cloudflare.billonsigndate.batch.reader.CustomerItemReader;
import com.cloudflare.billonsigndate.batch.writer.NoOpItemWriter;
import com.cloudflare.billonsigndate.entity.CustomerInfo;
import com.cloudflare.billonsigndate.repository.SubscriptionInfoRepo;
import com.cloudflare.billonsigndate.utils.HttpUtility;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Configuration
@AllArgsConstructor
public class SpringBatchConfig {


  @Autowired
  AppInfo appInfo;
  @Autowired
  HttpUtility httpUtility;
  private JobBuilderFactory factory;
  private StepBuilderFactory stepBuilderFactory;
  private SubscriptionInfoRepo subscriptionInfoRepo;

  @Bean
  public Step initialBatchProcess(CustomerItemReader reader, CustomerItemProcessor processor) {
    return stepBuilderFactory.get("bill-on-SignDate").<CustomerInfo, String>chunk(10)
                             .reader(reader)
                             .processor(processor)
                             .writer(new NoOpItemWriter())
                             .taskExecutor(taskExecutor())
                             .build();
  }

  @Bean
  public Job runJob(CustomerItemReader reader, CustomerItemProcessor processor) {
    return factory.get("importCustomers").
                  flow(initialBatchProcess(reader, processor)).end().build();

  }

  @Bean
  public TaskExecutor taskExecutor() {
    SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
    asyncTaskExecutor.setConcurrencyLimit(10);
    return asyncTaskExecutor;
  }


}
