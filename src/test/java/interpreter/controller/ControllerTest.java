package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    @TempDir
    Path dir;

    private PrgState run(IStmt program) throws MyException {
        PrgState state = PrgState.of(program);
        var repo = new Repository(state, dir.resolve("log.txt").toString());
        new Controller(repo, false).allStep();
        return state;
    }

    @Test
    void example1PrintsTwo() throws MyException {
        PrgState state = run(Examples.EX1);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(2), state.getOut().get(0));
    }

    @Test
    void example2PrintsEighteen() throws MyException {
        PrgState state = run(Examples.EX2);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(18), state.getOut().get(0));
    }

    @Test
    void example3PrintsTwo() throws MyException {
        PrgState state = run(Examples.EX3);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(2), state.getOut().get(0));
    }

    @Test
    void oneStepOnEmptyStackThrowsException() {
        PrgState state = PrgState.of( new NopStmt());
        Controller ctrl = new Controller(new Repository(state, "log.txt"), false);

        try {
            // Pop the initial NopStmt to empty the stack
            ctrl.oneStep(state);
            // The stack is now empty, so calling oneStep again should throw
            assertThrows(StatementException.class, () -> ctrl.oneStep(state));
        } catch (MyException e) {
            fail("The first valid step should not have thrown an exception.");
        }
    }

    @Test
    void logFileContainsTheFourSectionsInOrder() throws Exception {
        run(Examples.EX1);
        String log = Files.readString(dir.resolve("log.txt"));

        int a = log.indexOf("ExeStack:");
        int b = log.indexOf("SymTable:");
        int c = log.indexOf("Out:");
        int d = log.indexOf("FileTable:");

        assertTrue(0 <= a && a < b && b < c && c < d,
                "Log file is missing sections or they are in the wrong order.");
    }
}