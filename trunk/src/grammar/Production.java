/**
 * @author Pree T. 
 * represent an object of production rule
 */

package grammar;

public class Production {
	
	private final boolean rep;
	private final int prodNum;
	private final String lhs;
	private final String rhs;

	public Production(int prodNum, String lhs, String rhs, boolean rep) {
		this.prodNum = prodNum;
		this.lhs = lhs;
		this.rhs = rhs;
		this.rep = rep;
	}

	public int getProductionNum() {
		return prodNum;
	}

	public String getLHS() {
		return lhs;
	}

	public String getRHS() {
		return rhs;
	}

	public String toString() {
		return prodNum + " " + lhs + " -> " + rhs;
	}

	public boolean getRepitition() {
		return rep;
	}
}// end class Production
