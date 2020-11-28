import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class SRPNTest2 {

    private SRPN srpn;

    @BeforeEach
    void setUp() {
        srpn = new SRPN();
    }


    @Test
    void Test1() {
        final List<String> response1 = srpn.processNewCommands("3");
        final List<String> response2 = srpn.processNewCommands("3");
        final List<String> response3 = srpn.processNewCommands("*");
        final List<String> response4 = srpn.processNewCommands("4");
        final List<String> response5 = srpn.processNewCommands("4");
        final List<String> response6 = srpn.processNewCommands("*");
        final List<String> response7 = srpn.processNewCommands("+");
        final List<String> response8 = srpn.processNewCommands("=");

        assertThat(response1, is(Collections.emptyList()));
        assertThat(response2, is(Collections.emptyList()));
        assertThat(response3, is(Collections.emptyList()));
        assertThat(response4, is(Collections.emptyList()));
        assertThat(response5, is(Collections.emptyList()));
        assertThat(response6, is(Collections.emptyList()));
        assertThat(response7, is(Collections.emptyList()));
        assertThat(response8, is(List.of("25")));
    }

    @Test
    void Test2() {
        final List<String> response1 = srpn.processNewCommands("1234");
        final List<String> response2 = srpn.processNewCommands("2345");
        final List<String> response3 = srpn.processNewCommands("3456");
        final List<String> d1 = srpn.processNewCommands("d");
        final List<String> response5 = srpn.processNewCommands("+");
        final List<String> d2 = srpn.processNewCommands("d");
        final List<String> response7 = srpn.processNewCommands("+");
        final List<String> d3 = srpn.processNewCommands("d");
        final List<String> response9 = srpn.processNewCommands("=");

        assertThat(response1, is(Collections.emptyList()));
        assertThat(response2, is(Collections.emptyList()));
        assertThat(response3, is(Collections.emptyList()));
        assertThat(d1, is(List.of("1234", "2345", "3456")));
        assertThat(response5, is(Collections.emptyList()));
        assertThat(d2, is(List.of("1234", "5801")));
        assertThat(response7, is(Collections.emptyList()));
        assertThat(d3, is(List.of("7035")));
        assertThat(response9, is(List.of("7035")));
    }
}