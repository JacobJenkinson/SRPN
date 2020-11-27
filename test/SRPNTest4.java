import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

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
    void singleLineR() {
        final Optional<String> response1 = srpn.processNewCommands("r");
        final Optional<String> response2 = srpn.processNewCommands("r");
        final Optional<String> response3 = srpn.processNewCommands("r");
        final Optional<String> d1 = srpn.processNewCommands("d");

        assertEquals(Optional.of("1804289383\n846930886\n1681692777\n"), d1);
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