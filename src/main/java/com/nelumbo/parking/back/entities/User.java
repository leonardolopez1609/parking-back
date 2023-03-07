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
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="_user")
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long iduser;
	
	
	@Column(name="name", nullable = true, length = 100, unique=true)
	@NotNull(message = "El nombre es requerido")
	@NotBlank(message = "El nombre es requerido")
	private String name;
	
	 @Enumerated(EnumType.STRING)
	 @Column(name="_role")
	 @NotNull(message = "El Rol es requerido")
	  private Role role;

	public User(String name) {
		super();
		this.name = name;
	}

	public User() {
		
	}
	
	

}
