package havaBol;

/**
 * Used to display an error when a string without a correct format is found.
 * 
 * @author ckw273
 *
 */
public class StringFormatException extends Exception {
	public StringFormatException(String message) {
		super(message);
	}
}
