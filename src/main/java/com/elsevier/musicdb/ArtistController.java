package com.elsevier.musicdb;

import com.elsevier.musicdb.entity.Artist;
import com.elsevier.musicdb.service.ArtistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/artists")
@Api(value = "artist controller")
public class ArtistController {

    private final ArtistService artistService;
    private final ModelMapper modelMapper;

    public ArtistController(ArtistService artistService, ModelMapper modelMapper) {
        this.artistService = artistService;
        this.modelMapper = modelMapper;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @ApiOperation(value = "Add a new artist")
    public com.elsevier.musicdb.dto.Artist newArtist(@ApiParam(value = "new artist details") @RequestBody com.elsevier.musicdb.dto.Artist artist) {
        return convertToDto(artistService.saveArtist(convertToEntity(artist)));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "updated existing artist details")
    public com.elsevier.musicdb.dto.Artist saveOrUpdate(@ApiParam(value = "new Artist details") @RequestBody com.elsevier.musicdb.dto.Artist newArtist, @ApiParam(value = "ArtistId for which details needs to be updated") @PathVariable Long id) {
        return convertToDto(artistService.saveOrUpdate(convertToEntity(newArtist), id));
    }

    @GetMapping
    @ApiOperation(value = "retrieves list of artists. Can be filtered with artist first/last name")
    public Page<com.elsevier.musicdb.dto.Artist> findAll(
            @And({@Spec(path = "lastName", spec = LikeIgnoreCase.class),
                    @Spec(path = "firstName", spec = LikeIgnoreCase.class)}) Specification<Artist> spec,
            @RequestParam(value = "firstName", required = false) String firstName, @RequestParam(value = "lastName", required = false) String lastName,
            @ApiParam(value = "Query param for 'pageNumber'") @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @ApiParam(value = "Query param for 'pageSize'") @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @ApiParam(value = "Query param for 'sortDir' criteria eg: asc/dsc") @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir,
            @ApiParam(value = "Query param for 'sort' criteria eg: firstName/lastName") @RequestParam(value = "sort", defaultValue = "lastName", required = false) String sort) {
        return artistService.findAll(spec, pageNumber, pageSize, sortDir, sort).map(this::convertToDto);
    }

    private com.elsevier.musicdb.dto.Artist convertToDto(Artist artist) {
        return modelMapper.map(artist, com.elsevier.musicdb.dto.Artist.class);
    }

    private Artist convertToEntity(com.elsevier.musicdb.dto.Artist artist) {
        return modelMapper.map(artist, Artist.class);
    }
}
