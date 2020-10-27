package proyecto1;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
	
	private static String path = "1,2,3,4,5,6,7,163,164,165,168,172,327,329,331"+
			",332,333,489,490,491,492,493,496,653,654,656,657,661,815,816,817,820,823,871"+
			",978,979,980,981,982,984";

	public static void main(String[] args) {
		int[] tsp = readPath(path);

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

		double normalizer = Algorithms.normalizer(tsp, connections);
		
		System.out.println(Algorithms.funcionDeCosto(tsp, connections, world, normalizer));
	}
	
	private static int[] readPath(String path) {
		String[] tmpArray;
		tmpArray = path.split(",");

		int[] result = new int[tmpArray.length];
		for(int i = 0; i < tmpArray.length ; i++)
			result[i] = Integer.parseInt(tmpArray[i]) - 1;
		return result;
			
	}

}
