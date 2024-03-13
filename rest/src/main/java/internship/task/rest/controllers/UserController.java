package internship.task.rest.controllers;

import internship.task.rest.models.User;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com";

    private final RestTemplate restTemplate;

    public UserController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USERS_VIEWER', 'USERS_EDITOR')")
    public User getUser(@PathVariable Long userId) {
        String url = BASE_URL + "/users/" + userId;
        return restTemplate.getForObject(url, User.class);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USERS_EDITOR')")
    public User createUser(@RequestBody String user) {
        String url = BASE_URL + "/users";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(user, headers);
        return restTemplate.postForObject(url, request, User.class);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USERS_EDITOR')")
    public User updateUser(@PathVariable Long userId, @RequestBody String user) {
        String url = BASE_URL + "/users/" + userId;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(user, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, request, User.class).getBody();
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USERS_EDITOR')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        String url = BASE_URL + "/users/" + userId;
        restTemplate.delete(url);
        return ResponseEntity.noContent().build();
    }
}
