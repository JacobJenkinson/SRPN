import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandStringParser {

    private boolean isNotInCommendMode = true;

    public List<String> parseCommand(final String command) {
        if (command.isBlank()) {
            return Collections.emptyList();
        } else {
            final String possibleSeparators = "rd+*%^/=";
            final String regex = "(?<=[" + possibleSeparators + "])|(?=[" + possibleSeparators + "])| ";
            final List<String> parsedCommands = Stream.of(command.split(regex))
                    .filter(cmd -> !cmd.isBlank())
                    .map(String::trim).collect(Collectors.toList());
            return removeComments(parsedCommands);
        }
    }

    private List<String> removeComments(final List<String> commandArray) {
        final List<Integer> commentPositions = findCommandPositions(commandArray, "#");
        if (commentPositions.size() > 0) {
            final List<String> nonCommentCommands = new ArrayList<>();
            if (isNotInCommendMode) {
                nonCommentCommands.addAll(commandArray.subList(0, commentPositions.get(0)));
            }
            isNotInCommendMode = !isNotInCommendMode;


            for (int i = 0; i < commentPositions.size() - 1; i++) {
                final List<String> subList = commandArray.subList(commentPositions.get(i) + 1, commentPositions.get(i + 1));
                if (isNotInCommendMode) {
                    nonCommentCommands.addAll(subList);
                }
                isNotInCommendMode = !isNotInCommendMode;
            }

            if (isNotInCommendMode) {
                nonCommentCommands.addAll(commandArray.subList(commentPositions.get(commentPositions.size() - 1) + 1, commandArray.size()));
            }

            return nonCommentCommands;
        } else {
            return isNotInCommendMode ? commandArray : Collections.emptyList();
        }
    }


    private List<Integer> findCommandPositions(final List<String> commandArray, final String commandToSearch) {
        final List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < commandArray.size(); i++) {
            if (commandToSearch.equals(commandArray.get(i))) {
                positions.add(i);
            }
        }
        return positions;
    }

}
