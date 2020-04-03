package com.elsevier.musicdb.repository;

import com.elsevier.musicdb.entity.Artist;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ArtistRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    ArtistRepository artistRepository;

    @Test
    public void it_should_save_artist() {
        Artist artist = new Artist();
        artist.setFirstName("test");
        artist.setLastName("test");
        artist = entityManager.persistAndFlush(artist);
        assertEquals(artistRepository.findById(artist.getId()).get(), artist);
    }

}