package ua.tos.json_parser;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;
import static ua.tos.json_parser.TokenType.OBJECT_BEGIN;

/**
 * Created by tos on 6/12/16.
 */
public class TokenStreamTest {

    @Test
    public void testStream() throws Exception {
        String json = IOUtils.toString(getClass().getResourceAsStream("/my.json"), Charset.defaultCharset());
        TokenStream tokenStream = new TokenStream(json);
        Token<String> token = tokenStream.peekNext();

        assertEquals("First token type", OBJECT_BEGIN, token.getTokenType());
        assertEquals("First token value", "{", token.getValue());
        assertEquals("First token position", 0, token.getPosition());

        int tokenCount = 0;
        while (true) {
            token = tokenStream.pollNext();
            if (token == null) {
                break;
            }
            System.out.println(String.valueOf(token.getValue()));
            tokenCount++;
        }

        assertEquals("Token count", 77, tokenCount);
    }
}
