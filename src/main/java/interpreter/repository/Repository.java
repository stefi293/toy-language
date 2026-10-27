package interpreter.repository;

import interpreter.model.PrgState;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private final List<PrgState> programStates;

    public Repository(PrgState initialState) {
        this.programStates = new ArrayList<>();
        this.programStates.add(initialState);
    }

    @Override
    public PrgState getCrtPrg() {
        return programStates.get(0);
    }
}