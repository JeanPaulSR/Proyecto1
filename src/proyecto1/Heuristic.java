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
	
    private int seed;

    private CostFunction cf = new CostFunction();
	
    public Heuristic (double temperature, double cooling, double epsilon,
		      double l, World world, Connections connections, int seed){
	this.temperature = temperature;
	this.cooling = cooling;
	this.epsilon = epsilon;
	this.l = l;
	this.world = world;
	this.connections = connections;
	this.seed = seed;
		
    }
	
    public Solutions aceptacionPorUmbrales(Solutions solution, double normal) {
	double p = 0;
	double costResult = cf.funcionDeCosto(solution, connections, world,
					      normal);

		
	do {
	    double q = Double.POSITIVE_INFINITY;
			
	    do {
		q = p;
		Pair pair = calculateLot(solution, normal);
		p = pair.p;
		solution = pair.solution;
				
		if(cf.funcionDeCosto(solution, connections, world, normal) < 
		   costResult && solution.esFactible(connections)) {
		    minimumSolution = solution;
		    costResult = cf.funcionDeCosto(solution, connections,
						   world, normal);
					
		}
	    }while(p <= q);
			
	    temperature = cooling * temperature;
	}while(temperature > epsilon);
	
	return minimumSolution;
    }
	
    public Pair calculateLot(Solutions solution, double normal) {
	int c = 0;
	double r = 0.0;
	double costResult = cf.funcionDeCosto(solution, connections, world,
					      normal);
        double maximumIntents = Math.pow(l, 2);
	int i = 0;
	do {
	    if(i > maximumIntents){
		break;
	    }
	    Solutions newSolution = solution.newSolution();
	    double newCostResult = cf.funcionDeCosto(newSolution, connections,
						     world, normal);
			
	    if(newCostResult < costResult + temperature) {
		solution = newSolution;
		costResult = newCostResult;
		c++;
		r += costResult ;
	    }
	    i++;
	}while (c < l);
	//Pair pair = new Pair(solution, r/l);
	return new Pair(solution, r/l);//pair;
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
