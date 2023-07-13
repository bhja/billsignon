package com.cloudflare.billonsigndate.service;

import org.springframework.beans.factory.annotation.Autowired;

//@Service
public class Scheduler {
  @Autowired
  SubscriptionScheduleProcessor subscriptionProcessor;


  // @Scheduled(cron = "${com.cloudflare.cronExpression}")
  public void processSubscriptionSchedule() {
    subscriptionProcessor.executeSubscriptionScheduleProcess();
  }

}
