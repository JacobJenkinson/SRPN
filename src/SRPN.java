import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

/**
 * Program class for an SRPN calculator.
 */
public class SRPN {
    private final int MAX_STACK_SIZE = 23;    // SRPN can only store 22 integers in memory
    private final int MAX_RANDOM_NUMBERS = 22;      // SRPN only has 22 random numbers before looping back

    private List<Integer> randomNumbers;
    private int randomNumberIndex;

    private SizedArrayList<String> stack;

    public SRPN() {
        this.stack = new SizedArrayList<>(MAX_STACK_SIZE);
        initialiseRandomNumberGeneration();
    }

    // Due to the fact the SRPN uses only a limited subset of the random number generation a restricted list is
    // created upon initialisation rather than using the RandomNumberGenerator
    private void initialiseRandomNumberGeneration() {
        RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();
        final List<Integer> randomNumbers = new ArrayList<>();
        for (int i = 0; i < MAX_RANDOM_NUMBERS; i++) {
            randomNumbers.add(randomNumberGenerator.nextRandomNumber());
        }
        this.randomNumberIndex = 0;
        this.randomNumbers = randomNumbers;
    }

    public void processCommand(final String commandString) {
        final List<String> response = processNewCommands(commandString);
        for (String value : response) {
            System.out.println(value);
        }
    }

    /**
     * @param commandString
     */
    public List<String> processNewCommands(final String commandString) {
        final CommandStringParser commandStringParser = new CommandStringParser();
        final List<String> commands = commandStringParser.parseCommand(commandString);

        final List<String> responses = new ArrayList<>();
        for (String command : commands) {
            responses.addAll(handleExceptions(command));

        }
        return responses;
    }

    /**
     * @param commandString
     * @return
     */
    public List<String> handleExceptions(final String commandString) {
        try {
            return handleCommandString(commandString);
        } catch (IndexOutOfBoundsException ex) {
            return List.of("Stack empty.");
        } catch (EmptyStackException ex) {
            stack.remove(stack.size() - 1); // remove the last operator on the stack as invalid
            return List.of("Stack underflow.");
        } catch (ArithmeticException ex) {
            return List.of("Divide by 0.");
        } catch (StackOverflowError ex) {
            return List.of("Stack overflow.");
        }
    }

    private List<String> handleCommandString(final String commandString) {
        switch (commandString) {
            case "=":
                return List.of(stack.get(stack.size() - 1));
            case "d":
                return stack;
            case "r":
                stack.add(randomNumbers.get(randomNumberIndex).toString());
                randomNumberIndex = (randomNumberIndex + 1) % MAX_RANDOM_NUMBERS;
                break;
            default:
                stack.add(commandString);
                evaluateStack();
        }
        return Collections.emptyList();
    }

    private void evaluateStack() {
        final Stack<Integer> intStack = new Stack<>();
        for (String stackValue : stack) {
            switch (stackValue.charAt(stackValue.length() - 1)) {
                case '*':
                    intStack.push(intStack.pop() * intStack.pop());
                    break;
                case '^':
                    intStack.push(intStack.pop() ^ intStack.pop());
                    break;
                case '+':
                    intStack.push(safeAdd(intStack.pop(), intStack.pop()));
                    break;
                case '-':
                    intStack.push(safeAdd(safeNegate(intStack.pop()), intStack.pop()));
                    break;
                case '/':
                    checkDivision(intStack.peek());
                    int denominator = intStack.pop(), numerator = intStack.pop();
                    intStack.push(numerator / denominator);
                    break;
                case '%':
                    checkModulus(intStack.peek());
                    int divisor = intStack.pop(), dividend = intStack.pop();
                    intStack.push(dividend % divisor);
                    break;
                default:
                    intStack.push(getCappedIntegerValue(stackValue));
                    break;
            }
        }
        stack = SizedArrayList.from(intStack, MAX_STACK_SIZE);
    }

    static int safeAdd(int num1, int num2) {
        if (num2 > 0) {
            if (num1 > Integer.MAX_VALUE - num2) {
                return Integer.MAX_VALUE;
            }
        } else {
            if (num1 < Integer.MIN_VALUE - num2) {
                return Integer.MIN_VALUE;
            }
        }
        return num1 + num2;
    }

    static int safeNegate(int a) {
        if (a == Integer.MIN_VALUE) {
            return Integer.MAX_VALUE;
        }
        return -a;
    }

    static void checkDivision(final Integer denominator) {
        if (denominator == 0) {
            throw new ArithmeticException("Divided by zero");
        }
    }

    static void checkModulus(final Integer divisor) {
        if (divisor == 0) {
            throw new FloatingPointException("exit status 136");
        }
    }

    private int getCappedIntegerValue(final String stackValue) {
        final BigInteger unCappedValue = new BigInteger(stackValue);
        if (unCappedValue.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) > 0) {
            return Integer.MAX_VALUE;
        } else if (unCappedValue.compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) < 0) {
            return Integer.MIN_VALUE;
        } else {
            return Integer.parseInt(stackValue);
        }
    }
}
