package com.elsevier.musicdb;

import com.elsevier.musicdb.entity.Album;
import com.elsevier.musicdb.entity.Artist;
import com.elsevier.musicdb.entity.Genre;
import com.elsevier.musicdb.repository.AlbumRepository;
import com.elsevier.musicdb.repository.ArtistRepository;
import com.elsevier.musicdb.repository.GenreRepository;
import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

@SpringBootApplication
public class MusicdbApplication implements WebMvcConfigurer {
    @Resource
    private ArtistRepository artistRepository;

    @Resource
    private AlbumRepository albumRepository;

    @Resource
    private GenreRepository genreRepository;

    public static void main(String[] args) {
        SpringApplication.run(MusicdbApplication.class, args);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new SpecificationArgumentResolver());
        argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(10000);
        clientHttpRequestFactory.setReadTimeout(10000);
        return new RestTemplate(clientHttpRequestFactory);
    }
    @Bean
    public Artist initiateDb() {
        Artist artist1 = new Artist();
        artist1.setFirstName("Shakira ");
        Album album = new Album();
        album.setTitle("Introducing Shakira");
        album.setYearOfRelease("2002");
        album = albumRepository.save(album);
        artist1.getAlbums().add(album);
        Album album1 = new Album();
        album1.setTitle("Waka Waka / Did It Again   Part 1");
        album1.setYearOfRelease("2010");
        Genre genre = new Genre();
        genre.setValue("Electronic");
        genre = genreRepository.save(genre);
        album1.getGenres().add(genre);
        album1 = albumRepository.save(album1);
        artist1.getAlbums().add(album1);
        album.setArtist(artist1);
        album1.setArtist(artist1);
        artistRepository.save(artist1);
        albumRepository.save(album);
        albumRepository.save(album1);
        return artist1;
    }

}
