package com.linkedin.openhouse.jobs.scheduler.tasks;

import com.linkedin.openhouse.jobs.client.JobsClient;
import com.linkedin.openhouse.jobs.client.TablesClient;
import com.linkedin.openhouse.jobs.client.model.JobConf;
import com.linkedin.openhouse.jobs.util.DirectoryMetadata;
import java.util.Arrays;
import java.util.List;

/** A task to remove orphan table directories. */
public class OrphanTableDirectoryDeletionTask extends TableDirectoryOperationTask {
  public static final JobConf.JobTypeEnum OPERATION_TYPE =
      JobConf.JobTypeEnum.ORPHAN_DIRECTORY_DELETION;

  public OrphanTableDirectoryDeletionTask(
      JobsClient jobsClient,
      TablesClient tablesClient,
      DirectoryMetadata metadata,
      long pollIntervalMs,
      long queuedTimeoutMs,
      long taskTimeoutMs) {
    super(jobsClient, tablesClient, metadata, pollIntervalMs, queuedTimeoutMs, taskTimeoutMs);
  }

  public OrphanTableDirectoryDeletionTask(
      JobsClient jobsClient, TablesClient tablesClient, DirectoryMetadata metadata) {
    super(jobsClient, tablesClient, metadata);
  }

  @Override
  public JobConf.JobTypeEnum getType() {
    return OPERATION_TYPE;
  }

  @Override
  protected List<String> getArgs() {
    return Arrays.asList("--tableDirectoryPath", metadata.getPath().toString());
  }

  @Override
  protected boolean shouldRun() {
    return true;
  }
}
