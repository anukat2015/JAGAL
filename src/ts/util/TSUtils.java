package ts.util;

import ts.State;


public class TSUtils {
	
	public static <S extends State> boolean haveSameRelations(S s1, S s2){
		return s1.getIncomingEvents().equals(s2.getIncomingEvents()) && s1.getOutgoingEvents().equals(s2.getOutgoingEvents());
	}

}