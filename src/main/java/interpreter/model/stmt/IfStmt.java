package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.type.BoolType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public record IfStmt(IExp cond, IStmt thenS, IStmt elseS) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue condVal = cond.eval(state.getSymTable());

        if (!condVal.getType().equals(new BoolType())) {
            throw new StatementException("Condition of IF statement is not a boolean.");
        }

        if (((BoolValue) condVal).val()) {
            state.getExeStack().push(thenS);
        } else {
            state.getExeStack().push(elseS);
        }

        return state;
    }

    @Override
    public String toString() {
        return "(if " + cond + " then " + thenS + " else " + elseS + ")";
    }
}