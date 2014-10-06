package com.southamptoncodedojo.minesweeper;

/**
 * Interface used for handling exceptions in Players.
 *
 * This should be implemented by the UI and passed to the Game so that it can report back exceptions without
 * crashing out of the game.
 */
public interface PlayerExceptionHandler {
    public void handleException(Player player, Exception exception);
}
