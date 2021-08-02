package numbers;

import java.text.DecimalFormatSymbols;
import java.util.Arrays;

public class Menu {
    private final MenuOption[] menuOptions;
    private String[] requestParameters;

    public Menu(MenuOption[] menuOptions) {
        this.menuOptions = Arrays.copyOf(menuOptions, menuOptions.length);
    }

    public MenuOption getOption(String request) throws IllegalArgumentException {
        this.requestParameters = request.split("\\s+");
        handleIllegalMenuOption(request);

        switch (this.requestParameters.length) {
            case 1:
                if (Long.parseLong(this.requestParameters[0]) == 0) {
                    return MenuOption.EXIT;
                }

                return MenuOption.ONE_NUMBER;
            case 2:
                return MenuOption.LIST_OF_NUMBERS;
            case 3:
                return MenuOption.NUMBERS_WITH_PROPERTY;
            default:
                return MenuOption.NUMBERS_WITH_PROPERTIES;
        }
    }

    private void handleIllegalMenuOption(String request) throws IllegalArgumentException {
        if (request.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.EMPTY_REQUEST.getErrorMessage());
        }

        if (isNotStringNumeric(this.requestParameters[0])) {
            throw new IllegalArgumentException(ErrorCode.INVALID_FIRST_PARAMETER.getErrorMessage());
        }

        if (this.requestParameters.length > 1 && isNotStringNumeric(this.requestParameters[1])) {
            throw new IllegalArgumentException(ErrorCode.INVALID_SECOND_PARAMETER.getErrorMessage());
        }
    }

    private boolean isNotStringNumeric(String str) {
        final DecimalFormatSymbols currentLocaleSymbols = DecimalFormatSymbols.getInstance();
        final char localeMinusSign = currentLocaleSymbols.getMinusSign();

        if (!Character.isDigit(str.charAt(0)) && str.charAt(0) != localeMinusSign) return true;

        boolean isDecimalSeparatorFound = false;
        final char localeDecimalSeparator = currentLocaleSymbols.getDecimalSeparator();

        for (char c : str.substring(1).toCharArray()) {
            if (!Character.isDigit(c)) {
                if (c == localeDecimalSeparator && !isDecimalSeparatorFound) {
                    isDecimalSeparatorFound = true;
                    continue;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        final StringBuilder menuStringRepresentation = new StringBuilder("\nSupported requests:");
        for (int i = 0; i < this.menuOptions.length - 1; ++i) {
            menuStringRepresentation.append(menuOptions[i].getOptionDescription());
        }
        menuStringRepresentation
                .append("\n- a property preceded by minus must not be present in numbers;")
                .append("\n- separate the parameters with one space;")
                .append(this.menuOptions[this.menuOptions.length - 1].getOptionDescription());
        return menuStringRepresentation.toString();
    }
}
