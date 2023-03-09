package com.nelumbo.parking.back.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nelumbo.parking.back.entities.Token;

public interface ITokenRepository extends JpaRepository<Token, Long>{

	@Query(value = """
		      select t from Token t inner join User u\s
		      on t.user.iduser = u.iduser\s
		      where u.iduser = :id and (t.expired = false or t.revoked = false)\s
		      """)
		  List<Token> findAllValidTokenByUser(Long id);

		  Optional<Token> findByToken(String token);
}
