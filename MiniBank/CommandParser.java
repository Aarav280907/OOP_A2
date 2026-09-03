public class CommandParser {
    private CommandParser() {
    }

    public static Command parse(String line) {
        if (line == null) {
            throw new IllegalArgumentException("Command cannot be null.");
        }

        String[] parts = line.trim().split("\\s+");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Command must contain a type, account number, and amount.");
        }

        try {
            TransactionType type = TransactionType.valueOf(parts[0].toUpperCase());
            long amount = Long.parseLong(parts[2]);
            return new Command(type, parts[1], amount);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Invalid transaction command: " + line, exception);
        }
    }
}
