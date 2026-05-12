package com.linkedin.openhouse.tables.api.spec.v0.request;

import com.google.gson.GsonBuilder;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@EqualsAndHashCode
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchTablesRequestBody {

  @Schema(
      description =
          "Optional list of GetTableResponseBody field names to populate in the response. "
              + "If empty or omitted, only identifier columns (databaseId, tableId) are returned. "
              + "Supported values: \"tableLocation\".",
      example = "[\"tableLocation\"]")
  List<String> columns;

  public String toJson() {
    return new GsonBuilder().serializeNulls().create().toJson(this);
  }
}
