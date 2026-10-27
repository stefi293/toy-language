package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() {
        // Create a fresh state with a dummy initial statement
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
        // Pop the dummy statement to leave a clean stack for our tests
        try {
            state.getExeStack().pop();
        } catch (Exception ignored) {}
    }

    @Test
    void varDeclAddsToSymTable() throws MyException {
        IStmt stmt = new VarDeclStmt("v", new IntType());
        stmt.execute(state);
        assertTrue(state.getSymTable().isDefined("v"));
        assertEquals(new IntValue(0), state.getSymTable().lookup("v"));
    }

    @Test
    void varDeclTwiceThrowsException() throws MyException {
        IStmt stmt1 = new VarDeclStmt("v", new IntType());
        IStmt stmt2 = new VarDeclStmt("v", new BoolType());
        stmt1.execute(state);
        assertThrows(StatementException.class, () -> stmt2.execute(state));
    }

    @Test
    void assignUpdatesSymTable() throws MyException {
        new VarDeclStmt("v", new IntType()).execute(state);
        IStmt assign = new AssignStmt("v", new ValueExp(new IntValue(5)));
        assign.execute(state);
        assertEquals(new IntValue(5), state.getSymTable().lookup("v"));
    }

    @Test
    void assignToUndeclaredThrowsException() {
        IStmt assign = new AssignStmt("v", new ValueExp(new IntValue(5)));
        assertThrows(StatementException.class, () -> assign.execute(state));
    }

    @Test
    void assignTypeMismatchThrowsException() throws MyException {
        new VarDeclStmt("v", new IntType()).execute(state);
        IStmt assign = new AssignStmt("v", new ValueExp(new BoolValue(true)));
        assertThrows(StatementException.class, () -> assign.execute(state));
    }

    @Test
    void printAddsToOutList() throws MyException {
        IStmt print = new PrintStmt(new ValueExp(new IntValue(10)));
        print.execute(state);
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(10), state.getOut().get(0));
    }

    @Test
    void ifPushesCorrectBranch() throws MyException {
        IStmt thenS = new PrintStmt(new ValueExp(new IntValue(1)));
        IStmt elseS = new PrintStmt(new ValueExp(new IntValue(2)));

        // Test True branch
        IStmt ifTrue = new IfStmt(new ValueExp(new BoolValue(true)), thenS, elseS);
        ifTrue.execute(state);
        assertEquals(thenS, state.getExeStack().pop());

        // Test False branch
        IStmt ifFalse = new IfStmt(new ValueExp(new BoolValue(false)), thenS, elseS);
        ifFalse.execute(state);
        assertEquals(elseS, state.getExeStack().pop());
    }

    @Test
    void ifNonBooleanConditionThrowsException() {
        IStmt ifStmt = new IfStmt(new ValueExp(new IntValue(10)), new NopStmt(), new NopStmt());
        assertThrows(StatementException.class, () -> ifStmt.execute(state));
    }
}