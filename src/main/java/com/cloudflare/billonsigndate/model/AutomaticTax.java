package com.cloudflare.billonsigndate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AutomaticTax {
  public boolean enabled;
}
