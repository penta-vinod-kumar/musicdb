package com.elsevier.musicdb;

import com.elsevier.musicdb.dto.Artist;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * integrated test class for artists controller
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MusicdbApplication.class)
@AutoConfigureMockMvc
public class ArtistControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    /**
     * test post /artists call
     *
     * @throws Exception
     */
    @Test
    public void newArtist() throws Exception {
        mockMvc.perform(post("/artists").contentType(MediaType.APPLICATION_JSON).content(TestUtils.convertToJson(createArtist())))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", equalTo("Vinod")));
    }

    /**
     * test for put call
     *
     * @throws Exception
     */
    @Test
    public void saveOrUpdate() throws Exception {
        mockMvc.perform(post("/artists").contentType(MediaType.APPLICATION_JSON).content(TestUtils.convertToJson(createArtist())))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", equalTo("Vinod")));
        mockMvc.perform(put("/artists/5").contentType(MediaType.APPLICATION_JSON).content(TestUtils.convertToJson(createArtistPut())))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", equalTo("Chandu")));
    }

    /**
     * test for get call
     *
     * @throws Exception
     */
    @Test
    public void findAll() throws Exception {
        mockMvc.perform(get("/artists").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    private Artist createArtist() {
        Artist artist = new Artist();
        artist.setFirstName("Vinod");
        artist.setLastName("penta");
        return artist;
    }

    private Artist createArtistPut() {
        Artist artist = new Artist();
        artist.setFirstName("Chandu");
        artist.setLastName("penta");
        return artist;
    }

}