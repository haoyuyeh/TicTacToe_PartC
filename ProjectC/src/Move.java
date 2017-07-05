/*
 * Author: Hao Yu Yeh
 * Date: 18/05/2016
 * Comment: project C(TicTacToe game) in COMP90041
 */

public class Move {
	private int rowOfMove,colOfMove;
	
	public Move(){
		rowOfMove = 0;
		colOfMove = 0;
	}
	public Move(int row, int col){
		rowOfMove = row;
		colOfMove = col;
	}
	/*
	 * get row number of move
	 */
	public int getRow(){
		return rowOfMove;
	}
	/*
	 * set row number of move
	 */
	public void setRow(int row){
		rowOfMove = row;
	}
	/*
	 * get col number of move
	 */
	public int getColumn(){
		return colOfMove;
	}
	/*
	 * set col number of move
	 */
	public void setColumn(int col){
		colOfMove = col;
	}
}
