package com.elsevier.musicdb.dto;

import com.elsevier.musicdb.entity.Artist;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Album {

    @ApiModelProperty(hidden = true)
    private Long id;
    private String title;
    private String yearOfRelease;
    private List<String> genres = new ArrayList<>();
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Artist artist;
    @ApiModelProperty(hidden = true)
    private String resourceUrl;
}
