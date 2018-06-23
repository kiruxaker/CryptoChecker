package net.cryptochecker.exception;

public class MarketNotFoundException extends Exception {
    public MarketNotFoundException() {
    }

    public MarketNotFoundException(String message) {
        super(message);
    }
}
