/*
CommandSystem.java
For use in the Final project for COSC 236.
Based on starter code first developed by Prof. Dastyni Loksa

This class is the primary logic class for the system. It defines what commands are valid, 
and what happens when those commands are executed.  
*/

import java.io.Console;
import java.util.*;

import javax.swing.plaf.synth.SynthStyle;

public class CommandSystem {
    private GameState state;

    // ArrayList to store all of the verbs that the game knows about. (This is a
    // parallel array with the verbDescription arraylist)
    private List<String> verbs = new ArrayList<String>();

    // ArrayList of the descriptions for the verbs the game knows. (This is a
    // parallel array with the verbs arraylist)
    private List<String> verbDescription = new ArrayList<String>();

    // ArratList to store all of the nouns the game knows about.
    private List<String> nouns = new ArrayList<String>();

    /*
     * Constructor should defines all verbs that can be used in the commands and all
     * nouns the user can interact with.
     * 
     * Suggestion: These could all be loaded from a file.
     * 
     * Verb descriptions are stored in a parallel Arraylist with the Verbs and are
     * used when printing out the help menu (using the ? command).
     */
    public CommandSystem(GameState state) {
        this.state = state;
        // Assign verbs and descriptions here
        addVerb("walk", "You can walk in directions: foward, backward, left, right");
        addVerb("collect", "Use the collect term to take an item");
        addVerb("talk", "use the talk to command to interogate or talk to people");
        addVerb("look",
                "Use the look command by itself to look in your current area. \nYou can also look at a person or object by ntyping look and the name of what you want to look at.\nExample: look book");
        addVerb("quit", "Quit the game."); // NOTE: In the starter code, this is handeled by the client code - not the
        addVerb("?", "Show this help screen.");
        addVerb("open", "used to open something"); // CommandSystem.
    }

    // When a command is only one Verb this method controls the result.
    public void executeVerb(String verb) {
        switch (verb) {
            case "look": // will show the description of the current room (stored in the state object)
                System.out.println(state.currentLocation.description);
                break;
            case "walk":
                gameOutput("You must pick a direction to walk (foward, backward, left, right)");
                break;
            case "collect":
                gameOutput("You collect nothing");
                break;
            case "talk":
                gameOutput("You talk but you aren't talking to anyone");
            case "?":
                this.printHelp();
                break;
        }
    }

    // When a command is a Verb followed by a noun, this method controls the result.
    public void executeVerbNoun(String verb, String noun) {

        /**
         * Initilize the string that we will use as a response text.
         * This method allows us to create a single string and just print it at the end
         * of the method.
         * You can do it any way you wish.
         */
        String resultString = "";

        switch (verb) { // Deciddes what to do based on each verb
            case "look":
                resultString = lookAt(noun);
                break;
            case "walk":
                resultString = walkTo(noun);
                break;
            case "talk":
                resultString = talkTo(noun);
                break;
            case "collect":
                System.out.print("You must specify the location and the item");
                break;
            case "open":
                System.out.println("Specify the location when trying to open something. (open, location, item)");
                break;
        }

        gameOutput(resultString);
    }

    // When a command is a Verb followed by two nouns, this method controls the
    // result.
    public void executeVerbNounNoun(String verb, String noun1, String noun2) {
        switch (verb) {
            case "collect":
                switch (noun1) {
                    case "bathroom":
                        switch (noun2) {
                            case "note":
                                collectItem(noun2);
                                break;
                            default:
                                System.out.println("Not a valid item");
                                break;
                        }
                        break;
                    case "richardbedroom":
                        switch (noun2) {
                            case "letter":
                                collectItem(noun2);
                                break;
                            default:
                                System.out.println("Not a valid item");
                                break;
                        }
                        break;
                    case "livingroom":
                        switch (noun2) {
                            case "shard":
                                collectItem(noun2);
                                break;
                            default:
                                System.out.println("Not a valid item");
                                break;
                        }
                        break;
                    default:
                        System.out.println("There is nothing to collect in this room");
                        break;
                }
                break;
            case "open":
                switch (noun1) {
                    case "masterbedroom":
                        switch (noun2) {
                            case "safe":
                                openSafe(noun2);
                                break;
                            default:
                                System.out.println("Not a valid item");
                                break;
                        }
                        break;
                    default:
                        System.out.println("Cannot open any items in this location");
                        break;
                }
                break;
            default:
                System.out.println("Cannot execute using this command");
                break;
        }
    }

    public String walkTo(String direction) {
        String resultString = "";
        switch (direction) {
            case "forward":
                if ((state.row - 1 < 0) || (state.row - 1 >= 2)) {
                    System.out.println("Can't go here");
                } else {
                    state.row--;
                    state.currentLocation = state.grid[state.row][state.col];
                    System.out.println(state.currentLocation.here);
                }
                break;
            case "left":
                if ((state.col - 1 < 0) || (state.col - 1 >= 3)) {
                    System.out.println("Can't go here");
                } else {
                    state.col--;
                    state.currentLocation = state.grid[state.row][state.col];
                    System.out.println(state.currentLocation.here);
                }
                break;
            case "right":
                if ((state.col + 1 < 0) || (state.col + 1 >= 3)) {
                    System.out.println("Can't go here");
                } else {
                    state.col++;
                    state.currentLocation = state.grid[state.row][state.col];
                    System.out.println(state.currentLocation.here);
                }
                break;
            case "backward":
                if ((state.row + 1 < 0) || (state.row + 1 >= 2)) {
                    System.out.println("Can't go here");
                } else {
                    state.row++;
                    state.currentLocation = state.grid[state.row][state.col];
                    System.out.println(state.currentLocation.here);
                }
                break;
            default:
                System.out.println("Specify direction!");
                break;

        }
        return resultString;
    }

    public String openSafe(String item) {
        Scanner console = new Scanner(System.in);
        String result = "";
        int quit = 999;
        int code;
        if (state.currentLocation == state.grid[0][0]) {
            System.out.println("Enter an 8-digit code, 999 to quit");
            while (!console.hasNextInt()) {
                System.out.println("Not a valid entry, enter again: ");
                console.nextLine();
            }
            code = console.nextInt();
            while (code != quit) {
                if (code == state.safecode) {
                    System.out.println(
                            "By using all the clues, you manage to open the safe (code: 10211225). Inside the safe are two knives, one seems to be a kitchen knife and the other is a dagger with an engraving of \"Joseph\". You determine that all three of them are culpable for the murder of Mr. and Mrs. Dade.");
                    System.out.println("You return to the police station with the three suspects in custody.");
                    System.out.println("You won the game!");
                    state.safeEnd = 1;
                    break;
                } else {
                    System.out.println("Code incorrect");
                    System.out.println("Enter an 8-digit code, 999 to quit: ");
                    code = console.nextInt();
                    state.safeEnd = 0;
                }
            }
        } else {
            System.out.println("You dont see a safe");
        }
        return result;
    }

    public String talkTo(String person) {
        String resultString = "";
        switch (person) {
            case "richard":
                System.out.println(state.frontHouse.Entitieshere[0].description);
                break;
            case "joseph":
                System.out.println(state.frontHouse.Entitieshere[1].description);
                break;
            case "unclebob":
                System.out.println(state.frontHouse.Entitieshere[2].description);
                break;
            default:
                System.out.println("Talk to someone else!");
                break;
        }
        return resultString;
    }

    public String collectItem(String item) {
        String resultString = "";
        switch (item) {
            case "shard":
                if (state.currentLocation == state.grid[1][2]) {
                    state.player.itemscollected[0] = state.livingroom.itemsHere[1];
                    System.out.println("You collected the " + state.player.itemscollected[0].name);
                    state.livingroom.itemsHere[0] = null;
                    state.livingroom.description = "Uncle Bob slept in the living room when he stayed over. There is a glass shard on the couch. You collected the shard";
                    state.itemscollected++;
                } else {
                    System.out.println("You do not see the shard");
                }
                break;
            case "letter":
                if (state.currentLocation == state.grid[0][2]) {
                    state.player.itemscollected[1] = state.richardbedroom.itemsHere[0];
                    System.out.println("You collected the " + state.player.itemscollected[1].name);
                    state.richardbedroom.itemsHere[0] = null;
                    state.richardbedroom.description = "Richard's room is incredibly dirty. His walls are tearing down and he has clothes and papers all over. The letter is now in your inventory.";
                    state.itemscollected++;
                } else {
                    System.out.println("You don't see the letter");
                }
                break;
            case "note":
                if (state.currentLocation == state.grid[1][0]) {
                    state.player.itemscollected[2] = state.bathroom.itemsHere[3];
                    System.out.println("You collected the " + state.player.itemscollected[2].name);
                    state.bathroom.itemsHere[3] = null;
                    state.bathroom.description = "The bathroom is oddly damaged. The mirror, toilet, cabinets, and showers are damaged. It is unknown what happened here. You collected the ripped piece of note paper that was on the toilet seat.";
                    state.itemscollected++;
                } else {
                    System.out.println("You do not see the note");
                }
                break;
            default:
                break;
        }

        return resultString;
    }

    // Method to take care of looking at a noun.
    public String lookAt(String noun) {
        // This will be what is returned by the method.
        String resultString = "";

        switch (noun) { // for the given verb, decide what to do based on what noun was entered
            case "letter":
                if (state.currentLocation == state.grid[0][2]) {
                    System.out.println(state.richardbedroom.itemsHere[0].description);
                } else {
                    System.out.println("You don't see the letter");
                }
                // get the description from the item you are looking at.
                // resultString += state.mat.description;
                break;
            case "safe":
                if (state.currentLocation == state.grid[0][0]) {
                    System.out.println(state.masterbedroom.itemsHere[2].description);
                } else {
                    System.out.println("You don't see the safe");
                }
                break;
            case "crimereport":
                if (state.currentLocation == state.grid[0][1]) {
                    System.out.println(state.kitchen.itemsHere[1].description);
                } else {
                    System.out.println("You don't see the crime report");
                }
                break;
            case "note":
                if (state.currentLocation == state.grid[1][0]) {
                    System.out.println(state.bathroom.itemsHere[3].description);
                } else {
                    System.out.println("You don't see note");
                }
                break;
            case "shard":
                if (state.currentLocation == state.grid[1][2]) {
                    System.out.println(state.livingroom.itemsHere[1].description);
                } else {
                    System.out.println("You don't see a shard");
                }
                break;
            // You cound design a way to look at any item without having to specify how to
            // deal with each of them.
            // That way you can code special cases for some items, and others would just use
            // default behavior.
            // This is HIGHLY encouraged. (It will save time and headaches!)
            default:
                System.out.println("Must specify item and not a location");
                break;
        }
        return resultString;
    }

    /*****************************************************************
     * Helper methods to help system work.
     ******************************************************************/

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
            if (s2.length() == (DISPLAY_WIDTH / 2 - 10)) {
                s2 += " Commands ";
            } else {
                s2 += " ";
            }
        }

        System.out.println("\n\n" + s1 + "\n" + s2 + "\n" + s1 + "\n");
        for (String v : verbs) {
            // System.out.printp(v + " --> " + verbDescription.get(verbs.indexOf(v)));
            System.out.printf("%-8s  %s", v, formatMenuString(verbDescription.get(verbs.indexOf(v))));
        }
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
        if (addBrackets) {
            longstring = addNounBrackets(longstring);
        }
        if (formatWidth) {
            longstring = formatWidth(longstring);
        }

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

            // Get our next word in the string.
            String next = chop.next();

            // Add the legnth to our charLength.
            charLength += next.length() + 1;

            // Find and replace any special newline characters [nl] with \n.
            if (next.contains("[nl]")) {
                // Find the index after our [nl] characters.
                int secondHalf = next.indexOf("[nl]") + 4;

                // Set charLength to the number of characters after the [nl],
                // because that will be the beginnig of a new line.
                if (secondHalf < next.length()) {
                    charLength = secondHalf;
                } else {
                    charLength = 0;
                    addSpace = false; // Do not add space after if this ended with a newline character.
                }

                // Now actually replace the [nl] with the newline character
                next = next.replace("[nl]", "\n");

            }

            // Add the word to the result.
            result += next;

            // Only add a space if our special case did not happen.
            if (addSpace)
                result += " ";

            // Normally we add a space after a word, prepare for that.
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
    // lets the command system know this is something you an interact with.
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
