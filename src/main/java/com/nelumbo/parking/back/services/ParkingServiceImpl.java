package com.nelumbo.parking.back.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.nelumbo.parking.back.DTO.ParkingDTO;
import com.nelumbo.parking.back.DTO.ParkingVehicleDTO;
import com.nelumbo.parking.back.DTO.VehicleEntDTO;
import com.nelumbo.parking.back.DTO.VehicleRankDTO;
import com.nelumbo.parking.back.entities.Entering;
import com.nelumbo.parking.back.entities.History;
import com.nelumbo.parking.back.entities.Parking;
import com.nelumbo.parking.back.entities.User;
import com.nelumbo.parking.back.entities.Vehicle;
import com.nelumbo.parking.back.exceptions.BusinessException;
import com.nelumbo.parking.back.exceptions.RequestException;
import com.nelumbo.parking.back.repositories.IParkingRepository;
import com.nelumbo.parking.back.servicesDTO.ParkingVehicleDTOMapper;
import com.nelumbo.parking.back.servicesDTO.ParkingDTOMapper;
import com.nelumbo.parking.back.servicesDTO.ParkingEntityMapper;

@Service
public class ParkingServiceImpl implements IParkingService {

	@Autowired
	private IParkingRepository parkingRepository;
	
	@Autowired
	private IUserService userService;

	@Autowired
	private IEnteringService enteringService;
	
	@Autowired
	private IHistoryService historyService;
	
	@Autowired
	private IVehicleService vehicleService;

	@Autowired
	private ParkingVehicleDTOMapper parkingVehicleDTOMapper;

	@Autowired
	private ParkingEntityMapper parkingEntityMapper;
	
	@Autowired
	private ParkingDTOMapper parkingDTOMapper;

	@Override
	public void delete(Long id) {
		
		
		
		
		if (parkingRepository.findById(id).isEmpty()) {
			throw new RequestException("El parqueadero a eliminar no existe!");
		}
		
		
		List<Entering> enterings=parkingRepository.enteringsByIdParking(id);
		List<History> histories = parkingRepository.historiesByIdParking(id);
		enteringService.deleteAllByList(enterings);
		historyService.deleteAllByList(histories);
		
		parkingRepository.deleteById(id);
	}

	@Override
	public Parking update(Parking newParking, Long id) {
		
		Optional<ParkingDTO> parkingDb = this.findDTOByID(id);
		
		parkingDb.get().setName(newParking.getName());
		parkingDb.get().setSpots(newParking.getSpots());

		return this.create(parkingDb.get());
	}

	@Override
	public List<ParkingDTO> findAllByUser(Long iduser) {
		
		userService.findById(iduser);
	
		List<ParkingDTO> parkings = parkingRepository.findAllDTOByUser(iduser);
		if (parkings.isEmpty()) {
			throw new RequestException("El usuario no posee parqueaderos asociados");
		
		}
		return parkings;
		
	}

	//eliminar las validaciones por campos
	public Parking create(ParkingDTO parking) {
		if (parking.getName()==(null)||parking.getName().equals("")) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "El nombre del parqueadero es requerido!");
		}
		
		if (parking.getSpots() < 0) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "La capacidad del parqueadero debe ser mayor a cero!");
		}
        
		Optional<ParkingDTO> parkingMap = Optional.of(parking);

		return parkingRepository.save(parkingMap.map(parkingEntityMapper).get());
	}
	
     
	@Override
	public List<VehicleEntDTO> entVehicleDetails(Long id) {
		
		this.findById(id);
		
		List<VehicleEntDTO> vehiclesEntDTO = parkingRepository.getVehiclesEnteringByIdParking(id);
		if(vehiclesEntDTO.isEmpty()) {
			throw new RequestException("No existen registros de entrada para el parqueadero con ID: "+id);
		}
		return vehiclesEntDTO;
		

	}


	@Override
	public Parking findById(Long idParking) {
		Optional<Parking> parking = parkingRepository.findById(idParking);
		if (parking.isEmpty()) {
			throw new RequestException("Parqueadero no encontrado");
		}
		return parkingRepository.findById(idParking).get();
	
	}

	
	

	@Override
	public Optional<ParkingDTO> findDTOByID(Long id) {
		
		Optional<ParkingDTO> parking = parkingRepository.findDTOById(id);
		if (parking.isEmpty()) {
			throw new RequestException("Parqueadero no encontrado");
		}
		return parking;
	}

	@Override
	public List<ParkingVehicleDTO> findAllByUserInd(Long iduser) {
		List<Parking> parking = parkingRepository.findAllByUser_iduser(iduser);
		if (parking.isEmpty()) {
			throw new RequestException("El usuario no posee parqueaderos asociados");
		}
		return parking.stream().map(parkingVehicleDTOMapper).collect(Collectors.toList());
	}

	
	//usar el vehiculosEnt DTO para vehiculos
	@Override
	public List<Vehicle> vehiclesByParkingInd(Long idparking) {
		List<Entering> enterings = enteringService.findAllByParking_idparkingInd(idparking);
		List<Vehicle> vehicles = new ArrayList<>();
		for (Entering entering : enterings) {
			vehicles.add(entering.getVehicle());
		}
		return vehicles;
	}

	@Override
	public Parking updateDTO(ParkingDTO newParking, Long id) {
		Parking p =this.findById(id); 
		if(p.getUser()==null) {
			p.setUser(new User());
		}
		Optional<ParkingDTO> parkingDb =Optional.of(p).map(parkingDTOMapper);

		parkingDb.get().setName(newParking.getName());
		parkingDb.get().setSpots(newParking.getSpots());
		
		return this.create(parkingDb.get());
	}

	
	
	@Override
	public VehicleEntDTO vehicleById(Long idvehicle) {
	
		vehicleService.findById(idvehicle);
		Optional<VehicleEntDTO> vehicleEntDTO= parkingRepository.getVehicleEnteringByIdVehicle(idvehicle);
		if(vehicleEntDTO.isEmpty()) {
			throw new RequestException("El vehículo con ID: "+idvehicle+" no se encuentra en ningún parqueadero");
		}
		
		return vehicleEntDTO.get();
	
	}

	@Override
	public List<Vehicle> vehiclesFirstTime() {
		
		return vehicleService.findFirstTime();
	}

	@Override
	public List<Vehicle> vehiclesRepeatedly() {
		return vehicleService.findRepeatedly();
	}

	@Override
	public List<VehicleRankDTO> rankVehicles(Long id) {
		this.findById(id);
		List<VehicleRankDTO> vehicles = parkingRepository.findRank(id);
		if (vehicles.isEmpty()) {
			throw new RequestException("El parqueadero no tiene registros");
		}
		
		return vehicles;
	}

	@Override
	public Long averageUsageByid(Date min, Date max, Long idparking, int days) {
		this.findById(idparking);
		Long average=parkingRepository.averageUsageByid(min, max, idparking,days);
		if(average<=0) {
			throw new BusinessException(HttpStatus.OK, "El promedio es menor a 1 vehículo por día");
		}
		return parkingRepository.averageUsageByid(min, max, idparking,days);
	}

	@Override
	public Date parseDate(String date) {
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy"); 
		try {
			return formato.parse(date);
		} catch (ParseException e) { 
			throw new BusinessException(HttpStatus.BAD_REQUEST, "Error en el formato de fecha");
		}
	}

	@Override
	public int getDays(Date min, Date max) {
		int days = (int) ((max.getTime() - min.getTime())/86400000);
		if(days<=0) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "La diferencia de días debe ser mayor a 0");
		}
		return days;
	}

	@Override
	public Long averageUsageAll(Date min, Date max, int days) {
		
		return parkingRepository.averageUsageAll(min, max, days);
	}

	@Override
	public Long averageHoursById(Long idparking) {
		this.findById(idparking);
		Long average =parkingRepository.averageUsageHours(idparking);
		if(average==null||average<=0) {
			throw new RequestException("Aún no se puede calcular el promedio de horas para este parqueadero");
		}
		return average;
	}

	

	@Override
	public Parking updateWhitUser(Parking newParking, Long id) {
		
		return parkingRepository.save(this.findById(id));
	    
	}



}
