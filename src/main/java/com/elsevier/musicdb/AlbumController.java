package com.elsevier.musicdb;

import com.elsevier.musicdb.entity.Album;
import com.elsevier.musicdb.entity.Genre;
import com.elsevier.musicdb.service.AlbumService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/artists/{artistId}/albums")
@ApiOperation(value = "endpoint to manage Album data. Required information: title, year of release, genres (list of tags).")
public class AlbumController {

    private final AlbumService albumService;

    private final ModelMapper modelMapper;

    public AlbumController(AlbumService albumService, ModelMapper modelMapper) {
        this.albumService = albumService;
        this.modelMapper = modelMapper;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @ApiOperation(value = "add a new album to an existing artist")
    public com.elsevier.musicdb.dto.Album saveAlbum(@RequestBody com.elsevier.musicdb.dto.Album album, @PathVariable(value = "artistId") Long artistId) {
        return convertToDto(albumService.saveAlbum(convertToEntity(album), artistId));
    }

    @PutMapping("/{albumId}")
    @ApiOperation(value = "update an existing album")
    public com.elsevier.musicdb.dto.Album saveOrUpdate(@RequestBody com.elsevier.musicdb.dto.Album newAlbum, @PathVariable(value = "albumId") Long albumId) {
        return convertToDto(albumService.saveOrUpdate(convertToEntity(newAlbum), albumId));
    }

    @GetMapping
    @ApiOperation(value = "lists all albums by the given artist. Implement: \n filtering by genre(s) \n sorting by album name and release year (asc/desc)")
    public List<com.elsevier.musicdb.dto.Album> findAll(
            @RequestParam(value = "genre", required = false) String genre,
            @ApiParam(value = "Query param for 'sortDir' criteria eg: asc/dsc") @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir,
            @ApiParam(value = "Query param for 'sort' criteria eg: yearOfRelease/title") @RequestParam(value = "sort", defaultValue = "yearOfRelease", required = false) String sort,
            @PathVariable(value = "artistId") Long artistId) {
        List<com.elsevier.musicdb.dto.Album> list = new ArrayList<>();
        albumService.findAll(genre, sortDir, sort, artistId).forEach(album -> list.add(convertToDto(album)));
        return list;
    }

    @GetMapping("/{albumId}")
    @ApiOperation(value = "retrieves album details and resource url from discogs api")
    public com.elsevier.musicdb.dto.Album findById(@PathVariable(value = "albumId") Long albumId) {
        return convertToDto(albumService.findById(albumId));
    }


    /**
     * converts entity album to dto model
     *
     * @param album entity object
     * @return converted dto object
     */
    private com.elsevier.musicdb.dto.Album convertToDto(Album album) {
        com.elsevier.musicdb.dto.Album albumdto = modelMapper.map(album, com.elsevier.musicdb.dto.Album.class);
        albumdto.setGenres(album.getGenres().stream().map(Genre::getValue).collect(Collectors.toList()));
        albumdto.setResourceUrl(album.getResourceUrl());
        return albumdto;
    }

    /**
     * converts dto to entity
     *
     * @param album dto object
     * @return converted entity object
     */
    private Album convertToEntity(com.elsevier.musicdb.dto.Album album) {
        Album albumEntity = modelMapper.map(album, Album.class);
        albumEntity.setGenres(album.getGenres().stream().map(genre -> {
            Genre genreEntity = new Genre();
            genreEntity.setValue(genre);
            return genreEntity;
        }).collect(Collectors.toList()));
        return albumEntity;
    }
}