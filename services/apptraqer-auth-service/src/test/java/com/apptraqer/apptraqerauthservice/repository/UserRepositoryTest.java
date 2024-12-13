package com.apptraqer.apptraqerauthservice.repository;

import com.apptraqer.apptraqerauthservice.model.AtUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private AtUserRepository userRepository;

    @Test
    public void testCreateUser() {
        AtUser user = new AtUser();
        user.setUsername("testUser");
        user.setEmail("testuser@example.com");
        user.setOauthProvider("google");

        AtUser savedUser = userRepository.save(user);
        assertThat(savedUser.getId()).isNotNull(); // Check that the ID is generated (i.e., the user is saved)
    }
}
