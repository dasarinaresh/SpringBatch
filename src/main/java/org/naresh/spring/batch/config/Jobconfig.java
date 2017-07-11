package org.naresh.spring.batch.config;


import java.io.File;

import javax.sql.DataSource;

import org.naresh.spring.batch.listener.JdbcWriterListener;
import org.naresh.spring.batch.listener.JobCompletionNotificationListener;
import org.naresh.spring.batch.listener.StepExecutionNotifyListener;
import org.naresh.spring.batch.model.Airport;
import org.naresh.spring.batch.processor.AirportItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobFlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration
public class Jobconfig {
	
	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	public StepExecutionNotifyListener stepListener;
	
	@Autowired
	public JdbcWriterListener jdbcWriterListener;
	
	@Autowired
	public DataSource dataSource;
	
	
	@Bean
	public FlatFileItemReader<Airport> fileReader(){
		FlatFileItemReader<Airport> reader = new FlatFileItemReader<Airport>();
		reader.setResource(new FileSystemResource(new File("/Users/jeff/Downloads/airports.csv")));
		reader.setLinesToSkip(1);
		DefaultLineMapper<Airport> defaultLineMapper = new DefaultLineMapper<Airport>();
		DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer(DelimitedLineTokenizer.DELIMITER_COMMA);
		BeanWrapperFieldSetMapper<Airport> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<Airport>();
		delimitedLineTokenizer.setNames(new String[] {"airportCode","airportName","city","state","country","latitude","longitude"});
		defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
		beanWrapperFieldSetMapper.setTargetType(Airport.class);
		defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
		reader.setLineMapper(defaultLineMapper);
		return reader;
		
	}
	
	@Bean
	public AirportItemProcessor processor(){
		return new AirportItemProcessor();
	}

	@Bean
	public FlatFileItemWriter<Airport> fileWriter(){
		FlatFileItemWriter<Airport> writer = new FlatFileItemWriter<Airport>();
		BeanWrapperFieldExtractor<Airport> beanWrapperFieldExtractor = new BeanWrapperFieldExtractor<Airport>();
		beanWrapperFieldExtractor.setNames(new String[] {"airportCode","airportName","city","state","country","latitude","longitude"});
		DelimitedLineAggregator<Airport> delimitedLineAggregator = new DelimitedLineAggregator<Airport>();
		delimitedLineAggregator.setFieldExtractor((FieldExtractor<Airport>) beanWrapperFieldExtractor);
		writer.setResource(new FileSystemResource(new File("/Users/jeff/Documents/airportsFlorida.csv")));
		writer.setLineAggregator(delimitedLineAggregator);
		writer.setShouldDeleteIfExists(true);
		return writer;
		
	}
	
	@Bean
	public JdbcBatchItemWriter<Airport> jdbcWriter(){
		JdbcBatchItemWriter<Airport> jdbcWriter = new JdbcBatchItemWriter<Airport>();
		BeanPropertyItemSqlParameterSourceProvider<Airport> itemSqlParameterSourceProvider = new BeanPropertyItemSqlParameterSourceProvider<Airport>();
		jdbcWriter.setItemSqlParameterSourceProvider(itemSqlParameterSourceProvider);
		jdbcWriter.setSql("INSERT INTO airport (airportCode,airportName,city,state,country,latitude,longitude) values (:airportCode,:airportName,:city,:state,:country,:latitude,:longitude)");
		jdbcWriter.setDataSource(dataSource);
		return jdbcWriter;
		
	}
	
	@Bean
	public Job batchJob(JobCompletionNotificationListener jobListener){

		return jobBuilderFactory.get("Airport Job")
								.incrementer(new RunIdIncrementer())
								.listener(jobListener)
								.flow(step1(stepListener)).next(step2(stepListener))
								.end()
								.build();
		
		
	}
	
	@Bean
	public Step step1(StepExecutionNotifyListener stepListener){
		return stepBuilderFactory.get("Step1")
								 .<Airport,Airport> chunk (10)
								 .reader(fileReader())
								 .processor(processor())
								 .writer(fileWriter())
								 .listener((stepListener))
								 .build();
		
	}
	
	@Bean
	public Step step2(StepExecutionNotifyListener stepListener){
		return stepBuilderFactory.get("Step2")
								 .<Airport,Airport> chunk (10)
								 .reader(fileReader())
								 .processor(processor())
								 .writer(jdbcWriter()).listener(jdbcWriterListener)
								 .listener(stepListener)
								 .build();
		
	}
}
