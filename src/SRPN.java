import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;

//Comment all assumptions
//Explain un-obvious commands like peeks

/**
 * Program class for an SRPN calculator. Current it outputs "0" for every "=" sign.
 */
public class SRPN {
    private List<String> stack;

    public SRPN() {
        this.stack = new ArrayList<>();
    }

    /**
     * @param commandString
     */
    public void processCommand(final String commandString) {
        final Optional<String> maybeResponse = processNewCommands(commandString);
        maybeResponse.ifPresent(System.out::print);
    }

    /**
     * @param commandString
     * @return
     */
    public Optional<String> processNewCommands(final String commandString) {
        try {
            if (commandString.equals("=")) {
                return getFormattedStack(true);
            }
            if (commandString.equals("d")) {
                return getFormattedStack(false);
            } else {
                stack.add(commandString);
                evaluateStack();
                return Optional.empty();
            }
        } catch (IndexOutOfBoundsException ex) {
            return Optional.of("Stack empty.\n");
        } catch (EmptyStackException ex) {
            stack.remove(stack.size() - 1); // remove the last operator on the stack as invalid
            return Optional.of("Stack underflow.\n");
        } catch (ArithmeticException ex) {
            return Optional.of("Divide by 0.\n");
        }
    }

    private Optional<String> getFormattedStack(final boolean onlyLastItem) {
        final int fromIndex = onlyLastItem ? stack.size() - 1 : 0;
        return Optional.of(stack.subList(fromIndex, stack.size()).stream().map(stackValue -> stackValue + "\n").collect(Collectors.joining()));
    }

    private void evaluateStack() {
        Stack<Integer> intStack = calculateStackValue();
        stack = Arrays.stream(intStack.toArray())
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    private Stack<Integer> calculateStackValue() {
        final Stack<Integer> intStack = new Stack<>();
        for (String stackValue : stack) {
            switch (stackValue.charAt(stackValue.length() - 1)) {
                case '*':
                    intStack.push(intStack.pop() * intStack.pop());
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
                    int divisor = intStack.pop(), dividend = intStack.pop();
                    intStack.push(dividend % divisor);
                    break;
                default:
                    intStack.push(getCappedIntegerValue(stackValue));
                    break;
            }
        }
        return intStack;
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

    static void checkDivision(final Integer peek) {
        if (peek == 0) {
            throw new ArithmeticException("Divided by zero");
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
