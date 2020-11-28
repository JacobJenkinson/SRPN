import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AdditionalTests {

    private SRPN srpn;

    @BeforeEach
    void setUp() {
        srpn = new SRPN();
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
    void ASingleD() {
        final Optional<String> response1 = srpn.processNewCommands("d");

        assertEquals(Optional.of("-2147483648"), response1);
    }

    @Test
    void ZeroModulus() {
        final Optional<String> response1 = srpn.processNewCommands("1");
        final Optional<String> response2 = srpn.processNewCommands("0");

        assertEquals(Optional.empty(), response1);
        assertEquals(Optional.empty(), response2);
        final RuntimeException error = assertThrows(RuntimeException.class,
                () -> srpn.processNewCommands("%"));
        assertEquals(error.getMessage(), "34 Floating point exception(core dumped) ./srpn/srpn");
    }
}