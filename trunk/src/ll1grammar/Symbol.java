/**
 * @author Pree
 * Represent Symbol object
 */
package ll1grammar;

public class Symbol {
	private String sName;
	public String type;
	
	public Symbol(String s) {
		sName = s;
		type = "s";
	}
	public void setName(String s) {
		sName = s;
	}
	
	public String getSymbol() {
		return sName;
	}
	
	public String getType() {
		return type;
	}
	
	// override equals method
	public boolean equals(Symbol that) {
		if(this.sName.equals(that.sName))
			return true;
		return false;
	}
	
	public String toString() {
		return sName;
	}
}// end class Symbol