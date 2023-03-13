package com.nelumbo.parking.back.models.entities;

import java.io.Serializable;

import com.nelumbo.parking.back.customvalidators.ValuePlate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="vehicle")
public class Vehicle implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idvehicle;	
	
	@Column(name="plate", nullable = true, length = 6 ,unique=true)
	@ValuePlate(message="El formato de la placa es invalido")
	private String plate;

	public Vehicle(String plate) {
		super();
		this.plate = plate;
	}

	public Vehicle() {
		super();
	}
	
	
	
	
}
