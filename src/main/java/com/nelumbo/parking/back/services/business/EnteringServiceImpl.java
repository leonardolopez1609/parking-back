package com.nelumbo.parking.back.services.business;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.nelumbo.parking.back.exceptions.BusinessException;
import com.nelumbo.parking.back.exceptions.RequestException;
import com.nelumbo.parking.back.models.dto.EnteringDTO;
import com.nelumbo.parking.back.models.entities.Entering;
import com.nelumbo.parking.back.models.entities.Parking;
import com.nelumbo.parking.back.models.entities.Vehicle;
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
	public EnteringDTO findDTOById(Long id) {
		
		return enteringRepository.findOneDTOById(id).orElseThrow(()-> new RequestException("El registro de entrada con ID: " + id + " no existe en la base de datos!"));
	}

	@Override
	public Entering create(Long idParking, String plate) {
         
		if (this.vehicleIsPresent(plate)) {
			throw new BusinessException(HttpStatus.BAD_REQUEST,
					"El vehiculo de placa: " + plate + " ya tiene registrado un ingreso!");
		}
         
		
		Parking parking = parkingService.findById(idParking);
		if(parking.getUser()==null){
			throw new RequestException("Parqueadero no encontrado");
		}
		//----------------------------------------------------------------------------------------------------------------
		if (parking.getSpotsTaken()>= parking.getAllSpots()) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "El parqueadero no posee cupos disponibles!");
		}
		//----------------------------------------------------------------------------------------------------------------
		
		 if(!vehicleService.exists(plate)) {
			 vehicleService.create( new Vehicle(plate));
		 }
		
		 //---------------------------------------------
		 parking.setSpotsTaken(parking.getSpotsTaken() + 1);
		 //---------------------------------------------
			//parkingService.update(parking, idParking);
			Entering ent = new Entering();
			ent.setParking(parking);
		ent.setVehicle(vehicleService.findOneByPlate(plate));
		return enteringRepository.save(ent);
	}

	@Override
	public Entering findOneByPlate(String plate) {
		Entering entering = enteringRepository.findOneByVehicle_plate(plate).orElseThrow(() ->  new RequestException(
				"No se puede registar salida, no existe la placa"));
		
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

	@Override
	public List<Entering> findAll() {
		return enteringRepository.findAll();
	}

	@Override
	public Entering findById(Long id) {
		
		return enteringRepository.findById(id).orElse(null);
	}

	@Override
	public Entering findByIdVehicle(Long idvehicle) {
		
		return enteringRepository.findOneByVehicle_idvehicle(idvehicle).orElseThrow(() ->  new RequestException(
				"El vehiculo no se encuentra en ningun parqueadero"));
	}

	

}
