import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

/**
 * Program class for an SRPN calculator.
 */
public class SRPN {
    private final int MAX_STACK_SIZE = 23;          // SRPN can only store 22 integers in memory
    private final int MAX_RANDOM_NUMBERS = 22;      // SRPN only has 22 random numbers before looping back

    private List<String> randomNumbers;
    private int randomNumberIndex;

    private SizedArrayList<String> stack;

    public SRPN() {
        this.stack = new SizedArrayList<>(MAX_STACK_SIZE);
        initialiseRandomNumberGeneration();
        System.out.println("You can now start interacting with the SRPN calculator");
    }

    // Due to the fact the SRPN uses only a limited subset of the random number generation a restricted list is
    // created upon initialisation rather than using the RandomNumberGenerator
    private void initialiseRandomNumberGeneration() {
        RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();
        final List<String> randomNumbers = new ArrayList<>();
        for (int i = 0; i < MAX_RANDOM_NUMBERS; i++) {
            randomNumbers.add(randomNumberGenerator.nextRandomNumber().toString());
        }
        this.randomNumberIndex = 0;
        this.randomNumbers = randomNumbers;
    }

    /**
     * Delegates to processNewCommands function and prints out response to std out.
     *
     * @param commandString string to be processed
     */
    public void processCommand(final String commandString) {
        final List<String> response = processNewCommands(commandString);
        for (String value : response) {
            System.out.println(value);
        }
    }

    /**
     * Carries out all functionality of the SRPN by parsing the commandString and then carrying out the relevant
     * operations.
     *
     * @param commandString string to be processed
     */
    public List<String> processNewCommands(final String commandString) {
        final CommandStringParser commandStringParser = new CommandStringParser();
        final List<String> commands = commandStringParser.parseCommand(commandString);

        final List<String> responses = new ArrayList<>();
        for (String command : commands) {
            responses.addAll(handleCommandStringWithExceptionControl(command));

        }
        return responses;
    }

    // Delegates to handleCommandString but catches any expected exceptions and handles them gracefully
    private List<String> handleCommandStringWithExceptionControl(final String commandString) {
        try {
            return handleCommandString(commandString);
        } catch (IndexOutOfBoundsException ex) {
            return List.of("Stack empty.");
        } catch (EmptyStackException ex) {
            stack.remove(stack.size() - 1);     // remove the last operator on the stack as invalid
            return List.of("Stack underflow.");
        } catch (ArithmeticException ex) {
            return List.of(ex.getMessage());
        } catch (StackOverflowError ex) {
            return List.of("Stack overflow.");
        } catch (NumberFormatException ex) {
            return handleInvalidInput();
        }
    }

    // Create list of error messages for each invalid char in invalid input
    private List<String> handleInvalidInput() {
        final String invalidInput = stack.get(stack.size() - 1);
        final List<String> response = new ArrayList<>();
        for (char character : invalidInput.toCharArray()) {
            response.add("Unrecognised operator or operand \"" + character + "\".");
        }
        stack.remove(stack.size() - 1);     // remove the last value on the stack as invalid
        return response;
    }

    // Determines action based on input
    private List<String> handleCommandString(final String commandString) {
        switch (commandString) {
            case "=":
                return List.of(stack.get(stack.size() - 1));
            case "d":
                // ternary operator to support behaviour of d when stack is empty to return Integer.MIN_VALUE
                return stack.isEmpty() ? Collections.singletonList(String.valueOf(Integer.MIN_VALUE)) : stack;
            case "r":
                stack.add(randomNumbers.get(randomNumberIndex));
                // increase the index and modules the max number of random numbers to create looping effect of r
                randomNumberIndex = (randomNumberIndex + 1) % MAX_RANDOM_NUMBERS;
                break;
            default:
                stack.add(commandString);
                evaluateStack();
        }
        return Collections.emptyList();
    }

    // evaluates the current state of the stack
    private void evaluateStack() {
        final Stack<Integer> intStack = new Stack<>();
        for (String stackValue : stack) {
            switch (stackValue.charAt(stackValue.length() - 1)) {
                case '-':
                    intStack.push(MathUtilFunctions.safeAdd(MathUtilFunctions.safeNegate(intStack.pop()), intStack.pop()));
                    break;
                case '*':
                    intStack.push(MathUtilFunctions.safeMultiply(intStack.pop(), intStack.pop()));
                    break;
                case '^':
                    if (intStack.peek() < 0) {
                        throw new ArithmeticException("Negative power.");
                    }
                    int power = intStack.pop(), value = intStack.pop();
                    intStack.push((int) Math.pow(value, power));
                    break;
                case '+':
                    intStack.push(MathUtilFunctions.safeAdd(intStack.pop(), intStack.pop()));
                    break;
                case '/':
                    if (intStack.peek() == 0) {
                        throw new ArithmeticException("Divide by 0.");
                    }
                    int denominator = intStack.pop(), numerator = intStack.pop();
                    intStack.push(numerator / denominator);
                    break;
                case '%':
                    if (intStack.peek() == 0) {
                        throw new FloatingPointException("exit status 136");
                    }
                    int divisor = intStack.pop(), dividend = intStack.pop();
                    intStack.push(dividend % divisor);
                    break;
                default:
                    intStack.push(MathUtilFunctions.getCappedIntegerValue(stackValue));
                    break;
            }
        }
        stack = SizedArrayList.from(intStack, MAX_STACK_SIZE);
    }
}
