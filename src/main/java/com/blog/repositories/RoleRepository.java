package com.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.models.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	

}
