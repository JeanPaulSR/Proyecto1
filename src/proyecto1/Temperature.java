package proyecto1;

import java.util.Random;

public class Temperature {
	private int seed;
	private double epsilonp;
	private World world;
	private Connections connections;
	
	private CostFunction cf = new CostFunction();
	
	public Temperature(int seed, double epsilonp, Connections connections, World world) {
		this.seed = seed;
		this.epsilonp = epsilonp;
		this.connections = connections;
		this.world = world;
	}
	 public double inicialTemperature(int[] solucion, double t, double percentage, double normal) {
		 double p = acceptedPercentage(solucion, t, normal);
		 if(Math.abs(percentage - p) <= epsilonp) 
			 return t;
		 
			 
		 double t1; double t2;
		 if(p < percentage) {
			 do {
				 t = t * 2;
				 p = acceptedPercentage(solucion, t, normal);
			 }while(p < percentage);
			 t1 = t / 2;
			 t2 = t;
		 }else {
			 do {
				 t = t / 2;
				 p = acceptedPercentage(solucion, t, normal);
			 }while( p > percentage);
			 t1 = t;
			 t2 = 2 * t;
		 }
		 
		 return binarySearch(solucion, t1, t2, percentage, normal);
	 }

	private double binarySearch(int[] solucion, double t1, double t2, double percentage, double normal) {
		double tm = (t1 + t2)/2;
		if(t2 - t1 < epsilonp)
			return tm;
		double p = acceptedPercentage(solucion, tm, normal);
		if(Math.abs(percentage - p) < epsilonp)
			return tm;
		if(p > percentage)
			return binarySearch(solucion, t1, tm, percentage, normal);
		else 
			return binarySearch(solucion, tm, t2, percentage, normal);
	}

	private double acceptedPercentage(int[] solucion, double t, double normal) {
		double c = 0;
		int n = 1000;
		int[] newSolucion;
		for(int i = 0; i < n; i++) {
			newSolucion = newSolucion(solucion);
			if(cf.funcionDeCosto(newSolucion, connections, world, normal) < 
					cf.funcionDeCosto(solucion, connections, world, normal)) {
				c++;
				newSolucion = solucion;
			}
		}
		return c/n;
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
}
