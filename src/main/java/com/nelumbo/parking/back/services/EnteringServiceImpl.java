package com.nelumbo.parking.back.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.nelumbo.parking.back.entities.Entering;
import com.nelumbo.parking.back.entities.Parking;
import com.nelumbo.parking.back.entities.Vehicle;
import com.nelumbo.parking.back.exceptions.BusinessException;
import com.nelumbo.parking.back.exceptions.RequestException;
import com.nelumbo.parking.back.repositories.IEnteringRepository;

@Service
public class EnteringServiceImpl implements IEnteringService {

	@Autowired
	private IEnteringRepository enteringRepository;

	@Autowired
	private IParkingService parkingService;

	@Autowired
	private IVehicleService vehicleService;

	@Override
	public Optional<Entering> findById(Long id) {
		Optional<Entering> entering = enteringRepository.findById(id);
		if (entering.isEmpty()) {
			throw new RequestException("El registro de entrada con ID: " + id + " no existe en la base de datos!");
		}
		return entering;
	}

	@Override
	public Entering create(Long idParking, String plate) {

		if (this.vehicleIsPresent(plate)) {
			throw new BusinessException(HttpStatus.BAD_REQUEST,
					"El vehiculo de placa: " + plate + " ya tiene registrado un ingreso!");
		}
         
		Parking parking = parkingService.findById(idParking);
		vehicleService.invalidPlate(plate);
		if (parking.getSpots() <= 0) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "El parqueadero no posee cupos disponibles!");
		}
		parking.setSpots(parking.getSpots() - 1);
		parkingService.update(parking, idParking);
		Entering ent = new Entering();
		ent.setParking(parking);
		
		 if(!vehicleService.exists(plate)) {
			 vehicleService.create(new Vehicle(plate));
		 }
		ent.setVehicle(vehicleService.findOneByPlate(plate).get());
		return enteringRepository.save(ent);
	}

	@Override
	public Optional<Entering> findOneByPlate(String plate) {
		Optional<Entering> entering = enteringRepository.findOneByVehicle_plate(plate);
		if (entering.isEmpty()) {
			throw new RequestException(
					"El registro de entrada con vehículo de placa: " + plate + " no existe en la base de datos!");
		}
		return entering;
	}

	@Override
	public boolean vehicleIsPresent(String plate) {
		Optional<Entering> entering = enteringRepository.findOneByVehicle_plate(plate);
		return entering.isPresent();

	}

	@Override
	public void deleteByID(Long id) {
		Optional<Entering> entering = enteringRepository.findById(id);
		if (entering.isEmpty()) {
			throw new RequestException("La entrada a eliminar no existe en la base de datos!");
		}

		enteringRepository.deleteById(id);
	}

	
	
	@Override
	public List<Entering> findAllByParking_idparking(Long idparking) {
		List<Entering> enterings = enteringRepository.findByParking_idparking(idparking);
		if (enterings.isEmpty()) {
			throw new RequestException("No existen registros de entrada para el parqueadero con ID: " + idparking);
		}

		return enterings;
	}

	
	
	@Override
	public List<Entering> findAllByParking_idparkingInd(Long idparking) {
		List<Entering> enterings = enteringRepository.findByParking_idparking(idparking);

		return enterings;
	}

	@Override
	public void deleteAllByList(List<Entering> enterings) {
		enteringRepository.deleteAll(enterings);
		
	}

	

}
