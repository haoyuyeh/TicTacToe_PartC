/*
 * Author: Hao Yu Yeh
 * Date: 18/05/2016
 * Comment: project C(TicTacToe game) in COMP90041
 */

public class HumanPlayer extends Player{
	public HumanPlayer() {
		super();
	}
	public Move makeMove(char[][] gameBoard){
		Move move = new Move();
		int row = 0, col = 0;
		row = TicTacToe.keyboard.nextInt();
		col = TicTacToe.keyboard.nextInt();
		move.setRow(row);
		move.setColumn(col);
		// clear the command buffer
		TicTacToe.keyboard.nextLine();
		return move;
	}
}
