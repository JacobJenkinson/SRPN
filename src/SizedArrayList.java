import java.util.ArrayList;
import java.util.Stack;

/**
 * Extension of ArrayList which throws exceptions on addition if more than the specified number of values are added
 * with the exception of non integers which behave the same as a standard ArrayList
 *
 */
public class SizedArrayList<T> extends ArrayList<T> {
    private final int maxSize;

    public SizedArrayList(int size) {
        super();
        this.maxSize = size;
    }

    public static SizedArrayList<String> from(Stack<Integer> stack, int size) {
        final SizedArrayList<String> arrayList = new SizedArrayList<>(size);
        for (Integer value : stack) {
            arrayList.add(value.toString());
        }
        return arrayList;
    }

    public boolean add(T object) {
        final boolean isOverSize = this.size() >= maxSize;
        if (isOverSize && !objectIsOperator(object)) {
            throw new StackOverflowError("Unable to store value in Array");
        } else {
            return super.add(object);
        }
    }

    // allow non integers to be added to the stack
    private boolean objectIsOperator(final T object) {
        try {
            Integer.valueOf(object.toString());
            return false;
        } catch (NumberFormatException ex) {
            return true;
        }
    }
}
