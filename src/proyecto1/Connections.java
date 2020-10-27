package proyecto1;

import java.util.ArrayList;
import java.lang.Math;

public class Connections {

	public ArrayList<ArrayList<Double>> connections = new ArrayList<ArrayList<Double>>();
	@SuppressWarnings("unused")
	private int size;
	private double max = 4707944.87;
	
	public Connections(int size) {
		this.size = size;
		for(int i = 0; i < size; i++) {
			ArrayList<Double> newConnection = new ArrayList<Double>();
			for(int j = 0; j < size; j++) {
				newConnection.add(-1.0);
			}
			this.connections.add(newConnection);
		}
	}
	
	public void addConnection(int city1, int city2, double distance) {
		this.connections.get(city1).set(city2, distance );
		this.connections.get(city2).set(city1, distance );
	}
	
	public double getGlobalMax() {
		return this.max;
	}
	
	public double findMax(int[] permutation) {
		double max = -1.0;
		for(int i = 0; i < permutation.length ; i++) {
			for(int j = 0; j < permutation.length ; j++) {
				max = Math.max(max,this.connections.get(permutation[i]).get(permutation[j]));
			}
		}
		return max;
	}
	
	public double getDistance(int city1, int city2) {
		return this.connections.get(city1).get(city2);
	}
}