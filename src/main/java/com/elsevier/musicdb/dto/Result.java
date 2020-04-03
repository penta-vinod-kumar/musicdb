package com.elsevier.musicdb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Result {
    @JsonProperty("resource_url")
    private String resourceUrl;
}
