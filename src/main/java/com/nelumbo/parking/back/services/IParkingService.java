package com.nelumbo.parking.back.services;

import com.nelumbo.parking.back.DTO.ParkingDTO;
import com.nelumbo.parking.back.DTO.ParkingVehicleDTO;
import com.nelumbo.parking.back.DTO.TimeHoursDTO;
import com.nelumbo.parking.back.DTO.VehicleEntDTO;
import com.nelumbo.parking.back.DTO.VehicleRankDTO;
import com.nelumbo.parking.back.entities.Parking;
import com.nelumbo.parking.back.entities.Vehicle;
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
	
	Parking updateDTO(ParkingDTO newParking, Long id);
	
	Parking update(Parking newParking, Long id);
	
	Parking updateWhitUser(Parking newParking, Long id);
	
	void delete(Long id);
	
	Date parseDate(String date);
	
	int getDays(Date min, Date max);

	List<ParkingDTO> findAll();
	

}
