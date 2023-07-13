package com.cloudflare.billonsigndate.utils;

import com.cloudflare.billonsigndate.model.RetrieveSchedule;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class CommonUtility {

  public static RetrieveSchedule getSubscriptionItem(String json) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(json, RetrieveSchedule.class);
  }
}
