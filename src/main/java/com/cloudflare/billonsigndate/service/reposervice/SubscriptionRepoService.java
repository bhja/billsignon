package com.cloudflare.billonsigndate.service.reposervice;

import com.cloudflare.billonsigndate.entity.CustomerInfo;
import com.cloudflare.billonsigndate.model.SubscriptionModel;
import com.cloudflare.billonsigndate.repository.SubscriptionInfoRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionRepoService {
  private final Logger log = LoggerFactory.getLogger(SubscriptionRepoService.class);
  @Autowired
  SubscriptionInfoRepo subscriptionInfoRepo;

  @Transactional
  public List<SubscriptionModel> getSubscriptionListBySignInDate(String batchDate) {
    try {
      List<CustomerInfo> customerInformation = subscriptionInfoRepo.getSubscriptionListByDate0(batchDate);
      if (!customerInformation.isEmpty()) {
        return
            customerInformation.stream().
                               map(customerInfo -> new
                                   SubscriptionModel(customerInfo.getSubscriptionScheduleId(),
                                                     customerInfo.getLastInvoiceDate(),
                                                     customerInfo.getNextInvoiceDate())).collect(Collectors.toList());
      } else {
        log.debug("No results for the date {}", batchDate);
      }
    } catch (Exception e) {
      log.error("Error fetching the records for the given date {} due to {}", batchDate, e.getMessage());
    }
    return new ArrayList<>();
  }
}
