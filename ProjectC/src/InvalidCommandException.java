/*
 * Author: Hao Yu Yeh
 * Date: 18/05/2016
 * Comment: project C(TicTacToe game) in COMP90041
 */

public class InvalidCommandException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public InvalidCommandException(){
		super("InvalidCommandException");
	}
	public InvalidCommandException( String msg ){
		super(msg);
	}
}
