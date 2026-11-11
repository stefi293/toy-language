package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MyDictionaryTest {
    private IDictionary<String, Integer> dict;

    @BeforeEach
    void setUp() {
        dict = new MyDictionary<>();
    }

    @Test
    void putAndLookupWorkCorrectly() throws ADTException {
        dict.put("v", 5);
        assertTrue(dict.isDefined("v"));
        assertEquals(5, dict.lookup("v"));
    }

    @Test
    void updateChangesExistingValue() throws ADTException {
        dict.put("v", 5);
        dict.update("v", 10);
        assertEquals(10, dict.lookup("v"));
    }

    @Test
    void lookupMissingKeyThrowsException() {
        var exception = assertThrows(ADTException.class, () -> dict.lookup("missing"));
        assertTrue(exception.getMessage().contains("is not defined"));
    }

    @Test
    void removeExistingKeyDeletesItSuccessfully() throws ADTException {
        MyDictionary<String, Integer> dict = new MyDictionary<>();
        dict.put("var1", 10);
        dict.remove("var1");

        assertFalse(dict.isDefined("var1"));
    }

    @Test
    void removeMissingKeyThrowsADTException() {
        MyDictionary<String, Integer> dict = new MyDictionary<>();

        assertThrows(ADTException.class, () -> dict.remove("missingKey"));
    }

    @Test
    void toMapReturnsCorrectReadOnlyCopy() {
        MyDictionary<String, Integer> dict = new MyDictionary<>();
        dict.put("a", 1);
        dict.put("b", 2);

        Map<String, Integer> map = dict.toMap();

        assertEquals(2, map.size());
        assertEquals(1, map.get("a"));
        assertEquals(2, map.get("b"));

        // Verifies the returned map is read-only (Map.copyOf)
        assertThrows(UnsupportedOperationException.class, () -> map.put("c", 3));
    }
}