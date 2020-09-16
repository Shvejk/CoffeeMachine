package machine;

import java.util.Scanner;

public class TextUI {
    final private Scanner scanner;
    final private CoffeeMachine coffeeMachine;

    public TextUI(Scanner scanner, CoffeeMachine coffeeMachine) {
        this.scanner = scanner;
        this.coffeeMachine = coffeeMachine;
    }

    public void start() {
        while (coffeeMachine.getCurrentState() != State.EXIT) {
            System.out.println(coffeeMachine.stateMessage());
            coffeeMachine.takeCommand(scanner.nextLine());
        }
    }
}
