/**
 * @author Pree
 * Represent production object
 */

package ll1grammar;

import java.util.Vector;

public class Production {
	private Symbol lhs;
	private Vector<Symbol> rhs;
	
	Production(Symbol lhs, Vector<Symbol> rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}
	
	public Symbol getLHS() {
		return lhs;
	}
	
	public Vector<Symbol> getRHS() {
		return rhs;
	}
	
	public int getRHSLength() {
		return rhs.size();
	}
	
	public Vector<Symbol> getNonTerminals() {
		Vector<Symbol> retVal = new Vector<Symbol>();
		
		if(lhs.getType().equals("n"))
			retVal.add(lhs);
		
		for(int i=0; i<rhs.size(); i++) 
			if(rhs.elementAt(i).getType().equals("n"))
				retVal.add(rhs.elementAt(i));
		return retVal;
	}
	
	public Vector<Symbol> getTerminals() {
		Vector<Symbol> retVal = new Vector<Symbol>();
		
		if(lhs.getType().equals("t"))
			retVal.add(lhs);
		
		for(int i=0; i<rhs.size(); i++) 
			if(rhs.elementAt(i).getType().equals("t"))
				retVal.add(rhs.elementAt(i));
		return retVal;
	}
	
	public Vector<Symbol> getActionSymbols() {
		Vector<Symbol> retVal = new Vector<Symbol>();
		
		for(int i=0; i<rhs.size(); i++) 
			if(rhs.elementAt(i).getType().equals("action"))
				retVal.add(rhs.elementAt(i));
		return retVal;
	}
	
	public String toString() {
		return this.lhs + " -> " + this.rhs;
	}
}// end class Production