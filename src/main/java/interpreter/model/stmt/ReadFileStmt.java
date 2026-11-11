package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public record ReadFileStmt(IExp exp, String varName) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        if (!symTable.isDefined(varName)) {
            throw new StatementException("readFile: variable " + varName + " is not declared");
        }
        if (!(symTable.lookup(varName).getType() instanceof IntType)) {
            throw new StatementException("readFile: variable " + varName + " is not of type int");
        }
        if (!(exp.eval(symTable) instanceof StringValue fileName)) {
            throw new StatementException("readFile: " + exp + " does not evaluate to a string");
        }
        var fileTable = state.getFileTable();
        if (!fileTable.isDefined(fileName)) {
            throw new StatementException("readFile: file " + fileName + " is not open in FileTable");
        }

        BufferedReader reader = fileTable.lookup(fileName);
        try {
            String line = reader.readLine();
            int value = 0;
            if (line != null) {
                value = Integer.parseInt(line.strip());
            }
            symTable.update(varName, new IntValue(value));
        } catch (IOException e) {
            throw new StatementException("readFile: I/O error reading from " + fileName + ": " + e.getMessage());
        } catch (NumberFormatException e) {
            throw new StatementException("readFile: line from " + fileName + " is not a valid integer");
        }
        return state;
    }

    @Override
    public String toString() {
        return "readFile(" + exp + ", " + varName + ")";
    }
}