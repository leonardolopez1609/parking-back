package com.nelumbo.parking.back.security.services;

import java.util.List;
import java.util.Optional;

import com.nelumbo.parking.back.entities.Token;

public interface ITokenService {
	List<Token> findAllValidTokenByUser(Long id);

	 Optional<Token> findByToken(String token);

	Token save(Token token);

	void saveAll(List<Token> validUserTokens);
}

