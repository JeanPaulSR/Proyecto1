package proyecto1;

public class City{

    private int cityNumber;
    private String city;
    private String country;
    private int population;
    private double x;
    private double y;

    public City(int cityNumber, String city, String country, int population, double x, double y){
	this.cityNumber = cityNumber;
	this.city = city;
	this.country = country;
	this.population = population;
	this.x = x;
	this.y = y;
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

    public double getX(){
	return this.x;
    }

    public double getY(){
	return this.y;
    }

    public void updateCity(int cityNumber, String city, String country, int population, double x, double y){
	this.cityNumber = cityNumber;
	this.city = city;
	this.country = country;
	this.population = population;
	this.x = x;
	this.y = y;
    }
	  
    @Override public String toString() {
	return Integer.toString(this.cityNumber) + " " + this.city + " " + this.country + " " +
	    Integer.toString(this.population) + " " + String.valueOf(this.x) + " " + String.valueOf(this.y);
    }
}
