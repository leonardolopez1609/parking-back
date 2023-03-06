package com.nelumbo.parking.back.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.nelumbo.parking.back.DTO.VehicleHistoryDTO;
import com.nelumbo.parking.back.entities.Entering;
import com.nelumbo.parking.back.entities.History;
import com.nelumbo.parking.back.entities.Parking;
import com.nelumbo.parking.back.exceptions.BusinessException;
import com.nelumbo.parking.back.exceptions.RequestException;
import com.nelumbo.parking.back.repositories.IHistoryRepository;

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
	public Optional<History> findById(Long id) {
		Optional<History> history = historyRepository.findById(id);
		if (history.isEmpty()) {
			throw new RequestException("El historial con ID: " + id + " no existe en la base de datos!");
		}
		return history;
	}

	@Override
	public History create(String plate) {
		vehicleService.invalidPlate(plate);
		if (!enteringService.vehicleIsPresent(plate)) {
			throw new BusinessException(HttpStatus.BAD_REQUEST,
					"No se puede Registrar Salida, no existe la placa");
		}

		Entering ent = enteringService.findOneByPlate(plate).get();
		
		Parking parking = parkingService.findById(ent.getParking().getIdparking());
		parking.setSpots(parking.getSpots() + 1);
		parkingService.update(parking, ent.getParking().getIdparking());
		History his = new History();

		his.setParking(parking);
		his.setVehicle(ent.getVehicle());
		his.setEnteringDate(ent.getDate());
		enteringService.deleteByID(ent.getIdentering());
		
		return historyRepository.save(his);
	}

	@Override
	public List<VehicleHistoryDTO> getHistoryByRangeDate(Date min, Date max, Long idParking) {
		parkingService.findById(idParking);
		List<VehicleHistoryDTO> vehiclesHist = historyRepository.getHistoryByRangeDate(min, max, idParking);
		if(vehiclesHist.isEmpty()) {
			throw new RequestException("No hay registro de vehiculos en ese rango de fecha");
		}
		return vehiclesHist;
	}

	@Override
	public Date parseDate(String date) {
		return parkingService.parseDate(date);
	}

	@Override
	public int getDays(Date dateMin, Date dateMax) {
		return parkingService.getDays(dateMin, dateMax);
	}

	@Override
	public List<VehicleHistoryDTO> getHistoryByRangeDateAndPlate(Date min, Date max, Long idParking, String plate) {
		parkingService.findById(idParking);
		List<VehicleHistoryDTO> vehiclesHist = historyRepository.getHistoryByRangeDateAndPlate(min, max, idParking,plate);
		if(vehiclesHist.isEmpty()) {
			throw new RequestException("No hay registro de vehículos que coincidan con los filtros");
		}
		return vehiclesHist;
	}

	@Override
	public void deleteAllByList(List<History> histories) {
		historyRepository.deleteAll(histories);
		
	}

	

}
