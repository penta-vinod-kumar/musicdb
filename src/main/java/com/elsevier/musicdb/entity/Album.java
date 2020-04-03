package com.elsevier.musicdb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Album {
    @Id
    @GeneratedValue
    @ApiModelProperty(hidden = true)
    private Long id;
    private String title;
    private String yearOfRelease;
    @OneToMany
    @JoinColumn(name = "GENRE_ID", referencedColumnName = "ID")
    private List<Genre> genres = new ArrayList<>();
    @ApiModelProperty(hidden = true)
    @ManyToOne
    @JoinColumn(name = "ARTIST_ID", referencedColumnName = "ID")
    @JsonIgnore
    private Artist artist;
    @ApiModelProperty(hidden = true)
    private String resourceUrl;
}

