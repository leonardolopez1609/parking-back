package com.nelumbo.parking.back.security.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nelumbo.parking.back.entities.Token;
import com.nelumbo.parking.back.repositories.ITokenRepository;

@Service
public class TokenServiceImpl implements ITokenService{

	@Autowired
	ITokenRepository tokenRepository;
	
	@Override
	public List<Token> findAllValidTokenByUser(Long id) {
		return tokenRepository.findAllValidTokenByUser(id);
	}

	@Override
	public Optional<Token> findByToken(String token) {
		
		return tokenRepository.findByToken(token);
	}

	@Override
	public Token save(Token token) {
		return tokenRepository.save(token);
	}

	@Override
	public void saveAll(List<Token> validUserTokens) {
		tokenRepository.saveAll(validUserTokens);
		
	}

}
