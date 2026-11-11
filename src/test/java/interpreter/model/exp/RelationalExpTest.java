package interpreter.model.exp;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import interpreter.model.value.IValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RelationalExpTest {

    private IDictionary<String, IValue> symTable;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    @Test
    void evaluateLessThan() throws MyException {
        // 3 < 5 -> true
        RelationalExp expTrue = new RelationalExp(RelOp.LT, new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)));
        assertEquals(new BoolValue(true), expTrue.eval(symTable));

        // 5 < 3 -> false
        RelationalExp expFalse = new RelationalExp(RelOp.LT, new ValueExp(new IntValue(5)), new ValueExp(new IntValue(3)));
        assertEquals(new BoolValue(false), expFalse.eval(symTable));
    }

    @Test
    void evaluateLessThanOrEqual() throws MyException {
        // 3 <= 5 -> true
        RelationalExp expTrue1 = new RelationalExp(RelOp.LE, new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)));
        // 5 <= 5 -> true
        RelationalExp expTrue2 = new RelationalExp(RelOp.LE, new ValueExp(new IntValue(5)), new ValueExp(new IntValue(5)));
        assertEquals(new BoolValue(true), expTrue1.eval(symTable));
        assertEquals(new BoolValue(true), expTrue2.eval(symTable));

        // 5 <= 3 -> false
        RelationalExp expFalse = new RelationalExp(RelOp.LE, new ValueExp(new IntValue(5)), new ValueExp(new IntValue(3)));
        assertEquals(new BoolValue(false), expFalse.eval(symTable));
    }

    @Test
    void evaluateEqual() throws MyException {
        // 5 == 5 -> true
        RelationalExp expTrue = new RelationalExp(RelOp.EQ, new ValueExp(new IntValue(5)), new ValueExp(new IntValue(5)));
        assertEquals(new BoolValue(true), expTrue.eval(symTable));

        // 3 == 5 -> false
        RelationalExp expFalse = new RelationalExp(RelOp.EQ, new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)));
        assertEquals(new BoolValue(false), expFalse.eval(symTable));
    }

    @Test
    void evaluateNotEqual() throws MyException {
        // 3 != 5 -> true
        RelationalExp expTrue = new RelationalExp(RelOp.NE, new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)));
        assertEquals(new BoolValue(true), expTrue.eval(symTable));

        // 5 != 5 -> false
        RelationalExp expFalse = new RelationalExp(RelOp.NE, new ValueExp(new IntValue(5)), new ValueExp(new IntValue(5)));
        assertEquals(new BoolValue(false), expFalse.eval(symTable));
    }

    @Test
    void evaluateGreaterThan() throws MyException {
        // 5 > 3 -> true
        RelationalExp expTrue = new RelationalExp(RelOp.GT, new ValueExp(new IntValue(5)), new ValueExp(new IntValue(3)));
        assertEquals(new BoolValue(true), expTrue.eval(symTable));

        // 3 > 5 -> false
        RelationalExp expFalse = new RelationalExp(RelOp.GT, new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)));
        assertEquals(new BoolValue(false), expFalse.eval(symTable));
    }

    @Test
    void evaluateGreaterThanOrEqual() throws MyException {
        // 5 >= 3 -> true
        RelationalExp expTrue1 = new RelationalExp(RelOp.GE, new ValueExp(new IntValue(5)), new ValueExp(new IntValue(3)));
        // 5 >= 5 -> true
        RelationalExp expTrue2 = new RelationalExp(RelOp.GE, new ValueExp(new IntValue(5)), new ValueExp(new IntValue(5)));
        assertEquals(new BoolValue(true), expTrue1.eval(symTable));
        assertEquals(new BoolValue(true), expTrue2.eval(symTable));

        // 3 >= 5 -> false
        RelationalExp expFalse = new RelationalExp(RelOp.GE, new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)));
        assertEquals(new BoolValue(false), expFalse.eval(symTable));
    }

    @Test
    void nonIntegerLeftOperandThrowsExpressionException() {
        RelationalExp exp = new RelationalExp(RelOp.LT, new ValueExp(new BoolValue(true)), new ValueExp(new IntValue(5)));
        assertThrows(ExpressionException.class, () -> exp.eval(symTable));
    }

    @Test
    void nonIntegerRightOperandThrowsExpressionException() {
        RelationalExp exp = new RelationalExp(RelOp.LT, new ValueExp(new IntValue(5)), new ValueExp(new StringValue("test")));
        assertThrows(ExpressionException.class, () -> exp.eval(symTable));
    }
}