package com.cloudflare.billonsigndate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RetrieveSchedule {
  private String id;
  private String object;
  private Object application;

  @JsonProperty("canceled_at")
  private Object canceledAt;

  @JsonProperty("completed_at")
  private Object completedAt;
  private int created;
  private CurrentPhase current_phase;
  private String customer;
  private DefaultSettings default_settings;
  private String end_behavior;
  private boolean livemode;
  private Metadata metadata;
  private ArrayList<Phase> phases;
  private Object prebilling;
  private Object released_at;
  private Object released_subscription;
  private String status;
  private String subscription;
  private Object test_clock;
}
