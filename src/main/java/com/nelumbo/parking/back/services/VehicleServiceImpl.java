package com.nelumbo.parking.back.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.nelumbo.parking.back.entities.Vehicle;
import com.nelumbo.parking.back.exceptions.BusinessException;
import com.nelumbo.parking.back.exceptions.RequestException;
import com.nelumbo.parking.back.repositories.IVehicleRepository;

@Service
public class VehicleServiceImpl implements IVehicleService {

	@Autowired
	private IVehicleRepository vehicleRepository;

	@Override
	public Optional<Vehicle> findById(Long id) {
		Optional<Vehicle> vehicle = vehicleRepository.findById(id);

		if (vehicle.isEmpty()) {
			throw new RequestException("El vehículo con ID: " + id + " no existe en la base de datos!");
		}
		return vehicle;
	}

	
	
	@Override
	public Vehicle create(Vehicle vehicle) {
		String plate = vehicle.getPlate();
		
		//this.invalidPlate(plate);
			
		if (this.exists(plate)) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "La placa del vehículo ya está registrada!");
		}
		return vehicleRepository.save(vehicle);
	}

	@Override
	public Vehicle findOneByPlate(String plate) {
		this.invalidPlate(plate);
		Vehicle vehicle = vehicleRepository.findOneByPlate(plate).orElseThrow(() ->  new RequestException("El vehículo con la placa: " + plate + " no existe en la base de datos!"));
		
		return vehicle;
	}

	@Override
	public List<Vehicle> findFirstTime() {
		List<Vehicle> vehicle = vehicleRepository.findFirstTime();

		return vehicle;
	}

	@Override
	public List<Vehicle> findRepeatedly() {
		List<Vehicle> vehicle = vehicleRepository.findRepeatedly();

		return vehicle;
	}


	@Override
	public boolean exists(String plate) {
	return vehicleRepository.findOneByPlate(plate).isPresent();
	}


	@Override
	public void invalidPlate(String plate) {
		if (plate == null || !plate.matches("^[A-Z0-9]*$")||plate.length()!=6||plate.equals("")) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "La placa del vehículo no tiene el formato correcto");
		}
	}


}
