/**
 * @author Pree Thiengburanathum
 * preenet@gmail.com
 * CSC5640 Universal Compiler
 * Assignment 11 Due 12/01/08
 * ExprRecord.java
 */

package semantic;
public class ExprRecord {
	
	public ExprType kind;
	private String name;
	private int val;

	public ExprRecord() {
		kind = null;
	}
	
	public ExprRecord(ExprType kind) {
		this.kind = kind;
	}

	public String getName() {
		return name;
	}

	public int getVal() {
		return val;
	}

	public void setName(String s) {
		name = s;
	}

	public void setVal(int v) {
		val = v;
	}
	
	public String toString() {
		return kind.toString();
	}
}// end class ExprRecord
