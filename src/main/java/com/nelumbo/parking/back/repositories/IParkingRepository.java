package com.nelumbo.parking.back.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nelumbo.parking.back.DTO.ParkingDTO;
import com.nelumbo.parking.back.DTO.VehicleEntDTO;
import com.nelumbo.parking.back.DTO.VehicleRankDTO;
import com.nelumbo.parking.back.entities.Entering;
import com.nelumbo.parking.back.entities.History;
import com.nelumbo.parking.back.entities.Parking;

public interface IParkingRepository extends JpaRepository<Parking, Long> {

	List<Parking>findAllByUser_iduser(Long iduser);
	
	Optional<Parking> findOneByName(String name);
	
	@Query(value="select new com.nelumbo.parking.back.DTO.VehicleRankDTO(v.idvehicle as idvehicle,v.plate as plate, count(v) as quantity) from Vehicle as v inner join History as h on v.idvehicle = h.vehicle.idvehicle where h.parking.idparking =:idparking group by v.idvehicle order by quantity desc FETCH FIRST 10 ROWS ONLY")
	public List<VehicleRankDTO> findRank(Long idparking);
	
	@Query(value="select ((count(h.idhistory))/:days) as usos from History as h where h.enteringDate BETWEEN :min and :max AND h.parking.idparking =:idparking")
	public Long averageUsageByid(Date min, Date max, Long idparking, int days);
	
	@Query(value="select ((count(h.idhistory))/:days) as usos from History as h where h.enteringDate BETWEEN :min and :max ")
	public Long averageUsageAll(Date min, Date max, int days);
	
	@Query(value="select sum(DATEDIFF(second,h.enteringDate,h.departureDate))/count(h.idhistory) as horas_uso from History as h where h.parking.idparking =:idparking")
	public Long averageUsageHours(Long idparking);

    @Query(value="select new com.nelumbo.parking.back.DTO.ParkingDTO(p.idparking,p.name,p.user.name,p.spots) from Parking as p where p.idparking=:id")
	Optional<ParkingDTO> findDTOById(Long id);
    
    @Query(value="select new com.nelumbo.parking.back.DTO.ParkingDTO(p.idparking,p.name,p.user.name,p.spots) from Parking as p where p.user.iduser=:iduser")
    public List<ParkingDTO> findAllDTOByUser(Long iduser);
    
    @Query(value="select new com.nelumbo.parking.back.DTO.VehicleEntDTO(v.idvehicle,v.plate,e.date) from Vehicle as v inner join Entering as e on v.idvehicle=e.vehicle.idvehicle and e.parking.idparking=:id")
  	List<VehicleEntDTO> getVehiclesEnteringByIdParking(Long id);
    
    @Query(value="select new com.nelumbo.parking.back.DTO.VehicleEntDTO(v.idvehicle,v.plate,e.date) from Vehicle as v inner join Entering as e on v.idvehicle=e.vehicle.idvehicle and e.vehicle.idvehicle=:idvehicle")
    Optional<VehicleEntDTO> getVehicleEnteringByIdVehicle(Long idvehicle);
   
    @Query(value="select e from Entering as e where e.parking.idparking=:idparking")
    List<Entering> enteringsByIdParking(Long idparking);
    
    @Query(value="select h from History as h where h.parking.idparking=:idparking")
    List<History> historiesByIdParking(Long idparking);
}
