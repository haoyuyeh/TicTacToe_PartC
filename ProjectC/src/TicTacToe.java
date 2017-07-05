/*
 * Author: Hao Yu Yeh
 * Date: 18/05/2016
 * Comment: project C(TicTacToe game) in COMP90041
 */

import java.util.Scanner;

public class TicTacToe {
	public static Scanner keyboard;
	private PlayerManager currentPlayers;
	private GameManager currentGame;
	
	public TicTacToe(){
		keyboard = new Scanner(System.in);
		currentPlayers = new PlayerManager();
		currentGame = new GameManager();
	}
	// restore game data such as player stats
	private void initial(){
		currentPlayers.restoreStats();
	}
	// start a game system
	public void run(){
		initial();
		System.out.println("Welcome to Tic Tac Toe!");
		while( true ){
			incomingCommand();
			processCommand();
		}		
	}
	// receiving commands
	private void incomingCommand(){
		System.out.println();
		System.out.print(">");
	}
	// processing received commands
	private void processCommand(){
		String commandLine = "";
		commandLine = keyboard.nextLine();
		String[] commands;
		commands = commandLine.split(" ");
		int length = commands.length;
		try{
			switch( commands[0] ){
			case Commands.ADDPLAYER:
				if( length == CommonConstant.MINARGUMENTS ){
					addPlayer( "" );
				}else{
					addPlayer( commands[1] );
				}				
				break;
			case Commands.ADDAIPLAYER:
				if( length == CommonConstant.MINARGUMENTS ){
					addAIPlayer( "" );
				}else{
					addAIPlayer( commands[1] );
				}				
				break;
			case Commands.DELETEPLAYER: 
				if( length == CommonConstant.MINARGUMENTS ){
					deletePlayer( "" );
				}else{
					deletePlayer( commands[1] );
				}				
				break;
			case Commands.EDITPLAYER: 
				if( length == CommonConstant.MINARGUMENTS ){
					editPlayer( "" );
				}else{
					editPlayer( commands[1] );
				}				
				break;
			case Commands.RESETSTATS:
				if( length == 1 ){
					resetStats( "" );
				}else{
					resetStats( commands[1] );
				}				
				break;
			case Commands.DISPLAYPLAYER:
				if( length == CommonConstant.MINARGUMENTS ){
					displayPlayer( "" );
				}else{
					displayPlayer( commands[1] );
				}				
				break;
			case Commands.SHOWRANKING:
				if( length == CommonConstant.MINARGUMENTS ){
					showRanking();
				}else{
					throw new InvalidArgumentsException("Incorrect number of arguments supplied to"
							+ " command." );
				}				
				break;
			case Commands.PLAY:
				if( length == CommonConstant.MINARGUMENTS ){
					play( "" );
				}else{
					play( commands[1] );
				}				
				break;
			case Commands.EXIT:
				if( length == CommonConstant.MINARGUMENTS ){
					exit();
				}else{
					throw new InvalidArgumentsException("Incorrect number of arguments supplied to"
							+ " command." );
				}				
				break;
			// throw an invalid command exception
			default: 
				throw new InvalidCommandException("'" + commands[0] + "'" + " is not a valid "
						+ "command." );
			}
		}
		catch(InvalidCommandException e){
			System.out.println( e.getMessage() );
		}
		catch(InvalidArgumentsException e){
			System.out.println( e.getMessage() );
		}
	}
	// adding a new user
	private void addPlayer( String remainedCommand ){
		try{
			if( remainedCommand == "" ){
				throw new InvalidArgumentsException("Incorrect number of arguments supplied to"
						+ " command." );
			}else{
				String[] commands;
				// format of remained command is username,family_name,given_name
				commands = remainedCommand.split(",");		
				if( commands.length >= CommonConstant.MAXARGUMENTS ){
					currentPlayers.addPlayer( commands[0], commands[1], commands[2] );
				}else{
					throw new InvalidArgumentsException("Incorrect number of arguments supplied to"
							+ " command." );
				}
			}
		}
		catch(InvalidArgumentsException e){
			System.out.println( e.getMessage() );
		}		
	}
	// adding a AI user
	private void addAIPlayer( String remainedCommand ){
		try{
			if( remainedCommand == "" ){
				throw new InvalidArgumentsException("Incorrect number of arguments supplied to"
							+ " command." );
			}else{
				String[] commands;
				// format of remained command is username,family_name,given_name
				commands = remainedCommand.split(",");		
				if( commands.length >= CommonConstant.MAXARGUMENTS ){
					currentPlayers.addAIPlayer( commands[0], commands[1], commands[2] );
				}else{
					throw new InvalidArgumentsException("Incorrect number of arguments supplied to"
							+ " command." );
				}
			}
		}
		catch(InvalidArgumentsException e){
			System.out.println( e.getMessage() );
		}
	}
	// edit the name of user
	private void editPlayer( String remainedCommand ){
		try{
			if( remainedCommand == "" ){
				throw new InvalidArgumentsException("Incorrect number of arguments supplied to"
						+ " command." );
			}else{
				String[] commands;
				// format of remained command is username,new_family_name,new_given_name
				commands = remainedCommand.split(",");		
				if( commands.length >= CommonConstant.MAXARGUMENTS ){
					currentPlayers.editPlayer( commands[0], commands[1], commands[2] );
				}else{
					throw new InvalidArgumentsException("Incorrect number of arguments supplied to"
							+ " command." );
				}
			}
		}
		catch(InvalidArgumentsException e){
			System.out.println( e.getMessage() );
		}
	}
	// delete players from game system
	private void deletePlayer( String remainedCommand ){
		if( remainedCommand == "" ){
			System.out.println( "Are you sure you want to remove all players? (y/n)" );
			if( keyboard.nextLine().compareTo("y") == 0 ){
				currentPlayers.removeAllPlayers();
			}
		}else{
			currentPlayers.removePlayer( remainedCommand );
		}
	}
	// clear the statistics of players
	private void resetStats( String remainedCommand ){
		if( remainedCommand == "" ){
			System.out.println( "Are you sure you want to reset all player statistics? (y/n)" );
			if( keyboard.nextLine().compareTo("y") == 0 ){
				currentPlayers.resetAllStats();
			}
		}else{
			currentPlayers.resetPlayerStats( remainedCommand );
		}
	}
	// display the info of players
	private void displayPlayer( String remainedCommand ){
		if( remainedCommand == "" ){
			currentPlayers.displayAllPlayer();
		}else{
			currentPlayers.displayPlayer( remainedCommand );
		}
	}
	// show the ranking of existed users
	private void showRanking(){
		currentPlayers.displayRanking();
	}
	// play a Tic Tac Toe game
	private void play( String remainedCommand ){
		try{
			if( remainedCommand == "" ){
				throw new InvalidArgumentsException("Incorrect number of arguments supplied to"
						+ " command." );
			}else{
				String[] commands;
				// format of remained command is username1,username2
				commands = remainedCommand.split(",");		
				if( commands.length >= CommonConstant.NUMOFGAMEPLAYERS ){
					/* 
					 * checking the validation of the usernames.
					 * if the users don't exist, the search function will return a negative value
					 */
					int p1Pos = currentPlayers.searchPlayer( commands[0] ), 
						p2Pos = currentPlayers.searchPlayer( commands[1] );
					if( ( p1Pos >= 0 ) && ( p2Pos >= 0 ) ){
						
						currentGame.playGame( currentPlayers.playerTable[p1Pos], 
											  currentPlayers.playerTable[p2Pos]);
					}else{
						System.out.println( ErrorMessage.WRONGUSER );
					}
				}else{
					throw new InvalidArgumentsException("Incorrect number of arguments supplied to"
							+ " command." );
				}
			}
		}
		catch(InvalidArgumentsException e){
			System.out.println( e.getMessage() );
		}
	}
	// exiting the game system and store all the game stats
	private void exit(){
		System.out.println();
		currentPlayers.storeStats();
		System.exit(0);
	}	
	public static void main(String[] args){
		TicTacToe gameSystem = new TicTacToe();
		gameSystem.run();
	}
}