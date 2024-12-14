package com.example.kungfuapp;

public enum ResultStatus {
    CLEAR(0), NORMAL(1), FAILED(2);

    private int resultId;

    ResultStatus(int id) {
        this.resultId = id;
    }

    public int getResultId() {
        return resultId;
    }
}
