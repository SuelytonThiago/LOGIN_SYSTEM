package com.example.loginSystem.domain.repositories;

import com.example.loginSystem.domain.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Roles,Long> {
}
