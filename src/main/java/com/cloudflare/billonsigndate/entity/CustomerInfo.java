package com.cloudflare.billonsigndate.entity;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "CUSTOMER_INFO")
@Data
public class CustomerInfo {
  @Id
  private int id;
  @Column(name = "subscription_schedule_id")
  private String subscriptionScheduleId;
  @Column(name = "bdom")
  private Integer bDom;

  @Column(name = "last_invoice_date")
  private Date lastInvoiceDate;
  @Column(name = "next_invoice_date")
  private Date nextInvoiceDate;
  @CreationTimestamp
  @Column(name = "created_on")
  private Date createdOn;
  @UpdateTimestamp
  @Column(name = "updated_on")
  private Date updatedOn;
}
