package de.fh.stud.finalPacman.exceptions;

public class InvalidCoordinatesException extends Exception {

    public InvalidCoordinatesException(String text) {
        super(text);
    }

    public InvalidCoordinatesException(){}
}
