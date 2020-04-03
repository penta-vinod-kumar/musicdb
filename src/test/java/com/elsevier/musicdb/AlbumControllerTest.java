package com.elsevier.musicdb;

import com.elsevier.musicdb.dto.Album;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MusicdbApplication.class)
@AutoConfigureMockMvc
public class AlbumControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Test
    public void saveAlbum() throws Exception {
        mockMvc.perform(post("/artists/4/albums").contentType(MediaType.APPLICATION_JSON).content(TestUtils.convertToJson(createAlbum())))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", equalTo("Sale El Sol")));
    }


    @Test
    public void saveOrUpdate() throws Exception {
        mockMvc.perform(post("/artists/4/albums").contentType(MediaType.APPLICATION_JSON).content(TestUtils.convertToJson(createAlbum())))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", equalTo("Sale El Sol")));
        mockMvc.perform(put("/artists/4/albums/8").contentType(MediaType.APPLICATION_JSON).content(TestUtils.convertToJson(createAlbum())))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", equalTo("Sale El Sol")));
    }

    @Test
    public void findAll() throws Exception {
        mockMvc.perform(get("/artists/4/albums").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void findById() throws Exception {
        mockMvc.perform(get("/artists/4/albums/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.resourceUrl", Matchers.notNullValue()));
    }

    private Album createAlbum() {
        Album album = new Album();
        album.setGenres(Arrays.asList("fun", "Electronic"));
        album.setTitle("Sale El Sol");
        album.setYearOfRelease("2010");
        return album;
    }
}