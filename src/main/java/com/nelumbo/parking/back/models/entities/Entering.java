package com.nelumbo.parking.back.models.entities;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
@Table(name="entering")
public class Entering implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long identering;
	
	@PrePersist
	public void prePersist() {
		date= new Date();
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="_date")
	private Date date;
	
	@OneToOne
	@JoinColumn(name="idvehicle")
	private Vehicle vehicle;
	
	@ManyToOne
	@JoinColumn(name="idparking")
    private Parking parking;
	

	public Entering(Date date, Vehicle vehicle, Parking parking) {
		super();
		this.vehicle = vehicle;
		this.parking = parking;
	}

	public Entering() {
		super();
	}
	
	
	
}
