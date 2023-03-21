package com.nelumbo.parking.back.services.business;


import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.nelumbo.parking.back.exceptions.BusinessException;
import com.nelumbo.parking.back.exceptions.RequestException;
import com.nelumbo.parking.back.models.dto.HistoryDTO;
import com.nelumbo.parking.back.models.dto.VehicleHistoryDTO;
import com.nelumbo.parking.back.models.entities.Entering;
import com.nelumbo.parking.back.models.entities.History;
import com.nelumbo.parking.back.models.entities.Parking;
import com.nelumbo.parking.back.models.entities.Vehicle;
import com.nelumbo.parking.back.repositories.IHistoryRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@Service
public class HistoryServiceImpl implements IHistoryService {

	@Autowired
	private IHistoryRepository historyRepository;

	@Autowired
	private IEnteringService enteringService;

	@Autowired
	private IParkingService parkingService;
	
	@Autowired
	private IVehicleService vehicleService;
	


	@Override
	public HistoryDTO findDTOById(Long id) {
	
		return historyRepository.findOneDTOById(id).orElseThrow(()-> new RequestException("El historial con ID: " + id + " no existe en la base de datos!"));
	}

	@Override
	public History create(String plate) {
		vehicleService.invalidPlate(plate);
		if (!enteringService.vehicleIsPresent(plate)) {
			throw new BusinessException(HttpStatus.BAD_REQUEST,
					"No se puede Registrar Salida, no existe la placa");
		}

		Entering ent = enteringService.findOneByPlate(plate);
		
		Parking parking = parkingService.findById(ent.getParking().getIdparking());
		//------------------------------------------
		parking.setSpotsTaken(parking.getSpotsTaken() - 1);
		//------------------------------------------
		//parkingService.update(parking, ent.getParking().getIdparking());
		History his = new History();

		his.setParking(parking);
		his.setVehicle(ent.getVehicle());
		his.setEnteringDate(ent.getDate());
		enteringService.deleteByID(ent.getIdentering());
		
		return historyRepository.save(his);
	}


	@Override
	public Date parseDate(String date) {
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy"); 
		try {
	    return formato.parse(date);
		} catch (ParseException e) { 
			return new Date();
		}
	}

	@Override
	public int getDays(Date dateMin, Date dateMax) {
		return parkingService.getDays(dateMin, dateMax);
	}

	@Override
	public List<VehicleHistoryDTO> getHistoryByRangeDateAndPlate(Date min, Date max, Long idParking, String plate) {
		parkingService.findById(idParking);
	
		if(plate.equals("*") ){
			plate="";
		}
		
		List<VehicleHistoryDTO> vehiclesHist = historyRepository.findHistoryByRangeDateAndPlate(min, max, idParking,plate);
		
		return vehiclesHist;
	}

	@Override
	public void deleteAllByList(List<History> histories) {
		historyRepository.deleteAll(histories);
		
	}

	@Override
	public List<Vehicle> getHistoryVehicles(Long idparking) {
		
		return historyRepository.findHistoryVehicles(idparking);
	}

	@Override
	public History findById(Long id) {
		
		return historyRepository.findById(id).orElse(null);
	}

	

}
