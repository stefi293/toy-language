package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;

public record ArithExp(ArithOp op, IExp left, IExp right) implements IExp {
    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        IValue v1 = left.eval(symTable);
        if (!v1.getType().equals(new interpreter.model.type.IntType())) {
            throw new ExpressionException("First operand of arithmetic expression is not an integer");
        }

        IValue v2 = right.eval(symTable);
        if (!v2.getType().equals(new interpreter.model.type.IntType())) {
            throw new ExpressionException("Second operand of arithmetic expression is not an integer");
        }

        int n1 = ((IntValue) v1).val();
        int n2 = ((IntValue) v2).val();

        return switch (op) {
            case ADD -> new IntValue(n1 + n2);
            case SUB -> new IntValue(n1 - n2);
            case MUL -> new IntValue(n1 * n2);
            case DIV -> {
                if (n2 == 0) {
                    throw new ExpressionException("Division by zero");
                }
                yield new IntValue(n1 / n2);
            }
        };
    }

    @Override
    public String toString() {
        return "(" + left + " " + op + " " + right + ")";
    }
}