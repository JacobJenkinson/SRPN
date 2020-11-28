import java.util.ArrayList;
import java.util.Stack;

public class SizedArrayList<E> extends ArrayList<E> {
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

    public boolean add(E object) {
        final boolean b = this.size() == maxSize;
        if (b) {
            throw new StackOverflowError("Unable to store value in Array");
        } else {
            return super.add(object);
        }
    }
}
