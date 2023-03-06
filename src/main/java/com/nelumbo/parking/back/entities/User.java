package com.nelumbo.parking.back.entities;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="_user")
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long iduser;
	
	@Column(name="name", nullable = true, length = 100, unique=true)
	private String name;
	
	 @Enumerated(EnumType.STRING)
	 @Column(name="_role")
	  private Role role;

	public User(String name) {
		super();
		this.name = name;
	}

	public User() {
		super();
	}
	
	

}
