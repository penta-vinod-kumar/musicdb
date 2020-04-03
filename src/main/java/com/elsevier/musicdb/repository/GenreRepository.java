package com.elsevier.musicdb.repository;


import com.elsevier.musicdb.entity.Album;
import com.elsevier.musicdb.entity.Genre;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


/**
 * repository class for Genre table to do CRUD operations
 */
@Repository
public interface GenreRepository extends PagingAndSortingRepository<Genre, Long>, JpaSpecificationExecutor<Album> {
}
