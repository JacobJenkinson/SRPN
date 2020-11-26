import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SRPNTest1 {

    private SRPN srpn;

    @BeforeEach
    void setUp() {
        srpn = new SRPN();
    }

    @Test
    void EmptyStack() {
        final Optional<String> response = srpn.processNewCommands("=");
        assertEquals(Optional.of("Stack empty.\n"), response);
    }

    @Test
    void NotEnoughOperands() {
        final Optional<String> response = srpn.processNewCommands("1");
        final Optional<String> response1 = srpn.processNewCommands("+");
        final Optional<String> response2 = srpn.processNewCommands("d");
        assertEquals(Optional.of("Stack underflow.\n"), response1);
        assertEquals(Optional.of("1\n"), response2);
    }

    @Test
    void SimpleCase() {
        final Optional<String> response1 = srpn.processNewCommands("10");
        final Optional<String> response2 = srpn.processNewCommands("=");

        assertEquals(Optional.empty(), response1);
        assertEquals(Optional.of("10\n"), response2);
    }

    @Test
    void RepeatedSimpleCase() {
        final Optional<String> response1 = srpn.processNewCommands("10");
        final Optional<String> response2 = srpn.processNewCommands("5");
        final Optional<String> response3 = srpn.processNewCommands("=");

        assertEquals(Optional.empty(), response1);
        assertEquals(Optional.empty(), response2);
        assertEquals(Optional.of("5\n"), response3);
    }

    @Test
    void WeirdExample() {
        final Optional<String> response1 = srpn.processNewCommands("1");
        final Optional<String> response2 = srpn.processNewCommands("2");
        final Optional<String> response3 = srpn.processNewCommands("3");
        final Optional<String> response4 = srpn.processNewCommands("/");
        final Optional<String> response5 = srpn.processNewCommands("-");
        final Optional<String> response6 = srpn.processNewCommands("=");

        assertEquals(Optional.empty(), response1);
        assertEquals(Optional.empty(), response2);
        assertEquals(Optional.empty(), response3);
        assertEquals(Optional.empty(), response4);
        assertEquals(Optional.empty(), response5);
        assertEquals(Optional.of("0\n"), response6);
    }

    @Test
    void ZeroModulus() {
        final Optional<String> response1 = srpn.processNewCommands("1");
        final Optional<String> response2 = srpn.processNewCommands("0");
        final Optional<String> response3 = srpn.processNewCommands("%");

        assertEquals(Optional.empty(), response1);
        assertEquals(Optional.empty(), response2);
        assertEquals(Optional.of("0\n"), response3);
    }

    @Test
    void Test1Example1() {
        final Optional<String> response1 = srpn.processNewCommands("10");
        final Optional<String> response2 = srpn.processNewCommands("2");
        final Optional<String> response3 = srpn.processNewCommands("+");
        final Optional<String> response4 = srpn.processNewCommands("=");

        assertEquals(response1, Optional.empty());
        assertEquals(response2, Optional.empty());
        assertEquals(response3, Optional.empty());
        assertEquals(response4, Optional.of("12\n"));
    }

    @Test
    void Test1Example2() {
        final Optional<String> response1 = srpn.processNewCommands("11");
        final Optional<String> response2 = srpn.processNewCommands("3");
        final Optional<String> response3 = srpn.processNewCommands("-");
        final Optional<String> response4 = srpn.processNewCommands("=");

        assertEquals(response1, Optional.empty());
        assertEquals(response2, Optional.empty());
        assertEquals(response3, Optional.empty());
        assertEquals(response4, Optional.of("8\n"));
    }

    @Test
    void Test1Example3() {
        final Optional<String> response1 = srpn.processNewCommands("9");
        final Optional<String> response2 = srpn.processNewCommands("4");
        final Optional<String> response3 = srpn.processNewCommands("*");
        final Optional<String> response4 = srpn.processNewCommands("=");

        assertEquals(response1, Optional.empty());
        assertEquals(response2, Optional.empty());
        assertEquals(response3, Optional.empty());
        assertEquals(response4, Optional.of("36\n"));
    }

    @Test
    void Test1Example4() {
        final Optional<String> response1 = srpn.processNewCommands("11");
        final Optional<String> response2 = srpn.processNewCommands("3");
        final Optional<String> response3 = srpn.processNewCommands("/");
        final Optional<String> response4 = srpn.processNewCommands("=");

        assertEquals(response1, Optional.empty());
        assertEquals(response2, Optional.empty());
        assertEquals(response3, Optional.empty());
        assertEquals(response4, Optional.of("3\n"));
    }

    @Test
    void Test1Example5() {
        final Optional<String> response1 = srpn.processNewCommands("11");
        final Optional<String> response2 = srpn.processNewCommands("3");
        final Optional<String> response3 = srpn.processNewCommands("%");
        final Optional<String> response4 = srpn.processNewCommands("=");

        assertEquals(response1, Optional.empty());
        assertEquals(response2, Optional.empty());
        assertEquals(response3, Optional.empty());
        assertEquals(response4, Optional.of("2\n"));
    }
}