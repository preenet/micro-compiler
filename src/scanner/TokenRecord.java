package scanner;

/**
 * @author Pree Thiengburanathum
 * preenet@gmail.com
 * CSC5640 Universal Compiler 
 * Assignment 11 Due 12/01/08
 * TokenRecord.java
 */

public class TokenRecord {
	private TokenType type;
	private String token;
	
	public TokenRecord(TokenType type, String token) {
		this.type = type;
		this.token = token;
	}
	
	public TokenType getType() {
		return type;
	}
	
	public String getToken() {
		return token;
	}

	public String toString() {
		return this.token + " ";
	}
}// end class TokenRecord
