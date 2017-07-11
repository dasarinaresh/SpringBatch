package org.naresh.spring.batch.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JobCompletionNotificationListener implements JobExecutionListener {

	public void afterJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub
		if(jobExecution.getStatus() == BatchStatus.COMPLETED){
			log.info("Job Completed Successfully");
		}
	}

	public void beforeJob(JobExecution arg0) {
		// TODO Auto-generated method stub
		
	}

}
