

/**
 * These classes below are inherited from Symbol.
 * @author Pree
 *
 */

package ll1grammar;

public class Terminal extends Symbol {
	public Terminal(String s) {
		super(s);
		this.type = "t";
	}
}// end class Terminal