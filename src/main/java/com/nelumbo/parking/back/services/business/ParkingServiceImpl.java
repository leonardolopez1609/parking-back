package com.nelumbo.parking.back.services.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.nelumbo.parking.back.exceptions.BusinessException;
import com.nelumbo.parking.back.exceptions.RequestException;
import com.nelumbo.parking.back.models.dto.ParkingDTO;
import com.nelumbo.parking.back.models.dto.ParkingVehicleDTO;
import com.nelumbo.parking.back.models.dto.TimeHoursDTO;
import com.nelumbo.parking.back.models.dto.VehicleEntDTO;
import com.nelumbo.parking.back.models.dto.VehicleRankDTO;
import com.nelumbo.parking.back.models.entities.Entering;
import com.nelumbo.parking.back.models.entities.History;
import com.nelumbo.parking.back.models.entities.Parking;
import com.nelumbo.parking.back.models.entities.User;
import com.nelumbo.parking.back.models.entities.Vehicle;
import com.nelumbo.parking.back.repositories.IParkingRepository;
import com.nelumbo.parking.back.services.dto.ParkingDTOMapper;
import com.nelumbo.parking.back.services.dto.ParkingEntityMapper;
import com.nelumbo.parking.back.services.dto.ParkingVehicleDTOMapper;
import com.nelumbo.parking.back.services.dto.VehicleEntMapper;
import com.nelumbo.parking.back.services.security.JwtService;

import jakarta.servlet.http.HttpServletRequest;

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
	
	@Autowired
	private VehicleEntMapper vehicleEntMapper;

	@Autowired
	private JwtService jwtService;
	
	@Override
	public void delete(Long id) {
		
		if (parkingRepository.findById(id).isEmpty()) {
			throw new RequestException("El parqueadero a eliminar no existe");
		}
		
		List<Entering> enterings=parkingRepository.enteringsByIdParking(id);
		List<History> histories = parkingRepository.historiesByIdParking(id);
		enteringService.deleteAllByList(enterings);
		historyService.deleteAllByList(histories);
		
		parkingRepository.deleteById(id);
	}

	@Override
	public Parking update(Parking newParking, Long id) {
		
		ParkingDTO parkingDb = this.findDTOByID(id);
		
		parkingDb.setName(newParking.getName());
		parkingDb.setSpots(newParking.getSpots());

		return this.create(parkingDb);
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


	
	public Parking create(ParkingDTO parking) {
		if (parking.getSpots() < 0) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "La capacidad del parqueadero debe ser mayor a cero");
		}
        
		return parkingRepository.save(Optional.of(parking).map(parkingEntityMapper).get());
	}
	 
     
	@Override
	public List<VehicleEntDTO> entVehicleDetails(Long id) {
		
		this.findById(id);
		List<VehicleEntDTO> vehiclesEntDTO = parkingRepository.getVehiclesEnteringByIdParking(id);
		
		return vehiclesEntDTO;
	}


	@Override
	public Parking findById(Long idParking) {
		
		return parkingRepository.findById(idParking).orElseThrow(()->new RequestException("Parqueadero no encontrado"));	
	}

    
	@Override
	public ParkingDTO findDTOByID(Long id) {
			if(this.findById(id).getUser()==null) {
				return Optional.of(this.findById(id)).map(parkingDTOMapper).get();
			}
		return parkingRepository.findDTOById(id).orElseThrow(()->new RequestException("Parqueadero no encontrado"));
	}

	@Override
	public List<ParkingVehicleDTO> findAllByUserInd(Long iduser) {
		
		List<Parking> parking = parkingRepository.findAllByUser_iduser(iduser);
		
		return parking.stream().map(parkingVehicleDTOMapper).collect(Collectors.toList());
	}

	
	
	@Override
	public List<VehicleEntDTO> vehiclesByParkingInd(Long idparking) {
		List<VehicleEntDTO> enterings = enteringService.findAllByParking_idparkingInd(idparking).stream().map(vehicleEntMapper)
				.collect(Collectors.toList());
		
		return enterings;
	}

	@Override
	public ParkingDTO updateDTO(ParkingDTO newParking, Long id) {
		ParkingDTO p = this.findDTOByID(id);

		p.setName(newParking.getName());
		p.setSpots(newParking.getSpots());
		
		return Optional.of(this.create(p)).map(parkingDTOMapper).get();
	}

	
	
	@Override
	public VehicleEntDTO vehicleById(Long idvehicle) {
	
		vehicleService.findById(idvehicle);
		VehicleEntDTO vehicleEntDTO= parkingRepository.getVehicleEnteringByIdVehicle(idvehicle).orElseThrow(()->new RequestException("El vehículo con ID: "+idvehicle+" no se encuentra en ningún parqueadero"));
		
		return vehicleEntDTO;
	
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
	public TimeHoursDTO averageHoursById(Long idparking) {
		this.findById(idparking);
		
		Long average =parkingRepository.averageUsageHours(idparking);
		if(average==null) {
			throw new BusinessException(HttpStatus.OK, "El parqueadero no posee registros");
		}
		 Long hour = (average / 3600);
		 Long  min = ((average - hour * 3600) / 60);
		 Long  sec = average - (hour * 3600 + min * 60);  
	
		return new TimeHoursDTO(hour,min,sec);
	}

	

	@Override
	public Parking updateWhitUser(Parking newParking, Long id) {
		
		return parkingRepository.save(this.findById(id));
	    
	}

	@Override
	public List<ParkingDTO> findAll() {
		List<Parking> parkings=parkingRepository.findAll();
		if(parkings.isEmpty()) {
			throw new RequestException("No existe ningún parqueadero");
		}
		return parkings.stream().map(parkingDTOMapper).collect(Collectors.toList());
	}

	@Override
	public Parking findOneByName(String name) {
		return parkingRepository.findOneByName(name).orElseThrow(() ->  new RequestException("Parqueadero no encontrado"));
	}

	

	@Override
	public Long averageUsageAllByUser(Date dateMin2, Date dateMax2, int days, Long iduser) {
		
		Long average= parkingRepository.averageUsageAllByUser(dateMin2, dateMax2, days, iduser);
		
		if(average<=0) {
			throw new BusinessException(HttpStatus.OK, "El promedio es menor a 1 vehículo por día");
		}
		return average;
	}

	@Override
	public void parkingAccessIdUserFilter(HttpServletRequest request, Long iduser) {
		 final String authHeader = request.getHeader("Authorization");
		 final String jwt;
		    
		jwt = authHeader.substring(7);
		User user=userService.findById(iduser);    
        if(!user.getEmail().equals(jwtService.extractUsername(jwt))) {
        	throw new BusinessException(HttpStatus.FORBIDDEN, "Acceso no autorizado");
        }
		
	}

	@Override
	public List<ParkingVehicleDTO> findAllWithVehiclesInd() {
         List<Parking> parkings = parkingRepository.findAll();
		
		return parkings.stream().map(parkingVehicleDTOMapper).collect(Collectors.toList());
	}

}
