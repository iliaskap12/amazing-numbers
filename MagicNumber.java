package numbers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BooleanSupplier;

public class MagicNumber {
    private final HashMap<NumberProperty, BooleanSupplier> propertyToOperation = new HashMap<>();
    char[] numberToCharacters;
    private long number;

    public MagicNumber(long number) throws IllegalArgumentException {
        this.number = number;
        evaluateFirstParameter();
        this.numberToCharacters = String.valueOf(this.number).toCharArray();
        populatePropertiesMap();
    }

    private void evaluateFirstParameter() throws IllegalArgumentException {
        if (this.number < 0) {
            throw new IllegalArgumentException(ErrorCode.INVALID_FIRST_PARAMETER.getErrorMessage());
        }
    }

    protected long getNumber() {
        return this.number;
    }

    protected void setNumber(long number) {
        this.number = number;
        this.numberToCharacters = String.valueOf(this.number).toCharArray();
    }

    protected boolean isSpy() {
        long product = 1;
        long sum = 0;

        for (char character : this.numberToCharacters) {
            product *= Long.parseLong(String.valueOf(character));
            sum += Long.parseLong(String.valueOf(character));
        }

        return product == sum;
    }

    protected boolean isDuck() {
        for (char character : this.numberToCharacters) {
            if (character == '0') {
                return true;
            }
        }
        return false;
    }

    protected boolean isBuzz() {
        return this.number % 7 == 0 || this.number % 10 == 7;
    }

    protected boolean isEven() {
        return this.number % 2 == 0;
    }

    protected boolean isPalindrome() {
        for (int i = 0; i < this.numberToCharacters.length / 2; ++i) {
            if (this.numberToCharacters[i]
                    != this.numberToCharacters[this.numberToCharacters.length - 1 - i]) {
                return false;
            }
        }
        return true;
    }

    protected boolean isGapful() {
        final int digitConcatenation = Integer.parseInt(this.numberToCharacters[0] +
                String.valueOf(this.numberToCharacters[this.numberToCharacters.length - 1]));
        return this.number > 99 && this.number % digitConcatenation == 0;
    }

    protected boolean isSunny() {
        return isSquare(this.number + 1);
    }

    protected boolean isSquare() {
        return isSquare(this.number);
    }

    protected boolean isJumping() {
        boolean isJumping = true;
        for (int i = 1; i < this.numberToCharacters.length; ++i) {
            final int currentDigit = Character.getNumericValue(this.numberToCharacters[i]);
            final int previousDigit = Character.getNumericValue(this.numberToCharacters[i - 1]);
            final int absoluteDifference = Math.abs(currentDigit - previousDigit);
            if (absoluteDifference != 1) {
                isJumping = false;
            }
        }
        return isJumping;
    }

    protected boolean isHappy() {
        long sum = 0;
        char[] sumDigits = this.numberToCharacters;
        final ArrayList<Long> sums = new ArrayList<>();

        while (sum != 1 && !sums.contains(sum)) {
            sums.add(sum);
            sum = 0;
            for (char digit : sumDigits) {
                sum += (long) Math.pow(Long.parseLong(String.valueOf(digit)), 2);
            }
            sumDigits = String.valueOf(sum).toCharArray();
        }

        return sum == 1;
    }

    protected String toHumanReadableNumber() {
        if (this.number > 999) {
            final StringBuilder stringNumber = new StringBuilder();
            final int startIndex = this.numberToCharacters.length % 3;

            for (int i = 0; i < this.numberToCharacters.length; ++i) {
                if ((i - startIndex) % 3 == 0 && i != 0) {
                    stringNumber.append(",");
                }
                stringNumber.append(numberToCharacters[i]);
            }

            return String.valueOf(stringNumber);
        }

        return String.valueOf(this.number);
    }

    private boolean isSquare(long number) {
        final long intSquareRootOfNumber = (long) Math.sqrt(number);
        return Math.pow(intSquareRootOfNumber, 2) == number;
    }

    private void populatePropertiesMap() {
        this.propertyToOperation.put(NumberProperty.BUZZ, this::isBuzz);
        this.propertyToOperation.put(NumberProperty.DUCK, this::isDuck);
        this.propertyToOperation.put(NumberProperty.PALINDROMIC, this::isPalindrome);
        this.propertyToOperation.put(NumberProperty.GAPFUL, this::isGapful);
        this.propertyToOperation.put(NumberProperty.SPY, this::isSpy);
        this.propertyToOperation.put(NumberProperty.EVEN, this::isEven);
        this.propertyToOperation.put(NumberProperty.ODD, () -> !this.isEven());
        this.propertyToOperation.put(NumberProperty.SUNNY, this::isSunny);
        this.propertyToOperation.put(NumberProperty.SQUARE, this::isSquare);
        this.propertyToOperation.put(NumberProperty.JUMPING, this::isJumping);
        this.propertyToOperation.put(NumberProperty.HAPPY, this::isHappy);
        this.propertyToOperation.put(NumberProperty.SAD, () -> !this.isHappy());
    }

    protected HashMap<NumberProperty, BooleanSupplier> getPropertyToOperation() {
        return this.propertyToOperation;
    }

    @Override
    public String toString() {
        final String separator = ": ";
        final String lineFeed = "\n\t\t";
        final StringBuilder stringRepresentation = new StringBuilder("\nProperties of ")
                .append(toHumanReadableNumber());
        for (NumberProperty numberProperty : NumberProperty.values()) {
            stringRepresentation
                    .append(lineFeed)
                    .append(numberProperty.name().toLowerCase())
                    .append(separator)
                    .append(this.propertyToOperation.get(numberProperty).getAsBoolean());
        }
        return stringRepresentation.toString();
    }
}