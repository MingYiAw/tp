package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.function.Predicate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Person;

/**
 * Searches for clients who have appointments on the specified date and time.
 */
public class SearchAppointmentCommand extends Command {
    public static final String COMMAND_WORD = "search a/";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Searches for clients who have appointments on the specified date and time.\n"
            + "Parameters: DATE TIME (must be in yyyy-MM-dd HH:mm format)\n"
            + "Example: " + COMMAND_WORD + " 2023-12-31 14:30";

    public static final String MESSAGE_SUCCESS = "Listed all clients with appointments on %s";
    public static final String MESSAGE_INVALID_DATE_FORMAT = "The date format is invalid. "
            + "Please use yyyy-MM-dd HH:mm format.";

    private final String dateTime;

    /**
     * Creates a {@code SearchAppointmentCommand} to search for clients with appointments on the specified dateTime.
     *
     * @param dateTime The date and time in string format used to search for client appointments.
     * @throws CommandException if the {@code dateTime} format is invalid.
     */
    public SearchAppointmentCommand(String dateTime) throws CommandException {
        requireNonNull(dateTime);
        if (!isValidDateTime(dateTime)) {
            throw new CommandException(MESSAGE_INVALID_DATE_FORMAT);
        }
        this.dateTime = dateTime;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Predicate<Person> predicate = person -> {
            Appointment appointment = person.getAppointment();

            if (appointment == null) {
                return false;
            }
            String appointmentFormatted = appointment.toString();
            return appointmentFormatted.equals(dateTime);
        };
        model.updateFilteredPersonList(predicate);
        return new CommandResult(String.format(MESSAGE_SUCCESS, dateTime));
    }

    /**
     * Checks if the given dateTime string is in the yyyy-MM-dd HH:mm format.
     *
     * @param dateTime The dateTime string to check.
     * @return True if the dateTime is in yyyy-MM-dd HH:mm format, false otherwise.
     */
    private boolean isValidDateTime(String dateTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime.parse(dateTime, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        // Short circuit if the same object
        if (other == this) {
            return true;
        }

        // Instance of handles nulls and type check
        if (!(other instanceof SearchAppointmentCommand)) {
            return false;
        }

        // Cast and compare the dateTime attribute
        SearchAppointmentCommand otherCommand = (SearchAppointmentCommand) other;
        return this.dateTime.equals(otherCommand.dateTime);
    }
}
