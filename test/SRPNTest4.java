import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SRPNTest4 {

    private SRPN srpn;
    private int currentR;

    @BeforeEach
    void setUp() {
        srpn = new SRPN();
    }


    @Test
    void Test1() {
        final Optional<String> response1 = srpn.processNewCommands("1");
        final Optional<String> response2 = srpn.processNewCommands("+");

        assertEquals(Optional.empty(), response1);
        assertEquals(Optional.of("Stack underflow.\n"), response2);
    }


    @Test
    void Test2() {
        final Optional<String> response1 = srpn.processNewCommands("10");
        final Optional<String> response2 = srpn.processNewCommands("5");
        final Optional<String> response3 = srpn.processNewCommands("-5");
        final Optional<String> response4 = srpn.processNewCommands("+");
        final Optional<String> response5 = srpn.processNewCommands("/");

        assertEquals(Optional.empty(), response1);
        assertEquals(Optional.empty(), response2);
        assertEquals(Optional.empty(), response3);
        assertEquals(Optional.empty(), response4);
        assertEquals(Optional.of("Divide by 0.\n"), response5);
    }

    @Test
    void Test3() {
        final Optional<String> response1 = srpn.processNewCommands("11+1+1+d");

        assertEquals(Optional.of("Stack underflow.\n13\n"), response1);
    }

    @Test
    void Test4() {
        final Optional<String> response1 = srpn.processNewCommands("# This i s a comment #");
        final Optional<String> response2 = srpn.processNewCommands("1 2 + # And so i s t h i s #");
        final Optional<String> d1 = srpn.processNewCommands("d");

        assertEquals(Optional.empty(), response1);
        assertEquals(Optional.empty(), response2);
        assertEquals(Optional.of("3\n"), d1);
    }

    @Test
    void Test5() {
        final Optional<String> response1 = srpn.processNewCommands("3 3 ^ 3 ^ 3 ^=");

        assertEquals(Optional.of("3"), response1);
    }

    @Test
    void randomNumberTest() {
        int r0 = 1804289383;
        currentR = r0;
        for (int i =0; i < 10; i++) {
            currentR = rand(currentR);
            System.out.println(currentR);
        }

        int[] rValues = new int[100];
        rValues[0] = 1804289383;
        for (int i=1; i<31; i++) {
            rValues[i] = (16807 * rValues[i-1]) % 2147483647;
            if (rValues[i] < 0) {
                rValues[i] += 2147483647;
            }
        }
        System.out.println(rValues);
//        for (int i=31; i<34; i++) {
//            rValues[i] = rValues[i-31];
//        }
//        for (int i=34; i<344; i++) {
//            rValues[i] = rValues[i-31] + rValues[i-3];
//        }
//        for (int i=344; i<1000; i++) {
//            rValues[i] = rValues[i-31] + rValues[i-3];
//            System.out.println("%d\n", (rValues[i]) >> 1);
//        }
    }

    public Integer rand(final Integer intValue) {
        final BigInteger i = BigInteger.valueOf(16807L).multiply(BigInteger.valueOf(intValue));
        final BigInteger m = BigInteger.valueOf(2147483647L);
        return (i.mod(m)).intValue();
    }

    @Test
    void Test6() {
        final Optional<String> response1 = srpn.processNewCommands("r r r r r r r r r r r r r r r r r r r r r r d r r r d");

        assertEquals(Optional.of("1804289383\n" +
                "846930886\n" +
                "1681692777\n" +
                "1714636915\n" +
                "1957747793\n" +
                "424238335\n" +
                "719885386\n" +
                "1649760492\n" +
                "596516649\n" +
                "1189641421\n" +
                "1025202362\n" +
                "1350490027\n" +
                "783368690\n" +
                "1102520059\n" +
                "2044897763\n" +
                "1967513926\n" +
                "1365180540\n" +
                "1540383426\n" +
                "304089172\n" +
                "1303455736\n" +
                "35005211\n" +
                "521595368\n" +
                "Stack overflow.\n" +
                "Stack overflow.\n" +
                "1804289383\n" +
                "846930886\n" +
                "1681692777\n" +
                "1714636915\n" +
                "1957747793\n" +
                "424238335\n" +
                "719885386\n" +
                "1649760492\n" +
                "596516649\n" +
                "1189641421\n" +
                "1025202362\n" +
                "1350490027\n" +
                "783368690\n" +
                "1102520059\n" +
                "2044897763\n" +
                "1967513926\n" +
                "1365180540\n" +
                "1540383426\n" +
                "304089172\n" +
                "1303455736\n" +
                "35005211\n" +
                "521595368\n" +
                "1804289383\n"), response1);
    }
}