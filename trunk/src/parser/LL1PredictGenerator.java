/**
 * @author Pree Thiengburanathum
 * preenet@gmail.com
 * UC Assignment 8
 * due 11/03/08
 * LL1PredictGenerator.java
 */

package parser;
import ll1grammar.*;
import java.io.*;
import java.util.*;



public class LL1PredictGenerator {
	private MarkedVocabulary m;
	private Grammar g;
	private MarkedVocabulary derivesLambda;
	private Vector<TermSet> firstSet;
	private Vector<TermSet> followSet;
	private Symbol lambda;

	public LL1PredictGenerator(Grammar g) {
		this.g = g;

		// initialize first and follow ahd predict sets
		firstSet = new Vector<TermSet>();
		for (int i = 0; i < g.getVocabulary().size(); i++)
			firstSet.add(new TermSet(g.getVocabulary().elementAt(i)));
		
		followSet = new Vector<TermSet>();
		for (int i = 0; i < g.getNonTerminals().size(); i++)
			followSet.add(new TermSet(g.getNonTerminals().elementAt(i)));
		
		lambda = new Symbol("lambda");
		
		markLambda();
		fillFirstSet();
		fillFollowSet();
	}

	public void markLambda() {
		boolean changes = true;
		boolean rhsDerivesLambda;
		Production p = null;

		// initially, nothing is marked.
		derivesLambda = new MarkedVocabulary(g.getVocabulary());

		while (changes) {
			changes = false;
			for (int i = 0; i < g.getNumProduction(); i++) {
				p = g.getProduction().elementAt(i);
				rhsDerivesLambda = true;
				for (int j = 0; j < p.getRHSLength(); j++)
					rhsDerivesLambda = rhsDerivesLambda && derivesLambda.getMark(p.getRHS().elementAt(j));

				if (rhsDerivesLambda && !derivesLambda.getMark((p.getLHS()))) {
					changes = true;
					derivesLambda.setMark(p.getLHS(), true);
				}
			}
		}

		//System.out.println("\nDerivesLambda after MarkLambda: ");
		//System.out.println(derivesLambda);
	}

	/**
	 * This method will be use in fillFirstSet and fillFollowSet methods, to be able 
	 * to compute the first set and return the term set
	 * it check if the length of string of symbol alpha, if the length is zero, then
	 * it will return the set of lambda. 
	 * 
	 * if the length of alpha is greater than zero, then
	 * the result will be the union of the current result subtract the set of lambda. 
	 * until it iterates to the size of alpha, it will union the result with the set of lambda
	 * @param alpha
	 * @return
	 */
	public TermSet computeFirst(Vector<Symbol> alpha) {
		//System.out.println("alpha is " + alpha);
		TermSet result = new TermSet();
		TermSet alphaTmp;
		int i, k;
		k = alpha.size();

		if (k == 0)
			result.setSetOf(new Symbol("lambda"));
		else {
			result.setTerminalSymbols(firstSet.get(indexOf(alpha.elementAt(0))).getTerminalSymbols());
			
			for (i = 1; i < k && firstSet.get(indexOf(alpha.elementAt(i - 1))).hasMember(lambda); i++) {
				alphaTmp = new TermSet();
				alphaTmp.setTerminalSymbols(firstSet.get(indexOf(alpha.elementAt(i))).getTerminalSymbols());	
				if(!alphaTmp.hasMember(lambda)) {
					result.subtract(lambda);
					result.unionTermset(alphaTmp);
				}
				else 
					result.unionTermset(subtract(alphaTmp, lambda));
				//System.out.println("result is " + result);
			}

			if (i == k && firstSet.get(indexOf(alpha.elementAt(k - 1))).hasMember(lambda))
				result.unionSetOf(lambda);
		}
		//System.out.println("computeFirst return result = " + result);
		return result;
	}

	/**
	 * initialize first set, the set of all terminals that can begin a
	 * sentential form derivable from alpha.
	 */
	public void fillFirstSet() {
		//System.out.println("Call fillFirstSet()");
		//System.out.println("First set: " + firstSet);
		NonTerminal A;
		Terminal a;
		Production p;
		TermSet tmp;
		boolean changes;
		
		// for A in nonterminal
		//System.out.println("\nfor A in nonterminals (First loop):");
		for (int i = 0; i < g.getNonTerminals().size(); i++) {
			A = (NonTerminal) g.getNonTerminals().elementAt(i);
			if (derivesLambda.getMark(A))
				firstSet.get(indexOf(A)).setSetOf(lambda);
			else
				firstSet.get(indexOf(A)).setEmptySet();
		}

		//System.out.println("First set: " + firstSet);
		
		//for a in terminal
		//System.out.println("\nfor a in terminals (Second(nested) loop):");
		for (int i = 0; i < g.getTerminals().size(); i++) {
			a = (Terminal) g.getTerminals().elementAt(i);
			firstSet.get(indexOf(a)).setSetOf(a);
			for(int j = 0; j < g.getNonTerminals().size(); j++) {
				A = (NonTerminal) g.getNonTerminals().elementAt(j);
				if(isExistProduction(A, a)) {
					tmp = firstSet.get(indexOf(A));
					tmp.unionSetOf(a);
					firstSet.get(indexOf(A)).setTermSet(tmp);
				}
			}
		}
		//System.out.println("First set: " + firstSet);
		
		//System.out.println("\nThird loop:");
		
		do {
			changes = false;
			int fsize = getSize(firstSet);
			for(int i=0; i<g.getNumProduction(); i++) {
				p = g.getProduction().elementAt(i);
				//System.out.println("\n"+firstSet.get(indexOf(p.getLHS())));
				tmp = computeFirst(p.getRHS());
				firstSet.get(indexOf(p.getLHS())).unionTermset(tmp);
				//System.out.println("union " + firstSet.get(indexOf(p.getLHS())));
				
				if(fsize != getSize(firstSet)) // if first set has changed.
					changes = true;
			}
			
		} while(changes);
	
		//System.out.println("\nFirst set:\n " + firstSet);
	}
	
	/**
	 * search through the list of productions in G for A,
	 * if the first terminal of that production equal to a
	 * then return true. 
	 * @param A
	 * @param a
	 * @return
	 */
	public boolean isExistProduction(NonTerminal A, Terminal a) {
		Production p;
		for(int i=0; i<g.getNumProduction(); i++) {
			p = g.getProduction().elementAt(i);
			if(p.getLHS().equals(A) && p.getRHSLength() > 0) {
				if(p.getRHS().elementAt(0).equals(a))
					return true;
			}
		}
		return false;
	}
	
	public TermSet subtract(TermSet a, Symbol b) {
		TermSet retVal = new TermSet();
		retVal.setTerminalSymbols(a.getTerminalSymbols());
		if(retVal.contains(b) != -1) 
			retVal.getTerminalSymbols().remove(retVal.contains(b));

		return retVal;
	}
	
	public int getSize(Vector<TermSet> t) {
		int retVal = 0;
		for(int i=0; i<t.size(); i++) {
			retVal += t.elementAt(i).getTerminalSymbols().size();
		}
		return retVal;
	}

	public void fillFollowSet() {
		NonTerminal A, B;
		Vector<Symbol> beta;
		boolean changes;
		
		for(int i=0; i < g.getNonTerminals().size(); i++) {
			A = (NonTerminal) g.getNonTerminals().elementAt(i);
			followSet.get(indexOff(A)).setEmptySet();
		}
		
		followSet.get(indexOff(g.getStartSymbol())).setSetOf(lambda);
		
		Production p;
		
		do {
			changes = false;
			int fsize = getSize(followSet);
		// for each production
			for(int i=0; i < g.getNumProduction(); i++) {
				
				p = g.getProduction().elementAt(i);
				A = (NonTerminal) g.getProduction().elementAt(i).getLHS();
				
				// for each occurrence of B in its right hand side.
				for(int j=0; j < p.getRHSLength(); j++) {
					// case A -> xBy
					if(p.getRHS().elementAt(j).getType().equals("n") && (j+1) < p.getRHSLength()) {
						B = (NonTerminal) p.getRHS().elementAt(j);
						//System.out.println("production " + (i+1) + " " + B);
						
						beta = new Vector<Symbol> ();
						//for(int k=(j+1); k < p.getRHSLength(); k++)
							beta.add(p.getRHS().elementAt(j+1));
						
						followSet.get(indexOff(B)).unionTermset(subtract(computeFirst(beta), lambda));
						
						
						if(computeFirst(beta).hasMember(lambda)) 
							followSet.get(indexOff(B)).unionTermset(followSet.get(indexOff(A)));
	 				}
					// case A ->xB
					if(p.getRHS().elementAt(j).getType().equals("n") && (j+1) == p.getRHSLength()) {
						B = (NonTerminal) p.getRHS().elementAt(j);
						followSet.get(indexOff(B)).unionTermset(followSet.get(indexOff(A)));
					}

					if(fsize != getSize(followSet)) // if follow set has changed.
						changes = true;
	 			}
			}
		} while(changes);
		System.out.println("\nFollow Set:\n" + followSet);
	}
	
	public TermSet getFirstSet(Symbol s) {
		TermSet retVal = null;
		for(int i=0; i<firstSet.size(); i++) {
			if(firstSet.elementAt(i).getSymbol().equals(s))
				retVal =  firstSet.elementAt(i);
		}
		return retVal;
	}
	
	public TermSet getFollowSet(Symbol s) {
		TermSet retVal = null;
		for(int i=0; i<followSet.size(); i++) {
			if(followSet.elementAt(i).getSymbol().equals(s))
				retVal =  followSet.elementAt(i);
		}
		return retVal;
	}

	private int indexOf(Symbol s) {
		for (int i = 0; i < firstSet.size(); i++)
			if (s.equals(firstSet.elementAt(i).getSymbol()))
				return i;
		return -1;
	}
	
	private int indexOff(Symbol s) {
		for (int i = 0; i < followSet.size(); i++)
			if (s.equals(followSet.elementAt(i).getSymbol()))
				return i;
		return -1;
	}

	private static void usageAndExit() {
		System.out.println("Usage: LL1PredictGenerator <input_file>");
		System.out.println("     : for help, view README file");
		System.out.println("     : preenet@gmail.com");
		System.exit(0);
	}
}// end class LL1PredictGenerator

// a set of terminal/lambda
class TermSet {
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

/**
 * this class declare an object MarkedVocabularyRecord
 */

class MarkedVocabulary {
	private ArrayList<MarkedVocabularyRecord> m;

	MarkedVocabulary(Vector<Symbol> vocabulary) {
		m = new ArrayList<MarkedVocabularyRecord>();

		for (int i = 0; i < vocabulary.size(); i++)
			m.add(new MarkedVocabularyRecord(vocabulary.elementAt(i), false));
		setMark(new Symbol("lambda"), true);
	}

	public boolean getMark(Symbol t) {
		for (int i = 0; i < m.size(); i++) {
			if (m.get(i).getSymbol().equals(t))
				return m.get(i).getMark();
		}
		return false;
	}

	public void setMark(Symbol t, boolean b) {
		for (int i = 0; i < m.size(); i++) {
			if (m.get(i).getSymbol().equals(t))
				m.get(i).setMark(b);
		}
	}

	public String toString() {
		return this.m.toString();
	}

}// end class MarkedVocabulary

class MarkedVocabularyRecord {
	private boolean mark;
	private Symbol s;

	MarkedVocabularyRecord(Symbol s, boolean mark) {
		this.mark = mark;
		this.s = s;
	}

	public Symbol getSymbol() {
		return this.s;
	}

	public boolean getMark() {
		return this.mark;
	}

	public void setMark(boolean b) {
		this.mark = b;
	}

	public String toString() {
		return s + " = " + mark;
	}
}// end class MarkedVocabularyRecord

