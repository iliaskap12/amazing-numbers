package numbers;

import java.util.ArrayList;
import java.util.Arrays;

public enum ErrorCode {
    EMPTY_REQUEST((new Menu(MenuOption.values())).toString()),
    INVALID_FIRST_PARAMETER("\nThe first parameter should be a natural number or zero."),
    INVALID_SECOND_PARAMETER("\nThe second parameter should be a natural number."),
    INVALID_PROPERTY("\nThe property "),
    MULTIPLE_INVALID_PROPERTIES("\nThe properties "),
    MUTUAL_EXCLUSIVE_PROPERTIES("\nThe request contains mutually exclusive properties: ");

    private final String errorMessageInitializer;
    private String errorMessage = "";

    ErrorCode(String message) {
        this.errorMessageInitializer = message;
    }

    public String getErrorMessage() {
        if (this.errorMessage.isEmpty()) {
            this.errorMessage = this.errorMessageInitializer;
        }
        return this.errorMessage;
    }

    public ErrorCode setInvalidity(String invalidProperty) {
        this.errorMessage = String.format(
                "%s[%s] is wrong.%nAvailable properties: %s",
                this.errorMessageInitializer,
                invalidProperty,
                Arrays.toString(NumberProperty.values())
        );
        return this;
    }

    public ErrorCode setMultipleInvalidity(ArrayList<String> invalidProperties) {
        this.errorMessage = String.format(
                "%s%s are wrong.\nAvailable properties: %s",
                this.errorMessageInitializer,
                invalidProperties.toString(),
                Arrays.toString(NumberProperty.values())
        );
        return this;
    }

    public ErrorCode setMutualExclusivity(NumberProperty[] properties, boolean excluded) {
        final String sign = excluded ? "-" : "";
        this.errorMessage = String.format(
                "%s[%s%s, %s%s]%nThere are no numbers with these properties.",
                this.errorMessageInitializer,
                sign,
                properties[0],
                sign,
                properties[1]
        );
        return this;
    }

    public ErrorCode setMutualExclusivity(NumberProperty property) {
        this.errorMessage = String.format(
                "%s[%s, -%s]%nThere are no numbers with these properties.",
                this.errorMessageInitializer,
                property,
                property
        );
        return this;
    }
}
