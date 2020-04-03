package com.elsevier.musicdb.repository;


import com.elsevier.musicdb.entity.Artist;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


/**
 * repository class for artist table to do CRUD operations
 */
@Repository
public interface ArtistRepository extends PagingAndSortingRepository<Artist, Long>, JpaSpecificationExecutor<Artist> {
}
