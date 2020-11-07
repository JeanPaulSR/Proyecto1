package proyecto1;

import java.lang.Math;

public class Connections {

    public double[][] connections;
    @SuppressWarnings("unused")
    private int size;
    private double max = 4707944.87;
	
    public Connections(int size) {
	this.size = size;
	connections = new double[size][size];
	for(int i = 0; i < size; i++) {
	    for(int j = 0; j < size; j++) {
		connections[i][j] = -1.0;
	    }

	}
    }
	
    public void addConnection(int city1, int city2, double distance) {
	this.connections[city1][city2] = distance;
	this.connections[city2][city1] = distance;
    }
	
    public double getGlobalMax() {
	return this.max;
    }
	
    public double findMax(Solutions permutation) {
	double max = -1.0;
	for(int i = 0; i < permutation.length() ; i++) {
	    for(int j = 0; j < permutation.length() ; j++) {
		max = Math.max(max,this.connections
			       [permutation.get(i)][permutation.get(j)]);
	    }
	}
	return max;
    }
	
    public double getDistance(int city1, int city2) {
	return this.connections[city1][city2];
    }
}
