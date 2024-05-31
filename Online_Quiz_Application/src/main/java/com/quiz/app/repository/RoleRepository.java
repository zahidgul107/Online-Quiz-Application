package com.quiz.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quiz.app.entity.Role;
import com.quiz.app.enums.ERole;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(ERole roleAdmin);

}
