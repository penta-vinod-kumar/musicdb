package com.elsevier.musicdb.client;

import com.elsevier.musicdb.entity.Album;
import com.elsevier.musicdb.entity.Artist;
import com.elsevier.musicdb.dto.DiscogsResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DiscogsClientTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private DiscogsClient discogsClient;

    @Test
    public void retriveResourceUrl() {
        ReflectionTestUtils.setField(discogsClient, "endpoint", "https://xyz.com");
        when(restTemplate.getForObject(any(), any())).thenReturn(new DiscogsResponse());
        String resourceUrl = discogsClient.retriveResourceUrl(createAlbum());
        assertNotNull(resourceUrl);
    }

    private Album createAlbum() {
        Album album = new Album();
        album.setTitle("waka waka");
        Artist artist = new Artist();
        artist.setLastName("Shakira");
        artist.setFirstName("Shakira");
        album.setArtist(artist);
        return album;
    }

}