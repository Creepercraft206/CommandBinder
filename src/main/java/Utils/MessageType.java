package Utils;

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

    public String getMesssageIdentifier() {
        return messsageIdentifier;
    }

    public static String[] getMessageTypes() {
        MessageType[] messageTypes = MessageType.values();
        String[] messageTypeStrings = new String[messageTypes.length];
        for (int i = 0; i < messageTypes.length; i++) {
            messageTypeStrings[i] = messageTypes[i].getMesssageIdentifier();
        }
        return messageTypeStrings;
    }
}
