package Utils;

import de.hgpractice.commandbinder.CommandBinder;

public class Messages {

    public static String prefix = "§7» §3CommandBinder §8× §7";

    // ------------------ CommandOutput ------------------ //
    public static String cmdAdded = prefix + "Der §3Befehl §7wurde erfolgreich §3hinzugefügt§7!";
    public static String cmdRemoved = prefix + "Der §3Befehl §7wurde erfolgreich §3entfernt§7!";
    public static String cmdInserted = prefix + "Der §3Befehl §7wurde erfolgreich §3eingefügt§7!";
    public static String permAdded = prefix + "Die §3Permission §7wurde erfolgreich §3hinzugefügt§7!";
    public static String permRemoved = prefix + "Die §3Permission §7wurde erfolgreich §3entfernt§7!";
    public static String listHeader = prefix + "§3Commands:";
    public static String listPermHeader = prefix + "§3Permissions:";
    public static String listItemOdd = "§7× §9";
    public static String listItemEven = "§7× §b";
    public static String listItemPlaceholder = " §8» §7";
    public static String helpText = prefix + "Help\n" +
            "§8× §7/commandbinder add <Befehl> §8» §7Fügt einen Befehl hinzu.\n" +
            "§8× §7/commandbinder remove <ID> §8» §7Entfernt einen Befehl.\n" +
            "§8× §7/commandbinder insert <ID> <Befehl> §8» §7Fügt einen Befehl an einer bestimmten Stelle hinzu.\n" +
            "§8× §7/commandbinder list §8» §7Listet alle Befehle auf.\n" +
            "§8× §7/commandbinder addperm <Permission> §8» §7Fügt eine Permission hinzu.\n" +
            "§8× §7/commandbinder removeperm <Permission> §8» §7Entfernt eine Permission.\n" +
            "§8× §7/commandbinder listperms §8» §7Zeigt alle Permissions eines Items.\n" +
            "§8× §7/commandbinder placeholders §8» §7Listet alle Platzhalter auf.\n" +
            "§8× §7/commandbinder customcmds §8» §7Listet alle CustomCommands auf.\n" +
            "§8× §7/commandbinder help §8» §7Zeigt diese Hilfe an.\n" +
            "§8× §7/commandbinder info §8» §7Zeigt Informationen über das Plugin an.";
    public static String infoText = prefix + "Info\n" +
            "§8× §3Version: §8» §7" + CommandBinder.getInstance().getDescription().getVersion() + "\n" +
            "§8× §3Author: §8» §7" + CommandBinder.getInstance().getDescription().getAuthors().get(0) + "\n" +
            "§8× §3Source: §8» §7https://github.com/Creepercraft206/CommandBinder\n" +
            "§8× §3Spigot: §8» §7https://www.spigotmc.org/resources/commandbinder.114872/\n" +
            "§8× §7Plugin-Idee basierend auf dem CommandBinder-Plugin der NeruxVace.de PServer.";
    public static String placeholderText = prefix + "Placeholders\n" +
            "§8× §3%player% §8» §7Der Name des Spielers\n" +
            "§8× §3%UUID% §8» §7Die UUID des Spielers\n" +
            "§8× §3%world% §8» §7Der Name der Welt, in der sich der Spieler befindet\n" +
            "§8× §3%x% §8» §7Die x-Koordinate des Spielers\n" +
            "§8× §3%y% §8» §7Die y-Koordinate des Spielers\n" +
            "§8× §3%z% §8» §7Die z-Koordinate des Spielers\n" +
            "§8× §3%yaw% §8» §7Der Yaw des Spielers\n" +
            "§8× §3%pitch% §8» §7Der Pitch des Spielers\n" +
            "§8× §3%item% §8» §7Der Name des Items in der Hand des Spielers\n" +
            "§8× §3%amount% §8» §7Die Anzahl des Items in der Hand des Spielers\n" +
            "§8× §3%slot% §8» §7Der Slot des Items in der Hand des Spielers\n" +
            "§8× §3%durability% §8» §7Die Haltbarkeit des Items in der Hand des Spielers\n" +
            "§8× §3%playersOnline% §8» §7Die Anzahl der Spieler online\n" +
            "§8× §3%block% §8» §7Der Name des Blocks, den der Spieler ansieht\n" +
            "§8× §3%lookingAt% §8» §7Die Koordinaten des Blocks, den der Spieler ansieht\n" +
            "§8× §3%year% §8» §7Das aktuelle Jahr\n" +
            "§8× §3%month% §8» §7Der aktuelle Monat\n" +
            "§8× §3%day% §8» §7Der aktuelle Tag\n" +
            "§8× §3%hour% §8» §7Die aktuelle Stunde\n" +
            "§8× §3%minute% §8» §7Die aktuelle Minute\n" +
            "§8× §3%second% §8» §7Die aktuelle Sekunde";
    public static String customCmdsText = prefix + "CustomCommands\n" +
            "§8× §3!wait <Sekunden> §8» §7Wartet x Sekunden vor Ausführung des nächsten Befehls\n" +
            "§8× §3!repeat <Anzahl> §8» §7Führt die Nachfolgenden Befehle x Mal aus.\n" +
            "§8× §3!endrepeat §8» §7Legt das Ende eines !repeat-Befehls fest.";

    // ------------------ CommandOutput ------------------ //

    // ------------------- ErrorOutput ------------------- //
    public static String noPerms = prefix + "§cDazu hast du keine Rechte!";
    public static String invalidId = prefix + "Diese §3ID §7existiert §cnicht§7!";
    public static String noCmds = prefix + "Es sind §ckeine §7Befehle auf diesem Item gespeichert!";
    public static String noItem = prefix + "§cDu musst ein Item in der Hand halten!";
    // ------------------- ErrorOutput ------------------- //

    // --------------------- Usage ----------------------- //
    public static String usageAdd = prefix + "§7Verwendung: §7/commandbinder add <Befehl>";
    public static String usageRemove = prefix + "§7Verwendung: §7/commandbinder remove <ID>";
    public static String usageInsert = prefix + "§7Verwendung: §7/commandbinder insert <ID> <Befehl>";
    public static String usageAddPerm = prefix + "§7Verwendung: §7/commandbinder addperm <Permission>";
    public static String usageRemovePerm = prefix + "§7Verwendung: §7/commandbinder removeperm <Permission>";
    // --------------------- Usage ----------------------- //
}
