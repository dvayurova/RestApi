package internship.task.rest.controllers;

import internship.task.rest.models.Album;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com";

    private final RestTemplate restTemplate;

    public AlbumController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{albumId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ALBUMS_VIEWER', 'ALBUMS_EDITOR')")
    public Album getAlbum(@PathVariable Long albumId){
        String url = BASE_URL + "/albums/" + albumId;
        return restTemplate.getForObject(url, Album.class);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ALBUMS_EDITOR')")
    public Album createAlbum(@RequestBody String album){
        String url = BASE_URL + "/albums";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(album, headers);
        return restTemplate.postForObject(url, request, Album.class);
    }

    @PutMapping("/{albumId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ALBUMS_EDITOR')")
    public Album updateAlbum(@PathVariable Long albumId, @RequestBody String album){
        String url = BASE_URL + "/albums/" + albumId;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(album, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, request, Album.class).getBody();
    }

    @DeleteMapping("/{albumId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ALBUMS_EDITOR')")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long albumId){
        String url = BASE_URL + "/albums/" + albumId;
        restTemplate.delete(url);
        return ResponseEntity.noContent().build();
    }
}
