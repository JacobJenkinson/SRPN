import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

class CommandStringParserTest {

    private CommandStringParser commandStringParser;

    @BeforeEach
    void setUp() {
        commandStringParser = new CommandStringParser();
    }

    @Test
    void parseWhiteSpace() {
        final List<String> commands = commandStringParser.parseCommand("");
        assertThat(commands.isEmpty(), is(true));
    }

    @Test
    void parseSingleValue() {
        final List<String> commands = commandStringParser.parseCommand("1");
        assertThat(commands, contains("1"));
    }

    @Test
    void parseSingleNegativeValue() {
        final List<String> commands = commandStringParser.parseCommand("-1");
        assertThat(commands, contains("-1"));
    }

    @Test
    void parseSingleValueWithWhitespace() {
        final List<String> commands = commandStringParser.parseCommand("      1");
        assertThat(commands, contains("1"));
    }

    @Test
    void parseSingleValueWithWhitespaceAfter() {
        final List<String> commands = commandStringParser.parseCommand("3        ");
        assertThat(commands, contains("3"));
    }

    @Test
    void parseSimpleCommand() {
        final List<String> commands = commandStringParser.parseCommand("1 1 +");
        assertThat(commands, contains("1", "1", "+"));
    }

    @Test
    void equalsSeperation() {
        final List<String> commands = commandStringParser.parseCommand("11=4");
        assertThat(commands, contains("11", "=", "4"));
    }

    @Test
    void separationUsingOperands() {
        final List<String> commands = commandStringParser.parseCommand("11+1+1+d");
        assertThat(commands, contains("11", "+", "1", "+", "1", "+", "d"));
    }

    @Test
    void separationUsingOperandsNegative() {
        final List<String> commands = commandStringParser.parseCommand("-11 -1 + 1 + d");
        assertThat(commands, contains("-11", "-1", "+", "1", "+", "d"));
    }

    @Test
    void supportComments() {
        final List<String> commands = commandStringParser.parseCommand("1 2 + # And so i s t h i s #");
        assertThat(commands, contains("1", "2", "+"));
    }

    @Test
    void inAndOutOfComment() {
        final List<String> commands = commandStringParser.parseCommand("1 2 + # And so i s t h i s # 3");
        assertThat(commands, contains("1", "2", "+", "3"));
    }


    @Test
    void onlyComment() {
        final List<String> commands = commandStringParser.parseCommand("# This is a comment #");
        assertThat(commands, is(Collections.emptyList()));
    }

    @Test
    void multilineComments() {
        final List<String> commands = commandStringParser.parseCommand("1 2 + # And so i s t h i s ");
        final List<String> commands2 = commandStringParser.parseCommand("And so i s t h i s #");
        assertThat(commands, contains("1", "2", "+"));
        assertThat(commands2, is(Collections.emptyList()));
    }

    @Test
    void multilineCommentsNoHashOnLine2() {
        final List<String> commands = commandStringParser.parseCommand("1 2 + # And so i s t h i s ");
        final List<String> commands2 = commandStringParser.parseCommand("how about this ");
        final List<String> commands3 = commandStringParser.parseCommand("And now t h i s # 2");
        assertThat(commands, contains("1", "2", "+"));
        assertThat(commands2, is(Collections.emptyList()));
        assertThat(commands3, contains("2"));
    }
}