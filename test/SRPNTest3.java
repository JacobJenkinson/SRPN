import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class SRPNTest3 {

    private SRPN srpn;

    @BeforeEach
    void setUp() {
        srpn = new SRPN();
    }


    @Test
    void LargeInteger() {
        final List<String> response1 = srpn.processNewCommands("2147483648");
        final List<String> response2 = srpn.processNewCommands("1");
        final List<String> response3 = srpn.processNewCommands("+");
        final List<String> response4 = srpn.processNewCommands("=");

        assertThat(response1, is(Collections.emptyList()));
        assertThat(response2, is(Collections.emptyList()));
        assertThat(response3, is(Collections.emptyList()));
        assertThat(response4, is(List.of("2147483647")));
    }


    @Test
    void Test1() {
        final List<String> response1 = srpn.processNewCommands("2147483647");
        final List<String> response2 = srpn.processNewCommands("1");
        final List<String> response3 = srpn.processNewCommands("+");
        final List<String> response4 = srpn.processNewCommands("=");

        assertThat(response1, is(Collections.emptyList()));
        assertThat(response2, is(Collections.emptyList()));
        assertThat(response3, is(Collections.emptyList()));
        assertThat(response4, is(List.of("2147483647")));
    }

    @Test
    void Test2() {
        final List<String> response1 = srpn.processNewCommands("-2147483647");
        final List<String> response2 = srpn.processNewCommands("1");
        final List<String> response3 = srpn.processNewCommands("-");
        final List<String> response4 = srpn.processNewCommands("=");
        final List<String> response5 = srpn.processNewCommands("20");
        final List<String> response6 = srpn.processNewCommands("-");
        final List<String> response7 = srpn.processNewCommands("=");

        assertThat(response1, is(Collections.emptyList()));
        assertThat(response2, is(Collections.emptyList()));
        assertThat(response3, is(Collections.emptyList()));
        assertThat(response4, is(List.of("-2147483648")));
        assertThat(response5, is(Collections.emptyList()));
        assertThat(response6, is(Collections.emptyList()));
        assertThat(response7, is(List.of("-2147483648")));
    }

    @Test
    void Test3() {
        final List<String> response1 = srpn.processNewCommands("100000");
        final List<String> response2 = srpn.processNewCommands("0");
        final List<String> response3 = srpn.processNewCommands("-");
        final List<String> d1 = srpn.processNewCommands("d");
        final List<String> response5 = srpn.processNewCommands("*");
        final List<String> response6 = srpn.processNewCommands("=");


        assertThat(response1, is(Collections.emptyList()));
        assertThat(response2, is(Collections.emptyList()));
        assertThat(response3, is(Collections.emptyList()));
        assertThat(d1, is(List.of("100000")));
        assertThat(response5, is(List.of("Stack underflow.")));
        assertThat(response6, is(List.of("100000")));
    }
}