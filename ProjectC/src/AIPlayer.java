/*
 * Author: Hao Yu Yeh
 * Date: 18/05/2016
 * Comment: project C(TicTacToe game) in COMP90041
 */

public class AIPlayer extends Player{
	public AIPlayer(){
		super();
	}
	/*
	 * making move by searching empty grid in a raster scan way
	 */
	public Move makeMove(char[][] gameBoard){
		Move move = new Move();
		outer_loop:
		for( int i = 0; i < gameBoard.length; i++ ){
			for( int j = 0; j < gameBoard[0].length; j++ ){
				if( gameBoard[i][j] == ' ' ){
					move.setRow(i);
					move.setColumn(j);
					break outer_loop;
				}
			}
		}
		return move;
	}
}
