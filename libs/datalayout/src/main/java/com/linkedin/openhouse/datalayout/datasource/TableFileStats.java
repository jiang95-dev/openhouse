package com.linkedin.openhouse.datalayout.datasource;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;

/** Data source implementation for table file statistics. */
@Builder
public class TableFileStats implements DataSource<FileStat> {
  private final SparkSession spark;
  private final String tableName;

  @Override
  public Dataset<FileStat> get() {
    StructType fileSchema =
        spark.sql(String.format("SELECT * FROM %s.data_files", tableName)).schema();
    try {
      fileSchema.apply("partition");
      return spark
          .sql(
              String.format(
                  "SELECT file_path, file_size_in_bytes, partition FROM %s.data_files", tableName))
          .map(new FileStatMapper(), Encoders.bean(FileStat.class));
    } catch (IllegalArgumentException e) {
      return spark
          .sql(
              String.format(
                  "SELECT file_path, file_size_in_bytes, null FROM %s.data_files", tableName))
          .map(new FileStatMapper(), Encoders.bean(FileStat.class));
    }
  }

  static class FileStatMapper implements MapFunction<Row, FileStat> {
    @Override
    public FileStat call(Row row) {
      List<String> partitionValues = new ArrayList<>();
      Row partition = row.getStruct(2);
      if (partition != null) {
        for (int i = 0; i < partition.size(); i++) {
          partitionValues.add(Objects.toString(partition.get(i)));
        }
      }
      return FileStat.builder()
          .path(row.getString(0))
          .size(row.getLong(1))
          .partitionValues(partitionValues)
          .build();
    }
  }
}
