package org.naresh.spring.batch.model;

public class Airport {

	private String airportCode;
	private String airportName;
	private String city;
	private String state;
	private String country;
	private Double latitude;
	private Double longitude;
	
	public Airport(String airportCode, String airportName, String city, String state, String country, Double latitude,
			Double longitude) {
		this.airportCode = airportCode;
		this.airportName = airportName;
		this.city = city;
		this.state = state;
		this.country = country;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Airport(){
		
	}
	
	public String getAirportCode() {
		return airportCode;
	}
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	public String getAirportName() {
		return airportName;
	}
	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
}
