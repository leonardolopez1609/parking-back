package com.nelumbo.parking.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nelumbo.parking.back.entities.User;

public interface IUserRepository extends JpaRepository<User, Long> {

	public User findOneByName(String name);
}
