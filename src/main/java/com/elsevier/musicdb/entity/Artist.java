package com.elsevier.musicdb.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Artist {
    @Id
    @GeneratedValue
    @ApiModelProperty(hidden = true)
    private Long id;
    private String firstName = "";
    private String lastName = "";
    @OneToMany
    @JoinColumn(referencedColumnName = "ID", name = "ALBUM_ID")
    @ApiModelProperty(hidden = true)
    private List<Album> albums = new ArrayList<>();
}
