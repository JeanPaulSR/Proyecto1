package proyecto1;

public class CostFunction {

    public double funcionDeCosto(Solutions permutation, Connections connections,
				 World world, double normal) {
	double total = 0.0;
	double max = connections.findMax(permutation);

	//Starting at i = 1 to simulate the formula in the document.
	for(int i = 1; i < permutation.length(); i++) {
	    total += calculateDistance(connections,
				       world.getCity(permutation.get(i-1)),
				       world.getCity(permutation.get(i)), max);
	}
	return total/normal;
    }
	
    private double calculateDistance(Connections connections, City city1,
				    City city2, double max) {
	//Required to subtract by 1 as connections is a matrix array
	double distance = connections.getDistance(city1.getCityNumber()-1,
						  city2.getCityNumber()-1);
	if(distance <= 0)
	    return max * naturalDistance(city1, city2);
	return distance;
    }

    public double naturalDistance(City city1, City city2) {
	double r = 6373000.0;
	double a = a(city1, city2);
	return r * (2 * Math.atan2(Math.sqrt(a),Math.sqrt(1-a)));
    }
	
    private double a(City city1, City city2) {
	double latU = rad(city1.getLat());
	double lonU = rad(city1.getLon());
	//v
	double latV = rad(city2.getLat());
	double lonV = rad(city2.getLon());

	// sin(latv - latu)/2)^2
	double sin1 = Math.pow(Math.sin( (latV - latU)/2 ), 2);
	// sin(lonv - lonu)/2)^2
	double sin2 = Math.pow(Math.sin( (lonV - lonU)/2 ), 2);

	//sin(latv - latu)/2)^2 + cos(latu) * cos(latv) * sin(lonv - lonu)/2)^2
	return sin1 + (Math.cos(latU) * Math.cos(latV) * sin2);
    }
	
    private double rad(double g) {
	return g * Math.PI / 180;
    }
}
