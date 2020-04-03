package com.elsevier.musicdb.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Genre {
    @Id
    @GeneratedValue
    @ApiModelProperty(hidden = true)
    private Long id;
    private String value;

    public String toString() {
        return value;
    }
}
