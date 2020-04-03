package com.elsevier.musicdb.repository;


import com.elsevier.musicdb.entity.Album;
import com.elsevier.musicdb.entity.Artist;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * repository class for album table to do CRUD operations
 */
@Repository
public interface AlbumRepository extends PagingAndSortingRepository<Album, Long>, JpaSpecificationExecutor<Album> {
    /**
     * finds list of albums for given artist with given page specs
     *
     * @param artist artist for which we need to retrieve albums
     * @return albums created for given artist
     */
    Iterable<Album> findByArtist(Sort sort, Artist artist);
}
