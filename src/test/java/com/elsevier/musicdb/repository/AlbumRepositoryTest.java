package com.elsevier.musicdb.repository;

import com.elsevier.musicdb.entity.Album;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AlbumRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    AlbumRepository albumRepository;

    @Test
    public void it_should_save_album() {
        Album album = new Album();
        album.setTitle("test album");
        album = entityManager.persistAndFlush(album);
        assertEquals(albumRepository.findById(album.getId()).get(), album);
    }


}