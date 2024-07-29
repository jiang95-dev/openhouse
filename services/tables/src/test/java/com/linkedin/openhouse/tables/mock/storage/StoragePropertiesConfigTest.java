package com.linkedin.openhouse.tables.mock.storage;

import com.linkedin.openhouse.cluster.storage.StorageManager;
import com.linkedin.openhouse.cluster.storage.configs.StorageProperties;
import com.linkedin.openhouse.tables.mock.properties.CustomClusterPropertiesInitializer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = CustomClusterPropertiesInitializer.class)
@Slf4j
public class StoragePropertiesConfigTest {
  @Autowired private StorageProperties storageProperties;

  @MockBean private StorageManager storageManager;

  @Autowired private ApplicationContext appContext;

  @Test
  public void tmp() {
    try {
      Object bean = appContext.getBean("storageManager");
      log.info("bean: {}", bean);
    } catch (Exception e) {
      log.error("ignore bean");
    }
  }

  private static final String DEFAULT_TYPE = "hdfs";

  private static final String DEFAULT_ENDPOINT = "hdfs://localhost:9000";

  private static final String ANOTHER_TYPE = "objectstore";

  private static final String ANOTHER_ENDPOINT = "http://localhost:9000";
  private static final String NON_EXISTING_TYPE = "non-existing-type";

  @Test
  public void testDefaultType() {
    Assertions.assertEquals(DEFAULT_TYPE, storageProperties.getDefaultType());
  }

  @Test
  public void testStorageTypeEndpoint() {
    Assertions.assertEquals(
        DEFAULT_ENDPOINT, storageProperties.getTypes().get(DEFAULT_TYPE).getEndpoint());
  }

  @Test
  public void testStorageTypeLookup() {
    Assertions.assertEquals(
        ANOTHER_ENDPOINT, storageProperties.getTypes().get(ANOTHER_TYPE).getEndpoint());
  }

  @Test
  public void testStorageTypeVariableProperties() {
    Assertions.assertFalse(
        storageProperties.getTypes().get(DEFAULT_TYPE).getParameters().isEmpty());
  }

  @Test
  public void testUnsetPropertiesAreNull() {
    Assertions.assertNull(storageProperties.getTypes().get(NON_EXISTING_TYPE));
  }

  @AfterAll
  static void unsetSysProp() {
    System.clearProperty("OPENHOUSE_CLUSTER_CONFIG_PATH");
  }
}
