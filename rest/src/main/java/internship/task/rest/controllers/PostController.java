package internship.task.rest.controllers;

import internship.task.rest.models.Post;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com";

    private final RestTemplate restTemplate;

    public PostController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{postId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'POSTS_VIEWER', 'POSTS_EDITOR')")
    public Post getPost(@PathVariable Long postId) {
        String url = BASE_URL + "/posts/" + postId;
        return restTemplate.getForObject(url, Post.class);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'POSTS_EDITOR')")
    public Post createPost(@RequestBody String post) {
        String url = BASE_URL + "/posts";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(post, headers);
        return restTemplate.postForObject(url, request, Post.class);
    }

    @PutMapping("/{postId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'POSTS_EDITOR')")
    public Post updatePost(@PathVariable Long postId, @RequestBody String post) {
        String url = BASE_URL + "/posts/" + postId;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(post, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, request, Post.class).getBody();

    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'POSTS_EDITOR')")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        String url = BASE_URL + "/posts/" + postId;
        restTemplate.delete(url);
        return ResponseEntity.noContent().build();
    }
}
