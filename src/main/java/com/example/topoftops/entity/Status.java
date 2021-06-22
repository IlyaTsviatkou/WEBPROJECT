package com.example.topoftops.entity;

public enum Status {
    INACTIVE(0),
    ACTIVE(1),
    BLOCKED(2),
    DELETED(3);

    private int value;
    Status(int value){
        this.value = value;
    }


    public int getValue(){
        return value;
    }
}
