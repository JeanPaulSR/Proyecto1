package proyecto1;

public class City{

    private int cityNumber;
    private String city;
    private String country;
    private int population;
    private double latitude;
    private double longitude;

    public City(int cityNumber, String city, String country, int population, double latitude, double longitude){
	this.cityNumber = cityNumber;
	this.city = city;
	this.country = country;
	this.population = population;
	this.latitude = latitude;
	this.longitude = longitude;
    }

    public int getCityNumber(){
	return this.cityNumber;
    }

    public String getCity(){
	return this.city;
    }

    public String getCountry(){
	return this.country;
    }

    public int getPopulation(){
	return this.population;
    }

    public double getLat(){
	return this.latitude;
    }

    public double getLon(){
	return this.longitude;
    }

    public void updateCity(int cityNumber, String city, String country, int population, double latitude, double longitude){
	this.cityNumber = cityNumber;
	this.city = city;
	this.country = country;
	this.population = population;
	this.latitude = latitude;
	this.longitude = longitude;
    }
	  
    @Override public String toString() {
	return "City Number: " + Integer.toString(this.cityNumber) +
	    ", City Name: " + this.city +
	    ", Country Name: " + this.country +
	    ", City Population: " + Integer.toString(this.population) +
	    ", Latitude: " + String.valueOf(this.latitude) +
	    ", Longitude: " + String.valueOf(this.longitude);
    }
}
