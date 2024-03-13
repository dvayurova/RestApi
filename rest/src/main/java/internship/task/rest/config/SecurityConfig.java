package internship.task.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder){
        UserDetails admin = User.builder().username("admin").password(encoder.encode("admin")).roles("ADMIN").build();
        UserDetails userPosts1 = User.builder().username("userPosts1").password(encoder.encode("userPosts1")).roles("POSTS_VIEWER").build();
        UserDetails userPosts2 = User.builder().username("userPosts2").password(encoder.encode("userPosts2")).roles("POSTS_EDITOR").build();
        UserDetails userUser1 = User.builder().username("userUser1").password(encoder.encode("userUser1")).roles("USERS_VIEWER").build();
        UserDetails userUser2 = User.builder().username("userUser2").password(encoder.encode("userUser2")).roles("USERS_EDITOR").build();
        UserDetails userAlbum1 = User.builder().username("userAlbum1").password(encoder.encode("userAlbum1")).roles("ALBUMS_VIEWER").build();
        UserDetails userAlbum2 = User.builder().username("userAlbum2").password(encoder.encode("userAlbum2")).roles("ALBUMS_EDITOR").build();
        return new InMemoryUserDetailsManager(admin, userPosts1, userPosts2, userUser1, userUser2, userAlbum1, userAlbum2);
    }

    @Bean PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        return httpSecurity.build();
    }
}
