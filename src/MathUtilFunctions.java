import java.math.BigInteger;

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
            if (num1 > Integer.MAX_VALUE - num2) {      // checking whether num1 + num2 > Integer.MAX_VALUE
                return Integer.MAX_VALUE;
            }
        } else {
            if (num1 < Integer.MIN_VALUE - num2) {      // checking whether num1 + num2 < Integer.MIN_VALUE
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

    /**
     * Given two integers returns their product capped at Integer.MAX_VALUE and Integer.MIN_VALUE
     *
     * @param num1 to be added
     * @param num2 to be added
     * @return their product
     */
    public static int safeMultiply(int num1, int num2) {
        final BigInteger bigNum1 = BigInteger.valueOf(num1);
        final BigInteger bigNum2 = BigInteger.valueOf(num2);
        final BigInteger product = bigNum1.multiply(bigNum2);
        return getCappedIntegerValue(product.toString());
    }

    /**
     * Given a string representation of an integer returns its value capped at Integer.MAX_VALUE and Integer.MIN_VALUE
     *
     * @param intValue to be capped
     * @return capped value
     */
    public static int getCappedIntegerValue(final String intValue) {
        final BigInteger unCappedValue = new BigInteger(intValue);
        if (unCappedValue.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) > 0) {
            return Integer.MAX_VALUE;
        } else if (unCappedValue.compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) < 0) {
            return Integer.MIN_VALUE;
        } else {
            return Integer.parseInt(intValue);
        }
    }
}
