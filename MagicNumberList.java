package numbers;

import java.util.ArrayList;

public class MagicNumberList extends MagicNumber {
    private final long sequenceLength;
    private final ArrayList<NumberProperty> inclusionProperties = new ArrayList<>();
    private final ArrayList<NumberProperty> exclusionProperties = new ArrayList<>();

    public MagicNumberList(long number, long sequenceLength, String... properties)
            throws IllegalArgumentException {
        super(number);
        this.sequenceLength = sequenceLength;
        evaluateSecondParameter();
        processProperties(properties);
    }

    private void processProperties(String[] properties)
            throws IllegalArgumentException, UnsupportedOperationException {
        final ArrayList<String> invalidProperties = new ArrayList<>();

        for (String property : properties) {
            try {
                if (property.startsWith("-")) {
                    this.exclusionProperties.add(NumberProperty.valueOf(property.substring(1).toUpperCase()));
                } else {
                    this.inclusionProperties.add(NumberProperty.valueOf(property.toUpperCase()));
                }
            } catch (IllegalArgumentException e) {
                invalidProperties.add(property.toUpperCase());
            }
        }

        handleInvalidity(invalidProperties);
        handleMutualExclusivity();
    }

    private void handleInvalidity(ArrayList<String> properties) throws IllegalArgumentException {
        if (properties.size() == 1) {
            final String errorMessage = ErrorCode.
                    INVALID_PROPERTY.
                    setInvalidity(properties.get(0))
                    .getErrorMessage();
            throw new IllegalArgumentException(errorMessage);
        }
        if (properties.size() > 1) {
            final String errorMessage = ErrorCode
                    .MULTIPLE_INVALID_PROPERTIES
                    .setMultipleInvalidity(properties)
                    .getErrorMessage();
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private void handleMutualExclusivity() throws UnsupportedOperationException {
        /*
        * ODD + EVEN
        * SUNNY + SQUARE
        * HAPPY + SAD
        * DUCK + SPY
        * -ODD + -EVEN
        * -HAPPY + -SAD
        * -DUCK + -SPY
        * PROPERTY + -PROPERTY
        * */

        final NumberProperty[][] mutualExclusivePairs =
                {
                        {NumberProperty.ODD, NumberProperty.EVEN},
                        {NumberProperty.SUNNY, NumberProperty.SQUARE},
                        {NumberProperty.DUCK, NumberProperty.SPY},
                        {NumberProperty.HAPPY, NumberProperty.SAD}
                };

        for (NumberProperty[] pair : mutualExclusivePairs) {
            if (this.inclusionProperties.contains(pair[0]) && this.inclusionProperties.contains(pair[1])) {
                final String errorMessage = ErrorCode
                        .MUTUAL_EXCLUSIVE_PROPERTIES
                        .setMutualExclusivity(pair, false)
                        .getErrorMessage();
                throw new UnsupportedOperationException(errorMessage);
            }
        }

        for (NumberProperty[] pair : mutualExclusivePairs) {
            if (pair[0] != NumberProperty.SUNNY && pair[1] != NumberProperty.SQUARE) {
                if (this.exclusionProperties.contains(pair[0]) && this.exclusionProperties.contains(pair[1])) {
                    final String errorMessage = ErrorCode
                            .MUTUAL_EXCLUSIVE_PROPERTIES
                            .setMutualExclusivity(pair, true)
                            .getErrorMessage();
                    throw new UnsupportedOperationException(errorMessage);
                }
            }
        }

        for (NumberProperty property : NumberProperty.values()) {
            if (this.inclusionProperties.contains(property) && this.exclusionProperties.contains(property)) {
                final String errorMessage = ErrorCode
                        .MUTUAL_EXCLUSIVE_PROPERTIES
                        .setMutualExclusivity(property)
                        .getErrorMessage();
                throw new UnsupportedOperationException(errorMessage);
            }
        }
    }

    private void evaluateSecondParameter() throws IllegalArgumentException {
        if (this.sequenceLength < 1) {
            throw new IllegalArgumentException(ErrorCode.INVALID_SECOND_PARAMETER.getErrorMessage());
        }
    }

    @Override
    public String toString() {
        final StringBuilder stringRepresentation = new StringBuilder();
        int i = 0;
        while (i < this.sequenceLength) {
            boolean shouldAppend = true;
            for (NumberProperty property : this.inclusionProperties) {
                if (!super.getPropertyToOperation().get(property).getAsBoolean()) {
                    shouldAppend = false;
                    setNumber(getNumber() + 1);
                }
            }
            for (NumberProperty property : this.exclusionProperties) {
                if (super.getPropertyToOperation().get(property).getAsBoolean()) {
                    shouldAppend = false;
                    setNumber(getNumber() + 1);
                }
            }
            if (shouldAppend) {
                stringRepresentation.append("\n").append(toHumanReadableNumber()).append(" is ");

                for (NumberProperty property : NumberProperty.values()) {
                    if (super.getPropertyToOperation().get(property).getAsBoolean()) {
                        stringRepresentation.append(property.name().toLowerCase()).append(", ");
                    }
                }
                stringRepresentation.delete(stringRepresentation.length() - 2, stringRepresentation.length());

                setNumber(getNumber() + 1);
                ++i;
            }
        }
        return stringRepresentation.toString();
    }
}
