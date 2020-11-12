package proyecto1;

import java.util.Random;
    
public class Solutions{
    private int[] solution;
    private int seed;
    private double costFunction;
    private Connections connections;
    private double normal;
    private World world;

    
    private CostFunction cf = new CostFunction();
    
    Random rd; 
    
    public Solutions(int[] solution,int seed, Connections connections,
		     World world){
	this.solution = solution;
	this.seed = seed;
	rd = new Random(seed);
	this.connections = connections;
	this.world = world;
	this.costFunction = -1;
    }

    public int length(){
	return solution.length;
    }

    public int get(int i){
	return solution[i];
    }

    public void set(int i, int j){
	this.solution[i] = j;
    }

    public int getSeed(){
	return this.seed;
    }

    public Solutions clone(){
	Solutions auxSolution = new Solutions(solution,seed, connections,world);
	int[] deepCopy = new int[solution.length];
	for(int i = 0; i < solution.length; i++)
	    deepCopy[i] = solution[i];
	
	auxSolution.solution = deepCopy;
	auxSolution.setNormal(normal);
	return auxSolution;
    }
    public Solutions newSolution() {
	//Creating deep copy of solution
	Solutions auxSolution = new Solutions(solution,seed, connections,world);
	int[] deepCopy = new int[solution.length];
	for(int i = 0; i < solution.length; i++)
	    deepCopy[i] = solution[i];
	
	auxSolution.solution = deepCopy;
	auxSolution.setNormal(normal);

	double max = connections.findMax(this);

	//Finding suitiable positions to interchange
	int s = rd.nextInt(auxSolution.length());
	int t = s;
	while(s == t)
	    t = rd.nextInt(auxSolution.length());

	//Multiplying by normal
	double total = auxSolution.costFunction * normal;

	//Deleting edges that connect to the current cities to interchange
	//Additionally taking into account cities that are next to each other
	if(s != 0)
	    auxSolution.costFunction = auxSolution.costFunction -
		cf.calculateDistance(connections,
				     world.getCity(auxSolution.get(s-1)),
				     world.getCity(auxSolution.get(s)),
				     max);

	if(s != auxSolution.length()-1)
	    auxSolution.costFunction = auxSolution.costFunction -
		cf.calculateDistance(connections,
				     world.getCity(auxSolution.get(s)),
				     world.getCity(auxSolution.get(s+1)),
				      max);

	if(t != 0 && t-1 != s)
	    auxSolution.costFunction = auxSolution.costFunction -
		cf.calculateDistance(connections,
				     world.getCity(auxSolution.get(t-1)),
				     world.getCity(auxSolution.get(t)),
				      max);

	if(t != auxSolution.length()-1 && t+1 != s)
	    auxSolution.costFunction = auxSolution.costFunction -
		cf.calculateDistance(connections,
				     world.getCity(auxSolution.get(t)),
				     world.getCity(auxSolution.get(t+1)),
				      max);

	
	//Interchanging positions
	int aux = auxSolution.get(s);
	int aux2 = auxSolution.get(t);
	auxSolution.set(s, aux2);
        auxSolution.set(t, aux);
	auxSolution.rd = rd;

	//Adding new edges that have been formed
	if(s != 0)
	    auxSolution.costFunction = auxSolution.costFunction +
		cf.calculateDistance(connections,
				     world.getCity(auxSolution.get(s-1)),
				     world.getCity(auxSolution.get(s)),
				      max);
	

	if(s != auxSolution.length()-1)
	    auxSolution.costFunction = auxSolution.costFunction +
		cf.calculateDistance(connections,
				     world.getCity(auxSolution.get(s)),
				     world.getCity(auxSolution.get(s+1)),
				      max);

	if(t != 0 && t-1 != s)
	    auxSolution.costFunction = auxSolution.costFunction +
		cf.calculateDistance(connections,
				     world.getCity(auxSolution.get(t-1)),
				     world.getCity(auxSolution.get(t)),
				      max);

	if(t != auxSolution.length()-1 && t+1 != s)
	    auxSolution.costFunction = auxSolution.costFunction +
		cf.calculateDistance(connections,
				     world.getCity(auxSolution.get(t)),
				     world.getCity(auxSolution.get(t+1)),
				      max);

	//Dividing by the normal
	auxSolution.costFunction = total/normal;

	//Returning solution
	return auxSolution;
    }
    
    public boolean esFactible(Connections connections) {
	for(int i = 0; i < length() - 1;i++) {
	    if(connections.getDistance(get(i),
				       get(i+1)) == -1.0)
		return false;
	}
	return true;
		
    }

    	
    public boolean esVecino(Solutions permutation) {
	if(length() != permutation.length())
	    return false;
		
	int s = -1; int t = -1;
	for(int i = 0; i < length(); i++) {
	    if(get(i) == permutation.get(i))
		continue;
	    if(s == -1)
		s = i;
	    else if(t == -1)
		t = i;	
	    else
		return false;
	}
		
	if((get(t) == permutation.get(s)) &&
	   (get(s) == permutation.get(t)))
	    return true;
	return false;
    }

    private void calculateCostFunction(Connections connections, World world,
				       double normal){
	this.costFunction = cf.funcionDeCosto(this, connections, world,
					      normal);
    }

    public void setNormal(double normal){
	this.normal = normal;
    }

    public double getCostFunction(){
	if(this.costFunction == -1)
	    calculateCostFunction(connections, world, normal);
	return this.costFunction;
    }
    
    @Override
    public String toString(){
	String s = "";
	for(int i = 0; i < length(); i++){
	    if(i == length()-1)
		s += (get(i)+1);
	    else
		s += (get(i)+1) + ",";
	}
	return s;

    }

	   
}
