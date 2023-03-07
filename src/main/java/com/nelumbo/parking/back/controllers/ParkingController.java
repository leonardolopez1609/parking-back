package com.nelumbo.parking.back.controllers;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.nelumbo.parking.back.DTO.ParkingDTO;
import com.nelumbo.parking.back.DTO.VehicleEntDTO;
import com.nelumbo.parking.back.DTO.VehicleRankDTO;
import com.nelumbo.parking.back.entities.Parking;
import com.nelumbo.parking.back.entities.Vehicle;
import com.nelumbo.parking.back.services.IParkingService;

@RestController
@RequestMapping("/parkings")
@CrossOrigin(origins = { "*" })
public class ParkingController {

	
	
	//AGREGAR EL GET DE TODOS LOS PARQUEADEROS.
	
	@Autowired
	private IParkingService parkingService;

	
	//Revisado
	@GetMapping("getone/{id}")
	public ResponseEntity<?> getParking(@PathVariable Long id) {

		return new ResponseEntity<Parking>(parkingService.findById(id), HttpStatus.OK);
	}
	
	//Revisado
		@GetMapping()
		@ResponseStatus(value = HttpStatus.OK)
		public List<ParkingDTO> getParkings() {
			return parkingService.findAll();
		}

	//Revisado
	@GetMapping("getall/byuser/{id}")
	public ResponseEntity<?> getParkings(@PathVariable Long id) {
        
		return new ResponseEntity<List<ParkingDTO>>(parkingService.findAllByUser(id), HttpStatus.OK);
	}
    
	//Revisado
	@GetMapping("getvehiclesact/{id}")
	public ResponseEntity<?> getVehiclesAct(@PathVariable Long id) {

		return new ResponseEntity<List<VehicleEntDTO>>(parkingService.entVehicleDetails(id), HttpStatus.OK);
	}
	
	//Revisado
	@GetMapping("getvehicleact/{id}")
	public ResponseEntity<?> getVehicleAct(@PathVariable Long id) {

		return new ResponseEntity<VehicleEntDTO>(parkingService.vehicleById(id), HttpStatus.OK);
	}
	
	//Revisado
	@GetMapping("getvehiclesfirst")
	public ResponseEntity<?> getVehicleFistTime() {

		return new ResponseEntity<List<Vehicle>>(parkingService.vehiclesFirstTime(), HttpStatus.OK);
	}
	
	//Revisado
	@GetMapping("getvehiclesrep")
	public ResponseEntity<?> getVehicleRepeatedly() {

		return new ResponseEntity<List<Vehicle>>(parkingService.vehiclesRepeatedly(), HttpStatus.OK);
	}
	
	//Revisado
	@GetMapping("getvehiclesrank/{id}")
	public ResponseEntity<?> getVehiclesRank(@PathVariable Long id) {

		return new ResponseEntity<List<VehicleRankDTO>>(parkingService.rankVehicles(id), HttpStatus.OK);
	}
	
	//No retornar String
	@GetMapping("getaverage/min/{dateMin}/max/{dateMax}/in/{id}")
	public ResponseEntity<?> getAverageUseById(@PathVariable String dateMin,@PathVariable String dateMax,@PathVariable Long id) throws ParseException {
		
		Date dateMin2 = parkingService.parseDate(dateMin);
		Date dateMax2 = parkingService.parseDate(dateMax);
		
		return new ResponseEntity<String>("Parqueadero con promedio de "+parkingService.averageUsageByid(
				dateMin2,
				dateMax2,
				id,
				parkingService.getDays(dateMin2, dateMax2))+" entradas por día", HttpStatus.OK);
	}
	
	//No retornar String
	@GetMapping("getaverageall/min/{dateMin}/max/{dateMax}")
	public ResponseEntity<?> getAverageUseAll(@PathVariable String dateMin,@PathVariable String dateMax) throws ParseException {
		
		Date dateMin2 = parkingService.parseDate(dateMin);
		Date dateMax2 = parkingService.parseDate(dateMax);
		
		return new ResponseEntity<String>("Parqueaderos con promedio de "+parkingService.averageUsageAll(
				dateMin2,
				dateMax2,
				parkingService.getDays(dateMin2, dateMax2))+" entradas por día", HttpStatus.OK);
	}

	//Obtener segundos en ves de horas Y No retorna string
	@GetMapping("getaveragehours/{id}")
	public ResponseEntity<?> getAverageHours(@PathVariable Long id) {

		return new ResponseEntity<String>("El parqueadero posee un promedio de "+parkingService.averageHoursById(id)+" horas de uso por vehículo", HttpStatus.OK);
	}
	 
	
	//validaciones
	@PostMapping
	public ResponseEntity<?> createParking(@RequestBody ParkingDTO parking) {
		Map<String, Object> response = new HashMap<>();

		response.put("parking", parkingService.create(parking));
		response.put("mensaje", "Parqueadero creado con éxito!");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	
	//url por cambiar
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateParking(@RequestBody ParkingDTO parking, @PathVariable Long id) {

		
		Map<String, Object> response = new HashMap<>();

		response.put("parking", parkingService.updateDTO(parking, id));
		response.put("mensaje", "Parquedero actualizado con éxito!");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteParking(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		parkingService.delete(id);
		response.put("mensaje", "Parqueadero eliminado con éxito!");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
