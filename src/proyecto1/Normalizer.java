package proyecto1;

import java.util.ArrayList;
import java.util.Collections;

public class Normalizer {

	public double normalize(int[] elements, Connections connections) {
		ArrayList<Double> normalize = new ArrayList<Double>();
		for(int i = 0; i < elements.length; i++) {
			
			for(int j = 0; j < elements.length; j++) {
				if(j <= i  ||i == j+1 )
					continue;
				if(-1.0 != connections.getDistance(elements[i], elements[j])) 
					normalize.add(connections.getDistance(elements[i], elements[j]));		
			}	
		}
		Collections.sort(normalize, Collections.reverseOrder());
		double counter = 0.0;
		for(int i=0; i < elements.length-1; i++)
			counter += normalize.get(i);
		
		return counter;
	}
}
