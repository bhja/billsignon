package com.cloudflare.billonsigndate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DefaultSettings {
  public Object application_fee_percent;
  public AutomaticTax automatic_tax;
  public String billing_cycle_anchor;
  public Object billing_thresholds;
  public String collection_method;
  public Object default_payment_method;
  public Object description;
  public InvoiceSettings invoice_settings;
  public Object on_behalf_of;
  public Object transfer_data;
}
