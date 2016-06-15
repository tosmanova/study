package ua.tos.json_parser;

/**
 * Created by tos on 6/12/16.
 */
public class Token<T> {

    private TokenType tokenType;
    private T value;
    private int position;

    public Token(TokenType tokenType, T value, int position) {
        this.tokenType = tokenType;
        this.value = value;
        this.position = position;
    }

    TokenType getTokenType() {
        return tokenType;
    }

    T getValue() {
        return value;
    }

    int getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Token{" +
                "tokenType=" + tokenType +
                ", value=" + value +
                ", position=" + position +
                '}';
    }
}
