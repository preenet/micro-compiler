/**
 * @author Pree Thiengburanathum
 * preenet@gmail.com
 * GrammarAnalyzer.java
 */

package parser;
import ll1grammar.*;


import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

import uc.ExternalFile;

public class GrammarAnalyzer {
	private ExternalFile extFile;
	private Grammar g;
	private Vector<Symbol> vocabulary;
	private Vector<Symbol> terminals;
	private Vector<Symbol> nonterminals;

	public GrammarAnalyzer(String gramFile) throws IOException {
		extFile = new ExternalFile(gramFile);
		initializeGrammar();
	}

	private void initializeGrammar() throws IOException {
		boolean hasStartSymbol = false;
		StringTokenizer tokenizer;
		String dataLine = "";
		String tempToken = "";
		Symbol tempSymbol;
		Symbol startSymbol;
		Symbol lhs = null;
	
		Vector<Symbol> rhs = new Vector<Symbol>();
		vocabulary = new Vector<Symbol>();
		nonterminals = new Vector<Symbol>();
		terminals = new Vector<Symbol>();

		while (!extFile.havehitEOF()) {
			dataLine = extFile.readLine();
			tokenizer = new StringTokenizer(dataLine);
			
			
			// read the left hand side (LHS)
			tempToken = tokenizer.nextToken();
			
			if (isNonTerminal(tempToken)) {
				/* non terminal has to be in from of <symbol>, <sym bol> and such */
				if (!tempToken.endsWith(">"))
					tempToken += " " + tokenizer.nextToken();
				tempSymbol = new NonTerminal(tempToken);
				addSymbol(tempSymbol, nonterminals);
			}
			else {
				tempSymbol = new Terminal(tempToken);
				addSymbol(tempSymbol, terminals);
			}
			
			if(!tempSymbol.equals(new Symbol("lambda"))) {
				lhs = tempSymbol;
				addSymbol(lhs, vocabulary);
			}
			
			
			if (!hasStartSymbol) {
				startSymbol = lhs;
				hasStartSymbol = true;
				g = new Grammar(startSymbol);
			}
			
			// skip the assignment operator "->"
			tokenizer.nextToken();

			// read the right hand side(RHS)
			while (tokenizer.hasMoreTokens()) {
				tempToken = tokenizer.nextToken();

				if (isNonTerminal(tempToken)) {
					if (!tempToken.endsWith(">"))
						tempToken += " " + tokenizer.nextToken();
					tempSymbol = new NonTerminal(tempToken);
					addSymbol(tempSymbol, nonterminals);
				} 
				else {
					tempSymbol = new Terminal(tempToken);
					if(!tempSymbol.equals(new Symbol("lambda")))
						addSymbol(tempSymbol, terminals);
				}
				
				if(!tempSymbol.equals(new Symbol("lambda"))) {
					rhs.add(tempSymbol);
					addSymbol(tempSymbol, vocabulary);
				}
			}
			
			g.addProduction(lhs, rhs);
			lhs = null;
			rhs = new Vector<Symbol>();
		}
		g.set(terminals, nonterminals, vocabulary);
	}
	
	private void addSymbol(Symbol t, Vector<Symbol> s) {
		boolean dupe = false;
		for (int i = 0; i < s.size(); i++)
			if (s.elementAt(i).equals(t))
				dupe = true;
		if(!dupe)
			s.add(t);
	}

	private boolean isNonTerminal(String s) {
		if (s.startsWith("<"))
			return true;
		return false;
	}
	
	public Grammar getGrammar() {
		return this.g;
	}
	
	public void dumpGrammarStructure() {
		System.out.println("Start Symbol: " + g.getStartSymbol());
		
		System.out.println("\nList of nonterminals of Grammar: ");
		System.out.println(g.getNonTerminals());
		System.out.println("\nList of terminals of Grammar: ");
		System.out.println(g.getTerminals());
		
		System.out.println("\nNumber of Production: " + g.getNumProduction());
		System.out.println("\nList of productions:");
		for(int i=0; i<g.getProduction().size(); i++) 
			System.out.println(g.getProduction().elementAt(i));
		
		System.out.println("\nList of nonterminal symbols of productions:");
		for(int i=0; i<g.getProduction().size(); i++) 
			System.out.println(g.getProduction().elementAt(i).getNonTerminals());
		
		System.out.println("\nList of terminal symbols of productions:");
		for(int i=0; i<g.getProduction().size(); i++) 
			System.out.println(g.getProduction().elementAt(i).getTerminals());
		
		System.out.println("\nList of LHS of productions: ");
		for(int i=0; i<g.getProduction().size(); i++) 
			System.out.println(g.getProduction().elementAt(i).getLHS());
		
		System.out.println("\nList of RHS of productions: ");
		for(int i=0; i<g.getProduction().size(); i++) 
			System.out.println(g.getProduction().elementAt(i).getRHS());
		
		System.out.println("\nList of Vocabulary:");
		System.out.println(g.getVocabulary());
	}
}// end class GrammarAnalyzer
