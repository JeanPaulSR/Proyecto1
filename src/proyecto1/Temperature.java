package proyecto1;

import java.util.Random;

public class Temperature {
    private int seed;
    private double epsilonp;
    private World world;
    private Connections connections;
	
    private CostFunction cf = new CostFunction();
	
    public Temperature(int seed, double epsilonp, Connections connections,
		       World world) {
	this.seed = seed;
	this.epsilonp = epsilonp;
	this.connections = connections;
	this.world = world;
    }
    public double inicialTemperature(Solutions solution, double t,
				     double percentage, double normal) {
	double p = acceptedPercentage(solution, t, normal);
	if(Math.abs(percentage - p) <= epsilonp) 
	    return t;
		 
			 
	double t1; double t2;
	if(p < percentage) {
	    do {
		t = t * 2;
		p = acceptedPercentage(solution, t, normal);
	    }while(p < percentage);
	    t1 = t / 2;
	    t2 = t;
	}else {
	    do {
		t = t / 2;
		p = acceptedPercentage(solution, t, normal);
	    }while( p > percentage);
	    t1 = t;
	    t2 = 2 * t;
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

    private double acceptedPercentage(Solutions solution, double t,
				      double normal) {
	double c = 0;
	int n = 1000;
	Solutions newSolution;
	for(int i = 0; i < n; i++) {
	    newSolution = solution.newSolution(seed);
	    if(cf.funcionDeCosto(newSolution, connections, world, normal) < 
	       cf.funcionDeCosto(solution, connections, world, normal)) {
		c++;
		newSolution = solution;
	    }
	}
	return c/n;
    }
	

}
