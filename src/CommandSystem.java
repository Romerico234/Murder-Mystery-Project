import java.util.*;

public class CommandSystem {
    private GameState state;
    private Person player;
    private List<String> verbs = new ArrayList<>();
    private List<String> verbDescription = new ArrayList<>();
    private List<String> nouns = new ArrayList<>();
    private Location currentLocation;

    public CommandSystem(GameState state, Person player) {
        this.state = state;
        this.player = player;
        this.currentLocation = state.getCurrentLocation();
        initializeVerbs();
        initializeNouns();
    }

    private void initializeVerbs() {
        addVerb("walk", "You can walk in directions: [north], [south], [east], [west]");
        addVerb("collect", "Put an item into your inventory: [collect kitchen crimereport]");
        addVerb("talk", "Interrogate or talk to people [talk unclebob]");
        addVerb("look",
                "Look in your current area: [look] or look at your inventory: [look inventory] and [look inventory item]");
        addVerb("open", "Used to open something [open location item]");
        addVerb("quit", "Quit the game. [quit]");
        addVerb("?", "Show this help screen. [?]");
    }

    private void initializeNouns() {
        addNoun("north");
        addNoun("west");
        addNoun("east");
        addNoun("south");
        addNoun("fronthouse");
        addNoun("masterbedroom");
        addNoun("richardbedroom");
        addNoun("kitchen");
        addNoun("bathroom");
        addNoun("livingroom");
        addNoun("glassshards");
        addNoun("open");
        addNoun("note");
        addNoun("crimereport");
        addNoun("letter");
        addNoun("safe");
        addNoun("richard");
        addNoun("unclebob");
        addNoun("joseph");
        addNoun("inventory");
    }

    public void executeVerb(String verb) {
        currentLocation = state.getCurrentLocation();
        switch (verb) {
            case "look":
                gameOutput(currentLocation.getDescription());
                break;
            case "walk":
                gameOutput("You must pick a direction to walk (north, south, east, west)");
                break;
            case "collect":
                gameOutput("You collect nothing");
                break;
            case "talk":
                gameOutput("You talk but you aren't talking to anyone");
                break;
            case "open":
                gameOutput("Specify the location and item when trying to open something. (open, location, item)");
                break;
            case "quit":
                state.setRunning(false);
                gameOutput("Quitting the game...");
                break;
            case "?":
                printHelp();
                break;
            default:
                gameOutput("Unknown command: " + verb);
        }
    }

    public void executeVerbNoun(String verb, String noun) {
        currentLocation = state.getCurrentLocation();
        String resultString = "";

        switch (verb) {
            case "look":
                if (noun.equalsIgnoreCase("inventory"))
                    lookInventory();
                else
                    resultString = lookAt(noun);
                break;
            case "walk":
                resultString = walkTo(noun);
                break;
            case "talk":
                resultString = talkTo(noun);
                break;
            case "collect":
                gameOutput("You must specify the location and the item");
                break;
            case "open":
                gameOutput("Specify the location when trying to open something. (open, location, item)");
                break;
            default:
                resultString = "Unknown command: " + verb;
        }
        gameOutput(resultString);
    }

    public void executeVerbNounNoun(String verb, String noun1, String noun2) {
        currentLocation = state.getCurrentLocation();
        switch (verb) {
            case "collect":
                collectItemInLocation(noun1, noun2);
                break;
            case "open":
                openItemInLocation(noun1, noun2);
                break;
            case "look":
                if (noun1.equalsIgnoreCase("inventory"))
                    gameOutput(lookInventoryItem(noun2));
                else
                    gameOutput("Invalid command");
                break;
            default:
                gameOutput("Cannot execute using this command");
        }
    }

    private void lookInventory() {
        List<Item> inventory = player.getInventory();
        if (inventory.isEmpty())
            gameOutput("Your inventory is empty.");
        else {
            gameOutput("You have the following items in your inventory: ");
            player.showInventory();
        }
    }

    private String lookInventoryItem(String itemName) {
        for (Item item : player.getInventory())
            if (item.getName().equalsIgnoreCase(itemName)
                    || item.getName().replaceAll(" ", "").equalsIgnoreCase(itemName))
                return item.getDescription();
        return "Item not found in your inventory.";
    }

    private void collectItemInLocation(String location, String item) {
        currentLocation = state.getCurrentLocation();
        if (currentLocation.getName().equalsIgnoreCase(location)) {
            if (item.equalsIgnoreCase("safe"))
                gameOutput("You cannot collect the safe.");
            else {
                boolean itemFound = false;
                for (Item i : currentLocation.getItems()) {
                    if (i.getName().equalsIgnoreCase(item)) {
                        gameOutput("You collected the " + i.getName());
                        player.addItem(i);
                        currentLocation.removeItem(i);
                        if (player.getInventory().size() >= GameState.TOTAL_NO_OF_ITEMS) {
                            gameOutput(
                                    "You have collected all the items. You leave dissatisfied and the case currently remains unsolved. Your inventory:");
                            player.showInventory();
                            state.setRunning(false);
                        }
                        itemFound = true;
                    }
                }
                if (!itemFound)
                    gameOutput("Item not found in " + location);
            }
        } else
            gameOutput("You are not in the correct location to collect the item.");
    }

    private String walkTo(String direction) {
        int row = -1;
        int col = -1;
        currentLocation = state.getCurrentLocation();
        for (int i = 0; i < state.getGrid().length; i++) {
            for (int j = 0; j < state.getGrid()[i].length; j++) {
                if (state.getGrid()[i][j] == currentLocation) {
                    row = i;
                    col = j;
                    break;
                }
            }
        }

        if (row == -1 || col == -1)
            return "Current location not found in the grid!";

        switch (direction) {
            case "north":
                row--;
                break;
            case "south":
                row++;
                break;
            case "east":
                col++;
                break;
            case "west":
                col--;
                break;
            default:
                return "Invalid direction!";
        }

        if (row < 0 || col < 0 || row >= state.getGrid().length || col >= state.getGrid()[0].length)
            return "You can't go that way!";

        Location nextLocation = state.getLocationFromGrid(row, col);
        if (nextLocation != null) {
            state.setCurrentLocation(nextLocation);
            return "You moved to: " + nextLocation.getName();
        } else
            return "You can't go that way!";
    }

    private String lookAt(String noun) {
        currentLocation = state.getCurrentLocation();
        for (Item item : currentLocation.getItems())
            if (item.getName().equalsIgnoreCase(noun) || item.getName().replaceAll(" ", "").equalsIgnoreCase(noun))
                return item.getDescription();

        for (Person character : currentLocation.getCharacters())
            if (character.getName().equalsIgnoreCase(noun)
                    || character.getName().replaceAll(" ", "").equalsIgnoreCase(noun))
                return character.getDescription();
        return "You don't see " + noun + " here.";
    }

    private String talkTo(String person) {
        currentLocation = state.getCurrentLocation();
        for (Person character : currentLocation.getCharacters())
            if (character.getName().equalsIgnoreCase(person)
                    || character.getName().replaceAll(" ", "").equalsIgnoreCase(person))
                return character.getDescription();
        return "You don't see " + person + " here.";
    }

    private void openItemInLocation(String location, String item) {
        currentLocation = state.getCurrentLocation();
        if (currentLocation.getName().equalsIgnoreCase("MasterBedroom")) {
            if (location.equalsIgnoreCase("MasterBedroom") && item.equalsIgnoreCase("safe"))
                openSafe();
            else
                gameOutput("Cannot open that item here.");
        } else
            gameOutput("You are not in the correct location to open this item.");

    }

    private void openSafe() {
        Scanner console = new Scanner(System.in);
        int quit = 999;
        int code;

        System.out.println("Enter an 8-digit code, 999 to quit");
        while (!console.hasNextInt()) {
            System.out.println("Not a valid entry, enter again: ");
            console.nextLine();
        }
        code = console.nextInt();
        while (code != quit) {
            if (code == state.SAFE_CODE) {
                gameOutput(
                        "By using all the clues, you open the safe (code: 10211225). There are two knives, one seems to be a kitchen knife and the other is a dagger with an engraving of \"Joseph\". You determine that all three of them were responsible for the crime. You return to the police station with the three suspects in custody.");
                System.out.println("You won the game!");
                state.setRunning(false); // End the game
                break;
            } else {
                gameOutput("Code incorrect. Enter an 8-digit code, 999 to quit: ");
                code = console.nextInt();
            }
        }
        console.close();
    }

    /*****************************************************************
     * Helper methods to help system work.
     ******************************************************************/

    public GameState getGameState() {
        return this.state;
    }

    /*
     * Prints out the help menu. Goes through all verbs and verbDescriptions
     * printing a list of all commands the user can use.
     */
    public void printHelp() {
        int DISPLAY_WIDTH = GameState.DISPLAY_WIDTH;
        String s1 = "";
        while (s1.length() < DISPLAY_WIDTH)
            s1 += "-";

        String s2 = "";
        while (s2.length() < DISPLAY_WIDTH) {
            if (s2.length() == (DISPLAY_WIDTH / 2 - 10))
                s2 += " Commands ";
            else
                s2 += " ";
        }

        System.out.println("\n\n" + s1 + "\n" + s2 + "\n" + s1 + "\n");
        for (String v : verbs)
            System.out.printf("%-8s  %s", v, formatMenuString(verbDescription.get(verbs.indexOf(v))));
    }

    // Used to format the help menu
    public String formatMenuString(String longString) {
        String result = "";
        Scanner chop = new Scanner(longString);
        int charLength = 0;

        while (chop.hasNext()) {
            String next = chop.next();
            charLength += next.length();
            result += next + " ";
            if (charLength >= (GameState.DISPLAY_WIDTH - 30)) {
                result += "\n          ";
                charLength = 0;
            }
        }
        chop.close();
        return result + "\n\n";
    }

    /**
     * Default game output.
     * This is an alias for the other gameOutput method and defaults to
     * doing both the bracketing and the width formatting.
     **/
    public void gameOutput(String longstring) {
        gameOutput(longstring, true, true);
    }

    public void gameOutput(String longstring, boolean addBrackets, boolean formatWidth) {
        if (addBrackets)
            longstring = addNounBrackets(longstring);
        if (formatWidth)
            longstring = formatWidth(longstring);
        System.out.println(longstring);
    }

    // formats a string to DISPLAY_WIDTH character width.
    // Used when getting descriptions from items/locations and printing them to the
    // screen.
    // You can also add [nl] for a newline in a string in a description etc.
    public String formatWidth(String longString) {
        Scanner chop = new Scanner(longString);
        String result = "";
        int charLength = 0;
        boolean addSpace = true;

        while (chop.hasNext()) {
            String next = chop.next();
            charLength += next.length() + 1;

            // Find and replace any special newline characters [nl] with \n.
            if (next.contains("[nl]")) {
                int secondHalf = next.indexOf("[nl]") + 4;
                if (secondHalf < next.length())
                    charLength = secondHalf;
                else {
                    charLength = 0;
                    addSpace = false;
                }
                next = next.replace("[nl]", "\n");
            }

            result += next;
            if (addSpace)
                result += " ";

            addSpace = true;
            if (charLength >= GameState.DISPLAY_WIDTH) {
                result += "\n";
                charLength = 0;
            }
        }
        chop.close();
        return result;
    }

    /**
     * Adds brackets around whole words that are included in the `nouns` list,
     * ignoring case, and also deals with any that have punctuation after them.
     *
     * @param longString the string to check for nouns
     * @return the modified string with brackets around the nouns
     */
    public String addNounBrackets(String longString) {
        String[] words = longString.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            String word = words[i].replaceAll("\\p{Punct}+$", "");
            String punct = words[i].substring(word.length());
            for (String noun : nouns) {
                if (word.equalsIgnoreCase(noun)) {
                    words[i] = "[" + word + "]" + punct;
                    break;
                }
            }
        }
        return String.join(" ", words);
    }

    // Adds a noun to the noun list
    // lets the command system know this is something you can interact with.
    public void addNoun(String string) {
        if (!nouns.contains(string.toLowerCase()))
            nouns.add(string.toLowerCase());
    }

    // Adds a verb to the verb list and its description to the parallel description
    // list.
    // This method should be used to register new commands with the command system.
    public void addVerb(String verb, String description) {
        verbs.add(verb.toLowerCase());
        verbDescription.add(description.toLowerCase());
    }

    // Allows the client code to check to see if a verb is in the game.
    public boolean hasVerb(String string) {
        return verbs.contains(string);
    }

    // Allows the client code to check to see if a noun is in the game.
    public boolean hasNoun(String string) {
        return nouns.contains(string);
    }
}
