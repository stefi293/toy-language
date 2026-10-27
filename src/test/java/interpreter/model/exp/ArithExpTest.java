package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.IValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArithExpTest {
    private IDictionary<String, IValue> symTable;

    private static IExp num(int n) {
        return new ValueExp(new IntValue(n));
    }

    private static IExp bool(boolean b) {
        return new ValueExp(new BoolValue(b));
    }

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    @Test
    void additionAndMultiplicationWork() throws MyException {
        // 2 + 3 * 5 = 17
        IExp expr = new ArithExp(ArithOp.ADD, num(2), new ArithExp(ArithOp.MUL, num(3), num(5)));
        assertEquals(new IntValue(17), expr.eval(symTable));
    }

    @Test
    void divisionByZeroThrowsException() {
        IExp expr = new ArithExp(ArithOp.DIV, num(10), num(0));
        var ex = assertThrows(ExpressionException.class, () -> expr.eval(symTable));
        assertEquals("Division by zero", ex.getMessage());
    }

    @Test
    void nonIntegerOperandThrowsException() {
        IExp expr = new ArithExp(ArithOp.ADD, num(2), bool(true));
        assertThrows(ExpressionException.class, () -> expr.eval(symTable));
    }

    @Test
    void undefinedVariableThrowsException() {
        IExp expr = new VarExp("x");
        assertThrows(ExpressionException.class, () -> expr.eval(symTable));
    }
}