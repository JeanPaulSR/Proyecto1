package proyecto1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;


import java.util.Random;
import java.lang.Object.*;

public class Main {
    
    public static void main(String[] args) throws IOException {
	//Numer of solutions to generate
	int generations = 1;
	//Boolean to track if more than one solution will be generated
	boolean fiveBest = false;

	//Generate i number of solutions and select the 5 best
	if(!args[1].equals("${gen}")){
	    try{
	        generations = Integer.parseInt(args[1]);
	        fiveBest = true;
		System.out.println("Generating " + generations + " solutions "+
				   "and selecting 5 best ...");
	    }catch(Exception e){
		System.out.println("Generations is not an integer.");
		return;
	    }
	    
	}

	//Seed to test
        int seed = 0;
	//Starting seed if generting more than one
	int initialSeed = 0;
	if(args[0].equals("${seed}")){
	    if(fiveBest)
		System.out.println("Starting seed is set to 0");
	    else
		System.out.println("No seed, setting seed to 0");
	}else{
	    try{
		seed = Integer.parseInt(args[0]);
		if(fiveBest){
		    initialSeed = seed;
		    System.out.println("Starting seed is set to " + seed);
		}else{
		    System.out.println("Seed is set to " + seed);
		    }
	    }catch(Exception e){
		System.out.println("Seed is not an integer.");
		return;
	    }	
	}


	//Path txt file that contains the path we want
        String pathName = "tsp.txt";
	if(!args[2].equals("${path}"))
		pathName = args[2];

	//Array that contains the list of cities that will be used
	int[] tspArray = readPath(pathName+".txt");

	//Returns a permutation of the original solution path
	if(!args[3].equals("${perm}")){
	    try{
		int permSeed = Integer.parseInt(args[3]);
		tspArray = permutation(tspArray, permSeed);
	    }catch(Exception e){
		System.out.println("Seed for perm is not an integer.");
		return;
	    }	
	}

	boolean outPath = false;
	String outputPath = "output.txt";
	
	if(!args[4].equals("${out}")){
		outputPath = args[4]+".txt";
		outPath = true;
	}

	Database db = new Database();
	ResultSet res;

	//Generate an object that contains all the cities
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

	//Create an object that contains all connections between cities
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

	//Save the top 5 solutions if generating more than 1 solution
	Solutions[] best5 = {null, null, null, null, null};

	//Create a solution to calculate the normal. Should just need the array
	Solutions normalSolution = new Solutions(tspArray,0,connections, world);
	Normalizer normalizer = new Normalizer();
	double normal = normalizer.normalize(normalSolution, connections);

	for(int i = initialSeed; i < generations + initialSeed; i++){
	    if(fiveBest)
		seed = i;

	    //Create new solution with current seed
	    Solutions tsp = new Solutions(tspArray, seed, connections, world);

	    //Set the normal for the solution. 
	    tsp.setNormal(normal);

	    //Settings for the values related to finding the temperature
	    double epsilonp = .0001;
	    double percentage = .50;
	    double startingTemp = 10;
	    int n = 1000;
	    Temperature temp = new Temperature(seed, epsilonp, connections,
					       world, n);
	
	    double initialTemperature = temp.initialTemperature(tsp,
								startingTemp,
								percentage,
								normal);

	    //Will let the user know at what temperature the solution is
	    //starting at
	    if(!fiveBest)
		System.out.printf("Temperature: %.6f \n", initialTemperature);

	    //Settings for the value of the heuristic calculation
	    double cooling = .95;
	    //l is preferable set to length()^/2, set low due to time constraint
	    double l = tsp.length() * tsp.length()-1 * .2;
	    //double l = tsp.length() * (tsp.length()-1 * .5);
	    double epsilon = .001;
	    Heuristic heuristic = new Heuristic(initialTemperature, cooling,
						epsilon, l,  world,
						connections);

	    //Lets the user know that the solution is being found
	    if(fiveBest)
		System.out.println("Finding Solution to seed "+i+" ...");
	    else
		System.out.println("Finding Solution...");
	    Solutions result = heuristic.aceptacionPorUmbrales(tsp, normal);

	    //Orders the solutions if one was found
	    for(int j = 0; j < 5; j++){
		if(result != null){
		    if(best5[j] == null){
			best5[j] = result;
			break;
		    }
		    else{
			if(result.getCostFunction() <
			   best5[j].getCostFunction()){

			    //If a better solution was found, adds it
			    for(int k = 4; j < k; k--)
				best5[k] = best5[k-1];
			    best5[j] = result;
			    break;
			}
		    }
		}
	    }
	}

	//Prints out the top 5 solutions or a single solution
	Solutions result;
	String s = "";

	if(best5[0] == null)
	    s += "No solution found";
	else{
	    if(fiveBest){
		for(int i = 0; i < 5; i++){
		    result = best5[i];
		    if(result != null){
		        s += "Resulting Solution: "+ result.toString() + "\n";
		        s += "Cost Function Result: " +
			    result.getCostFunction() + "\n";
			s += "Seed: " + result.getSeed() + "\n\n";
		    }
		}
	    }else{
		result = best5[0];
		if(result.getCostFunction() > 1){
		    s += "No valid result found \n";
		    s += "Cost Function Result: " +
			result.getCostFunction() + "\n";
		}else{
		    s += "Resulting Solution: " + result.toString() + "\n";
		    if(result.esFactible(connections))
		        s += "Solution exists in database \n" ;
		    s += "Cost Function Result: " +
			result.getCostFunction() + "\n";
		}
	    }
	    
	}

	
	if(outPath){
	    try {
		FileWriter writer = new FileWriter(outputPath);
		writer.write(s);
		writer.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}else
	    System.out.println(s);
    }

    private static int[] permutation(int[] tsp, int seed){
	int length = tsp.length;
	Random rd = new Random(seed);
	int[] result = new int[length];
	
	for(int i = 0; i < length; i++){
	    result[i] = -1;
	}

	//Uses a seed to consistently gain the same permutation
	for(int i = 0; i < length; i++){
	    double max = Math.pow(length, 2);
	    while(true){
		int counter = 0;
		int j = rd.nextInt(length);
		if(result[j] == -1){
		    result[j] = i;
		    break;
		}
		if(++counter > max){
		    for(int n = 0; n < length; n++)
			if(result[n] == -1){
			    result[n] = i;
			    break;
			}
		    break;
		}
		
	    }
	}

	int[] result2 = new int[length];
	for(int i = 0; i < length; i++){
	    result2[i] = tsp[result[i]];
	}
	return result2;
    }
	
    private static int[] readPath(String pathName) throws IOException {
	File file = new File(pathName); 
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
