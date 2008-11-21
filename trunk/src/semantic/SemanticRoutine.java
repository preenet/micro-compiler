/**
 * @author Pree Thiengburanathum
 * preenet@gmail.com
 * CSC5640 Universal Compiler 
 * Assignment 11 Due 12/01/08
 * SemanticRountine.java
 */

package semantic;

import java.util.Vector;
import parser.Parser;
import scanner.TokenType;
import scanner.Scanner;

public class SemanticRoutine {
	private int maxTemp; // max temporary used so far
	private Vector<String> symbolTable; // symbolic storage
	private Vector<GenActionTable> genAct;
	private Scanner ms;

	public SemanticRoutine(Scanner ms) {
		this.ms = ms;
		symbolTable = new Vector<String>();
		genAct = new Vector<GenActionTable>();
	}
	
	public void start() {
		maxTemp = 0;
	}

	public void assign(ExprRecord target, ExprRecord source) {
		generate("Store", extract(source), target.getName());
	}
	
	/**
	 * declare Id and build a corresponding semantic record.
	 */
	public void processId(ExprRecord e) {
		checkId(ms.getTokenBuffer());
		e.kind = ExprType.IdExpr;
		e.setName(ms.getTokenBuffer());
	}
	
	public void processLiteral(ExprRecord e) {
		e.kind = ExprType.LiteralExpr;
		e.setVal(Integer.parseInt(ms.getTokenBuffer()));
	}
	
	public void processOp(OpRecord oper) {
		if(ms.getCurrentToken().equals(TokenType.PlusOp))
			oper.setPlusOp();
		else if(ms.getCurrentToken().equals(TokenType.MultiOp))
			oper.setMultiOp();
		else
			oper.setMinusOp();
	}
	
	public void readId(ExprRecord inVar) {
		generate("Read", inVar.getName(), "Integer");
	}
	
	public void writeExpr(ExprRecord outExpr) {
		generate("Write", extract(outExpr), "Integer");
	}
	
	public ExprRecord getInfix(ExprRecord e1, OpRecord oper, ExprRecord e2) {
		ExprRecord exprTemp = new ExprRecord(ExprType.TempExpr);
		exprTemp.setName(getTemp());
		generate(extractOp(oper), extract(e1), extract(e2), exprTemp.getName());
		return exprTemp;
	}
	
	/**
	 * generate code to finish program
	 */
	public void finish() {
		generate("Halt");
	}
	
	/*
	 *  Auxiliary Routines
	 */
	
	/**
	 * Takes 4 string arguments and produces a correctly formatted instruction
	 * in an output file.
	 * @throws IOException
	 */
	private void generate(String ot, String op1, String op2, String rf) {
		String s = ot + " " + op1 + ", " + op2 + ", " + rf;
		genAct.lastElement().setCode(s);
	}
	
	/**
	 * Takes 3 string arguments and produces a correctly formatted instruction
	 * in an output file.
	 * @throws IOException
	 */
	private void generate(String ot, String op1, String rf) {
		String s = ot + " " + op1 + ", " + rf;
		genAct.lastElement().setCode(s);
	}
	
	/**
	 * Takes 1 string arguments and produces a correctly formatted instruction
	 * in an output file.
	 */
	private void generate(String ot) {
		genAct.lastElement().setCode(ot);
	}


	/**
	 * Enter identifier S unconditionally into the symbol table.
	 */
	private void enter(String s) {
		symbolTable.add(s);
	}
	
	/**
	 * Is s in the symbol table?
	 * @param s
	 * @return true if found, false otherwise
	 */
	private boolean lookUp(String s) {
		for(int i=0; i<symbolTable.size(); i++)
			if(s.equals(symbolTable.elementAt(i)))
					return true;
		return false;
	}// end method lookUp
	
	/**
	 * If variable called S is not declared yet CheckId enters it into the symbol
	 * table and then generates an assembler directive to reserve space for it.
	 * @param s
	 */
	private void checkId(String s) {
		System.out.println(">> checkId  " + s);
		if(!lookUp(s)) {
			enter(s);
			generate("Declare", s, "Integer");
		}
	}
	
	/**
	 * To hold intermediate results of a computation
	 */
	private String getTemp() {
		maxTemp++;
		String tempName = "Temp&" + Integer.toString(maxTemp);
		checkId(tempName);
		return tempName;
	}

	/**
	 * Takes a semantic record and returns a string corresponding to the
	 * semantic information it contains. This may be an identifier, an operation
	 * code, a literal, and so on, to feed procedure Generate. In case of Micro
	 * we need two functions.
	 * 
	 * @param e, semantic record
	 * @return String
	 */
	private String extract(ExprRecord e) {
		switch (e.kind) {
			case IdExpr:
				return e.getName();
			case LiteralExpr:
				return Integer.toString(e.getVal());
			case TempExpr:
				return e.getName();
		}
		return "";
	}

	private String extractOp(OpRecord o) {
		if (o.toString().equals(operator.Plus.toString()))
			return "ADD";
		else if (o.toString().equals(operator.Multi.toString()))
			return "MULT";
		return "SUB";
	}
	

	public void addAction(String action, String remain) {
		genAct.add(new GenActionTable(action, remain));
	}

	
	public void dumpSymbolTable() {
		System.out.println();
		System.out.println("Symbol Table:");
		for(int i=0; i<symbolTable.size(); i++)
			System.out.println(symbolTable.elementAt(i));
		System.out.println();
	}
	
	public void dumpGeneratedCode() {
		System.out.println();
		System.out.println("Generated Code:");
		for(int i=0; i<genAct.size(); i++) {
			if(!genAct.elementAt(i).hasCode())
			System.out.println(genAct.elementAt(i).getGeneratedCode());
		}
		System.out.println();
	}

	public void dumpGenAction() {
		System.out.println();
		System.out.println("Parsing Steps Trace:");
		System.out.println("Step Parser Action\t\t\tRemaining Input\t\t\t\t"
				+ "Generated Code");
		System.out
				.println("---- -------------\t\t\t---------------\t\t\t\t----------------------");

		for (int i = 0; i < genAct.size(); i++)
			System.out.println("(" + (i+1) + ")  " + genAct.elementAt(i));

		System.out.println();
	}
}// end class SemanticRoutine


/**
 * To store parser actions which could display as requirement.
 * @author Pree T.
 */
class GenActionTable {
	private String action;
	private String remain;
	private String[] code;
	private int codeCount;

	public GenActionTable(String action, String remain) {
		this.action = action;
		this.remain = remain;
		code = new String[10];
		for(int i = 0; i < code.length; i++)
			code[i] = "";
		codeCount = 0;
	}

	public void setCode(String s) {
		code[codeCount] = "[ "+ s + " ] ";
		codeCount++;
	}
	
	public int getRemainSize() {
		return this.remain.length();
	}
	
	public StringBuffer getGeneratedCode() {
		StringBuffer retVal = new StringBuffer();
		for(int i = 0; i < code.length; i++)
			retVal.append(code[i]);
		return retVal;
	}
	
	public boolean hasCode() {
		if(codeCount > 0)
			return false;
		return true;
	}

	private String outputFormat(int fieldSize, String value) {
		return value + 
		("                                              ").substring(0, fieldSize - value.length());
	}

	public String toString() {
		return outputFormat(35, action) +  remain + getGeneratedCode().toString() ;
	}
}// end class GenActionTable