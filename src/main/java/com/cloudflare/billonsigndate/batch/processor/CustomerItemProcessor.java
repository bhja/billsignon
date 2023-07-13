package com.cloudflare.billonsigndate.batch.processor;

import com.cloudflare.billonsigndate.config.AppInfo;
import com.cloudflare.billonsigndate.entity.CustomerInfo;
import com.cloudflare.billonsigndate.model.Response;
import com.cloudflare.billonsigndate.model.SubscriptionModel;
import com.cloudflare.billonsigndate.utils.HttpUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;


@Component
public class CustomerItemProcessor
    implements ItemProcessor<CustomerInfo, String> {

  private Logger logger = LoggerFactory.getLogger(CustomerItemProcessor.class);


  private AppInfo appInfo;
  private HttpUtility httpUtility;
  private Executor executor;




  public CustomerItemProcessor(AppInfo appInfo, HttpUtility httpUtility, @Qualifier("jobExecutor")Executor executor) {
    this.appInfo = appInfo;
    this.httpUtility = httpUtility;
    this.executor=executor;
  }

  @Override
  public String process(CustomerInfo customerInfo) throws Exception {
    logger.debug("{}", customerInfo);
    if (customerInfo != null) {
      CompletableFuture.runAsync(()-> getSubscription(customerInfo).thenApplyAsync(value -> {
        logger.debug("Value" + value);
        //Construct the required value and pass it along
        if (value != null) {
          return postSubscription(null);
        } else {
          return CompletableFuture.completedStage(null);
        }

      }).whenComplete((value, error) -> {
        if (error != null) {
          throw new RuntimeException(error);
        } else {
          //Do what you need to.
        }
      }), executor);
    }
    return "Task completed";
  }

  protected CompletableFuture<Response> getSubscription(CustomerInfo customerInfo) {

    CompletableFuture<Response> completableFuture = new CompletableFuture<>();
    try {
      Response responseObject =
          httpUtility.post(appInfo.getGetSubscriptionItemUrl() + customerInfo.getSubscriptionScheduleId(),
                           Collections.singletonMap("prebilling[iterations]", "1"), Collections.singletonMap("apiKey",
                                                                                                             appInfo.getSubscriptionScheduleApiKey()));
      completableFuture.complete(responseObject);
    } catch (IOException | InterruptedException ex) {
      logger.error("Get method failed {}", ex.getMessage());
      completableFuture.completeExceptionally(ex);
    }
    return completableFuture;
  }

  protected CompletableFuture<Response> postSubscription(SubscriptionModel model) {
    CompletableFuture<Response> completableFuture = new CompletableFuture<>();
    //TODO - Add the logic to run the code.
    return completableFuture;
  }
}
