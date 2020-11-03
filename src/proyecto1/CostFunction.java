package proyecto1;

public class CostFunction {

    public double funcionDeCosto(int[] permutation, Connections connections, World world, double normal) {
	double total = 0.0;
	double max = connections.findMax(permutation);
	for(int i = 0; i < permutation.length-1; i++) {
	    total += calculateDistance(connections, world.getCity(permutation[i]),
				       world.getCity(permutation[i+1]), max);
	}
	return total/normal;
    }
	
    public double calculateDistance(Connections connections, City city1, City city2, double max) {
	double distance = connections.getDistance(city1.getCityNumber()-1, city2.getCityNumber()-1);
	if(distance == -1.0)
	    distance = max * naturalDistance(connections, city1, city2);
	return distance;
    }

    private double naturalDistance(Connections connections, City city1, City city2) {
	double r = 6373000.0;
	double a = a(city1, city2);
	return r * 2 * Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
    }
	
    private double a(City city1, City city2) {
	//x = lon y == lat
	double x1 = rad(city1.getX());
	double y1 = rad(city1.getY());
	double x2 = rad(city2.getX());
	double y2 = rad(city2.getY());

	double sin1 = Math.pow( Math.sin(x2 - x1)/2 , 2);
	double sin2 = Math.pow( Math.sin(y2 - y1)/2 , 2);

	return sin1 + (Math.cos(y1) * Math.cos(y2) * sin2);
	

    }
	
    private double rad(double g) {
	return g * Math.PI / 180;
    }
}
