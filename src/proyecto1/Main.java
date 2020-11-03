package proyecto1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws IOException {
	int[] tsp = readPath();

	Database db = new Database();
	ResultSet res;
		
	World world = new World();
	try {
	    res = db.displayCities();
	    while (res.next()){
		world.addCity(res.getInt("Id"),
			      res.getString("Name"),
			      res.getString("Country"),
			      res.getInt("Population"),
			      res.getDouble("Longitude"),
			      res.getDouble("Latitude"));
	    }
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
		
	Connections connections = new Connections(world.size());
	try {
	    res = db.displayConnections();
	    while (res.next()){ 
		connections.addConnection(res.getInt("id_city_1") - 1,
					  res.getInt("id_city_2") - 1,
					  res.getDouble("distance"));

	    }
			
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
		
	Normalizer normalizer = new Normalizer();
	double normal = normalizer.normalize(tsp, connections);

	//Test funcion de cost
	CostFunction cf = new CostFunction();
	String value = String.format("%.9f", cf.funcionDeCosto(tsp, connections, world, normal));

	System.out.println("Normal Result: " + String.format("%.9f",normal));
	System.out.println("Max Result: " +
			   String.format("%.9f",connections.findMax(tsp)));
	System.out.println("Cost Function Result: " + value);
		

	int seed = 10;
	double epsilonp = 2;
	double percentage = .85;
	Temperature temp = new Temperature(seed, epsilonp, connections, world);
	double initialTemperature = temp.inicialTemperature(tsp, 10, percentage, normal);

	double cooling = .05;
	double l = 1000;
	double epsilon = 10;
	Heuristic heuristic = new Heuristic(initialTemperature, cooling, epsilon, l,
					    world, connections, seed);
	int[] result = heuristic.aceptacionPorUmbrales(tsp, normal);
	String s = "";
	for(int i = 0; i < result.length; i++){
	    if(i == result.length-1)
		s += result[i];
	    else
		s += result[i] + ", ";
	}
	System.out.println("Resulting Solution: " + s);
		
    }
	
    private static int[] readPath() throws IOException {
	File file = new File("tsp.TXT"); 
	String path ="";
	try {
	    BufferedReader br;
	    br = new BufferedReader(new FileReader(file)); 
	    path = br.readLine();
	    br.close();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} 
	String[] tmpArray;
	tmpArray = path.split(",");

	int[] result = new int[tmpArray.length];
	for(int i = 0; i < tmpArray.length ; i++)
	    result[i] = Integer.parseInt(tmpArray[i]) - 1;
	return result;
			
    }

}
