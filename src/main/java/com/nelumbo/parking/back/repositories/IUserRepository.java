package com.nelumbo.parking.back.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.nelumbo.parking.back.models.dto.UserDTO;
import com.nelumbo.parking.back.models.entities.User;


public interface IUserRepository extends JpaRepository<User, Long> {

	public User findOneByName(String name);
	
	Optional<User> findByEmail(String email);

	
	@Query(value="select new com.nelumbo.parking.back.models.dto.UserDTO(u.name as name, u.email as email, u.role as rol) from User as u where u.user.iduser= :iduser ")
	public List<UserDTO> findAllByUserDTO(Long iduser);
}
