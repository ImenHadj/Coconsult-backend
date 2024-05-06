package com.bezkoder.springjwt.repository;

import java.util.List;
import java.util.Optional;

import com.bezkoder.springjwt.models.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bezkoder.springjwt.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);


  Optional<User> findByEmail(String email);
  @Query("SELECT r.name, COUNT(u) FROM User u JOIN u.roles r GROUP BY r.name")
  List<Object[]> countUsersByRole();



  long countByRolesName(ERole role);
  @Query("SELECT DISTINCT u FROM User u JOIN u.roles r WHERE r.name = 'ROLE_PRODUCT_OWNER'")
  List<User> getByRole();
}
