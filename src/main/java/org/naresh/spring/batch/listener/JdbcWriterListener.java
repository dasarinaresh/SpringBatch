package org.naresh.spring.batch.listener;

import java.util.List;

import org.naresh.spring.batch.model.Airport;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JdbcWriterListener implements ItemWriteListener<Airport>{
	
	private int count=0;
	
	@Override
	public void beforeWrite(List<? extends Airport> items) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterWrite(List<? extends Airport> items) {
		// TODO Auto-generated method stub
//		if(items.size()>0){
//			
//			for (Airport airport : items) {
//				count++;
//			}
//		
//		}
//	   log.info("Count:"+count);
	}

	@Override
	public void onWriteError(Exception exception, List<? extends Airport> items) {
		// TODO Auto-generated method stub
		
	}

}
