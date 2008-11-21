/**
 * @author Pree Thiengburanathum
 * preenet@gmail.com
 * UC Assignment 10
 * due 11/19/08
 * ExternalFile.java
 */

package uc;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExternalFile {
	private BufferedReader br;
	private int readAheadLimit = 1;

	public ExternalFile(String ext_file) throws IOException {
		br = new BufferedReader(new InputStreamReader(new FileInputStream(
				ext_file)));
	}

	/**
	 * the next input character is returned, but input is not advanced; error if
	 * Eof is true
	 * @return the next char
	 * @throws IOException
	 */
	public char peekChar() throws IOException {
		char retVal = ' ';
		br.mark(readAheadLimit);
		retVal = (char) br.read();
		br.reset();
		return retVal;
	}

	/**
	 * the next input character is removed, but not returned; no effect at end
	 * of file.
	 */
	public void nextChar() throws IOException {
		br.read();
	}
	
	/**
	 * the next input character is read
	 * 
	 * @return the next char
	 * @throws IOException
	 */
	public char readChar() throws IOException {
		return (char) br.read();
	}
	
	public String readLine() throws IOException {
		return br.readLine();
	}

	/**
	 * check for end of file
	 * @return true if end of the file
	 * @throws IOException
	 */
	public boolean havehitEOF() throws IOException {
		return !br.ready();
	}

	public void close() throws IOException {
		br.close();
	}// end method close
}// end class ExternalFile
