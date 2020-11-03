package proyecto1;

public class CostFunction {

	public double funcionDeCosto(int[] permutation, Connections connections, World world, double normal) {
		double total = 0.0;
		for(int i = 0; i < permutation.length-1; i++) {
				total += calculateDistance(connections, world.getCity(permutation[i]),
											world.getCity(permutation[i+1]), connections.findMax(permutation));
			}

		return total/normal;
	}
	
	public double calculateDistance(Connections connections, City city1, City city2, double max) {
		double distance = connections.getDistance(city1.getCityNumber(), city2.getCityNumber());
		if(distance == -1.0)
			distance = max * naturalDistance(connections, city1, city2);
		return distance;
	}

	private double naturalDistance(Connections connections, City city1, City city2) {
		double r = 6373000.0;
		
		return r * 2 * Math.atan2(Math.sqrt(a(city1, city2)),Math.sqrt(1-a(city1,city2)));
	}
	
	private double a(City city1, City city2) {
		double a = Math.pow(Math.sin(((rad(city1.getX()) - rad(city2.getX()))/2)), 2 )
				+ Math.cos(rad(city1.getX())) * Math.cos(rad(city2.getX()))
				* Math.pow(Math.sin(((rad(city1.getY()) - rad(city2.getY()))/2)),2 );
		return a;
	}
	
	private double rad(double g) {
		return g * Math.PI / 180;
	}
}
