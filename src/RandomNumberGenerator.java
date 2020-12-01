import java.util.ArrayList;
import java.util.List;

/**
 * An instance of this class is used to generate a stream of pseudorandom numbers. It follows the GNU C library's
 * random() function using a linear additive feedback method.
 * The algorithm for the class is taken from https://www.mathstat.dal.ca/~selinger/random/
 */
public class RandomNumberGenerator {
    public List<Integer> randomNumberArray;

    public RandomNumberGenerator() {
        // use the default seed of 1
        this.randomNumberArray = generateRandomNumberArray(1);
    }

    // different seeds can be used for different random Number generation
    private List<Integer> generateRandomNumberArray(final int seed) {
        List<Integer> r = new ArrayList<>();
        r.add(seed);
        // the algorithm below follows that referenced in the source to generate an initial collection of integers
        for (int i = 1; i < 31; i++) {
            final int rValue = (int) ((16807L * r.get(i - 1)) % 2147483647);
            r.add(rValue < 0 ? rValue + 2147483647 : rValue);   // ensure value is in range 0 - 2147483647
        }
        for (int i = 31; i < 34; i++) {
            r.add(r.get(i - 31));
        }
        for (int i = 34; i <= 344; i++) {
            r.add(r.get(i - 31) + r.get(i - 3));
        }
        return r;
    }

    /**
     * Generates the next pseudorandom number
     *
     * @return pseudorandom number
     */
    public Integer nextRandomNumber() {
        final int index = randomNumberArray.size();
        randomNumberArray.add(randomNumberArray.get(index - 31) + randomNumberArray.get(index - 3));
        // here >>> is the unsigned right bit-shift operator, its needed as shown in the source
        return randomNumberArray.get(index - 1) >>> 1;
    }
}
