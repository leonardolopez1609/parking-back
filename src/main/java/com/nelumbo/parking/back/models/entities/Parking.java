package com.nelumbo.parking.back.models.entities;

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
@Table(name = "parking")
public class Parking implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idparking;

	@Column(name = "name", nullable = false, length = 100, unique = true)
	@NotNull(message = "El nombre es requerido")
	@NotBlank(message = "El nombre es requerido")
	private String name;

	@ManyToOne
	@JoinColumn(name = "iduser")
	private User user;

	@Column(name = "all_spots")
	@NotNull(message = "La capacidad es requerida")
	private int allSpots;

	@Column(name = "spots_takken")
	@NotNull(message = "La capacidad es requerida")
	private int spotsTaken;

	public Parking(String name, int allSpots) {
		super();
		this.name = name;
		this.allSpots = allSpots;
	}

	public Parking() {
		super();
	}

	public Parking(Long idparking, String name, User user, int allSpots, int spotsTaken) {
		super();
		this.idparking = idparking;
		this.name = name;
		this.user = user;
		this.allSpots = allSpots;
		this.spotsTaken = spotsTaken;
	}

}
