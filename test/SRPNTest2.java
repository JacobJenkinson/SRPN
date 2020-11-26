import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SRPNTest2 {

    private SRPN srpn;

    @BeforeEach
    void setUp() {
        srpn = new SRPN();
    }


    @Test
    void Test1() {
        final Optional<String> response1 = srpn.processNewCommands("3");
        final Optional<String> response2 = srpn.processNewCommands("3");
        final Optional<String> response3 = srpn.processNewCommands("*");
        final Optional<String> response4 = srpn.processNewCommands("4");
        final Optional<String> response5 = srpn.processNewCommands("4");
        final Optional<String> response6 = srpn.processNewCommands("*");
        final Optional<String> response7 = srpn.processNewCommands("+");
        final Optional<String> response8 = srpn.processNewCommands("=");

        assertEquals(Optional.empty(), response1);
        assertEquals(Optional.empty(), response2);
        assertEquals(Optional.empty(), response3);
        assertEquals(Optional.empty(), response4);
        assertEquals(Optional.empty(), response5);
        assertEquals(Optional.empty(), response6);
        assertEquals(Optional.empty(), response7);
        assertEquals(Optional.of("25\n"), response8);
    }

    @Test
    void Test2() {
        final Optional<String> response1 = srpn.processNewCommands("1234");
        final Optional<String> response2 = srpn.processNewCommands("2345");
        final Optional<String> response3 = srpn.processNewCommands("3456");
        final Optional<String> d1 = srpn.processNewCommands("d");
        final Optional<String> response5 = srpn.processNewCommands("+");
        final Optional<String> d2 = srpn.processNewCommands("d");
        final Optional<String> response7 = srpn.processNewCommands("+");
        final Optional<String> d3 = srpn.processNewCommands("d");
        final Optional<String> response9 = srpn.processNewCommands("=");

        assertEquals(Optional.empty(), response1);
        assertEquals(Optional.empty(), response2);
        assertEquals(Optional.empty(), response3);
        assertEquals(Optional.of("1234\n2345\n3456\n"), d1);
        assertEquals(Optional.empty(), response5);
        assertEquals(Optional.of("1234\n5801\n"), d2);
        assertEquals(Optional.empty(), response7);
        assertEquals(Optional.of("7035\n"), d3);
        assertEquals(Optional.of("7035\n"), response9);
    }
}