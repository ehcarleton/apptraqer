package com.apptraqer.apptraqerauthservice.repository;

import com.apptraqer.apptraqerauthservice.model.AtUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtUserRepository extends JpaRepository<AtUser, Long> {
    AtUser findByEmail(String email);

    AtUser findByUsername(String username);
}
