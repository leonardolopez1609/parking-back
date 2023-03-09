package com.nelumbo.parking.back.entities;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name="parking")
public class Parking implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idparking;
	
	@Column(name="name", nullable = true, length = 100, unique=true)
	@NotNull(message = "El nombre es requerido")
	@NotBlank(message = "El nombre es requerido")
	private String name;
	
	@ManyToOne
	@JoinColumn(name="iduser")
	private User user;
	
	@Column(name="spots")
	@NotNull(message = "La capacidad es requerida")
	private int spots;
	
	public Parking(String name, int spots) {
		super();
		this.name = name;
		this.spots = spots;
	}

	public Parking() {
		super();
	}

	public Parking(Long idparking, String name, User user, int spots) {
		super();
		this.idparking = idparking;
		this.name = name;
		this.user = user;
		this.spots = spots;
	}
	
	

}
