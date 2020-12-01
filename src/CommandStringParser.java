import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandStringParser {
    private boolean isNotInCommentMode = true;      //field represents state of whether input is treated as a comment

    public List<String> parseCommand(final String command) {
        if (command.isBlank()) {
            return Collections.emptyList();
        } else {
            return separateCommandStringIntoCommands(command);
        }
    }

    private List<String> separateCommandStringIntoCommands(final String command) {
        final String commandStringWithSwappedEquals = swapWhenEqualsNextToOperator(command);
        final String possibleSeparators = "+*%^/=rd";
        //positive lookbehind ?<= or lookahead ?= where a single character matches any values in the possibleSeparators
        //or any whitespace denoted by \s
        final String regex = "(?<=[" + possibleSeparators + "])|(?=[" + possibleSeparators + "])|\\s";
        final List<String> parsedCommands = Stream.of(commandStringWithSwappedEquals.split(regex))
                .filter(cmd -> !cmd.isBlank())
                .map(String::trim).collect(Collectors.toList());
        return removeComments(parsedCommands);
    }

    // needed to handle SRPN behaviour differences between '+=' and '+ =' for all operators
    private String swapWhenEqualsNextToOperator(final String command) {
        final String operators = "-+*%^/";
        String commandWithSwaps = command;
        for (int i = 0; i < commandWithSwaps.length() - 1; i++) {
            final boolean isOperator = operators.contains(Character.toString(commandWithSwaps.charAt(i)));
            final boolean nextCharIsEquals = commandWithSwaps.charAt(i + 1) == '=';
            if (isOperator && nextCharIsEquals) {
                commandWithSwaps = swap(commandWithSwaps, i, i + 1);
            }
        }
        return commandWithSwaps;
    }

    // swaps two characters round in a char array
    private String swap(String str, int i, int j) {
        StringBuilder sb = new StringBuilder(str);
        sb.setCharAt(i, str.charAt(j));
        sb.setCharAt(j, str.charAt(i));
        return sb.toString();
    }

    private List<String> removeComments(final List<String> commandArray) {
        final List<Integer> commentPositions = findCommandPositions(commandArray, "#");
        if (commentPositions.size() > 0) {
            final List<String> nonCommentCommands = new ArrayList<>();
            // string upto first command depending on mode
            if (isNotInCommentMode) {
                nonCommentCommands.addAll(commandArray.subList(0, commentPositions.get(0)));
            }
            isNotInCommentMode = !isNotInCommentMode;


            for (int i = 0; i < commentPositions.size() - 1; i++) {
                final List<String> subList = commandArray.subList(commentPositions.get(i) + 1, commentPositions.get(i + 1));
                if (isNotInCommentMode) {
                    nonCommentCommands.addAll(subList);
                }
                isNotInCommentMode = !isNotInCommentMode;
            }

            // string upto last command depending on mode, no need to swap mode when finished as all # handled
            if (isNotInCommentMode) {
                nonCommentCommands.addAll(commandArray.subList(commentPositions.get(commentPositions.size() - 1) + 1, commandArray.size()));
            }

            return nonCommentCommands;
        } else {
            // if no comment commands in string then response depending on mode
            return isNotInCommentMode ? commandArray : Collections.emptyList();
        }
    }

    //finds positions in the array where the given string is equal to its contents
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
