package proyecto1;

import java.util.Random;
    
public class Solutions{
    private int[] solution;
    private int seed;
    
    Random rd; 
    
    public Solutions(int[] solution,int seed){
	this.solution = solution;
	this.seed = seed;
	rd = new Random(seed);
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
    
    public Solutions newSolution() {
	Solutions auxSolution = new Solutions(solution,seed);
	int[] deepCopy = new int[solution.length];
	for(int i = 0; i < solution.length; i++)
	    deepCopy[i] = solution[i];
	
	auxSolution.solution = deepCopy;
	int s = rd.nextInt(auxSolution.length());
	int t = s;
	while(s == t)
	    t = rd.nextInt(auxSolution.length());
	int aux = auxSolution.get(s);
	int aux2 = auxSolution.get(t);
	auxSolution.set(s, aux2);
        auxSolution.set(t, aux);
	
	auxSolution.rd = rd;
	
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
