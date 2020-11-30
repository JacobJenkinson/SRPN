/**
 * Class to house all the utility methods to support saturation
 */
public class MathUtilFunctions {

    /**
     * Given two integers returns their sum capped at Integer.MAX_VALUE and Integer.MIN_VALUE
     *
     * @param num1 to be added
     * @param num2 to be added
     * @return their sum
     */
    public static int safeAdd(int num1, int num2) {
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

    /**
     * Returns negated value capped at Integer.MAX_VALUE and Integer.MIN_VALUE
     *
     * @param a to be negated
     * @return negated value
     */
    public static int safeNegate(int a) {
        if (a == Integer.MIN_VALUE) {
            return Integer.MAX_VALUE;
        }
        return -a;
    }
}
