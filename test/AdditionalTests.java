import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
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
        final List<String> response1 = srpn.processNewCommands("1");
        final List<String> response2 = srpn.processNewCommands("2");
        final List<String> response3 = srpn.processNewCommands("3");
        final List<String> response4 = srpn.processNewCommands("/");
        final List<String> response5 = srpn.processNewCommands("-");
        final List<String> response6 = srpn.processNewCommands("=");

        assertThat(response1, is(Collections.emptyList()));
        assertThat(response2, is(Collections.emptyList()));
        assertThat(response3, is(Collections.emptyList()));
        assertThat(response4, is(Collections.emptyList()));
        assertThat(response5, is(Collections.emptyList()));
        assertThat(response6, is(List.of("0")));
    }

    @Test
    void ASingleD() {
        final List<String> response1 = srpn.processNewCommands("d");

        assertThat(response1, is(List.of("-2147483648")));
    }

    @Test
    void ZeroModulus() {
        final List<String> response1 = srpn.processNewCommands("1");
        final List<String> response2 = srpn.processNewCommands("0");

        assertThat(response1, is(Collections.emptyList()));
        assertThat(response2, is(Collections.emptyList()));
        final RuntimeException error = assertThrows(RuntimeException.class,
                () -> srpn.processNewCommands("%"));
        assertEquals(error.getMessage(), "exit status 136");
    }
}