package proyecto1;

import java.util.Random;
    
public class Solutions{
    private int[] solution;
    
    public Solutions(int[] solution){
	this.solution = solution;
    }

    public int length(){
	return solution.length;
    }

    public int get(int i){
	return this.solution[i];
    }

    public void set(int i, int j){
	this.solution[i] = j;
    }
    
    public Solutions newSolution(int seed) {
	Random rd = new Random(seed); 
	int s = rd.nextInt(length());
	int t = rd.nextInt(length());
	int aux = get(s);
	set(s, get(t));
        set(t, aux);
	return this;
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
		s += get(i);
	    else
		s += get(i) + ", ";
	}
	return s;

    }

	   
}
