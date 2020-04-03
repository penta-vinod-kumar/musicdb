package com.elsevier.musicdb.repository;

import com.elsevier.musicdb.entity.Genre;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GenreRepositoryTest {


    @Autowired
    TestEntityManager entityManager;

    @Autowired
    GenreRepository genreRepository;

    @Test
    public void it_should_save_genre() {
        Genre genre = new Genre();
        genre.setValue("test");
        genre = entityManager.persistAndFlush(genre);
        assertEquals(genreRepository.findById(genre.getId()).get(), genre);
    }

}