package de.vkoop;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MapUtilsTest {

    @Test
    public void testFlatten(){
        var input = Map.of("key1", Map.of("key2", "value"));
        final Map<String, Object> result = MapUtils.flatten(input);

        assertTrue(result.containsKey("key1.key2"));
    }

    @Test
    public void testUnflatten(){
        var input = Map.of("a.b.c", "test1", "a.b.d", "test2", "b","test3");

        var result = MapUtils.unflatten(input);

        assertEquals(2, result.keySet().size());
        assertTrue(result.containsKey("a"));
        assertTrue(result.containsKey("b"));
        final Object a = result.get("a");
        assertTrue(a instanceof Map<?,?>);
        assertTrue(((Map<?, ?>) a).containsKey("b"));
        assertFalse(((Map<?, ?>) a).containsKey("a"));

        assertEquals("test3", result.get("b"));
    }

}
