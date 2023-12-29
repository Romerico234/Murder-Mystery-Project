/*
App.java
Final project for COSC 236.
Based on starter code first developed by Prof. Dastyni Loksa

This is the driver class of the project. It is in charge of the following:

- Setting up the game by creating the GameState.
- The main game loop deciding when the game quits.
- The input from the user and routing to the correct methods in the CommandSystem.

*/

import java.util.*;
import java.lang.Thread;

public class App {
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) throws Exception {

        // Create needed objects for the game.
        GameState state = new GameState();
        // Store the command system for easy reference in the client code.
        CommandSystem commandSystem = state.commandSystem;

        // This controls if the game should continue running.
        boolean gameRunning = true;
        System.out.println(
                "At 12:00 AM, you receive an emergency call that Mr. and Mrs. Dade have been found dead. The cause of death was a slit to their throats indicating a homicide. Upon arriving at the scene, you are tasked with gathering evidence and series of clues.");
        System.out.println("You are currently at the front of the house");
        // The main game loop.
        while (gameRunning) {
            if (state.itemscollected == 3) {
                System.out.println(
                        "You have collected all the known items used in the murder. You return to the police station.");
                System.out.println("You have won the game!");
                Thread.sleep(15000);
                System.exit(1);
            }
            if (state.safeEnd == 1) {
                Thread.sleep(15000);
                System.exit(1);
            }

            // Gets input from the user in an array of strings that they typed in.
            String[] input = getCommand();

            if (input.length < 1) {
                System.out.println("Unknown command. Type ? for help.");

            } else if (input[0].equalsIgnoreCase("quit")) {
                System.out.println("Goodbye.");
                gameRunning = false;
                in.close();

                // Command has 1 word - Check if it is a valid verb and execute it.
            } else if (input.length == 1 && commandSystem.hasVerb(input[0])) {
                commandSystem.executeVerb(input[0]);

                // Command has 2 words - should be verb and noun.
            } else if (input.length == 2) {
                // Validate that the commands are known verb/nouns
                if (!commandSystem.hasVerb(input[0])) {
                    unknownCommand(input[0]);
                } else if (!commandSystem.hasNoun(input[1])) {
                    unknownCommand(input[1]);
                } else {
                    // Run command
                    commandSystem.executeVerbNoun(input[0], input[1]);
                }

                // command has 3 words - should be verb noun noun
            } else if (input.length == 3) {
                // Validate that the commands are known verb/nouns
                if (!commandSystem.hasVerb(input[0])) {
                    unknownCommand(input[0]);
                } else if (!commandSystem.hasNoun(input[1])) {
                    unknownCommand(input[1]);
                } else if (!commandSystem.hasNoun(input[2])) {
                    unknownCommand(input[2]);
                } else {
                    // Run command
                    commandSystem.executeVerbNounNoun(input[0], input[1], input[2]);
                }

                // Deal with any possible unknown structure/command
            } else {
                if (input.length > 1) {
                    String userInput = "";

                    for (String s : input)
                        userInput += s + " ";

                    unknownCommand(userInput);

                } else {
                    unknownCommand(input[0]);
                }
            }
        }
    }

    // Gets input from the user
    // seperates the input into each word (determined by whitespace)
    // returns an array with each word an element of the array.
    public static String[] getCommand() {

        in = new Scanner(System.in);
        System.out.println("\n------------------------------");
        System.out.print("What would you like to do? >  ");
        String input = in.nextLine();
        System.out.println();
        return input.toLowerCase().split("\\s+");
    }

    // Used to let the user know that what they typed as a command is not
    // understood.
    public static void unknownCommand(String input) {
        if (Math.random() < .3) // A random chance for a silly response.
            System.out.println("Don't be silly. Everyone knows '" + input + "' is not a command! Type ? for help.");
        else
            System.out.println("I don't understand '" + input + "'. Type ? for help.");
    }

}