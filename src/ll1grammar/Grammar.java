/**
 * @author Pree Thiengburanathum
 * preenet@gmail.com
 * UC Assignment 8
 * due 11/03/08
 * Grammar.java
 */

/**
 *  A grammar G object is represented in this class 
 */

package ll1grammar;
import java.util.Vector;

public class Grammar {
	private Symbol startSymbol;
	private Vector<Symbol> terminals;
	private Vector<Symbol> nonterminals;
	private Vector<Symbol> vocabulary;
	private Vector<Production> prods;
	
	public Grammar(Symbol startSymbol) {
	 	this.startSymbol = startSymbol;
		prods = new Vector<Production>();
	}
	
	public void set(Vector<Symbol> terminals, Vector<Symbol> nonterminals, Vector<Symbol> vocabulary) {
		this.nonterminals = nonterminals;
		this.terminals = terminals;
		this.vocabulary = vocabulary;
	}
	
	public void addProduction(Symbol lhs, Vector<Symbol> rhs) {
		prods.add(new Production(lhs, rhs));
	}
	
	public Symbol getStartSymbol() {
		return startSymbol;
	}
	
	public Vector<Symbol> getVocabulary() {
		return vocabulary;
	}
	
	public Vector<Symbol> getTerminals() {
		return terminals;
	}
	
	public Vector<Symbol> getNonTerminals() {
		return nonterminals;
	}
	
	public Vector<Production> getProduction() {
		return prods;
	}
	
	public int getNumProduction() {
		return prods.size();
	}
	
	public void dumpGrammar() {
		for(int i=0; i<prods.size(); i++)
			System.out.println(i+1 + " " + prods.elementAt(i).getLHS() + " -> " + prods.elementAt(i).getRHS());
		System.out.println();
	}
}// end class Grammar




