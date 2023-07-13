package com.cloudflare.billonsigndate.batch.processor;

import com.cloudflare.billonsigndate.config.AppInfo;
import com.cloudflare.billonsigndate.entity.CustomerInfo;
import com.cloudflare.billonsigndate.model.Response;
import com.cloudflare.billonsigndate.model.RetrieveSchedule;
import com.cloudflare.billonsigndate.utils.HttpUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;


@Component
public class CustomerItemProcessor
    implements ItemProcessor<CustomerInfo, String> {

  private Logger logger = LoggerFactory.getLogger(CustomerItemProcessor.class);


  @Autowired
  private AppInfo appInfo;

  @Autowired
  private HttpUtility httpUtility;

  @Autowired
  @Qualifier("jobExecutor")
  private Executor executor;


  @Override
  public String process(CustomerInfo customerInfo) throws Exception {
    logger.debug("{}", customerInfo);
    if (customerInfo != null) {
      CompletableFuture.supplyAsync(() -> getSubscription(customerInfo).whenComplete((value, error) -> {
        if (error == null) {
          logger.debug("Value {} ", value);
          if (value.getStatusCode() == HttpStatus.OK.value()) {
            postSubscription(null).whenComplete((val, err) -> {
                                                  if (err == null) {
                                                    //Process the code.

                                                  }else{
                                                    logger.error("Issue with the call to the due to {}" ,
                                                                 err.getMessage());
                                                  }
                                                }
            );
          } else {
            logger.error("Issue with the call" + value.getResponseBody());
          }
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

  protected CompletableFuture<Response> postSubscription(RetrieveSchedule model) {
    CompletableFuture<Response> completableFuture = new CompletableFuture<>();
    //TODO - Add the logic to run the code.
    return completableFuture;
  }
}
