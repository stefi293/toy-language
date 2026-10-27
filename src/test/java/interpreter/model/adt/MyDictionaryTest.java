package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
}