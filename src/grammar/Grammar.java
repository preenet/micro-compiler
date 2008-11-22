/**
 * @author Pree Thiengburanathum
 * preenet@gmail.com
 * CSC5640 Universal Compiler
 * Assignment 11 Due 12/01/08  
 * Grammar.java
 */

/*
	 This is the extended grammar for Micro language enhanced with Action Symbols. 
	 From 14 original rules to 22 extended rules to be able to support the equal operator, 
	 the exponential operator and proper demonstrate the parsing technique.
	
	 1. <program> —> #Start begin <stmt list> end
	 2. <stmt list> —> <statement>
	 3. <stmt list> —> <statement> <stmt list>
	 4. <statement> —> <ident> <assignment> <expression> #Assign;
	 5. <statement> —> read(<id list>) ;
	 6. <statement> —> write(<expr list>) ;
	 7. <id list> —> <ident> #ReadId
	 8. <id list> -> <ident> #ReadId, <id list>
	 9. <id list> -> null
	 10. <expr list> —> <expression> #WriteExpr
	 11. <expr list> —> <expression> #WriteExpr, <expr list>
	 12. <assignment> —> AssignOp 
	 13. <assignment> —> EqualOp
	 14. <expression> —> <primary> 
	 15. <expression> —> <primary>  <add op> <expression> #GenInfix
	 16. <primary> —> (<expression>)
	 17. <primary> —> <ident>
	 18. <primary> —> IntLiteral #ProcessLiteral
	 19. <ident> —> Id #ProcessId
	 20. <add op> —> PlusOp #ProcessOp
	 21. <add op> —> MinusOp #ProcessOp
	 22. <system goal> —> <program> % #Finish
 */

package grammar;
import java.util.*;

public class Grammar {
	private Vector<Integer> step;
	private Vector<Production> prods;
	private String result;
	private int stmtNum;

	public Grammar(Vector<Integer> step, int stmtNum) {
		this.step = step;
		this.stmtNum = stmtNum;
		prods = new Vector<Production>();
		result = "<system goal>";
		
		initMicroGrammar();
	}

	/**
	 * construct the Micro Grammar from the above table.
	 */
	private void initMicroGrammar() {
		prods.add(new Production(1, "<program>", "BeginSym <stmt list> EndSym", false));
		prods.add(new Production(2, "<stmt list>", "<statement>", true));
		prods.add(new Production(3, "<stmt list>", "<statement> <stmt list>", false));
		prods.add(new Production(4, "<statement>", "<ident> <assignment> <expression> SemiColon", false));
		prods.add(new Production(5, "<statement>", "ReadSym (<id list>)SemiColon", false));
		prods.add(new Production(6, "<statement>", "WriteSym (<expr list>)SemiColon", false));
		prods.add(new Production(7, "<id list>", "<ident>", true));
		prods.add(new Production(8, "<id list>", "<ident> Comma <id list>", false));
		prods.add(new Production(9, "<id list>", "", false));
		prods.add(new Production(10, "<expr list>", "<expression>", true));
		prods.add(new Production(11, "<expr list>", "<expression>, <expr list>", false));
		prods.add(new Production(12, "<assignment>", "AssignOp", false));
		prods.add(new Production(13, "<assignment>", "EqualOp", false));
		prods.add(new Production(14, "<expression>", "<factor>", true));
		prods.add(new Production(15, "<expression>", "<factor> <add op> <expression>", false));
		prods.add(new Production(16, "<factor>", "<primary>", false));
		prods.add(new Production(17, "<factor>", "<primary> <mul op> <expression>", false));
		prods.add(new Production(18, "<primary>", "(<expression>)", false));
		prods.add(new Production(19, "<primary>", "<ident>", false));
		prods.add(new Production(20, "<primary>", "IntLiteral", false));
		prods.add(new Production(21, "<ident>", "Id", false));
		prods.add(new Production(22, "<add op>", "PlusOp", false));
		prods.add(new Production(23, "<add op>", "MinusOp", false));
		prods.add(new Production(24, "<mul op>", "MultiOp", false));
		prods.add(new Production(25, "<system goal>", "<program> EofSym", false));
	}

	/**
	 * demonstrate all the steps of my parsing procedure
	 */
	public void derivation() {
		System.out.println("\nParsing Derivation:");
		System.out.print(result);
		for (int i = 0; i < step.size(); i++)
			replaceGrammar(step.elementAt(i));
	}

	/**
	 * @param rule , the rule to be replaced  
	 */
	private void replaceGrammar(int rule) {
		if(isRepitition(rule)) 
		if(rule == 2) {
			// case <statement>
			if (stmtNum > 1) {
				rule++;
				stmtNum--;
			}
		}
		result = result.replaceFirst(getLHS(rule), getRHS(rule));
		System.out.println("\t" + rule + "\u2014" + "> " + result);
	}
	
	private boolean isRepitition(int index) {
		for (int i = 0; i < prods.size(); i++)
			if(index == prods.elementAt(i).getProductionNum())
				return prods.elementAt(i).getRepitition();
		return false;
	}

	private String getRHS(int index) {
		for (int i = 0; i < prods.size(); i++)
			if (index == prods.elementAt(i).getProductionNum())
				return prods.elementAt(i).getRHS();
		grammarError(step.elementAt(index));
		return "";
	}
	
	private String getLHS(int index) {
		for (int i = 0; i < prods.size(); i++)
			if (index == prods.elementAt(i).getProductionNum())
				return prods.elementAt(i).getLHS();
		grammarError(step.elementAt(index));
		return "";
	}

	private void grammarError(int t) {
		System.out.println("Grammar Error: " + t);
	}

	public void showGrammar() {
		System.out.println("Grammar for Micro language:");
		for (int i = 0; i < prods.size(); i++)
			System.out.println(prods.elementAt(i));
		System.out.println();
	}
}// end class Grammar
