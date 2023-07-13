package com.cloudflare.billonsigndate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionModel {
  private String subscriptionScheduleId;
  private Date lastInvoiceDate;
  private Date nextInvoiceDate;
}
