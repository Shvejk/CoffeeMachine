package machine;

import java.util.Scanner;

public class CoffeeMachine {
    private int water;
    private int milk;
    private int coffeeBeans;
    private int cups;
    private int money;
    private State currentState;

    public CoffeeMachine(int water, int milk, int coffeeBeans, int cups, int money) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cups = cups;
        this.money = money;
        currentState = State.MAIN_MENU;
    }

    //should be in its own class but fails Hyperskill test
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CoffeeMachine coffeeMachine = new CoffeeMachine(400, 540, 120, 9, 550);
        TextUI ui = new TextUI(scanner, coffeeMachine);
        ui.start();
    }

    public String stateMessage() {
        switch (getCurrentState()) {
            case MAIN_MENU:
                return "Write action (buy, fill, take, remaining, exit):";
            case BUY:
                return "\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:";
            case FILL_WATER:
                return "\nWrite how many ml of water do you want to add:";
            case FILL_MILK:
                return "Write how many ml of milk do you want to add:";
            case FILL_COFFEE_BEANS:
                return "Write how many grams of coffee beans do you want to add:";
            case FILL_CUPS:
                return "Write how many disposable cups of coffee do you want to add:";
        }
        return "";
    }

    public void takeCommand(String command) {
        switch (currentState) {
            case MAIN_MENU:
                mainMenuCommands(command);
                break;
            case BUY:
                buyCommands(command);
                break;
            case FILL_WATER:
                fillWater(command);
                break;
            case FILL_MILK:
                fillMilk(command);
                break;
            case FILL_COFFEE_BEANS:
                fillCoffeeBeans(command);
                break;
            case FILL_CUPS:
                fillCups(command);
                break;
        }
    }

    private void mainMenuCommands(String command) {
        switch (command) {
            case "buy":
                currentState = State.BUY;
                break;
            case "fill":
                currentState = State.FILL_WATER;
                break;
            case "take":
                take();
                break;
            case "remaining":
                remaining();
                break;
            case "exit":
                currentState = State.EXIT;
                break;
        }
    }

    private void buyCommands(String command) {
        switch (command) {
            case "1":
                buyCoffee(CoffeeMenu.ESPRESSO);
                break;
            case "2":
                buyCoffee(CoffeeMenu.LATTE);
                break;
            case "3":
                buyCoffee(CoffeeMenu.CAPPUCCINO);
                break;
            case "back":
                currentState = State.MAIN_MENU;
                break;
        }
    }

    private void fillWater(String waterToAdd) {
        this.water += Integer.parseInt(waterToAdd);
        currentState = State.FILL_MILK;
    }

    private void fillMilk(String milkToAdd) {
        this.milk += Integer.parseInt(milkToAdd);
        currentState = State.FILL_COFFEE_BEANS;
    }

    private void fillCoffeeBeans(String coffeeBeansToAdd) {
        this.coffeeBeans += Integer.parseInt(coffeeBeansToAdd);
        currentState = State.FILL_CUPS;
    }

    private void fillCups(String cupsToAdd) {
        this.cups += Integer.parseInt(cupsToAdd);
        System.out.println();
        currentState = State.MAIN_MENU;
    }

    public void printCoffeeMachineStatus() {
        System.out.println("\nThe coffee machine has:\n" +
                water + " of water\n" +
                milk + " of milk\n" +
                coffeeBeans + " of coffee beans\n" +
                cups + " of disposable cups\n" +
                money + " of money\n");
    }

    private void remaining() {
        printCoffeeMachineStatus();
        currentState = State.MAIN_MENU;
    }

    public State getCurrentState() {
        return currentState;
    }

    private void buyCoffee(CoffeeMenu coffee) {
        if (checkSupplies(coffee.getWater(), coffee.getMilk(), coffee.getCoffeeBeans())) {
            System.out.println("I have enough resources, making you a coffee!\n");
            this.water -= coffee.getWater();
            this.milk -= coffee.getMilk();
            this.coffeeBeans -= coffee.getCoffeeBeans();
            this.cups--;
            this.money += coffee.getMoney();
        }
        currentState = State.MAIN_MENU;
    }

    public void take() {
        System.out.println("\nI gave you $" + money + "\n");
        this.money = 0;
    }

    private boolean checkSupplies(int water, int milk, int coffeeBeans) {
        return (checkWater(water) && checkMilk(milk) && checkCoffeeBeans(coffeeBeans) && checkCups());
    }

    private boolean checkWater(int water) {
        if (this.water - water < 0) {
            System.out.println("Sorry, not enough water!\n");
            return false;
        }
        return this.water - water >= 0;
    }

    private boolean checkMilk(int milk) {
        if (this.milk - milk < 0) {
            System.out.println("Sorry, not enough milk!\n");
            return false;
        }
        return true;
    }

    private boolean checkCoffeeBeans(int coffeeBeans) {
        if (this.coffeeBeans - coffeeBeans < 0) {
            System.out.println("Sorry, not enough coffee beans!\n");
            return false;
        }
        return true;
    }

    private boolean checkCups() {
        if (this.cups == 0) {
            System.out.println("Sorry, not enough cups!\n");
        }
        return true;
    }
}
