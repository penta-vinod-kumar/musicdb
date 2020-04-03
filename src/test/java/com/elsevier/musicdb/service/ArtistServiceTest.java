package com.elsevier.musicdb.service;

import com.elsevier.musicdb.entity.Artist;
import com.elsevier.musicdb.repository.ArtistRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArtistServiceTest {

    @Mock
    private ArtistRepository artistRepository;

    @InjectMocks
    private ArtistService artistService;

    @Test
    public void saveArtist() {
        Artist artist = createArtist(10L);
        when(artistRepository.save(any())).thenReturn(artist);
        Artist saveArtist = artistService.saveArtist(artist);
        assertEquals("response is not as expected", artist.getFirstName(), saveArtist.getFirstName());
    }

    @Test
    public void saveOrUpdate() {
        Artist artist = createArtist(10L);
        when(artistRepository.findById(anyLong())).thenReturn(Optional.of(artist));
        when(artistRepository.save(any())).thenReturn(artist);
        Artist saveArtist = artistService.saveOrUpdate(artist, 1L);
        assertEquals("response is not as expected", Long.valueOf(10), saveArtist.getId());
    }

    @Test
    public void saveOrUpdateWhenInvalidId() {
        Artist artist = createArtist(10L);
        when(artistRepository.findById(1L)).thenReturn(Optional.empty());
        when(artistRepository.save(any())).thenReturn(artist);
        Artist saveArtist = artistService.saveOrUpdate(artist, 1L);
        assertEquals("response is not as expected", Long.valueOf(1), saveArtist.getId());
    }

    @Test
    public void findAll() {
        when(artistRepository.findAll(any(), any(Pageable.class))).thenReturn(new PageImpl<Artist>(new ArrayList<>()));
        Page<Artist> all = artistService.findAll(null, 1, 50, "asc", "firstName");
        Assert.assertEquals("response is not as expected", 0, all.getContent().size());
    }

    private Artist createArtist(long id) {
        Artist artist = new Artist();
        artist.setId(id);
        artist.setLastName("Shakira");
        artist.setFirstName("Shakira");
        return artist;
    }
}