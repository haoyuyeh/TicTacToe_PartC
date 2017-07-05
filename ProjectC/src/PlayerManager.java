/*
 * Author: Hao Yu Yeh
 * Date: 18/05/2016
 * Comment: project C(TicTacToe game) in COMP90041
 */

import java.util.Arrays;
import java.lang.String;
import java.io.File;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.Math;

public class PlayerManager {
	// keep the record of the position of the next new player
	private int newPlayerPos;
	public Player[] playerTable;

	public PlayerManager() {
		newPlayerPos = 0;
		playerTable = new Player[CommonConstant.MAXPLAYERS];
	}
	/*
	 * use for searching particular player if player exists, the function will
	 * return the position index. otherwise, it will return a negative value
	 */
	public int searchPlayer(String userName) {
		HumanPlayer temp = new HumanPlayer();
		temp.setUserName(userName);
		// searching in the existed players(the index of the last element is exclusive)
		int exist = Arrays.binarySearch(playerTable, 0, newPlayerPos, temp, 
										Player.playerUserNameComparator);
		return exist;
	}
	/*
	 * if the user name has not been used or the number of existed players
	 * doesn't exceed the limit of players, it would add the new player into
	 * playerTable.
	 */
	public void addPlayer(String userName, String familyName, String givenName) {
		int exist = searchPlayer(userName);
		// user does not exist
		if(exist < CommonConstant.NOTFOUND){
			if(newPlayerPos < CommonConstant.MAXPLAYERS){
				HumanPlayer temp = new HumanPlayer();
				temp.setPlayerType("human");
				temp.setUserName(userName);
				temp.setFamilyName(familyName);
				temp.setGivenName(givenName);
				playerTable[newPlayerPos] = temp;
				newPlayerPos++;
				// binary search should be applied in a sorted array
				Arrays.sort(playerTable, 0, newPlayerPos, Player.playerUserNameComparator);
			}else{
				System.out.println(ErrorMessage.EXCEEDMAX);
			}
		}else{
			System.out.println(ErrorMessage.EXISTALREADY);
		}
	}
	/*
	 * if the user name has not been used or the number of existed players
	 * doesn't exceed the limit of players, it would add the new AI player into
	 * playerTable.
	 */
	public void addAIPlayer(String userName, String familyName, String givenName) {
		int exist = searchPlayer(userName);
		// user does not exist
		if(exist < CommonConstant.NOTFOUND){
			if(newPlayerPos < CommonConstant.MAXPLAYERS){
				AIPlayer temp = new AIPlayer();
				temp.setPlayerType("AI");
				temp.setUserName(userName);
				temp.setFamilyName(familyName);
				temp.setGivenName(givenName);
				playerTable[newPlayerPos] = temp;
				newPlayerPos++;
				// binary search should be applied in a sorted array
				Arrays.sort(playerTable, 0, newPlayerPos, Player.playerUserNameComparator);
			}else{
				System.out.println(ErrorMessage.EXCEEDMAX);
			}
		}else{
			System.out.println(ErrorMessage.EXISTALREADY);
		}
	}
	/*
	 * if the user name has been found, it would delete the user.
	 */
	public void removePlayer(String userName) {
		int exist = searchPlayer(userName);
		// not found in the array
		if(exist < CommonConstant.NOTFOUND){
			System.out.println(ErrorMessage.NOTEXIST);
		}else{
			// move the last element to the position of the deleted element
			playerTable[exist] = playerTable[newPlayerPos - 1];
			newPlayerPos--;
			// binary search should be applied in a sorted array
			Arrays.sort(playerTable, 0, newPlayerPos, HumanPlayer.playerUserNameComparator);
		}
	}
	/*
	 * it would remove all the users.
	 */
	public void removeAllPlayers() {
		newPlayerPos = 0;
	}
	/*
	 * if the user name has been found, it would modify the info of the user.
	 */
	public void editPlayer(String userName, String familyName, String givenName) {
		int exist = searchPlayer(userName);
		if(exist < CommonConstant.NOTFOUND){
			System.out.println(ErrorMessage.NOTEXIST);
		}else{
			playerTable[exist].setFamilyName(familyName);
			playerTable[exist].setGivenName(givenName);
		}
	}
	/*
	 * if the user name has been found, it would reset the game stats of the
	 * user.
	 */
	public void resetPlayerStats(String userName) {
		int exist = searchPlayer(userName);
		if(exist < CommonConstant.NOTFOUND){
			System.out.println(ErrorMessage.NOTEXIST);
		}else{
			playerTable[exist].setGamePlayed(CommonConstant.RESET);
			playerTable[exist].setGameWon(CommonConstant.RESET);
			playerTable[exist].setGameDrawn(CommonConstant.RESET);
		}
	}
	/*
	 * it would remove the game stats of all users.
	 */
	public void resetAllStats() {
		for (int i = 0; i < newPlayerPos; i++) {
			playerTable[i].setGamePlayed(CommonConstant.RESET);
			playerTable[i].setGameWon(CommonConstant.RESET);
			playerTable[i].setGameDrawn(CommonConstant.RESET);
		}
	}
	/*
	 * if the user name has been found, it would display the info of the user.
	 */
	public void displayPlayer(String userName) {
		int exist = searchPlayer(userName);
		if (exist < CommonConstant.NOTFOUND) {
			System.out.println(ErrorMessage.NOTEXIST);
		} else {
			System.out.println(playerTable[exist].getUserName() + "," + 
							   playerTable[exist].getFamilyName() + "," + 
							   playerTable[exist].getGivenName() + "," + 
							   playerTable[exist].getGamePlayed() + " games" + "," + 
							   playerTable[exist].getGameWon() + " wins" + "," + 
							   playerTable[exist].getGameDrawn() + " draws");
		}
	}
	/*
	 * it would display all users.
	 */
	public void displayAllPlayer() {
		for (int i = 0; i < newPlayerPos; i++) {
			System.out.println(playerTable[i].getUserName() + "," + 
							   playerTable[i].getFamilyName() + "," + 
							   playerTable[i].getGivenName() + "," + 
							   playerTable[i].getGamePlayed() + " games" + "," + 
							   playerTable[i].getGameWon() + " wins" + "," + 
							   playerTable[i].getGameDrawn() + " draws");
		}
	}
	/*
	 * display top MAXRANKINGPLAYERS players, if the number of players is
	 * greater or equal to MAXRANKINGPLAYERS. otherwise, display all players.
	 */
	public void displayRanking() {
		// calculating the win and draw rate
		for (int i = 0; i < newPlayerPos; i++) {
			if (playerTable[i].getGamePlayed() == 0) {
				playerTable[i].setWinRate(0);
				playerTable[i].setDrawRate(0);
			} else {
				// convert to the format of percentage
				playerTable[i].setWinRate(100.0 * (double)playerTable[i].getGameWon() / 
										  (double)playerTable[i].getGamePlayed());
				playerTable[i].setDrawRate(100.0 * (double)playerTable[i].getGameDrawn() / 
										   (double)playerTable[i].getGamePlayed());
			}
		}
		/*
		 * after the three sorting, it can break ties first by draw and then by
		 * username alphabetically
		 */
		Arrays.sort(playerTable, 0, newPlayerPos, Player.playerUserNameComparator);
		Arrays.sort(playerTable, 0, newPlayerPos, Player.playerDrawRateComparator);
		Arrays.sort(playerTable, 0, newPlayerPos, Player.playerWinRateComparator);
		System.out.println(" WIN  | DRAW | GAME | USERNAME");
		if (newPlayerPos >= CommonConstant.MAXRANKINGPLAYERS) {
			for (int i = 0; i < CommonConstant.MAXRANKINGPLAYERS; i++) {
				System.out.println(String.format(" %3d%% | %3d%% | %2d   | %s", 
												 Math.round(playerTable[i].getWinRate()),
												 Math.round(playerTable[i].getDrawRate()), 
												 playerTable[i].getGamePlayed(),
												 playerTable[i].getUserName()));
			}
		} else {
			for (int i = 0; i < newPlayerPos; i++) {
				System.out.println(String.format(" %3d%% | %3d%% | %2d   | %s", 
												 Math.round(playerTable[i].getWinRate()),
												 Math.round(playerTable[i].getDrawRate()), 
												 playerTable[i].getGamePlayed(),
												 playerTable[i].getUserName()));
			}
		}
		// other functions need an array sorted by user name
		Arrays.sort(playerTable, 0, newPlayerPos, Player.playerUserNameComparator);
	}
	/*
	 * store all the existed game stats into a .dat file
	 */
	public void storeStats(){
		try{
			PrintWriter write;
			write = new PrintWriter(new FileOutputStream("players.dat"));
			for( int i = 0; i < newPlayerPos; i++ ){
				write.println(playerTable[i].getPlayerType() + " " +
							  playerTable[i].getUserName() + "," + 
							  playerTable[i].getFamilyName() + "," + 
							  playerTable[i].getGivenName() + " " +
							  playerTable[i].getGamePlayed() + "," + 
							  playerTable[i].getGameWon() + "," +
							  playerTable[i].getGameDrawn());
			}
			write.close();
		}
		catch(FileNotFoundException e){
			
		}
	}
	/*
	 * set all stats of one player
	 */
	private void setPlayerData( String playerData ){
		String[] stats, content;
		stats = playerData.split(" ");		
		if( stats[0].equals("AI") ){
			AIPlayer temp = new AIPlayer();
			playerTable[newPlayerPos] = temp;
			playerTable[newPlayerPos].setPlayerType("AI");
		}
		else{
			HumanPlayer temp = new HumanPlayer();
			playerTable[newPlayerPos] = temp;
			playerTable[newPlayerPos].setPlayerType("human");
		}
		content = stats[1].split(",");
		playerTable[newPlayerPos].setUserName(content[0]);
		playerTable[newPlayerPos].setFamilyName(content[1]);
		playerTable[newPlayerPos].setGivenName(content[2]);
		content = stats[2].split(",");
		playerTable[newPlayerPos].setGamePlayed(Integer.parseInt(content[0]));
		playerTable[newPlayerPos].setGameWon(Integer.parseInt(content[1]));
		playerTable[newPlayerPos].setGameDrawn(Integer.parseInt(content[2]));		
	}
	/*
	 * restore all the game stats
	 */
	public void restoreStats(){
		try{
			Scanner read;
			read = new Scanner(new FileInputStream("players.dat"));
			String str = "";			
			// read all the data in the file
			while( read.hasNextLine() ){
				str = read.nextLine();
				setPlayerData(str);
				newPlayerPos++;
			}
			read.close();
		}
		catch(FileNotFoundException e){
			// because players.dat is not existed, it should create one
			File f = new File("players.dat");
			try{
				f.createNewFile();
			} 
			catch(IOException e1){
			}
		}
	}
}