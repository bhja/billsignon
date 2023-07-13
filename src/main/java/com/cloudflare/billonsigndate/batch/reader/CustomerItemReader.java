package com.cloudflare.billonsigndate.batch.reader;

import com.cloudflare.billonsigndate.config.AppInfo;
import com.cloudflare.billonsigndate.entity.CustomerInfo;
import com.cloudflare.billonsigndate.repository.SubscriptionInfoRepo;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;


@Component
public class CustomerItemReader
    extends RepositoryItemReader<CustomerInfo> {


  public CustomerItemReader(SubscriptionInfoRepo repository, AppInfo appInfo) {
    super();
    setRepository(repository);
    setPageSize(appInfo.getPageSize());
    setSort(Collections.singletonMap("id", Sort.Direction.ASC));
    setMethodName("getSubscriptionListByDate");
    setArguments(Collections.singletonList(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now())));
  }


}
