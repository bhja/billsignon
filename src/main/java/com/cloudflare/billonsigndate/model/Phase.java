package com.cloudflare.billonsigndate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Phase {
  public ArrayList<Object> add_invoice_items;
  public Object application_fee_percent;
  public Object billing_cycle_anchor;
  public Object billing_thresholds;
  public Object collection_method;
  public Object coupon;
  public String currency;
  public Object default_payment_method;
  public ArrayList default_tax_rates;
  public Object description;
  public ArrayList discounts;
  public int end_date;
  public Object invoice_settings;
  public ArrayList<Item> items;
  public Metadata metadata;
  public Object on_behalf_of;
  public String proration_behavior;
  public int start_date;
  public Object transfer_data;
  public Object trial_end;
}
