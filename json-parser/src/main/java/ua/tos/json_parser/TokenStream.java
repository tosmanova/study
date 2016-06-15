package ua.tos.json_parser;


public class TokenStream {

    private final String json;
    private int position = 0;

    public TokenStream(String json) {
        this.json = json.replaceAll("\n", "");
        System.out.println(this.json);
    }

    <T> Token<T> peekNext() {
        Token<T> next = pollNext();
        if (next != null) {
            position = next.getPosition();
        }
        return next;
    }

    public <T> Token<T> pollNext() {
        Token<T> token = pollNextInner();
        System.out.println(token);
        return token;
    }

    @SuppressWarnings("unchecked")
    private <T> Token<T> pollNextInner() {
        int oldPosition = position;
        if (position >= json.length()) {
            return null;
        }

        switch (json.charAt(position)) {
            case ' ':
                position++;
                return pollNext();
            case '{':
                return (Token<T>) new Token<String>(TokenType.OBJECT_BEGIN, "{", position++);
            case '}':
                return (Token<T>) new Token<String>(TokenType.OBJECT_END, "}", position++);
            case '[':
                return (Token<T>) new Token<String>(TokenType.ARRAY_BEGIN, "[", position++);
            case ']':
                return (Token<T>) new Token<String>(TokenType.ARRAY_END, "]", position++);

            case '"':
                int indexOfClosingQuote = json.indexOf('"', position + 1);
                String value = json.substring(position + 1, indexOfClosingQuote);
                position = indexOfClosingQuote + 1;
                return (Token<T>) new Token<String>(TokenType.STRING, value, oldPosition);

            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                int i = 0;
                Double result = null;
                while (true) {
                    try {
                        i++;
                        result = Double.parseDouble(json.substring(position, position + i));
                    } catch (NumberFormatException e) {
                        break;
                    }
                }

                position += i - 1;
                return (Token<T>) new Token<Double>(TokenType.NUMBER, result, oldPosition);
            case ':':
                return (Token<T>) new Token<String>(TokenType.COLON, ":", position++);
            case ',':
                return (Token<T>) new Token<String>(TokenType.COMMA, ",", position++);

            case 't':
                position += 4;
                return (Token<T>) new Token<Boolean>(TokenType.BOOLEAN, true, oldPosition);
            case 'f':
                position += 5;
                return (Token<T>) new Token<Boolean>(TokenType.BOOLEAN, false, oldPosition);
            case 'n':
                position += 4;
                return (Token<T>) new Token<Boolean>(TokenType.NULL, null, oldPosition);
            default:
                throw new IllegalStateException("kaka");

        }
    }
}
