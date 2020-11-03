package proyecto1;

public class CostFunction {

    public double funcionDeCosto(int[] permutation, Connections connections, World world, double normal) {
	double total = 0.0;
	double max = connections.findMax(permutation);
	for(int i = 1; i < permutation.length; i++) {
	    total += calculateDistance(connections, world.getCity(permutation[i-1]),
				       world.getCity(permutation[i]), max);
	}
	return total/normal;
    }
	
    public double calculateDistance(Connections connections, City city1, City city2, double max) {
	double distance = connections.getDistance(city1.getCityNumber()-1, city2.getCityNumber()-1);
	if(distance <= 0)
	    return max * naturalDistance(city1, city2);
	return distance;
    }

    private double naturalDistance(City city1, City city2) {
	double r = 6373000.0;
	double a = a(city1, city2);
	return r * (2 * Math.atan2(Math.sqrt(a),Math.sqrt(1-a)));
    }
	
    private double a(City city1, City city2) {
	//x = lat y == lon
	//u
	double x1 = rad(city1.getX());
	double y1 = rad(city1.getY());
	//v
	double x2 = rad(city2.getX());
	double y2 = rad(city2.getY());

	// sin(latv - latu)/2)^2
	double sin1 = Math.pow( Math.sin(x2 - x1)/2 , 2);
	// sin(lonv - lonu)/2)^2
	double sin2 = Math.pow( Math.sin(y2 - y1)/2 , 2);

	//sin(latv - latu)/2)^2 + cos(latu) * cos(latv) * sin(lonv - lonu)/2)^2
	return sin1 + (Math.cos(x1) * Math.cos(x2) * sin2);
	

    }
	
    private double rad(double g) {
	return g * Math.PI / 180;
    }
}
