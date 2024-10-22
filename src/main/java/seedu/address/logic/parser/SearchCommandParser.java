package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.SearchAppointmentCommand;
import seedu.address.logic.commands.SearchBirthdayCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a SearchBirthdayCommand or SearchAppointmentCommand object.
 */
public class SearchCommandParser implements Parser<Command> {

    public static final String PREFIX_BIRTHDAY = "b/";
    public static final String PREFIX_APPOINTMENT = "a/";

    /**
     * Parses the given {@code String} of arguments and determines whether it's a birthday or an appointment search.
     * Returns the appropriate command for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public Command parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException("Search command cannot be empty.");
        }

        // Check if the command starts with a recognized prefix
        if (trimmedArgs.contains(PREFIX_BIRTHDAY)) {
            String date = trimmedArgs.substring(PREFIX_BIRTHDAY.length()).trim();
            System.out.println(date);
            return parseBirthdayCommand(date);
        } else if (trimmedArgs.contains(PREFIX_APPOINTMENT)) {
            String dateTime = trimmedArgs.substring(PREFIX_APPOINTMENT.length()).trim();
            return parseAppointmentCommand(dateTime);
        } else {
            throw new ParseException("Invalid prefix. Use 'b/' for birthday or 'a/' for appointment.");
        }
    }

    /**
     * Parses the birthday search arguments and returns a SearchBirthdayCommand.
     */
    private SearchBirthdayCommand parseBirthdayCommand(String date) throws ParseException {
        if (date.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchBirthdayCommand.MESSAGE_USAGE));
        }

        try {
            return new SearchBirthdayCommand(date);
        } catch (CommandException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchBirthdayCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the appointment search arguments and returns a SearchAppointmentCommand.
     */
    private SearchAppointmentCommand parseAppointmentCommand(String dateTime) throws ParseException {
        if (dateTime.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchAppointmentCommand.MESSAGE_USAGE));
        }

        try {
            return new SearchAppointmentCommand(dateTime);
        } catch (CommandException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchAppointmentCommand.MESSAGE_USAGE));
        }
    }
}
