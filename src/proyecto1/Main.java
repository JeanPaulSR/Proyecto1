package proyecto1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.lang.Object.*;

public class Main {

    public static void main(String[] args) throws IOException {
        int seed = 0;
	boolean graphs = false;
	if(args[0].equals("${seed}")){
	    System.out.println("No seed, setting seed to 0");
	}else{
	    try{
		seed = Integer.parseInt(args[0]);
	    }catch(Exception e){
		System.out.println("Seed is not an integer.");
		return;
	    }	
	}

	if(!args[1].equals("${graphs}")){
	    try{
		if(Integer.parseInt(args[1]) == 1)
		    graphs = true;
	    }catch(Exception e){
		System.out.println("Error reading graph command. Use 1 or 0.");
	    }	
	}
	
	int[] tspArray = readPath();
	

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
			      res.getDouble("Latitude"),
			      res.getDouble("Longitude"));
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
		connections.addConnection(res.getInt("id_city_1") -1,
					  res.getInt("id_city_2") -1,
					  res.getDouble("distance"));
	    }
			
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	} catch (SQLException e) {
	    e.printStackTrace();
	}

	/*
	  Solutions[] best5 = {null, null, null, null, null};
	  for(int i = 0; i < 50; i++){
	  seed = i;
	  System.out.println(i);
	*/
	
	Solutions tsp = new Solutions(tspArray, seed, connections, world);
	    
	Normalizer normalizer = new Normalizer();
	double normal = normalizer.normalize(tsp, connections);

	tsp.setNormal(normal);
	
	CostFunction cf = new CostFunction();

	double epsilonp = .0001;
	double percentage = .50;
	double startingTemp = 10;
	int n = 1000;
	Temperature temp = new Temperature(seed, epsilonp, connections, world,
					   n);
	
	double initialTemperature = temp.initialTemperature(tsp, startingTemp,
							    percentage, normal);

	System.out.printf("Temperature: %.6f \n", initialTemperature);
	
	double cooling = .95;
	//Valor recomendado para es menos que todas las posibles combinaciones
	double l = Math.pow(tsp.length(),2)/2;
	
	//Recommended value for epsilon is .001
	double epsilon = .001;
	Heuristic heuristic = new Heuristic(initialTemperature, cooling,
					    epsilon, l,  world,
					    connections);

	System.out.println("Finding Solution...");
	
	Solutions result = heuristic.aceptacionPorUmbrales(tsp, normal);

	/*
	  for(int j = 0; j < 5; j++){
	  if(result != null){
	  if(best5[j] == null){
	  best5[j] = result;
	  break;
	  }
	  else{
	  if(result.getCostFunction() < best5[j].getCostFunction()){
	  for(int k = 4; j < k; k--)
	  best5[k] = best5[k-1];
			   
	  best5[j] = result;
	  break;
	  }
	  }
	  }
	  }
	  }
	    
	  for(int i = 0; i < 5; i++){
	  Solutions result = best5[i];
	  if(result != null){
	  System.out.println("Resulting Solution: "+ result.toString());
	  System.out.println("Cost Function Result: " +
	  (result.getCostFunction()));
	  System.out.println(result.getSeed());
	  }
	  }
	*/

	if(result == null)
	    System.out.println("No valid result found");
	else{
	    System.out.println("Resulting Solution: "+ result.toString());
	    if(result.esFactible(connections))
		System.out.println("Solution exists in database");
	    System.out.println("Cost Function Result: " +
			       (result.getCostFunction()));
	}
		
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
