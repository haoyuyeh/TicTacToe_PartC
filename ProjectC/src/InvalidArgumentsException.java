/*
 * Author: Hao Yu Yeh
 * Date: 18/05/2016
 * Comment: project C(TicTacToe game) in COMP90041
 */

public class InvalidArgumentsException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public InvalidArgumentsException(){
		super("InvalidArguments");
	}
	public InvalidArgumentsException(String msg){
		super(msg);
	}
}
