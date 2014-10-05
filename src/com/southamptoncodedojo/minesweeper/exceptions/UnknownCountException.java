package com.southamptoncodedojo.minesweeper.exceptions;

/**
 * You have attempted to get the count for a space which has not been revealed yet
 */
public class UnknownCountException extends Exception {
    public UnknownCountException(String s) {
        super(s);
    }
}
