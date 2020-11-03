package proyecto1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
	
//	private static String path = "1,2,3,4,5,6,7,163,164,165,168,172,327,329,331"+
//			",332,333,489,490,491,492,493,496,653,654,656,657,661,815,816,817,820,823,871"+
//			",978,979,980,981,982,984";

	public static void main(String[] args) throws IOException {
		int[] tsp = readPath();

		Database db = new Database();
		ResultSet res;
		
		World world = new World();
		try {
			res = db.displayCities();
			while (res.next()){
				world.addCity(res.getInt("Id"), res.getString("Name"), res.getString("Country"),
						res.getInt("Population"), res.getDouble("Longitude"), res.getDouble("Latitude"));
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
				connections.addConnection(res.getInt("id_city_1") - 1, res.getInt("id_city_2") - 1,
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
		String value = String.format("%.18f", cf.funcionDeCosto(tsp, connections, world, normal));
		System.out.println("Result: " + value);
		System.out.println("Expect: 3305585.454990047" );
		

		int seed = 10;
		double epsilonp = 2;
		double percentage = .85;
		Temperature temp = new Temperature(seed, epsilonp, connections, world);
		double initialTemperature = temp.inicialTemperature(tsp, 10, percentage, normal);
		
		double cooling = 2;
		double l = 1000;
		double epsilon = 10;
		Heuristic heuristic = new Heuristic(initialTemperature, cooling, epsilon, l,
					world, connections, seed);
		//heuristic.aceptacionPorUmbrales(tsp, normal)
		
	}
	
	private static int[] readPath() throws IOException {
		File file = new File("tsp.TXT"); 
		String path ="";
		try {
			BufferedReader br;
			br = new BufferedReader(new FileReader(file)); 
			path = br.readLine();
			System.out.println(path);
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
