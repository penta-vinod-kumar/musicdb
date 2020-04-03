package com.elsevier.musicdb.service;

import com.elsevier.musicdb.entity.Artist;
import com.elsevier.musicdb.repository.ArtistRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * service class for artist controller
 */
@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    /**
     * saves artist
     *
     * @param artist new artist details
     * @return saved artist
     */
    public Artist saveArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    /**
     * updates existing artist details
     *
     * @param newArtist artist details which needs to be updated
     * @param id        extisting artist id
     * @return updated artist details
     */
    public Artist saveOrUpdate(Artist newArtist, Long id) {
        return artistRepository.findById(id).map(artist -> {
            artist.setFirstName(newArtist.getFirstName());
            artist.setLastName(newArtist.getLastName());
            return artistRepository.save(artist);
        }).orElseGet(() -> {
            newArtist.setId(id);
            return artistRepository.save(newArtist);
        });
    }

    /**
     * retrieves all artist list from db
     *
     * @param spec       Specification for filtering
     * @param pageNumber page number
     * @param pageSize   number of items per page
     * @param sortDir    sorting order eg: asc/dsc
     * @param sort       sorting field name
     * @return list of artists after filtering
     */
    public Page<Artist> findAll(Specification<Artist> spec, Integer pageNumber, Integer pageSize, String sortDir, String sort) {
        Pageable paging = PageRequest.of(pageNumber, pageSize, "asc".equalsIgnoreCase(sortDir) ? Sort.by(sort).ascending() : Sort.by(sort).descending());
        return artistRepository.findAll(spec, paging);
    }
}
