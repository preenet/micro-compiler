/**
 * @author Pree Thiengburanathum
 * preenet@gmail.com
 * CSC5640 Universal Compiler
 * Assignment 11 Due 12/01/08
 * Parser.java
 */

package parser;
import java.util.Vector;

import scanner.TokenType;
import scanner.Scanner;
import semantic.ExprRecord;
import semantic.ExprType;
import semantic.SemanticRoutine;
import semantic.OpRecord;
import grammar.TerminalSet;

public class Parser {
	private SemanticRoutine sr;
	private Scanner ms;
	private Vector<Integer> prodNums; // storage of applied production
	private int exprCount; // display the number of time that call expression function.

	public Parser(Scanner ms) {
		this.ms = ms;
		prodNums = new Vector<Integer> ();
		exprCount = 0;
		sr = new SemanticRoutine(ms);
	}

	public void parse() {
		systemGoal();
	}

	private void systemGoal() {
		sr.addAction("Call systesroal()", ms.getRemainToken());
		prodNums.add(25);
		program();
		match(TokenType.EofSym);
		sr.addAction("Semantic Action: finish()", ms.getRemainToken());
		sr.finish();
	}

	private void program() {
		sr.addAction("Call program()", ms.getRemainToken());
		prodNums.add(1);
		sr.addAction("Semantic Action: start()", ms.getRemainToken());
		sr.start();
		match(TokenType.BeginSym);
		statementList();
		match(TokenType.EndSym);
	}

	private void statementList() {
		sr.addAction("Call statementList()", ms.getRemainToken());
		prodNums.add(2);
		statement();
		while (true) {
			switch (ms.nextToken()) {
			case Id:
			case ReadSym:
			case WriteSym:
				statementList();
				break;
			default:
				return;
			}// end switch
		}
	}

	private void statement() {
		ExprRecord identifier = new ExprRecord();
		ExprRecord expr = new ExprRecord();
		
		sr.addAction("Call statement()", ms.getRemainToken());
		TokenType t = ms.nextToken();
		switch (t) {
			case Id:
				prodNums.add(4);
				ident(identifier);
				assignment();
				expression(expr);
				sr.addAction("Semantic Action: assign()", ms.getRemainToken());
				sr.assign(identifier, expr);
				match(TokenType.Semicolon);
				break;
	
			case ReadSym:
				prodNums.add(5);
				match(TokenType.ReadSym);
				match(TokenType.LPalen);
				idList();
				match(TokenType.RPalen);
				match(TokenType.Semicolon);
				break;
	
			case WriteSym:
				prodNums.add(6);
				match(TokenType.WriteSym);
				match(TokenType.LPalen);
				exprList();
				match(TokenType.RPalen);
				match(TokenType.Semicolon);
				break;
	
			default:
				syntaxError(t);
				break;
		}
	}

	/**
	 * extended grammar to for equal operator where both assignment and equal
	 * have to same property
	 */
	private void assignment() {
		sr.addAction("Call assignment()", ms.getRemainToken());
		TokenType t = ms.nextToken();
		switch (t) {
			case AssignOp:
				prodNums.add(12);
				match(TokenType.AssignOp);
				break;
			case EqualOp:
				prodNums.add(13);
				match(TokenType.EqualOp);
				break;
			default:
				syntaxError(t);
				break;
		}
	}

	private void expression(ExprRecord result) {
		exprCount ++;
		ExprRecord leftOp = new ExprRecord();
		ExprRecord rightOp = new ExprRecord();
		OpRecord oper = new OpRecord();
		
		sr.addAction("Call expression()[" + exprCount + "]", ms.getRemainToken());
		if(ms.peekToken().equals(TokenType.PlusOp) || ms.peekToken().equals(TokenType.MinusOp))
			prodNums.add(15);
		else
			prodNums.add(14);

		factor(leftOp);
		TokenType t = ms.nextToken();
		
		// this is only if the next token is the operator
		if(t.equals(TokenType.PlusOp) || t.equals(TokenType.MinusOp)) {
			addOp(oper); 
			expression(rightOp);
			sr.addAction("Semantic Action: genInfix()", ms.getRemainToken());
			copyExprRecord(result, sr.getInfix(leftOp, oper, rightOp));
		}
		else {
			// in this case we just store it to the left operand
			copyExprRecord(result, leftOp);
		}
	}
	
	private void factor(ExprRecord result) {
		ExprRecord leftOp = new ExprRecord();
		ExprRecord rightOp = new ExprRecord();
		OpRecord oper = new OpRecord();
		
		sr.addAction("Call factor()", ms.getRemainToken());
		if(ms.peekToken().equals(TokenType.MultiOp))
			prodNums.add(17);
		else
			prodNums.add(16);
	
		primary(leftOp);
		TokenType t = ms.nextToken();
		
		// this is only if the next token is the operator
		if(t.equals(TokenType.MultiOp)) {
			mulOp(oper); 
			expression(rightOp);
			sr.addAction("Semantic Action: genInfix()", ms.getRemainToken());
			copyExprRecord(result, sr.getInfix(leftOp, oper, rightOp));
		}
		else {
			// in this case we just store it to the left operand
			copyExprRecord(result, leftOp);
		}
	}

	private void primary(ExprRecord result) {
		sr.addAction("Call primary()", ms.getRemainToken());
		TokenType t = ms.nextToken();
		switch (t) {
			case LPalen:
				prodNums.add(18);
				match(TokenType.LPalen);
				expression(result);
				match(TokenType.RPalen);
				break;
			case Id:
				prodNums.add(19);
				ident(result);
				break;
			case IntLiteral:
				prodNums.add(20);
				match(TokenType.IntLiteral);
				sr.addAction("Semantic Action: processLiteral()", ms.getRemainToken());
				sr.processLiteral(result);
				break;
			default:
				syntaxError(t);
				break;
		}
	}
	
	private void ident(ExprRecord result) {
		sr.addAction("Call ident()", ms.getRemainToken());
		prodNums.add(21);
		match(TokenType.Id);
		sr.addAction("Semantic Action: processId()", ms.getRemainToken());
		sr.processId(result);
	}

	private void addOp(OpRecord oper) {
		sr.addAction("Call addOp()", ms.getRemainToken());
		TokenType t = ms.nextToken();
		if (t.equals(TokenType.PlusOp)) {
			prodNums.add(22);
			match(t);
			sr.addAction("Semantic Action: processOp()", ms.getRemainToken());
			sr.processOp(oper);
		} 
		else if (t.equals(TokenType.MinusOp)) {
			prodNums.add(23);
			match(t);
			sr.addAction("Semantic Action: processOp()", ms.getRemainToken());
			sr.processOp(oper);
		} 
		else
			syntaxError(t);
	}
	
	private void mulOp(OpRecord oper) {
		sr.addAction("Call MulOp()", ms.getRemainToken());
		TokenType t = ms.nextToken();
		if (t.equals(TokenType.MultiOp)) {
			prodNums.add(24);
			match(t);
			sr.addAction("Semantic Action: processOp()", ms.getRemainToken());
			sr.processOp(oper);
		} 
		else
			syntaxError(t);
	}
	
	private void idList() {
		ExprRecord identifier = new ExprRecord(ExprType.IdExpr);
		
		if(ms.peekToken().equals(TokenType.Comma))
			prodNums.add(8);
		else 
			prodNums.add(7);
		ident(identifier);
		sr.addAction("Semantic Action: readId()", ms.getRemainToken());
		sr.readId(identifier);
		
		while (ms.nextToken().equals(TokenType.Comma)) {
			prodNums.add(8);
			match(TokenType.Comma);
			ident(identifier);
			sr.addAction("Semantic Action: readId()", ms.getRemainToken());
		}
		prodNums.add(9);
	}

	private void exprList() {
		ExprRecord expr = new ExprRecord(ExprType.TempExpr);
		
		expression(expr);
		sr.addAction("Semantic Action: writeExpr()", ms.getRemainToken());
		sr.writeExpr(expr);
		if(ms.peekToken().equals(TokenType.Comma))
			prodNums.add(11);
		else 
			prodNums.add(10);
		while (ms.nextToken().equals(TokenType.Comma)) {
			match(TokenType.Comma);
			expression(expr);
			sr.addAction("Semantic Action: writeExpr()", ms.getRemainToken());
			sr.writeExpr(expr);
		}
	}
	
	private boolean match(TokenType t) {
		sr.addAction("match" + "(" + t + ")", ms.getRemainToken());
		System.out.println("> " + t + " matched " + ms.getTokens().elementAt(ms.index).getType());
		if (ms.getTokens().elementAt(ms.index).getType().equals(t)) {
			ms.index++;
			return true;
		} 
		else if (!ms.getTokens().elementAt(ms.index).getType().equals(t)) {
			syntaxError(t);
			return false;
		} 
		else
			return false;
	}
	
	/**
	 * This function will copy the exprRecord routines
	 * @param target
	 * @param source
	 */
	private void copyExprRecord(ExprRecord target, ExprRecord source) {
		target.kind = source.kind;
		target.setName(source.getName());
		target.setVal(source.getVal());
	}
	
	/**
	 * It checks the validity of input and if it is invalid calls scanner repeatedly to get next token
	 * while next token is not equal to one of the acceptable tokens. This way we skip as many tokens as necessary.
	 */
	private void checkInput(TerminalSet validSet, TerminalSet followSet, TerminalSet header) {
		
	}
	
	public Vector<Integer> getProductionNums() {
		return prodNums;
	}
	
	public void parseAction() {
		sr.dumpGenAction();
		sr.dumpSymbolTable();
		sr.dumpGeneratedCode();
	}
	
	private void syntaxError(TokenType t) {
		System.out.println("Syntax Error: " + t);
	}
	
	public void dumpSteps() {
		System.out.println("Grammar rule applied:");
		for (int i = 0; i < prodNums.size(); i++)
			System.out.print(prodNums.elementAt(i) + ", ");
		System.out.println();
	}
}// end class MicroParser
