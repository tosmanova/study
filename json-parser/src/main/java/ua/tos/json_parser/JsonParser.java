package ua.tos.json_parser;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class JsonParser {

    Map<String, Object> parse(String json) throws ParseException {
        TokenStream tokenStream = new TokenStream(json);
        return readObject(tokenStream);
    }

    private Map<String, Object> readObject(TokenStream tokenStream) throws ParseException {
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        Token<Object> openQuote = tokenStream.pollNext();
        if (openQuote.getTokenType() != TokenType.OBJECT_BEGIN) {
            throw new ParseException("Object begin expected", openQuote.getPosition());
        }

        while (tokenStream.peekNext().getTokenType() != TokenType.OBJECT_END) {
            Token<String> key = tokenStream.pollNext();
            if (key.getTokenType() != TokenType.STRING) {
                throw new ParseException("String expected", key.getPosition());
            }
            Token<Object> colon = tokenStream.pollNext();
            Token<Object> valueToken = tokenStream.peekNext();
            Object value;
            switch (valueToken.getTokenType()) {
                case OBJECT_BEGIN:
                    value = readObject(tokenStream);
                    break;
                case STRING:
                case BOOLEAN:
                case NUMBER:
                case NULL:
                    value = tokenStream.pollNext().getValue();
                    break;
                case ARRAY_BEGIN:
                    value = readArray(tokenStream);
                    break;
                default:
                    throw new ParseException("Unexpected token " + valueToken.getValue(), valueToken.getPosition());

            }

            result.put(key.getValue(), value);

            if (tokenStream.peekNext().getTokenType() == TokenType.COMMA) {
                tokenStream.pollNext();
            }

        }

        return result;
    }

    private List<Object> readArray(TokenStream tokenStream) {
        List<Object> result = new LinkedList<Object>();
        tokenStream.pollNext();

        while (tokenStream.peekNext().getTokenType() != TokenType.ARRAY_END) {
            result.add(tokenStream.pollNext().getValue());
            if (tokenStream.peekNext().getTokenType() == TokenType.COMMA) {
                tokenStream.pollNext();
            }
        }

        tokenStream.pollNext();

        return result;
    }


}
