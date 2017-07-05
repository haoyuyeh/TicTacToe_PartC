/*
 * Author: Hao Yu Yeh
 * Date: 18/05/2016
 * Comment: project C(TicTacToe game) in COMP90041
 */

public class CommonConstant {
	// for class TicTacToe
	public final static int MAXARGUMENTS = 3, MINARGUMENTS = 1, NUMOFGAMEPLAYERS = 2;
	// for class PlayerManager 
	public final static int MAXPLAYERS = 100, MAXRANKINGPLAYERS = 10, NULL = 0, RESET = 0;
	public final static int NOTFOUND = 0, SUCCESS = 1, PLAYEREXISTED = 2, EXCEEDMAXPLAYER = 3;
	// for class Player
	public final static int CLEAR = 0;
	// for class GameManager
	public final static int GRIDNUM = 9, OWIN = 0, XWIN = 1, DRAW = 2, CONTINUE = 3, OCCUPIED = 4;
	public final static int OUTOFBOUND = 5;
	public final static char O = 'O', X = 'X';
	// for class AdvancedAIPlayer
	public final static int SCOREFORWIN = 10;
	public final static char DRAWCHAR = 'D';
}
