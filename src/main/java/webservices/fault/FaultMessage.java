package webservices.fault;

public enum FaultMessage {
    NO_EMAIL_FOUND_ON_SERVER("There is no email found on server"),
    NO_EMAIL_FOUND_BY_EMAIL_ADDRESS("There is no email found by email address [%s]"),
    NO_EMAIL_FOUND_BY_SUBJECT("There is no email found by subject [%s]"),
    SUCH_EMAIL_EXISTS_OR_NOT_FILLED_ALL_FIELDS("Email with subject [%s] exists or not filled all fields");

    private String messageExpression;

    FaultMessage(String message) {
        this.messageExpression = message;
    }

    public String getMessageExpression() {
        return this.messageExpression;
    }

    }
