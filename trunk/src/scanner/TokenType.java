package scanner;
/**
 * @author Pree Thiengburanathum
 * preenet@gmail.com
 * CSC5640 Universal Compiler
 * Assignment 11 
 * TokenType.java
 */

public enum TokenType {
	BeginSym, EndSym, ReadSym, WriteSym, Id, IntLiteral,
	LPalen, RPalen, Semicolon, Comma, AssignOp, EqualOp,
	PlusOp, MinusOp, MultiOp, ExpOp, EofSym
}// end enumerator TokenTypes