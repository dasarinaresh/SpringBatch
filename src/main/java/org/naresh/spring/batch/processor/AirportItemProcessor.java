package org.naresh.spring.batch.processor;

import org.naresh.spring.batch.model.Airport;
import org.springframework.batch.item.ItemProcessor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AirportItemProcessor implements ItemProcessor<Airport, Airport> {
	
	public Airport process(Airport airport) throws Exception {
		Airport floridaAirport = new Airport();
//		log.info("Airport State:"+airport.getState());
		if("FL".equals(airport.getState())){
			floridaAirport = airport;			
//			log.info("Florida Airport:"+ floridaAirport.getState());
			return floridaAirport;
		}else{
			return null;
		}
		
		
	}

}
