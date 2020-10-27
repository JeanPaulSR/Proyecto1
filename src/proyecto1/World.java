package proyecto1;

import java.util.ArrayList;

public class World{
  private ArrayList<City> cityList;

  public World(){
    cityList = new ArrayList<City>();
  }

  public void addCity(int cityNumber, String city, String country, int population, double x, double y){
    cityList.add(new City(cityNumber, city, country, population, x, y));
  }

  public City getCity(int cityNumber){
    return cityList.get(cityNumber);
  }
  
  public int size() {
	  return cityList.size();
  }
  
}

