
/*
 * Author: Hao Yu Yeh
 * Date: 18/05/2016
 * Comment: project C(TicTacToe game) in COMP90041
 */

import java.util.ArrayList;
import java.util.Collections;

public class AdvancedAIPlayer extends Player {
	private int chessBoardCol, emptyGrids, occupiedGrids;
	// the depth that is this move located
	private int level;
	private char player, opponent;

	public AdvancedAIPlayer() {
		super();
		chessBoardCol = (int) Math.sqrt(CommonConstant.GRIDNUM);
	}
	/*
	 * making the best move
	 */
	public Move makeMove(char[][] gameBoard) {		
		// check player's symbol
		emptyGrids = 0;
		for (int i = 0; i < chessBoardCol; i++) {
			for (int j = 0; j < chessBoardCol; j++) {
				if (gameBoard[i][j] == ' ') {
					emptyGrids++;
				}
			}
		}
		occupiedGrids = CommonConstant.GRIDNUM - emptyGrids;
		if ((emptyGrids % 2) == 1) {
			player = CommonConstant.O;
			opponent = CommonConstant.X;
		} else {
			player = CommonConstant.X;
			opponent = CommonConstant.O;
		}
		// calculate the score of each possible move at this stage
		char[][] temp;
		ArrayList<Integer> score = new ArrayList<Integer>();
		ArrayList<Move> move = new ArrayList<Move>();
		level = 1;
		for (int i = 0; i < chessBoardCol; i++) {
			for (int j = 0; j < chessBoardCol; j++) {
				if (gameBoard[i][j] == ' ') {
					temp = copy2DArray(gameBoard);
					temp[i][j] = player;
					score.add(minMax(temp, occupiedGrids + 1, level + 1));
					Move tMove = new Move(i,j);
					move.add(tMove);
				}
			}
		}
		// choose the best move
		int index = 0, max = score.get(0);
		for( int i = 1; i < score.size(); i++ ){
			if( score.get(i) > max ){
				max = score.get(i);
				index = i;
			}
		}
		return move.get(index);
	}
	/*
	 * copy a 2D array
	 */
	private char[][] copy2DArray(char[][] gameBoard){
		char[][] temp = new char[gameBoard.length][];
		for( int i = 0; i < gameBoard.length; i++ ){
			temp[i] = gameBoard[i].clone();
		}
		return temp;
	}
	/*
	 * using miniMax algorithm to calculate weights for each possible move to choose a best move
	 */
	private int minMax(char[][] gameBoard, int oGrids, int lv) {
		ArrayList<Integer> score = new ArrayList<Integer>();
		char[][] temp;
		char activePlayer;
		if ((lv % 2) == 1) {
			activePlayer = player;
		} else {
			activePlayer = opponent;
		}
		/*
		 * check if game is over
		 * and return score when game is over
		 */
		int result = getGameState(oGrids, gameBoard);
		switch (result) {
		case CommonConstant.OWIN:
			if (player == CommonConstant.O) {
				return score(player, lv);
			} else {
				return score(opponent, lv);
			}
		case CommonConstant.XWIN:
			if (player == CommonConstant.X) {
				return score(player, lv);
			} else {
				return score(opponent, lv);
			}
		case CommonConstant.DRAW:
			return score(CommonConstant.DRAWCHAR, lv);
		default:
		case CommonConstant.CONTINUE:
			for (int i = 0; i < chessBoardCol; i++) {
				for (int j = 0; j < chessBoardCol; j++) {
					if (gameBoard[i][j] == ' ') {
						temp = copy2DArray(gameBoard);
						temp[i][j] = activePlayer;	
						score.add(minMax(temp, oGrids + 1, lv + 1));
					}
				}
			}
			// choose the best score
			if (activePlayer == player) {
				// return the max score(because player try to maximize the possibility of winning)
				Collections.sort(score);
				return score.get(score.size() - 1);
			} else {
				/* 
				 * return the min score(because the opponent try to interfere with the winning 
				 * of player)
				 */
				Collections.sort(score);
				return score.get(0);
			}
		}		
	}
	/*
	 * checking if there is a player satisfying the winning criteria
	 */
	private boolean checkWinner(char target, char[][] chessBoard) {
		char tempVertival, tempHorizontal, tempDiagonal1 = chessBoard[0][0],
				tempDiagonal2 = chessBoard[0][chessBoardCol - 1];
		for (int i = 0; i < chessBoardCol; i++) {
			tempHorizontal = chessBoard[i][0];
			tempVertival = chessBoard[0][i];
			/*
			 * check if there is a consecutive character 'target' filled in all
			 * grids of one horizontal line
			 */
			if (tempHorizontal == target) {
				for (int j = 1; j < chessBoardCol; j++) {
					tempHorizontal &= chessBoard[i][j];
					/*
					 * stop checking once there is a grid which is not filled
					 * with the character 'target'
					 */
					if (tempHorizontal != target) {
						break;
					} else if ((j == (chessBoardCol - 1)) && (tempHorizontal == target)) {
						return true;
					}
				}
			}
			/*
			 * check if there is a consecutive character 'target' filled in all
			 * grids of one vertical line
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
			 * check if there is a consecutive character 'target' filled in all
			 * grids of one diagonal line
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
	/*
	 * determine the current status of the game, such as who wins, draw or continue
	 */
	private int getGameState(int count, char[][] gameBoard) {
		if (checkWinner(CommonConstant.O, gameBoard)) {
			return CommonConstant.OWIN;
		} else if (checkWinner(CommonConstant.X, gameBoard)) {
			return CommonConstant.XWIN;
		} else if (count == CommonConstant.GRIDNUM) {
			return CommonConstant.DRAW;
		} else {
			return CommonConstant.CONTINUE;
		}
	}
	/*
	 * calculating the score according the ID of winner and the depth of the move
	 */
	private int score(char winner, int lv) {
		if (winner == player) {
			return CommonConstant.SCOREFORWIN - lv;
		} else if (winner == opponent) {
			return lv - CommonConstant.SCOREFORWIN;
		} else {
			return 0;
		}
	}
}
