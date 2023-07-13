package com.cloudflare.billonsigndate.repository;

import com.cloudflare.billonsigndate.entity.CustomerInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionInfoRepo
    extends JpaRepository<CustomerInfo, Integer> {

  /**
   * Retrieve all the records for the batchDate irrespective of time.
   *
   * @param batchDate
   * @return list of objects of type {@link CustomerInfo}
   */
  @Query(value = "SELECT * FROM CUSTOMER_INFO WHERE DATE(NEXT_INVOICE_DATE) <= to_date(:batchDate, 'yyyy-MM-dd')",
      nativeQuery = true)
  List<CustomerInfo> getSubscriptionListByDate0(String batchDate);

  @Query(value = "SELECT * FROM CUSTOMER_INFO WHERE CAST(NEXT_INVOICE_DATE as DATE) <= to_date(:batchDate, " +
      "'yyyy-MM-dd') ",
      nativeQuery = true)
  Page<CustomerInfo> getSubscriptionListByDate(String batchDate, Pageable pageable);


}
