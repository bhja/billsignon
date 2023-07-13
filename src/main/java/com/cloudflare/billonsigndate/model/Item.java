package com.cloudflare.billonsigndate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
  public Object billing_thresholds;
  public ArrayList discounts;
  public Metadata metadata;
  public String price;
  public int quantity;
  public ArrayList tax_rates;
}
