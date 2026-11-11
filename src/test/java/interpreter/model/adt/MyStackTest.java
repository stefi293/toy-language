package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyStackTest {
    private IStack<Integer> stack;

    @BeforeEach
    void setUp() {
        stack = new MyStack<>();
    }

    @Test
    void respectsLifoOrder() throws ADTException {
        stack.push(10);
        stack.push(20);
        assertEquals(20, stack.pop());
        assertEquals(10, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void popOnEmptyStackThrowsException() {
        var exception = assertThrows(ADTException.class, () -> stack.pop());
        assertEquals("pop from an empty stack", exception.getMessage());
    }

    @Test
    void toListReturnsElementsWithTopFirst() {
        MyStack<Integer> stack = new MyStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);

        List<Integer> list = stack.toList();

        // The last pushed element (3) is at the top of the stack, so it must be first in the list
        assertEquals(3, list.size());
        assertEquals(3, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(1, list.get(2));
    }
}