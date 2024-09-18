
package hw2;

import api.PlayerPosition;
import api.BallType;
import static api.PlayerPosition.*;
import static api.BallType.*;

/* 
 * @author Onur Onal
 */

public class ThreeCushion {	
	
	// The player who wins the lag
	private PlayerPosition lagWinner;
	
	// The player who loses the lag
	private PlayerPosition lagLoser;
	
	// The current player
	private PlayerPosition currentPlayer;
	
	// The current inning player
	private PlayerPosition inningPlayer;
	
	// The cue ball the lag winner selects
	private BallType cueBall;
		
	// The cue ball of the other player
	private BallType cueBallOther;
	
	// The first is always the red ball because it cannot be the cue ball
	private BallType ballOne = RED;
	
	// The second ball of the winner
	private BallType ballTwoA;
	
	// The second ball of the loser
	private BallType ballTwoB;
	
	// The number of points required to win the game
	private int pointsToWin;
	
	// The score for the first player;
	private int playerAScore;
	
	// The score for the second player;
	private int playerBScore;
	
	// The number of innings 
	private int inningCount = 1;
	
	// Whether the player has taken their first shot of the inning
	private int firstShotInning = 0;
	
	// The amount of times player A has struck the first ball
	private int ballOneStrikeA = 0;
	
	// The amount of times player A has struck the second ball
	private int ballTwoStrikeA = 0;
	
	// The amount of times player B has struck the first ball
	private int ballOneStrikeB = 0;
	
	// The amount of times player B has struck the second ball
	private int ballTwoStrikeB = 0;
	
	// The amount of times the cue ball of player A has struck the cushion
	private int cushionStrikeA = 0;
	
	// The amount of times the cue ball of player B has struck the cushion
	private int cushionStrikeB = 0;
	
	// Whether the ball has been broken
	private int broken = 0;
	
	// Whether the player has struck a ball
	private boolean struckBall = false;
	
	// Whether the lag winner chooses to break  
	private boolean selfBreak;
	
	// Indicates whether the cue ball is selected or not 
	private boolean cueBallSelected = false;
	
	// Whether a shot has been made and the cue ball is still in motion
	private boolean shotMotion = false;
	
	// Whether an inning is in progress
	private boolean inning = false;
	
	// Boolean for rule 3A
	private boolean foulInning;
	
	// Whether player A has committed a foul
	private boolean foulA = false;
	
	// Whether player B has committed a foul
	private boolean foulB = false;
	
	// Whether player A's last shot was a bank shot
	private boolean bankA = false;
	
	// Whether player B's last shot was a bank shot
	private boolean bankB = false;

	// Whether the game has ended
	private boolean gameEnded;
	
	/*
	 * Constructor to start a new game of three-cushion billiards
	 * @param - lagWinner: The player who wins the lag
	 * @param - pointsToWin: The number of points required to win the game
	 */
	public ThreeCushion(PlayerPosition lagWinner, int pointsToWin) {
		
		this.pointsToWin = pointsToWin;
		this.lagWinner = lagWinner;

		if (lagWinner == PLAYER_A) {
			lagLoser = PLAYER_B;
		}
		else if (lagWinner == PLAYER_B) {
			lagLoser = PLAYER_A;
		}
		
		playerAScore = 0;
		playerBScore = 0;
				
		gameEnded = false;
	}
	
	/*
	 * The lag winner chooses whether to break or let the opposition break, and also his/her cue ball
	 * @param - selfBreak: Who the lag winner chooses to break
	 * @param - cueBall: The ball the lag winner chooses for his cue ball
	 */
	public void lagWinnerChooses(boolean selfBreak, BallType cueBall) {
	
		if ((!cueBallSelected) && (cueBall != RED)) {
			
			this.cueBall = cueBall;
			this.selfBreak = selfBreak;
			cueBallSelected = true;
						
			if (selfBreak) {
				currentPlayer = lagWinner;
				inningPlayer = lagWinner;
			}
			else if (!selfBreak) {
				currentPlayer = lagLoser;
				inningPlayer = lagLoser;
			}
						
			if (cueBall == WHITE) {
				cueBallOther = YELLOW;
				ballTwoA = YELLOW;
				ballTwoB = WHITE;
			}
			else if (cueBall == YELLOW) {
				cueBallOther = WHITE;
				ballTwoA = WHITE;
				ballTwoB = YELLOW;
			}		
		}
	}
	
	/*
	 * The cue stick strikes a ball
	 * @param - ball: The given ball that the cue stick strikes
	 */
	public void cueStickStrike(BallType ball) {
	
		if (((selfBreak) || (!selfBreak)) && (!gameEnded)) {
			
			shotMotion = false;
			inning = false;
			struckBall = true;
			bankA = false;
			bankB = false;
						
			if ((shotMotion) || (inning) || (ball != cueBall)) {
				foul();
			}
			
			if ((!foulA) && (!foulB)) {
				shotMotion = true;
				firstShotInning = 1;
			}
			shotMotion = true;
		}
	}
	
	/*
	 * The cue ball strikes another ball
	 * @param - ball: The ball being struck
	 */
	public void cueBallStrike(BallType ball) {
	
		if ((struckBall) && (!gameEnded)) {
			
	
			if (ball == ballOne) {
				if (inningPlayer == PLAYER_A) {
					if ((cushionStrikeA == 0) || (cushionStrikeA == 3)) {
						ballOneStrikeA++;
					}
					
					if (cushionStrikeA > 0) {
						inning = true;
					}
				}
				else if (inningPlayer == PLAYER_B) {
					if ((cushionStrikeB == 0) || (cushionStrikeB == 3)) {
						ballOneStrikeB++;
					}
						
					if (cushionStrikeB > 0) {
						inning = true;
					}
				}
			}
			
			if (ball == ballTwoA) {
				if (inningPlayer == PLAYER_A) {
					ballTwoStrikeA++;
				}
				else if (inningPlayer == PLAYER_B) {
					ballTwoStrikeB++;
				}			}
			
			if (ball == ballTwoB) {
				if (inningPlayer == PLAYER_B) {
					ballTwoStrikeB++;
				}
				else if (inningPlayer == PLAYER_A) {
					ballTwoStrikeA++;
				}
			}
		}
	}
	
	/*
	 * The cue ball strikes the cushion
	 */
	public void cueBallImpactCushion() {
	
		if ((struckBall) && (!gameEnded)) {
			
			if (inningPlayer == PLAYER_A) {
				cushionStrikeA++;
				
				if ((cushionStrikeA == 3) && (ballOneStrikeA == 0)
						&& (ballTwoStrikeA == 0)) {
					bankA = true;
				}
			}
			
			else if (inningPlayer == PLAYER_B) {
				cushionStrikeB++;
				
				if ((cushionStrikeB == 3) && (ballOneStrikeB == 0)
						&& (ballTwoStrikeB == 0)) {
					bankB = true;
				}
			}
		}
	}
	
	/*
	 * Stops the motion of the balls
	 */
	public void endShot() {
				
		if ((struckBall) && (shotMotion) && (!gameEnded)) {
				
			shotMotion = false;
			struckBall = false;
			
			if (ballTwoStrikeA == 0) {
				bankA = false;
			}
			if (ballTwoStrikeB == 0) {
				bankB = false;
			}
			
			if (inningPlayer == PLAYER_A) {
				if (!foulA) {
					
					if ((ballOneStrikeA >= 1) && (ballTwoStrikeA >= 1) 
							&& (cushionStrikeA >= 3)) {
						playerAScore += 1;
						ballOneStrikeA = 0;
						ballTwoStrikeA = 0;
						cushionStrikeB = 0;
					}
					
					if (playerAScore == pointsToWin) {
						gameEnded = true;
					}
					
				}
			}
					
			else if (inningPlayer == PLAYER_B) {
				if (!foulB) {
					
					if ((ballOneStrikeB >= 1) && (ballTwoStrikeB >= 1) 
							&& (cushionStrikeB >= 3)) {
						playerBScore += 1;
						ballOneStrikeB = 0;
						ballTwoStrikeB = 0;
						cushionStrikeB = 0;
					}

					if (playerBScore == pointsToWin) {
						gameEnded = true;
					}
				}
			}
			broken = 1;
		}
	} 
	
	
	/*
	 * Indicates a foul, and ends the player's inning
	 */
	public void foul() {
	
		if (((selfBreak) || (!selfBreak)) && (!gameEnded)) {
			
			if (currentPlayer == PLAYER_A) {
				
				foulA = true;
				firstShotInning = 0;
				inning = false;
				inningCount++;	
				
				currentPlayer = PLAYER_B;
				
			}

			else if (currentPlayer == PLAYER_B) {
				
				foulB = true;
				firstShotInning = 0;
				inning = false;
				inningCount++;
				
				currentPlayer = PLAYER_A;
						
			}			
		}
	}
	
	/*
	 * Returns the score of the first player
	 */
	public int getPlayerAScore() {
		return playerAScore;
	}
	
	/*
	 * Returns the score of the second player
	 */
	public int getPlayerBScore() {
		return playerBScore;
	}

	/*
	 * Returns the number of innings
	 */
	public int getInning() {
		return inningCount;
	}

	/*
	 * Returns the cue ball of the current player
	 */
	public BallType getCueBall() {
		
		foulInning = inning;
				
		if (ballTwoStrikeA == 1) {
			if (ballOneStrikeA == 0) {
				foulInning = true;
			}
		}
		
		if (ballTwoStrikeB == 1) {
			if (ballOneStrikeB == 0) {
				foulInning = true;
			}
		}
		
		if (selfBreak) {
			
			if (foulInning) {
				return cueBallOther;
			}

			else {
				return cueBall;
			}
		}
		
		else if (!selfBreak) {

			if (foulInning) {
				return cueBall;
			}
			
			else {
				return cueBallOther;
			}
		}
		
		else {
			return null;
		}
	}
	
	/*
	 * Returns the current player
	 */
	public PlayerPosition getInningPlayer() {
		
		if ((ballOneStrikeA == 0) && (ballTwoStrikeA == 0) 
				&& (cushionStrikeA == 0)) {
			if ((broken == 1) && (!bankA)) {
				inningPlayer = PLAYER_B;
			}
			else if ((broken == 1) && (bankA)) {
				inningPlayer = currentPlayer;
			}
		}
		
		if ((ballOneStrikeB == 0) && (ballTwoStrikeB == 0) 
				&& (cushionStrikeB == 0)) {
			if ((broken == 1) && (!bankB)) {
				inningPlayer = PLAYER_A;
			}
			else if ((broken == 1) && (bankB)) {
				inningPlayer = currentPlayer;
			}
		}
				
		if (inningPlayer == PLAYER_A) {

			return PLAYER_A;
		}
		
		else if (inningPlayer == PLAYER_B) {

			return PLAYER_B;
		}
		
		else {
			return null;
		}
	}
	
	/*
	 * Returns true if current shot is the first shot of the game
	 */
	public boolean isBreakShot() {
		
		if (broken == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/*
	 * Returns true if most recent shot was a bank shot
	 */
	public boolean isBankShot() {
		 
		if ((bankA) || (bankB)) {
			return true;
		}
		else {
			return false;
		}
	}

	/*
	 * Returns true if a shot has been taken
	 */
	public boolean isShotStarted() {
		
		if (shotMotion) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/*
	 * Returns true if player has taken their first shot of the inning 
	 */
	public boolean isInningStarted() {
		
		if ((firstShotInning == 1) && (!gameEnded)) {
			return true;
		}
		else {
			return false;
		}
	}

	/*
	 * Returns true if the game is over
	 */
	public boolean isGameOver() {
		
		if (gameEnded) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/*
	 * Returns a string representation of the current game state
	 */
	public String toString() {
		
		String fmt = "Player A%s: %d, Player B%s: %d, Inning: %d %s%s";
		String playerATurn = "";
		String playerBTurn = ""; 
		String inningStatus = "";
		String gameStatus = "";
		
		if (getInningPlayer() == PLAYER_A) {
			playerATurn = "*";
		}
		else if (getInningPlayer() == PLAYER_B) {
			playerBTurn = "*";
		}
		if (isInningStarted()) {
			inningStatus = "started";
		} 
		else {
			inningStatus = "not started";
		}
		if (isGameOver()) {
			gameStatus = ", game result final";
		}
		return String.format(fmt, playerATurn, getPlayerAScore(), playerBTurn, getPlayerBScore(), getInning(),
				inningStatus, gameStatus);
	}
}

