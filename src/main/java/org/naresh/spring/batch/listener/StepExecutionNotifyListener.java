package org.naresh.spring.batch.listener;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.naresh.spring.batch.model.Airport;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class StepExecutionNotifyListener implements StepExecutionListener {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DataSource datasource;
	
	@Autowired
	public  StepExecutionNotifyListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void beforeStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		
		if (stepExecution.getStepName().equals("Step1")) {
			log.info("|-----------------Step1 START-------------|");
			log.info("  No. of records read:" + stepExecution.getReadCount());
			log.info("  No. of Florida Records:" + stepExecution.getWriteCount());
			log.info("  No. of Skipped Records:" + stepExecution.getFilterCount());
			log.info("|-----------------------------------------|");
		}

		if (stepExecution.getStepName().equals("Step2")) {

			log.info("|----------------Step2 START--------------|");
			log.info("  WriteCount: " + stepExecution.getWriteCount());
		
			List<Airport> results = jdbcTemplate.query(
					"SELECT airportCode,airportName,city,state,country,latitude,longitude FROM airport;",
					new RowMapper<Airport>() {

						@Override
						public Airport mapRow(ResultSet rs, int row) throws SQLException {
							// TODO Auto-generated method stub
							log.info("Row Count:"+row);
							return new Airport(rs.getString(0), rs.getString(1), rs.getString(2), rs.getString(3),
									rs.getString(4), rs.getDouble(5), rs.getDouble(6));
						}
					});
			for (Airport airport : results) {
				log.info("Airport Name:" + airport.getAirportName() + " City:" + airport.getCity());
			}
			log.info("|-----------------------------------------|");
		}
		
		return stepExecution.getExitStatus();
	}

}
