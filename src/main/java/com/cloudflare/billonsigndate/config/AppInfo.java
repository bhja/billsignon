package com.cloudflare.billonsigndate.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Data
@ConfigurationProperties(prefix = "com.cloudflare")
@Configuration
public class AppInfo
    implements WebMvcConfigurer {
  private String updateSubscriptionItemUrl;
  private String getSubscriptionItemUrl;
  private String subscriptionScheduleApiKey;
  private String Authorization;
  private String cronExpression;
  private String threadPrefix;
  private Integer corePoolSize;
  private Integer maxPoolSize;
  private Integer queueCapacity;

}
