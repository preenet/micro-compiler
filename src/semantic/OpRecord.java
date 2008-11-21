/**
 * @author Pree Thiengburanathum
 * preenet@gmail.com
 * CSC5640 Universal Compiler 
 * Assignment 11 Due 12/01/08
 * OpRecord.java
 */

package semantic;
enum operator {Plus, Minus, Multi}
public class OpRecord {
	
	private operator op;
	
	public OpRecord() {
		op =operator.Minus;
	}
	
	public void setPlusOp() {
		op = operator.Plus;
	}
	
	public void setMinusOp() {
		op = operator.Minus;
	}
	
	public void setMultiOp() {
		op = operator.Multi;
	}

	public String toString() {
		return op.name();
	}
}// end class OpRecord
