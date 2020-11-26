import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SRPNTest3 {

    private SRPN srpn;

    @BeforeEach
    void setUp() {
        srpn = new SRPN();
    }


    @Test
    void LargeInteger() {
        final Optional<String> response1 = srpn.processNewCommands("2147483648");
        final Optional<String> response2 = srpn.processNewCommands("1");
        final Optional<String> response3 = srpn.processNewCommands("+");
        final Optional<String> response4 = srpn.processNewCommands("=");

        assertEquals(Optional.empty(), response1);
        assertEquals(Optional.empty(), response2);
        assertEquals(Optional.empty(), response3);
        assertEquals(Optional.of("2147483647\n"), response4);
    }


    @Test
    void Test1() {
        final Optional<String> response1 = srpn.processNewCommands("2147483647");
        final Optional<String> response2 = srpn.processNewCommands("1");
        final Optional<String> response3 = srpn.processNewCommands("+");
        final Optional<String> response4 = srpn.processNewCommands("=");

        assertEquals(Optional.empty(), response1);
        assertEquals(Optional.empty(), response2);
        assertEquals(Optional.empty(), response3);
        assertEquals(Optional.of("2147483647\n"), response4);
    }

    @Test
    void Test2() {
        final Optional<String> response1 = srpn.processNewCommands("-2147483647");
        final Optional<String> response2 = srpn.processNewCommands("1");
        final Optional<String> response3 = srpn.processNewCommands("-");
        final Optional<String> response4 = srpn.processNewCommands("=");
        final Optional<String> response5 = srpn.processNewCommands("20");
        final Optional<String> response6 = srpn.processNewCommands("-");
        final Optional<String> response7 = srpn.processNewCommands("=");

        assertEquals(Optional.empty(), response1);
        assertEquals(Optional.empty(), response2);
        assertEquals(Optional.empty(), response3);
        assertEquals(Optional.of("-2147483648\n"), response4);
        assertEquals(Optional.empty(), response5);
        assertEquals(Optional.empty(), response6);
        assertEquals(Optional.of("-2147483648\n"), response7);
    }

    @Test
    void Test3() {
        final Optional<String> response1 = srpn.processNewCommands("100000");
        final Optional<String> response2 = srpn.processNewCommands("0");
        final Optional<String> response3 = srpn.processNewCommands("-");
        final Optional<String> d1 = srpn.processNewCommands("d");
        final Optional<String> response5 = srpn.processNewCommands("*");
        final Optional<String> response6 = srpn.processNewCommands("=");

        assertEquals(Optional.empty(), response1);
        assertEquals(Optional.empty(), response2);
        assertEquals(Optional.empty(), response3);
        assertEquals(Optional.of("100000\n"), d1);
        assertEquals(Optional.of("Stack underflow.\n"), response5);
        assertEquals(Optional.of("100000\n"), response6);
    }
}