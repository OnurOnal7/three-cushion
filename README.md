
# Three Cushion

## Overview
This project implements a Java class, `ThreeCushion`, that models the game of three-cushion billiards. The game involves two players competing to score a predetermined number of points by making successful shots. A shot is considered successful when the player's cue ball strikes both object balls and impacts at least three cushions before striking the second object ball.

The class allows tracking of game states, player turns, shots, fouls, and the game-ending condition.

## Features
- **Game Setup:**
  - Players can lag to determine who will take the break shot and choose their cue ball.
  - Players alternate turns based on successful shots or fouls.
  
- **Shot Logic:**
  - A shot is valid if the player’s cue ball impacts the table’s cushions three or more times before hitting the second object ball.
  - The game keeps track of whether a shot is valid or if a foul was committed.

- **Scoring and Ending:**
  - Players score points based on successful shots.
  - The game ends when a player reaches the predetermined score.

- **Fouls:**
  - The class detects fouls like hitting the wrong ball or striking balls while they are still in motion.

- **Bank Shots:**
  - Detection of bank shots, which are more challenging shots where the cue ball makes all cushion impacts before hitting the object balls.

## Public Methods
- `lagWinnerChooses(boolean takeBreakShot, BallType cueBall)`: The lag winner chooses whether to take the break shot and assigns their cue ball.
- `cueStickStrike(BallType cueBall)`: Starts a shot by striking the cue ball.
- `cueBallStrike(BallType objectBall)`: Registers the impact of the cue ball with an object ball.
- `cueBallImpactCushion()`: Registers the impact of the cue ball with a cushion.
- `endShot()`: Ends the shot after all balls have stopped moving.
- `foul()`: Marks a foul, ending the current inning.
- `isGameEnded()`: Checks if the game has ended based on the score.
- `isBankShot()`: Checks if the last shot was a bank shot.
- `getInningPlayer()`: Returns the current player in the inning.
- `getCueBall()`: Returns the player's current cue ball.
- `getInning()`: Returns the current inning number.
- `isShotStarted()`: Checks if a shot has started.
- `isBreakShot()`: Checks if the current shot is the break shot.
