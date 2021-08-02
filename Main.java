package numbers;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Amazing Numbers!");

        MenuOption menuOption = null;
        final Menu menu = new Menu(MenuOption.values());
        System.out.println(menu);

        do {
            System.out.print("\nEnter a request: ");

            try {
                final String request = scanner.nextLine().trim();
                menuOption = menu.getOption(request);
                final String[] requests = request.split("\\s+");
                final long number = Long.parseLong(requests[0]);
                final long sequenceLength;
                final MagicNumber magicNumber;

                switch (menuOption) {
                    case ONE_NUMBER:
                        magicNumber = new MagicNumber(number);

                        System.out.println(magicNumber);
                        break;
                    case LIST_OF_NUMBERS:
                        sequenceLength = Long.parseLong(requests[1]);
                        magicNumber = new MagicNumberList(number, sequenceLength);

                        System.out.println(magicNumber);
                        break;
                    case NUMBERS_WITH_PROPERTY:
                    case NUMBERS_WITH_PROPERTIES:
                        sequenceLength = Long.parseLong(requests[1]);
                        final String[] specifiedProperties = Arrays.copyOfRange(requests, 2, requests.length);
                        magicNumber = new MagicNumberList(number, sequenceLength, specifiedProperties);

                        System.out.println(magicNumber);
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (menuOption != MenuOption.EXIT);
        System.out.println("\nGoodbye!");
    }
}