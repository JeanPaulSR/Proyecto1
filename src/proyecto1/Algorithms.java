package proyecto1;

import java.lang.Math;
import java.util.ArrayList;

public class Algorithms {
	
	public static double calculateDistance(Connections connections, City city1, City city2, double max) {
		double distance = connections.getDistance(city1.getCityNumber(), city2.getCityNumber());
		if(distance == -1.0)
			distance = max * naturalDistance(connections, city1, city2);
		return distance;
	}

	private static double naturalDistance(Connections connections, City city1, City city2) {
		double r = 6373000.0;
		
		return r * 2 * Math.atan2(Math.sqrt(a(city1, city2)),Math.sqrt(1-a(city1,city2)));
	}

	//private static double a(City city1, City city2) {
	//	double a = Math.sin( Math.pow(((city1.getX() - city2.getX())/2),2) ) + Math.cos(city1.getX()) * Math.cos(city2.getX())
	//			* Math.sin( Math.pow(((city1.getY() - city2.getY())/2),2) );
	//	return a;
	//}
	
	private static double a(City city1, City city2) {
		double a = Math.sin( Math.pow(((rad(city1.getX()) - rad(city2.getX()))/2),2) )
				+ Math.cos(rad(city1.getX())) * Math.cos(rad(city2.getX()))
				* Math.sin( Math.pow(((rad(city1.getY()) - rad(city2.getY()))/2),2) );
		return a;
	}
	
	private static double rad(double g) {
		return g * Math.PI / 180;
	}
	

	public static boolean esFactible(int[] elements,Connections connections) {
		for(int i = 0; i < elements.length - 1;i++) {
			if(connections.getDistance(elements[i], elements[i+1]) == -1.0)
				return false;
		}
		return true;
		
	}
	
	public static double normalizer(int[] elements, Connections connections) {
		ArrayList<Double> normalize = new ArrayList<Double>();
		double currentMax;
		for(int i = 0; i < elements.length; i++) {
			currentMax = 0;
			
			for(int j = 0; j < elements.length; j++) {
				if(j <= i || i == j+1 )
					continue;
				if(currentMax < connections.getDistance(elements[i], elements[j])) 
					currentMax = connections.getDistance(elements[i], elements[j]);		
			}	
			normalize.add(currentMax);
		}
		
		double counter = 0.0;
		for(Double d: normalize)
			counter += d;
		
		return counter;
	}
	
	public static double funcionDeCosto(int[] permutation, Connections connections, World world, double normal) {
		double total = 0.0;
		for(int i = 0; i < permutation.length; i++) 
			for(int j = 0; j < permutation.length; j++) {
				total += calculateDistance(connections, world.getCity(permutation[i]),
											world.getCity(permutation[j]), connections.findMax(permutation));
			}

		return total/normal;
	}
	
}
