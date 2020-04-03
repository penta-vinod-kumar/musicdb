package com.elsevier.musicdb.service;

import com.elsevier.musicdb.client.DiscogsClient;
import com.elsevier.musicdb.entity.Album;
import com.elsevier.musicdb.entity.Artist;
import com.elsevier.musicdb.repository.AlbumRepository;
import com.elsevier.musicdb.repository.ArtistRepository;
import com.elsevier.musicdb.repository.GenreRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AlbumServiceTest {

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private ArtistRepository artistRepository;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private DiscogsClient discogsClient;

    @InjectMocks
    AlbumService albumService;

    @Test
    public void saveAlbumWhenArtistIdIsValid() {
        Album album = createNewAlbum();
        when(artistRepository.findById(10L)).thenReturn(Optional.of(createArtist(10L)));
        when(albumRepository.save(any())).thenReturn(album);
        when(artistRepository.save(any())).thenReturn(createArtist(10L));
        Album response = albumService.saveAlbum(album, 10L);
        Assert.assertEquals("response album is not as expected", album.getTitle(), response.getTitle());
    }

    @Test
    public void saveAlbumWhenArtistIdIsNotValid() {
        Album album = createNewAlbum();
        when(artistRepository.findById(10L)).thenReturn(Optional.empty());
        Album response = albumService.saveAlbum(album, 10L);
        Assert.assertNull("response album is not as expected", response.getTitle());
    }

    @Test
    public void saveOrUpdate() {
        Album album = createNewAlbum();
        when(albumRepository.findById(1L)).thenReturn(Optional.of(album));
        when(albumRepository.save(any())).thenReturn(album);
        Album response = albumService.saveOrUpdate(album, 1L);
        Assert.assertEquals("response album is not as expected", album.getTitle(), response.getTitle());
    }

    @Test
    public void saveOrUpdateWhenIdIsNotValid() {
        Album album = createNewAlbum();
        when(albumRepository.findById(1L)).thenReturn(Optional.empty());
        when(albumRepository.save(any())).thenReturn(album);
        Album response = albumService.saveOrUpdate(album, 1L);
        Assert.assertEquals("response album is not as expected", album.getTitle(), response.getTitle());
    }

    @Test
    public void findAll() {
        when(artistRepository.findById(10L)).thenReturn(Optional.of(createArtist(10L)));
        when(albumRepository.findByArtist(any(), any())).thenReturn(new PageImpl<Album>(Arrays.asList(new Album())));
        List<Album> albums = albumService.findAll(null, "asc", "firstName", 10L);
        Assert.assertEquals("response is not as expected", 1, albums.size());
    }

    @Test
    public void findAllWhenArtistIdInvalid() {
        when(artistRepository.findById(10L)).thenReturn(Optional.empty());
        List<Album> albums = albumService.findAll(null, "asc", "firstName", 10L);
        Assert.assertEquals("response is not as expected", 0, albums.size());
    }

    @Test
    public void findById() {
        Album album = createNewAlbum();
        when(albumRepository.findById(1L)).thenReturn(Optional.of(album));
        when(discogsClient.retriveResourceUrl(any())).thenReturn("https://api.discogs.com");
        Album albumServiceById = albumService.findById(1L);
        Assert.assertEquals("response is not as expected", "https://api.discogs.com", albumServiceById.getResourceUrl());
    }

    @Test
    public void findByIdWhenAlbumIdInvalid() {
        Album album = createNewAlbum();
        when(albumRepository.findById(1L)).thenReturn(Optional.empty());
        Album albumServiceById = albumService.findById(1L);
        Assert.assertNull("response is not as expected", albumServiceById.getResourceUrl());
    }

    private Artist createArtist(long id) {
        Artist artist = new Artist();
        artist.setId(id);
        artist.setLastName("Shakira");
        artist.setFirstName("Shakira");
        return artist;
    }

    private Album createNewAlbum() {
        Album album = new Album();
        album.setId(1L);
        album.setTitle("Waka waka");
        return album;
    }

}