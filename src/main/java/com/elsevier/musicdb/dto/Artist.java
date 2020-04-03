package com.elsevier.musicdb.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Artist {
    @ApiModelProperty(hidden = true)
    private Long id;
    private String firstName = "";
    private String lastName = "";
    @ApiModelProperty(hidden = true)
    private List<Album> albums = new ArrayList<>();
}
