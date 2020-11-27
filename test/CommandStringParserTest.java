import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void separationUsingOperands() {
        final List<String> commands = commandStringParser.parseCommand("11+1+1+d");
        assertThat(commands, contains("11", "+", "1", "+", "1", "+", "d"));
    }

    @Test
    void supportComments() {
        final List<String> commands = commandStringParser.parseCommand("1 2 + # And so i s t h i s #");
        assertThat(commands, contains("1", "2", "+"));
    }
}