package interpreter.view;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.repository.Repository;

import java.util.Scanner;

public class TextMenu {
    private IStmt selectedProgram = null;

    public void show() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Interpreter Menu ---");
            System.out.println("Selected program: " + (selectedProgram == null ? "None" : selectedProgram.toString()));
            System.out.println("1. Select a program");
            System.out.println("2. Run the selected program");
            System.out.println("0. Exit");
            System.out.print("> ");

            String input = scanner.nextLine();
            switch (input) {
                case "1" -> selectProgramMenu(scanner);
                case "2" -> runSelectedProgram();
                case "0" -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void selectProgramMenu(Scanner scanner) {
        System.out.println("\n--- Available Programs ---");
        System.out.println("1. " + Examples.EX1);
        System.out.println("2. " + Examples.EX2);
        System.out.println("3. " + Examples.EX3);
        System.out.print("Select (1-3): ");

        String input = scanner.nextLine();
        switch (input) {
            case "1" -> selectedProgram = Examples.EX1;
            case "2" -> selectedProgram = Examples.EX2;
            case "3" -> selectedProgram = Examples.EX3;
            default -> System.out.println("Invalid selection. No program was selected.");
        }
    }

    private void runSelectedProgram() {
        if (selectedProgram == null) {
            System.out.println("Error: No program selected. Please select a program first.");
            return;
        }

        try {
            // Rule: Each run must start from a fresh PrgState
            PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), selectedProgram);
            Repository repo = new Repository(state);
            Controller ctrl = new Controller(repo, true); // true = display steps

            System.out.println("\n-- Execution Start --");
            ctrl.allStep();
            System.out.println("-- Execution End --");

        } catch (MyException e) {
            // Rule: Interpreter errors must be caught and shown as messages
            System.out.println("\nINTERPRETER ERROR: " + e.getMessage());
        } catch (Exception e) {
            // Catch any other unexpected Java crashes to ensure the menu stays alive
            System.out.println("\nUNEXPECTED SYSTEM ERROR: " + e.getMessage());
        }
    }
}