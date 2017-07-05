/*
 * Author: Hao Yu Yeh
 * Date: 18/05/2016
 * Comment: project C(TicTacToe game) in COMP90041
 */

public class ErrorMessage {
	// for class TicTacToe
	public final static String WRONGUSER = "Player does not exist.";
	// for class PlayerManager
	public final static String EXCEEDMAX = "Already reach the limit of maximun players(" +
										   CommonConstant.MAXPLAYERS + ").";
	public final static String NOTEXIST = "The player does not exist.";
	public final static String EXISTALREADY = "The username has been used already.";	
	// for class GameManager
	public final static String OCCUPIEDCELL = "Invalid move. The cell has been occupied.";
	public final static String OUTOFBOUNDARY = "Invalid move. You must place at a cell within "
											   + "{0,1,2} {0,1,2}.";
}
