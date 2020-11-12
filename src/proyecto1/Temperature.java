package proyecto1;

import java.util.Random;

public class Temperature {
    private int seed;
    private double epsilonp;
    private int n;
    private World world;
    private Connections connections;
        
	
    public Temperature(int seed, double epsilonp, Connections connections,
		       World world, int n) {
	this.seed = seed;
	this.epsilonp = epsilonp;
	this.n = n;
	this.connections = connections;
	this.world = world;
    }
    public double initialTemperature(Solutions solution, double temperature,
				     double percentage, double normal) {
	double p = acceptedPercentage(solution, temperature, normal);
	
	if(Math.abs(percentage - p) <= epsilonp) 
	    return temperature;     
			 
	double t1; double t2;
	if(p < percentage) {
	    do {
		temperature = temperature * 2;
		p = acceptedPercentage(solution, temperature, normal);
	       
	    }while(p < percentage);
	    t1 = temperature / 2;
	    t2 = temperature;
	}else {
	    do {
		temperature = temperature / 2;
		p = acceptedPercentage(solution, temperature, normal);
	    }while( p > percentage);
	    t1 = temperature;
	    t2 = 2 * temperature;
	}
	return binarySearch(solution, t1, t2, percentage, normal);
    }

    private double binarySearch(Solutions solution, double t1, double t2,
				double percentage, double normal) {
	double tm = (t1 + t2)/2;
	if(t2 - t1 < epsilonp)
	    return tm;
	
	double p = acceptedPercentage(solution, tm, normal);
	if(Math.abs(percentage - p) < epsilonp)
	    return tm;
	
	if(p > percentage)
	    return binarySearch(solution, t1, tm, percentage,
				normal);
	else 
	    return binarySearch(solution, tm, t2, percentage, normal);
    }

    private double acceptedPercentage(Solutions solution, double temperature,
				      double normal) {
	double c = 0;
	
	for(int i = 0; i < n; i++) {
	    Solutions newSolution = solution;
	    newSolution = newSolution.newSolution();
	    
	    if(newSolution.getCostFunction() <= solution.getCostFunction()
	       + temperature) {
		c++;
	        solution = newSolution;
	    }
	}
	return c/n;
    }
	

}
