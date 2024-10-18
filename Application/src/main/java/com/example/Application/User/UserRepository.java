package com.example.Application.User;
import com.example.Application.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    @Query("SELECT s FROM User s WHERE s.email = ?1")
    Optional<User> findUserByEmail(String email);

    @Query("SELECT u.email FROM User u WHERE u.email = ?1")
    Optional<String> findEmailByEmail(String email);

}
