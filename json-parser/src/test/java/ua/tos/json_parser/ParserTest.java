package ua.tos.json_parser;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Created by tos on 6/12/16.
 */
public class ParserTest {

    @Test
    @SuppressWarnings("unchecked")
    public void test() throws Exception {
        String json = IOUtils.toString(getClass().getResourceAsStream("/my.json"), Charset.defaultCharset());

        Map<String, Object> object = new JsonParser().parse(json);

        assertTrue("Deepest element", ((Map<String, Object>) ((Map<String, Object>) object.get("glossary"))
                .get("GlossDiv")).get("GlossList") != null);

        System.out.println(object);

        Glossary glossary = new Mapper().map(object);

    }


}
