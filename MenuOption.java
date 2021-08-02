package numbers;

public enum MenuOption {
    ONE_NUMBER("\n- enter a natural number to know its properties;"),
    LIST_OF_NUMBERS("\n- enter two natural numbers to obtain the properties of the list:" +
            "\n  * the first parameter represents a starting number;" +
            "\n  * the second parameters show how many consecutive numbers are to be processed;"),
    NUMBERS_WITH_PROPERTY("\n- two natural numbers and a property to search for;"),
    NUMBERS_WITH_PROPERTIES("\n- two natural numbers and two properties to search for;"),
    EXIT("\n- enter 0 to exit.");

    private final String optionDescription;

    MenuOption(String optionDescription) {
        this.optionDescription = optionDescription;
    }

    public String getOptionDescription() {
        return this.optionDescription;
    }
}
