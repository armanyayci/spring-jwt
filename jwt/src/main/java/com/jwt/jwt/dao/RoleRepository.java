package com.jwt.jwt.dao;

import com.jwt.jwt.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer>
{
    Optional<Role> findByName(String name);
}
