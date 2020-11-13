package proyecto1;

import java.util.Random;

public class Heuristic {

    private double temperature;
    private double cooling;
    private double epsilon;
    private double l;
    private Solutions minimumSolution;
    private World world;
    private Connections connections;

    private CostFunction cf = new CostFunction();
	
    public Heuristic (double temperature, double cooling, double epsilon,
		      double l, World world, Connections connections){
	this.temperature = temperature;
	this.cooling = cooling;
	this.epsilon = epsilon;
	this.l = l;
	this.world = world;
	this.connections = connections;
		
    }
	
    public Solutions aceptacionPorUmbrales(Solutions solution, double normal) {
	double p = 0;
	double costResult = solution.getCostFunction();

		
	do {
	    double q = Double.POSITIVE_INFINITY;
			
	    do {
		q = p;
		Pair pair = calculateLot(solution, normal);
		p = pair.p;
	        
		solution = pair.solution;
				
		if(solution.getCostFunction() < costResult) {
		    minimumSolution = solution;
		    costResult = solution.getCostFunction();
					
		}
	    }while(p <= q && p > 0);

	    temperature = cooling * temperature;
	}while(temperature > epsilon);
	
	return minimumSolution;
    }
	
    public Pair calculateLot(Solutions solution, double normal) {
	int c = 0;
	double r = 0.0;
	double costResult = solution.getCostFunction();
        //double maximumIntents = Math.pow(solution.length(),2)/2;
	double maximumIntents = l * 10;
	int i = 0;
	boolean escaped = false;
	do {
	    if(i > maximumIntents){
		escaped = true;
		break;
	    }
	    Solutions newSolution = solution.newSolution();
	    double newCostResult = newSolution.getCostFunction();
	    if(newCostResult < costResult + temperature) {
		solution = newSolution;
		costResult = newCostResult;
		c++;
		r += costResult ;
	    }
	    i++;
	}while (c < l);
        if(escaped)
	    return new Pair(solution, r/c);
	return new Pair(solution, r/l);
    }

	
    public class Pair {         
	public Solutions solution;
	public double p;

	public Pair(Solutions solution, double p) {         
	    this.solution= solution;
	    this.p= p;
	}
    }
	 

}
