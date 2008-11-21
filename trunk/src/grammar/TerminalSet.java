package grammar;

import java.util.*;
import scanner.TokenRecord;

public class TerminalSet {
	
	private Vector<String> terminals;
	
	public TerminalSet() {
		terminals = new Vector<String>();
	}
	
	public boolean isMemberOf(TokenRecord t) {
		return false;
	}
	
	public void unionSets(TerminalSet a, TerminalSet b) {
		
	}
}// end class TerminalSet
