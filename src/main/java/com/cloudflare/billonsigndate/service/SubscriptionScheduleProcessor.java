package com.cloudflare.billonsigndate.service;

import com.cloudflare.billonsigndate.config.AppInfo;
import com.cloudflare.billonsigndate.model.Response;
import com.cloudflare.billonsigndate.model.RetrieveSchedule;
import com.cloudflare.billonsigndate.model.SubscriptionModel;
import com.cloudflare.billonsigndate.service.reposervice.SubscriptionRepoService;
import com.cloudflare.billonsigndate.utils.CommonUtility;
import com.cloudflare.billonsigndate.utils.HttpUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
public class SubscriptionScheduleProcessor {
  private Logger log = LoggerFactory.getLogger(SubscriptionScheduleProcessor.class);
  @Autowired
  private SubscriptionRepoService subscriptionRepoService;
  @Autowired
  private HttpUtility httpUtility;
  @Autowired
  private AppInfo appInfo;
  @Autowired
  @Qualifier("jobExecutor")
  private Executor executor;


  public void executeSubscriptionScheduleProcess() {
    try {
      String batchDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
      List<SubscriptionModel> subscriptionList = subscriptionRepoService.getSubscriptionListBySignInDate(batchDate);
      log.info("Customer List from DB  {}", subscriptionList);
      subscriptionList.forEach(subscriptionInfo -> {
        CompletableFuture.supplyAsync(() ->
                                      {
                                        try {
                                          return getSubscriptionItem(subscriptionInfo);
                                        } catch (IOException | InterruptedException e) {
                                          throw new RuntimeException(e);
                                        }
                                      }, executor).whenComplete((val, error) -> {
          if (error == null) {
            if (val.getCanceledAt() != null) {
              try {
                updateSubscriptionItem(subscriptionInfo);
              } catch (Exception e) {
                throw new RuntimeException(e);
              }
            }
          } else {
            log.error("Could not update the account details due to " + error.getMessage());
          }
        });
      });
    } catch (Exception ex) {
      log.error("Error due to {}", ex.getMessage());
    }
  }

  protected int updateSubscriptionItem(SubscriptionModel subscriptionInfo) throws Exception {
    log.debug("customerInfo {} ", subscriptionInfo);
    Response responseObject = null;
    if (subscriptionInfo != null) {
      Map<String, String> header = new HashMap<>();
      Map<String, Object> body = new HashMap<>();
      header.put("apiKey", appInfo.getSubscriptionScheduleApiKey());
      body.put("prebilling[iterations]", "1");
      try {
        responseObject = httpUtility.post(appInfo.getUpdateSubscriptionItemUrl() + subscriptionInfo.getSubscriptionScheduleId(), body, header);
      } catch (IOException | InterruptedException ex) {
        throw ex;
      }
    }
    return responseObject.getStatusCode();
  }

  protected RetrieveSchedule getSubscriptionItem(SubscriptionModel customer) throws IOException, InterruptedException {
    Map<String, String> header = new HashMap<>();
    header.put("Authorization", appInfo.getAuthorization());
    String responseObject = httpUtility.get(appInfo.getGetSubscriptionItemUrl() + customer.getSubscriptionScheduleId(), "", header);
    return CommonUtility.getSubscriptionItem(responseObject);
  }
}
