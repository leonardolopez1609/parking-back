package com.nelumbo.parking.back.services.business;

import com.nelumbo.parking.back.models.dto.ParkingDTO;
import com.nelumbo.parking.back.models.dto.ParkingVehicleDTO;
import com.nelumbo.parking.back.models.dto.TimeHoursDTO;
import com.nelumbo.parking.back.models.dto.VehicleEntDTO;
import com.nelumbo.parking.back.models.dto.VehicleRankDTO;
import com.nelumbo.parking.back.models.entities.Parking;
import com.nelumbo.parking.back.models.entities.Vehicle;
import java.util.Date;
import java.util.List;
public interface IParkingService {

	Parking findById(Long idParking);
	
	Parking findOneByName(String name);
	
	List<ParkingDTO> findAllByUser(Long iduser);
	
	ParkingDTO findDTOByID(Long id);
	
	List<ParkingVehicleDTO> findAllByUserInd(Long iduser);
	
	List<VehicleEntDTO> vehiclesByParkingInd(Long idparking);
	
	List<VehicleEntDTO> entVehicleDetails(Long id);
	
	VehicleEntDTO vehicleById(Long idvehicle);
	
    List<Vehicle> vehiclesFirstTime();
	
	List<Vehicle> vehiclesRepeatedly();
	
	List<VehicleRankDTO> rankVehicles(Long id);
	
	Long averageUsageByid(Date min, Date max, Long idparking,int days);
	
	Long averageUsageAll(Date min, Date max, int days);
	
	TimeHoursDTO averageHoursById(Long idparking);
	
	Parking create(ParkingDTO parking);
	
	ParkingDTO updateDTO(ParkingDTO newParking, Long id);
	
	Parking update(Parking newParking, Long id);
	
	Parking updateWhitUser(Parking newParking, Long id);
	
	void delete(Long id);
	
	Date parseDate(String date);
	
	int getDays(Date min, Date max);

	List<ParkingDTO> findAll();

	Long averageUsageAllByUser(Date dateMin2, Date dateMax2, int days, Long iduser);

	List<ParkingVehicleDTO> findAllWithVehiclesInd();

	List<Vehicle> vehiclesFirstTimeByUser(Long iduser);
	
	List<Vehicle> vehiclesRepeatedlyByUser(Long iduser);
	

}
