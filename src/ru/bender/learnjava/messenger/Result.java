package ru.bender.learnjava.messenger;

/**
 * Created by bender on 16.10.16.
 */
public class Result {
    private boolean isError;
    private String message;

    public Result(boolean isError, String message) {
        this.isError = isError;
        this.message = message;
    }

    public Result(boolean isError) {
        new Result(isError, "");
    }

    public boolean isError() {
        return isError;
    }

    public String getMessage() {
        return message;
    }
}
