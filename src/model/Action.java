package model;

/**
 * Action types and symbols
 */
public enum Action {
    UP("^"),
    DOWN("v"),
    LEFT("<"),
    RIGHT(">");

    private String symbol;

    Action(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
