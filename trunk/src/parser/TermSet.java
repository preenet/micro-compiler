package parser;

import java.util.Vector;

import ll1grammar.Symbol;

public class TermSet {
	private Symbol name;
	private Vector<Symbol> terminals;

	TermSet() {
		terminals = new Vector<Symbol>();
	}

	TermSet(Symbol s) {
		terminals = new Vector<Symbol>();
		name = s;
	}

	/**
	 * reference this set of terminal to the set of t e.g. firstSet(A) =
	 * setOf(a);
	 * 
	 * @param t
	 */
	public void setSetOf(Symbol t) {
		terminals.clear();
		terminals.add(t);
	}

	/**
	 * empty this set of terminal symbols.
	 */
	public void setEmptySet() {
		terminals.clear();
	}

	/**
	 * reference this TermSet that the TermSet t e.g. firstSet(A) = firstSet(B);
	 * 
	 * @param t
	 */
	public void setTermSet(TermSet t) {
		terminals = t.getTerminalSymbols();
	}

	/**
	 * This method will add the terminal symbols from that TermSet to this set
	 * terminal symbols and remove the duplicate symbols. e.g. firstSet(A) U
	 * firstSet(B);
	 * 
	 * @param that
	 */
	public void unionTermset(TermSet that) {
		Symbol tmp = null;
		for (int i = 0; i < that.getTerminalSymbols().size(); i++) {
			tmp = that.getTerminalSymbols().elementAt(i);

			if (contains(tmp) == -1)
				terminals.add(tmp);
		}
	}

	/**
	 * This method will add the symbol from that set to this set terminal
	 * symbols and remove the duplicate symbols. e.g. firstSet(A) U setOf(a);
	 * 
	 * @param that
	 */
	public void unionSetOf(Symbol that) {
		if (contains(that) == -1)
			terminals.add(that);
	}
	
	public void subtract(Symbol t) {
		if (contains(t) != -1)
			terminals.remove(contains(t));
	}


	/**
	 * check if the symbol t is a member of this set of terminal symbols.
	 * 
	 * @param t
	 * @return boolean
	 */
	public boolean hasMember(Symbol t) {
		if (contains(t) == -1)
			return false;
		return true;
	}
	
	/**
	 * override function of contain, return the index of that symbol
	 * otherwise, return -1 if not found in this set of terminal symbols.
	 * @param t
	 * @return
	 */
	public int contains(Symbol t) {
		for(int i=0; i<terminals.size(); i++) {
			if(t.equals(terminals.elementAt(i)))
				return i;
		}
		return -1;
	}

	public Symbol getSymbol() {
		return name;
	}

	public Vector<Symbol> getTerminalSymbols() {
		return terminals;
	}
	
	public void setTerminalSymbols(Vector<Symbol> t) {
		// deep copy of the vector
		for(int i=0; i<t.size(); i++)
			terminals.add(t.elementAt(i));
	}

	public String toString() {
		return name + " " + terminals + "\n";
	}
}// end class TermSet