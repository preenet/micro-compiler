
/**
 * @author Pree Thiengburanathum
 * preenet@gmail.com
 * CSC5640 Universal Compiler
 * Assignment 11 Due 12/01/08
 * Scanner.java
 */

package scanner;
import java.io.IOException;
import java.util.Vector;
import uc.ExternalFile;

public class Scanner {
	private ExternalFile extFile;
	private Vector<TokenRecord> tokens;
	private char inChar;
	private String cbuf;
	public int index;
	private int errorCount;
	private int stmtCount;

	public Scanner(String sourceFile) throws IOException {
		extFile = new ExternalFile(sourceFile);
		tokens = new Vector<TokenRecord>();
		cbuf = "";
		index = 0;
		errorCount = 0;
		stmtCount = 0;
	}

	public void scan() throws IOException {
		if (extFile.havehitEOF())
			tokens.add(new TokenRecord(TokenType.EofSym, "EofSym"));

		while (!extFile.havehitEOF()) {
			inChar = extFile.readChar();

			// recognize tab, new line and white space
			if (inChar == '\t' || inChar == '\n' || Character.isWhitespace(inChar))
				continue;
			
			// recognize letter
			else if (Character.isLetter(inChar)) {
				cbuf += inChar;
				while (Character.isUpperCase(extFile.peekChar())
						|| Character.isLowerCase(extFile.peekChar())
						|| Character.isDigit(extFile.peekChar())
						|| extFile.peekChar() == '\u005F')
					cbuf += extFile.readChar();
				checkReserved(cbuf);
			}

			// recognize string of digit
		
			else if (Character.isDigit(inChar)) {
				String digit = Character.toString(inChar);
				while (Character.isDigit(extFile.peekChar())) {
					digit += extFile.peekChar();
					extFile.nextChar();
				}
				tokens.add(new TokenRecord(TokenType.IntLiteral, digit));
			}

			// recognize operators, comments, and delimiters
			else if (inChar == '(')
				tokens.add(new TokenRecord(TokenType.LPalen, "("));
			else if (inChar == ')')
				tokens.add(new TokenRecord(TokenType.RPalen, ")"));
			else if (inChar == ';')
				tokens.add(new TokenRecord(TokenType.Semicolon, ";"));
			else if (inChar == ',')
				tokens.add(new TokenRecord(TokenType.Comma, ","));
			else if (inChar == '+')
				tokens.add(new TokenRecord(TokenType.PlusOp, "+"));
			else if (inChar == '-' || inChar == '\u2014') {
				// check for comment start -- and the EM bash char
				if (extFile.peekChar() == '-' || extFile.peekChar() == '\u2014')
					while (extFile.readChar() != '\n');
				else
					tokens.add(new TokenRecord(TokenType.MinusOp, "-"));
			} 
			else if (inChar == ':') {
				// looking for ":="
				inChar = extFile.peekChar();
				if (inChar == '=') {
					tokens.add(new TokenRecord(TokenType.AssignOp, ":="));
					extFile.nextChar();
				} 
				else
					lexicalError(inChar);
			} 
			else if (inChar == '=')
				tokens.add(new TokenRecord(TokenType.EqualOp, "="));

			else if (inChar == '*') {
				if (extFile.peekChar() == '*') {
					tokens.add(new TokenRecord(TokenType.ExpOp, "**"));
					extFile.nextChar();
				} 
				else
					tokens.add(new TokenRecord(TokenType.MultiOp, "*"));
			} 
			else if (inChar == '$')
				tokens.add(new TokenRecord(TokenType.EofSym, "$"));
			else
				lexicalError(inChar);
		}
		if (!tokens.lastElement().getType().equals(TokenType.EofSym))
			tokens.add(new TokenRecord(TokenType.EofSym, "EofSym"));
		extFile.close();
	}

	/**
	 * takes the identifiers as they are recognized and returns the proper token
	 * class (either Id or some reserved word).
	 */
	private void checkReserved(String c) {
		if (c.equals("begin") || c.equals("BEGIN"))
			tokens.add(new TokenRecord(TokenType.BeginSym, "BEGIN"));
		else if (c.equals("read") || c.equals("READ"))
			tokens.add(new TokenRecord(TokenType.ReadSym, "READ"));
		else if (c.equals("write") || c.equals("WRITE"))
			tokens.add(new TokenRecord(TokenType.WriteSym, "WRITE"));
		else if (c.equals("end") || c.equals("END"))
			tokens.add(new TokenRecord(TokenType.EndSym, "END"));
		else
			tokens.add(new TokenRecord(TokenType.Id, c));
		// reset char buffer
		cbuf = "";
	}

	private void lexicalError(char c) {
		System.out.println("Lexical(Token) Error: " + c);
	}
	
	public TokenType nextToken() {
		TokenType retVal = tokens.elementAt(index).getType();
		return retVal;
	}
	
	public TokenType peekToken() {
		TokenType retVal = tokens.elementAt(index+1).getType();
		return retVal;
	}
	
	public String getRemainToken() {
		String retVal = "";
		for(int i = index; i < tokens.size(); i++) {
			retVal += tokens.elementAt(i).getToken() + " ";
		}
		return retVal;
	}

	/**
	 * get the current working token
	 * @return a token
	 */
	public TokenType getCurrentToken() {
		return tokens.elementAt(index-1).getType();
	}
	
	public String getTokenBuffer() {
		return tokens.elementAt(index-1).getToken();
	}

	public void dumpTokens() {
		System.out.print("[");
		for (int i = 0; i < tokens.size(); i++) {
			System.out.print(tokens.elementAt(i).getType() + " ");
			if (tokens.elementAt(i).getType().equals(TokenType.Semicolon)) {
				stmtCount++;
				System.out.println();
			}
		}
		System.out.println("]");
		System.out.println("Token Error " + errorCount + " error(s).");
	}

	public Vector<TokenRecord> getTokens() {
		return tokens;
	}
	
	public int getStmtCount() {
		return stmtCount;
	}
}// end class Scanner
