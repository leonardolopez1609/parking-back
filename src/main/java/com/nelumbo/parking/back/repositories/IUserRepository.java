package com.nelumbo.parking.back.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nelumbo.parking.back.models.entities.User;

public interface IUserRepository extends JpaRepository<User, Long> {

	public User findOneByName(String name);
	
	Optional<User> findByEmail(String email);
}
