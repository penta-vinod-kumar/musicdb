package com.elsevier.musicdb.client;

import com.elsevier.musicdb.dto.DiscogsResponse;
import com.elsevier.musicdb.dto.Result;
import com.elsevier.musicdb.entity.Album;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * client class connecting discogs api to get more details about album
 */
@Component
public class DiscogsClient {

    private final RestTemplate restTemplate;

    private String endpoint;
    private Map<String, String> params = new HashMap<>();

    public DiscogsClient(@Value("${discogs.endpointURL}") String endpoint,
                         @Value("${discogs.key}") String key,
                         @Value("${discogs.secret}") String secret,
                         @Value("${discogs.type}") String type, RestTemplate restTemplate) {
        this.endpoint = endpoint;
        params.put("key", key);
        params.put("secret", secret);
        params.put("type", type);
        this.restTemplate = restTemplate;
    }


    /**
     * retrieves resource url for given album from discogs api
     *
     * @param album album for which we need resource url
     * @return resource Url if album found in discogs api. or else returns empty string
     */
    public String retriveResourceUrl(Album album) {
        params.put("q", album.getArtist().getFirstName() + album.getArtist().getLastName() + "-" + album.getTitle());
        final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(endpoint);
        params.forEach(uriBuilder::queryParam);
        DiscogsResponse response = restTemplate.getForObject(uriBuilder.build().encode().toUri(), DiscogsResponse.class);
        return response.getResults().stream().findAny().map(Result::getResourceUrl).orElse(" ");
    }
}
