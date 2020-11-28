import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class SRPNTest1 {

    private SRPN srpn;

    @BeforeEach
    void setUp() {
        srpn = new SRPN();
    }

    @Test
    void EmptyStack() {
        final List<String> response = srpn.processNewCommands("=");
        assertThat(response, is(List.of("Stack empty.")));
    }

    @Test
    void NotEnoughOperands() {
        final List<String> response = srpn.processNewCommands("1");
        final List<String> response1 = srpn.processNewCommands("+");
        final List<String> response2 = srpn.processNewCommands("d");

        assertThat(response1, is(List.of("Stack underflow.")));
        assertThat(response2, is(List.of("1")));
    }

    @Test
    void SimpleCase() {
        final List<String> response1 = srpn.processNewCommands("10");
        final List<String> response2 = srpn.processNewCommands("=");

        assertThat(response1, is(Collections.emptyList()));
        assertThat(response2, is(List.of("10")));
    }

    @Test
    void RepeatedSimpleCase() {
        final List<String> response1 = srpn.processNewCommands("10");
        final List<String> response2 = srpn.processNewCommands("5");
        final List<String> response3 = srpn.processNewCommands("=");

        assertThat(response1, is(Collections.emptyList()));
        assertThat(response2, is(Collections.emptyList()));
        assertThat(response3, is(List.of("5")));
    }

    @Test
    void Test1Example1() {
        final List<String> response1 = srpn.processNewCommands("10");
        final List<String> response2 = srpn.processNewCommands("2");
        final List<String> response3 = srpn.processNewCommands("+");
        final List<String> response4 = srpn.processNewCommands("=");

        assertThat(response1, is(Collections.emptyList()));
        assertThat(response2, is(Collections.emptyList()));
        assertThat(response3, is(Collections.emptyList()));
        assertThat(response4, is(List.of("12")));
    }

    @Test
    void Test1Example2() {
        final List<String> response1 = srpn.processNewCommands("11");
        final List<String> response2 = srpn.processNewCommands("3");
        final List<String> response3 = srpn.processNewCommands("-");
        final List<String> response4 = srpn.processNewCommands("=");

        assertThat(response1, is(Collections.emptyList()));
        assertThat(response2, is(Collections.emptyList()));
        assertThat(response3, is(Collections.emptyList()));
        assertThat(response4, is(List.of("8")));
    }

    @Test
    void Test1Example3() {
        final List<String> response1 = srpn.processNewCommands("9");
        final List<String> response2 = srpn.processNewCommands("4");
        final List<String> response3 = srpn.processNewCommands("*");
        final List<String> response4 = srpn.processNewCommands("=");

        assertThat(response1, is(Collections.emptyList()));
        assertThat(response2, is(Collections.emptyList()));
        assertThat(response3, is(Collections.emptyList()));
        assertThat(response4, is(List.of("36")));
    }

    @Test
    void Test1Example4() {
        final List<String> response1 = srpn.processNewCommands("11");
        final List<String> response2 = srpn.processNewCommands("3");
        final List<String> response3 = srpn.processNewCommands("/");
        final List<String> response4 = srpn.processNewCommands("=");

        assertThat(response1, is(Collections.emptyList()));
        assertThat(response2, is(Collections.emptyList()));
        assertThat(response3, is(Collections.emptyList()));
        assertThat(response4, is(List.of("3")));
    }

    @Test
    void Test1Example5() {
        final List<String> response1 = srpn.processNewCommands("11");
        final List<String> response2 = srpn.processNewCommands("3");
        final List<String> response3 = srpn.processNewCommands("%");
        final List<String> response4 = srpn.processNewCommands("=");

        assertThat(response1, is(Collections.emptyList()));
        assertThat(response2, is(Collections.emptyList()));
        assertThat(response3, is(Collections.emptyList()));
        assertThat(response4, is(List.of("2")));
    }
}