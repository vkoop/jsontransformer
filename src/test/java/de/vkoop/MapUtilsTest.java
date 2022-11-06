package de.vkoop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MapUtilsTest {

    @Test
    public void testFlatten(){
        var input = Map.of("key1", Map.of("key2", "value"));
        final Map<String, Object> result = MapUtils.flatten(input);

        assertTrue(result.containsKey("key1_key2"));
    }

    @Test
    public void testUnflatten(){
        var input = Map.of("a_b_c", "test1", "a_b_d", "test2", "b","test3");

        var result = MapUtils.unflatten(input);

        assertEquals(2, result.keySet().size());
        assertTrue(result.containsKey("a"));
        assertTrue(result.containsKey("b"));
        assertEquals("test3", result.get("b"));
    }

}
