package com.linkedin.openhouse.jobs.config;

import com.linkedin.openhouse.cluster.configs.YamlPropertySourceFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@EnableConfigurationProperties
@Configuration
@ConfigurationProperties(prefix = "jobs.spark")
@PropertySource(
    name = "jobs",
    value = "file:${OPENHOUSE_JOBS_CONFIG_PATH:/var/config/jobs.yaml}",
    factory = YamlPropertySourceFactory.class,
    ignoreResourceNotFound = true)
@Getter
@Setter
@ToString
public class JobsProperties {
  private String storageUri;
  private String metricsUri;
  private String authTokenPath;
  private String defaultEngine;
  private List<JobsEngineProperties> engines = new ArrayList<>();
  private List<JobLaunchConf> apps = new ArrayList<>();
}
