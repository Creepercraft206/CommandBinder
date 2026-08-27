package Utils;

import javax.annotation.Nullable;

public enum MessageType {
    CMD_ADDED("cmdAdded"),
    CMD_REMOVED("cmdRemoved"),
    CMD_INSERTED("cmdInserted"),
    CMD_SET("cmdSet"),
    ONE_TIME_USE_TRUE("oneTimeUseTrue"),
    ONE_TIME_USE_FALSE("oneTimeUseFalse"),
    CONFIRM_TRUE("confirmTrue"),
    CONFIRM_FALSE("confirmFalse"),
    ON_COOLDOWN("onCooldown"),
    COOLDOWN_SET("cooldownSet"),
    COOLDOWN_REMOVED("cooldownRemoved"),
    NO_PERMS("noPerms");

    MessageType(String messsageIdentifier) {
        this.messsageIdentifier = messsageIdentifier;
    }

    private final String messsageIdentifier;

    /**
     * Gets the identifier used on items and in the config.
     * @return The identifier.
     */
    public String getMesssageIdentifier() {
        return messsageIdentifier;
    }

    /**
     * Gets all the message-type-identifiers as a String array.
     * @return An array of all message-type identifiers.
     */
    public static String[] getMessageTypes() {
        MessageType[] messageTypes = MessageType.values();
        String[] messageTypeStrings = new String[messageTypes.length];
        for (int i = 0; i < messageTypes.length; i++) {
            messageTypeStrings[i] = messageTypes[i].getMesssageIdentifier();
        }
        return messageTypeStrings;
    }

    /**
     * Converts a string to a MessageType.
     * @param string The string to convert.
     * @return null if the string is not a valid message type identifier, otherwise the corresponding MessageType.
     */
    public static @Nullable MessageType fromString(String string) {
        for (MessageType type : MessageType.values()) {
            if (type.getMesssageIdentifier().equalsIgnoreCase(string)) {
                return type;
            }
        }
        return null;
    }
}
