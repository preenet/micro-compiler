/**
 * @author Pree Thiengburanathum
 * preenet@gmail.com
 * CSC5640 Universal Compiler
 * Assignment 11 Due 12/01/08
 * MicroCompiler.java
 *
 * Driver of the program
 */

package uc;

import java.io.IOException;
import parser.Parser;
import parser.GrammarAnalyzer;
import parser.LL1PredictGenerator;
import grammar.Grammar;
import scanner.Scanner;


public class MicroCompiler {
	
	private static void usageAndExit() {
		System.out.println("Usage: MicroCompiler <input_file>");
		System.out.println("     : help, please view README");
		System.out.println("     : author, pree thiengburanathum");
		System.out.println("     : UC Assignment 11				");
		System.out.println("     : Micro Compiler (Non-Universal)");
		System.out.println("     : preenet@gmail.com");
		System.exit(1);
	}
	
	public static void main(String args[]) throws IOException {
		Scanner ms;
		Parser mp;
		Grammar mg;
		GrammarAnalyzer ga;
		LL1PredictGenerator lpg;


		// validate input argument
		if (args.length != 1)
			usageAndExit();
		else if(!args[0].endsWith(".m")) {
			System.out.println("Error: Invalid source file extension.");
			usageAndExit();
		}
		
		System.out.println("**************Scanning***************");
		ms = new Scanner(args[0]);
		ms.scan();
		System.out.println("\nDone scanning, Tokens after scanned:");
		ms.dumpTokens();
		
	    ga = new GrammarAnalyzer("LL1MicroGrammar.md");
		lpg = new LL1PredictGenerator(ga.getGrammar());
		
		System.out.println("First Set:\n " + lpg.getFirstSet());
		System.out.println("Follow Set:\n " + lpg.getFollowSet());


		System.out.println("\n*******Recursive Descent Parsing****************");
		mp = new Parser(ms, lpg);
		mp.parse();

		System.out.println("\n********Demonstrate of the parser*************");
		mg = new Grammar(mp.getProductionNums(), ms.getStmtCount());
		mg.showGrammar();
		mp.dumpSteps();
		mg.derivation();
		mp.parseAction();
		
		System.out.println("Done compilng!");
	}
}// end class MicroCompiler
