

/**
 * These classes below are inherited from Symbol.
 * @author Pree
 *
 */

package ll1grammar;

public class NonTerminal extends Symbol {
	public NonTerminal(String s) {
		super(s);
		this.type = "n";
	}
}// end class NonTerminal