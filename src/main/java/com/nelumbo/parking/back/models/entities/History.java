package com.nelumbo.parking.back.models.entities;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
@Table(name="history")
public class History implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idhistory;
	
	@PrePersist
	public void prePersist() {
		departureDate= new Date();
	}
	
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name="entering_date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date enteringDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="departure_date")
	private Date departureDate;
	
	@ManyToOne
	@JoinColumn(name="idparking")
    private Parking parking;
	
	@ManyToOne
	@JoinColumn(name="idvehicle")
	private Vehicle vehicle;

	public History(Date enteringDate, Date departureDate, Parking parking, Vehicle vehicle) {
		super();
		this.enteringDate = enteringDate;
		this.departureDate = departureDate;
		this.parking = parking;
		this.vehicle = vehicle;
	}

	public History() {
		super();
	}
	
	
}
