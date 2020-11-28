import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class SRPNTest4 {

    private SRPN srpn;
    private int currentR;

    @BeforeEach
    void setUp() {
        srpn = new SRPN();
    }


    @Test
    void Test1() {
        final List<String> response1 = srpn.processNewCommands("1");
        final List<String> response2 = srpn.processNewCommands("+");

        assertThat(response1, is(Collections.emptyList()));
        assertThat(response2, is(List.of("Stack underflow.")));
    }


    @Test
    void Test2() {
        final List<String> response1 = srpn.processNewCommands("10");
        final List<String> response2 = srpn.processNewCommands("5");
        final List<String> response3 = srpn.processNewCommands("-5");
        final List<String> response4 = srpn.processNewCommands("+");
        final List<String> response5 = srpn.processNewCommands("/");

        assertThat(response1, is(Collections.emptyList()));
        assertThat(response2, is(Collections.emptyList()));
        assertThat(response3, is(Collections.emptyList()));
        assertThat(response4, is(Collections.emptyList()));
        assertThat(response5, is(List.of("Divide by 0.")));
    }

    @Test
    void Test3() {
        final List<String> response1 = srpn.processNewCommands("11+1+1+d");

        assertThat(response1, is(List.of("Stack underflow.", "13")));
    }

    @Test
    void Test4() {
        final List<String> response1 = srpn.processNewCommands("# This i s a comment #");
        final List<String> response2 = srpn.processNewCommands("1 2 + # And so i s t h i s #");
        final List<String> d1 = srpn.processNewCommands("d");

        assertThat(response1, is(Collections.emptyList()));
        assertThat(response2, is(Collections.emptyList()));
        assertThat(d1, is(List.of("3")));
    }

    @Test
    void Test5() {
        final List<String> response1 = srpn.processNewCommands("3 3 ^ 3 ^ 3 ^=");

        assertThat(response1, is(List.of("3")));
    }

    @Test
    void singleLineR() {
        final List<String> response1 = srpn.processNewCommands("r");
        final List<String> response2 = srpn.processNewCommands("r");
        final List<String> response3 = srpn.processNewCommands("r");
        final List<String> d1 = srpn.processNewCommands("d");

        assertThat(d1, is(List.of("1804289383", "846930886", "1681692777")));
    }

    @Test
    void Test6() {
        final List<String> response1 = srpn.processNewCommands("r r r r r r r r r r r r r r r r r r r r r r d r r r d");

        assertThat(response1, is(List.of("1804289383", "846930886", "1681692777", "1714636915", "1957747793", "424238335",
                "719885386", "1649760492", "596516649", "1189641421", "1025202362", "1350490027", "783368690", "1102520059",
                "2044897763", "1967513926", "1365180540", "1540383426", "304089172", "1303455736", "35005211", "521595368",
                "Stack overflow.", "Stack overflow.", "1804289383", "846930886", "1681692777", "1714636915", "1957747793",
                "424238335", "719885386", "1649760492", "596516649", "1189641421", "1025202362", "1350490027", "783368690",
                "1102520059", "2044897763", "1967513926", "1365180540", "1540383426", "304089172", "1303455736", "35005211",
                "521595368", "1804289383")));
    }
}