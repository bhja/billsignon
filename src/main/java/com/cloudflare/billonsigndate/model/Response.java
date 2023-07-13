package com.cloudflare.billonsigndate.model;

import lombok.Data;

@Data
public class Response {

  private int statusCode;
  private String responseBody;
}
