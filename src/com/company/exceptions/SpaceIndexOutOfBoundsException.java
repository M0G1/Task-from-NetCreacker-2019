package com.company.exceptions;

public class SpaceIndexOutOfBoundsException extends IndexOutOfBoundsException {
    public SpaceIndexOutOfBoundsException(){
    }

    public SpaceIndexOutOfBoundsException(String message){
        super(message);
    }
}
