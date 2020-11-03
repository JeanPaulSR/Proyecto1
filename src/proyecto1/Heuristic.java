package proyecto1;

import java.util.Random;

public class Heuristic {

	private double temperature;
	private double cooling;
	private double epsilon;
	private double l;
	private int[] minimumSolution;
	private World world;
	private Connections connections;
	
	private int seed;

	private CostFunction cf = new CostFunction();
	
	public Heuristic (double temperature, double cooling, double epsilon, double l,
			 World world, Connections connections, int seed) {
		this.temperature = temperature;
		this.cooling = cooling;
		this.epsilon = epsilon;
		this.l = l;
		this.world = world;
		this.connections = connections;
		this.seed = seed;
		
	}
	
	public int[] aceptacionPorUmbrales(int[] solution, double normal) {
		double p = 0;
		double costResult = cf.funcionDeCosto(solution, connections, world, normal);
		
		do {
			double q = Double.POSITIVE_INFINITY;
			
			do {
				q = p;
				Pair pair = calculateLot(solution, normal);
				p = pair.p;
				solution = pair.solution;
				
				if(cf.funcionDeCosto(solution, connections, world, normal) < 
						costResult) {
					minimumSolution = solution;
					costResult = cf.funcionDeCosto(solution, connections, world, normal);
					
				}
			}while(p <= q);
			
			temperature = cooling * temperature;
		}while(temperature > epsilon);
		
		return minimumSolution;
	}
	
	public Pair calculateLot(int[] solution, double normal) {
		int c = 0;
		double r = 0.0;
		double costResult = cf.funcionDeCosto(solution, connections, world, normal);
		
		while (c < l) {
			int[] newSolution = newSolucion(solution);
			double newCostResult = cf.funcionDeCosto(newSolution, connections, world, normal);
			
			if(newCostResult < costResult + temperature) {
				solution = newSolution;
				costResult = newCostResult;
				c++;
				r += costResult ;
			}
		}
		Pair pair = new Pair(solution, r/l);
		return pair;
	}

	private int[] newSolucion(int[] solucion) {
		Random rd = new Random(seed); 
		int s = rd.nextInt(solucion.length);
	    int t = rd.nextInt(solucion.length);
	    int aux = solucion[s];
	    solucion[s] = solucion[t];
	    solucion[t] = aux;
		return solucion;
	}
	
	public boolean esFactible(int[] elements,Connections connections) {
		for(int i = 0; i < elements.length - 1;i++) {
			if(connections.getDistance(elements[i], elements[i+1]) == -1.0)
				return false;
		}
		return true;
		
	}
	
	public boolean esVecino(int[] permutation1, int permutation2[]) {
		if(permutation1.length != permutation2.length)
			return false;
		
		int s = -1; int t = -1;
		for(int i = 0; i < permutation1.length; i++) {
			if(permutation1[i] == permutation2[i])
				continue;
			if(s == -1)
				s = i;
			else if(t == -1)
				t = i;	
			else
				return false;
		}
		
		if(permutation1[t] == permutation2[s] && permutation1[s] == permutation2[t])
			return true;
		return false;
	}
	
	 public class Pair {         
		    public int[] solution;
		    public double p;

		    public Pair(int[] solution, double p) {         
		        this.solution= solution;
		        this.p= p;
		     }
	 }
	 

}
