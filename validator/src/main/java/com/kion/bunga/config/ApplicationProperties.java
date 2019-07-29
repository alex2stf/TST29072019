package com.kion.bunga.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = true)
public class ApplicationProperties {

  private String debugEnabled;

  public String getDebugEnabled() {
    return debugEnabled;
  }

  public void setDebugEnabled(String debugEnabled) {
    this.debugEnabled = debugEnabled;
  }

  public boolean isDebugEnabled(){
    return "true".equals(debugEnabled);
  }
}
