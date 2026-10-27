package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyListTest {
    private IList<String> list;

    @BeforeEach
    void setUp() {
        list = new MyList<>();
    }

    @Test
    void addAndGetWorkCorrectly() throws ADTException {
        list.add("First");
        list.add("Second");
        assertEquals("First", list.get(0));
        assertEquals("Second", list.get(1));
        assertEquals(2, list.size());
    }

    @Test
    void getOutOfBoundsThrowsException() {
        assertThrows(ADTException.class, () -> list.get(0));
    }
}