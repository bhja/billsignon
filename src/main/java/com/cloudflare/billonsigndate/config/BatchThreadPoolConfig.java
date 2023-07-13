package com.cloudflare.billonsigndate.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class BatchThreadPoolConfig {
  @Autowired
  AppInfo appInfo;

  @Bean("jobExecutor")
  public ThreadPoolTaskExecutor allocateThreadPoolExecutor() {
    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
    taskExecutor.setMaxPoolSize(appInfo.getMaxPoolSize());
    taskExecutor.setCorePoolSize(appInfo.getCorePoolSize());
    taskExecutor.setQueueCapacity(appInfo.getQueueCapacity());
    taskExecutor.setThreadNamePrefix(appInfo.getThreadPrefix());
    return taskExecutor;
  }
}
