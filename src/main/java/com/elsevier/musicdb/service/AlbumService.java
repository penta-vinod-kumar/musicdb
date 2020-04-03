package com.elsevier.musicdb.service;

import com.elsevier.musicdb.client.DiscogsClient;
import com.elsevier.musicdb.entity.Album;
import com.elsevier.musicdb.entity.Genre;
import com.elsevier.musicdb.repository.AlbumRepository;
import com.elsevier.musicdb.repository.ArtistRepository;
import com.elsevier.musicdb.repository.GenreRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * service class for album controller
 */
@Service
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final GenreRepository genreRepository;
    private final DiscogsClient discogsClient;

    public AlbumService(AlbumRepository albumRepository, ArtistRepository artistRepository, GenreRepository genreRepository, DiscogsClient discogsClient) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.genreRepository = genreRepository;
        this.discogsClient = discogsClient;
    }

    /**
     * saves album under existing artist. If artist doesn't exit returns empty album object
     *
     * @param newAlbum album which needs to save
     * @param artistId arist id
     * @return saved album details.
     */
    public Album saveAlbum(Album newAlbum, Long artistId) {
        return artistRepository.findById(artistId).map(artist -> {
            List<Genre> genreList = newAlbum.getGenres().stream().map(genreRepository::save).collect(Collectors.toList());
            newAlbum.setGenres(genreList);
            newAlbum.setArtist(artist);
            Album album = albumRepository.save(newAlbum);
            artist.getAlbums().add(album);
            artistRepository.save(artist);
            return album;
        }).orElse(new Album());
    }

    /**
     * updates existing album details
     *
     * @param newAlbum new album details
     * @param albumId  album id which needs to be updated
     * @return updated album details
     */
    public Album saveOrUpdate(Album newAlbum, Long albumId) {
        return albumRepository.findById(albumId).map(album -> {
            album.setYearOfRelease(newAlbum.getYearOfRelease());
            album.setTitle(newAlbum.getTitle());
            album.setGenres(newAlbum.getGenres().stream().map(genreRepository::save).collect(Collectors.toList()));
            return albumRepository.save(album);
        }).orElseGet(() -> {
            newAlbum.setId(albumId);
            newAlbum.setGenres(newAlbum.getGenres().stream().map(genreRepository::save).collect(Collectors.toList()));
            return albumRepository.save(newAlbum);
        });
    }

    /**
     * retrieves all album list for given artist
     *
     * @param genre
     * @param sortDir  sorting order eg:  asc/dsc
     * @param sort     on which field sorting needs
     * @param artistId artist id
     * @return list of albums with given sorting order
     */
    public List<Album> findAll(String genre, String sortDir, String sort, Long artistId) {
        List<Album> albumList = new ArrayList<>();
        return artistRepository.findById(artistId).map(artist -> {
            Sort order = "asc".equalsIgnoreCase(sortDir) ? Sort.by(sort).ascending() : Sort.by(sort).descending();
            for (Album album : albumRepository.findByArtist(order, artist)) {
                if (StringUtils.isBlank(genre) || album.getGenres().stream().map(Genre::getValue).collect(Collectors.toList()).contains(genre)) {
                    albumList.add(album);
                }
            }
            return albumList;
        }).orElse(albumList);
    }

    /**
     * retrieves album details for given id
     *
     * @param albumId album id
     * @return album details with resourceUrl
     */
    public Album findById(Long albumId) {
        return albumRepository.findById(albumId).map(album -> {
            album.setResourceUrl(discogsClient.retriveResourceUrl(album));
            return album;
        }).orElse(new Album());
    }
}
