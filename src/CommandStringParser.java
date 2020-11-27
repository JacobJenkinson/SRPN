import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandStringParser {

    public List<String> parseCommand(final String command) {
        if (command.isBlank()) {
            return Collections.emptyList();
        } else {
            final String possibleSeparators = "-rd+*%^/";
            final String regex = "(?<=[" + possibleSeparators + "])|(?=[" + possibleSeparators + "])| ";
            final List<String> parsedCommands = Stream.of(command.split(regex))
                    .filter(cmd -> !cmd.isBlank())
                    .map(String::trim).collect(Collectors.toList());
            return removeComments(parsedCommands);
        }
    }

    private List<String> removeComments(final List<String> commandArray) {
        if (commandArray.contains("#")) {
            return commandArray.subList(0, commandArray.indexOf("#"));
        } else {
            return commandArray;
        }
    }

}
