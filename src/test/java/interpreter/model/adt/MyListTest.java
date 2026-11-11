package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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

    @Test
    void toListReturnsElementsInInsertionOrder() {
        MyList<Integer> myList = new MyList<>();
        myList.add(1);
        myList.add(2);
        myList.add(3);

        List<Integer> list = myList.toList();

        // The elements should remain in the exact order they were added
        assertEquals(3, list.size());
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));

    }
}