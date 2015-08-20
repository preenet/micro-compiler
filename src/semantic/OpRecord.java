/**
 * @author Pree Thiengburanathum
 * preenet@gmail.com
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
