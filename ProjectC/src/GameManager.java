/*
 * Author: Hao Yu Yeh
 * Date: 18/05/2016
 * Comment: project C(TicTacToe game) in COMP90041
 */

import java.lang.Math;

public class GameManager {
	private char[][] chessBoard;
	private String gridLine = "";
	private int chessBoardCol;

	public GameManager() {
		chessBoardCol = (int) Math.sqrt(CommonConstant.GRIDNUM);
		chessBoard = new char[chessBoardCol][chessBoardCol];		
		// according the size of chess board to generate the horizontal grid line
		for (int i = 0; i < (2 * chessBoardCol - 1); i++) {
			gridLine += "-";
		}
	}
	// construct an empty chess board
	private void initial(){
		for (int i = 0; i < chessBoardCol; i++) {
			for (int j = 0; j < chessBoardCol; j++) {
				chessBoard[i][j] = ' ';
			}
		}
	}
	// playing Tic Tac Toe game
	public void playGame(Player p1, Player p2) {
		initial();
		printGrid();
		int type = CommonConstant.CONTINUE, count = 1;
		while (true) {
			switch (type) {
			case CommonConstant.OWIN:
				System.out.println("Game over. " + p1.getGivenName() + " won!");
				updatePlayerStats(p1, p2, CommonConstant.OWIN);
				return;
			case CommonConstant.XWIN:
				System.out.println("Game over. " + p2.getGivenName() + " won!");
				updatePlayerStats(p1, p2, CommonConstant.XWIN);
				return;
			case CommonConstant.DRAW:
				System.out.println("Game over. It was a draw!");
				updatePlayerStats(p1, p2, CommonConstant.DRAW);
				return;
			default:
			case CommonConstant.CONTINUE:
				// player1's turn
				if ((count % 2) == 1) {
					makingMove( p1, CommonConstant.O );
					type = getGameState(count);
				} else {
					makingMove( p2, CommonConstant.X );
					type = getGameState(count);
				}
				break;
			}
			printGrid();
			count++;
		}
	}
	// print the chess board
	private void printGrid() {
		for (int i = 0; i < chessBoardCol; i++) {
			String temp = "";
			for (int j = 0; j < chessBoardCol; j++) {
				temp += chessBoard[i][j];
				// adding vertical grid line
				if (j < (chessBoardCol - 1)) {
					temp += "|";
				}
			}
			System.out.println(temp);
			// adding horizontal grid line
			if (i < (chessBoardCol - 1)) {
				System.out.println(gridLine);
			}
		}
	}
	// making moves
	private void makingMove( Player p, char symbol ){
		System.out.println(p.getGivenName() + "'s move:");
		Move move;
		move = p.makeMove(chessBoard);
		// checking the validation of movements and giving the suggestion.
		int type;
		while( true ){
			type = checkValidMove( move.getRow(), move.getColumn() );
			if( type == CommonConstant.SUCCESS ){
				break;
			}else if( type == CommonConstant.OCCUPIED ){
				System.out.println( ErrorMessage.OCCUPIEDCELL );
				System.out.println(p.getGivenName() + "'s move:");
				move = p.makeMove(chessBoard);
			}else{
				System.out.println( ErrorMessage.OUTOFBOUNDARY );
				System.out.println(p.getGivenName() + "'s move:");
				move = p.makeMove(chessBoard);
			}
		}
		chessBoard[move.getRow()][move.getColumn()] = symbol;
	}
	// checking the validation of movements
	private int checkValidMove( int row, int col ){
		// check if the position is inside the boundary
		if( ( row >= 0 ) && ( row < chessBoardCol ) && ( col >= 0 ) && ( col < chessBoardCol ) ){
			// check if the grid is occupied
			if( chessBoard[row][col] == ' ' ){
				return CommonConstant.SUCCESS;
			}else{
				return CommonConstant.OCCUPIED;
			}
		}else{
			return CommonConstant.OUTOFBOUND;
		} 
	}
	// checking if there is a player satisfying the winning criteria
	private boolean checkWinner(char target) {
		char tempVertival, tempHorizontal, tempDiagonal1 = chessBoard[0][0],
				tempDiagonal2 = chessBoard[0][chessBoardCol - 1];
		for (int i = 0; i < chessBoardCol; i++) {
			tempHorizontal = chessBoard[i][0];
			tempVertival = chessBoard[0][i];
			/*
			 * check if there is a consecutive character 'target' filled in all grids of one 
			 * horizontal line
			 */
			if (tempHorizontal == target) {
				for (int j = 1; j < chessBoardCol; j++) {
					tempHorizontal &= chessBoard[i][j];
					/*
					 * stop checking once there is a grid which is not filled with the 
					 * character 'target'
					 */
					if (tempHorizontal != target) {
						break;
					} else if ((j == (chessBoardCol - 1)) && (tempHorizontal == target)) {
						return true;
					}
				}
			}
			/*
			 * check if there is a consecutive character 'target' filled in all grids of one 
			 * vertical line
			 */
			if (tempVertival == target) {
				for (int j = 1; j < chessBoardCol; j++) {
					tempVertival &= chessBoard[j][i];
					if (tempVertival != target) {
						break;
					} else if ((j == (chessBoardCol - 1)) && (tempVertival == target)) {
						return true;
					}
				}
			}
			/*
			 * check if there is a consecutive character 'target' filled in all grids of one 
			 * diagonal line
			 */
			if (i > 0) {
				tempDiagonal1 &= chessBoard[i][i];
				tempDiagonal2 &= chessBoard[i][chessBoardCol - 1 - i];
			}
		}
		if (tempDiagonal1 == target) {
			return true;
		} else if (tempDiagonal2 == target) {
			return true;
		} else {
			return false;
		}
	}
	// determine the current status of the game, such as who wins, draw or continue
	private int getGameState(int count) {
		if (checkWinner(CommonConstant.O)) {
			return CommonConstant.OWIN;
		} else if (checkWinner(CommonConstant.X)) {
			return CommonConstant.XWIN;
		} else if (count == CommonConstant.GRIDNUM) {
			return CommonConstant.DRAW;
		} else {
			return CommonConstant.CONTINUE;
		}
	}
	// update the statistics of two players
	private void updatePlayerStats(Player p1, Player p2, int result){
		switch(result){
		case CommonConstant.OWIN:
			p1.setGamePlayed(1);
			p1.setGameWon(1);
			p2.setGamePlayed(1);
			break;
		case CommonConstant.XWIN:
			p2.setGamePlayed(1);
			p2.setGameWon(1);
			p1.setGamePlayed(1);
			break;
		case CommonConstant.DRAW:
			p1.setGamePlayed(1);
			p1.setGameDrawn(1);
			p2.setGamePlayed(1);
			p2.setGameDrawn(1);
			break;
		default:
			break;
		}
	}
}