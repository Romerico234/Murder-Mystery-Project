import java.util.*;

public class App {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println(
        "At 12:00 AM, you arrive at a crime scene. Mr. and Mrs. Dade have been found dead, their throats slit, causing fatal blood loss and lack of oxygen. Your task is to gather evidence and follow a series of clues.");
        initializeGame();
        System.out.println("Game over. Thanks for playing!");
    }

    private static void initializeGame() {
        // Create GameState
        GameState gameState = new GameState(true, 2, 3);

        // Create locations
        Location frontHouse = initializeFrontHouse();
        Location masterBedroom = initializeMasterBedroom();
        Location richardBedroom = initializeRichardBedroom();
        Location kitchen = initializeKitchen();
        Location bathroom = initializeBathroom();
        Location livingRoom = initializeLivingRoom();

        // Add locations to grid
        gameState.setLocationInGrid(1, 1, frontHouse);
        gameState.setLocationInGrid(0, 0, masterBedroom);
        gameState.setLocationInGrid(0, 2, richardBedroom);
        gameState.setLocationInGrid(0, 1, kitchen);
        gameState.setLocationInGrid(1, 0, bathroom);
        gameState.setLocationInGrid(1, 2, livingRoom);

        // Add items to locations
        masterBedroom.addItem(initializeSafe());
        richardBedroom.addItem(initializeLetter());
        kitchen.addItem(initializeCrimeReport());
        bathroom.addItem(initializeNote());
        livingRoom.addItem(initializeChessBoard());

        // Create player
        Person player = new Person("Detective");

        // Create in-game characters
        Person richard = initializeRichard();
        Person joseph = initializeJoseph();
        Person uncle = initializeUncleBob();

        // Add characters to locations
        frontHouse.addCharacter(richard);
        frontHouse.addCharacter(joseph);
        frontHouse.addCharacter(uncle);

        // Set player initial location
        gameState.setCurrentLocation(frontHouse);

        // Create CommandSystem
        CommandSystem commandSystem = new CommandSystem(gameState, player);

        // Start game loop
        startGameLoop(commandSystem, player);
    }

    private static void startGameLoop(CommandSystem commandSystem, Person player) {
        GameState gameState = commandSystem.getGameState();
        while (gameState.isRunning()) {
            // Gets input from the user in an array of strings that they typed in.
            String[] input = getCommand();

            if (input.length < 1) 
                System.out.println("Unknown command. Type ? for help.");
            else if (input.length == 1 && commandSystem.hasVerb(input[0])) {
                // Command has 1 word - Check if it is a valid verb and execute it.
                commandSystem.executeVerb(input[0]);
            } else if (input.length == 2) {
                // Command has 2 words - should be verb and noun.
                // Validate that the commands are known verb/nouns
                if (!commandSystem.hasVerb(input[0])) 
                    unknownCommand(input[0]);
                else if (!commandSystem.hasNoun(input[1])) 
                    unknownCommand(input[1]);
                else 
                    // Run command
                    commandSystem.executeVerbNoun(input[0], input[1]);
            } else if (input.length == 3) {
                // command has 3 words - should be verb noun noun
                // Validate that the commands are known verb/nouns
                if (!commandSystem.hasVerb(input[0])) 
                    unknownCommand(input[0]);
                else if (!commandSystem.hasNoun(input[1])) 
                    unknownCommand(input[1]);
                else if (!commandSystem.hasNoun(input[2])) 
                    unknownCommand(input[2]);
                else 
                    // Run command
                    commandSystem.executeVerbNounNoun(input[0], input[1], input[2]);
            } else {
                // Deal with any possible unknown structure/command
                if (input.length > 1) {
                    String userInput = "";

                    for (String s : input)
                        userInput += s + " ";
                    unknownCommand(userInput);
                } else 
                    unknownCommand(input[0]);
            }
        }
    }

    private static Location initializeFrontHouse() {
        return new Location("FrontHouse",
                "You are at the front of the house. Around are the police and the 3 people at the time of the crime. They are Richard Jr. (Mr. and Mrs. Dade's son), Joseph (Richard's older friend), and Uncle Bob (Mr. Dade's brother).");
    }

    private static Location initializeMasterBedroom() {
        return new Location("MasterBedroom",
                "There are blood stains and a key laying on the bed sheets. You stumble by Mrs. Dade's wardrobe and find a safe. The safe requires an 8-digit code.");
    }

    private static Location initializeRichardBedroom() {
        return new Location("RichardBedroom",
                "Richard's room is really dirty. His walls are tearing and has things everywhere. You notice a letter on his cluttered desk.");
    }

    private static Location initializeKitchen() {
        return new Location("Kitchen",
                "They kitchen's got the usual stuff. But, one of the knives is missing.");
    }

    private static Location initializeBathroom() {
        return new Location("Bathroom",
                "Nothing seems out of the ordinary in the bathroom.");
    }

    private static Location initializeLivingRoom() {
        return new Location("LivingRoom",
        "The living room has a worn-out sofa, a small coffee table, and a TV. Uncle Bob slept here when he stayed over.");
    }

    private static Item initializeSafe() {
        return new Item("Safe",
                "The safe requires an 8-digit code.");
    }

    private static Item initializeLetter() {
        return new Item("Letter",
                "The letter was written to Richards by Uncle Bob. It says: \naye i need you to do a \nlil favor for me. Yo\npops been pressing me about working so i \nhave to go job hunting this \nafternoon. do my laundry while im out \nbuddy. ill come back with ur favorite\nensaimadas\nthanks ");
    }

    private static Item initializeCrimeReport() {
        return new Item("CrimeReport",
                "The Dade family lives in a tiny one-story house. Mr. and Mrs. Dade were found dead in the master bedroom. Walk around to explore the house.");
    }

    private static Item initializeNote() {
        return new Item("Note", "The ripped note says: \"rth Month\".");
    }

    private static Item initializeChessBoard() {
        return new Item("ChessBoard", "A chess board lies on the coffle table. There are two pawns positioned on A1 and B2");
    }

    private static Person initializeRichard() {
        return new Person("Richard",
                "Richard streams tears saying, \"I don't know what happened, I'm sorry.\" Richard didn't have the best relationship with his parents and constantly argued. He seems really devastated by this event. You give him time to console. He was born on July 30th 2007, is 5'10\", weighs 160lbs, and has 11\" feet size");
    }

    private static Person initializeJoseph() {
        return new Person("Joseph",
                "Joseph stared with shock. He's Richard's older friend. His parents disliked him for being a bad influence to their son. You ask him regarding the murder but he responds by saying \"I have no idea how this happened. Richard never told me anything.\" He was born on January 22nd, 2005, is 6'0\"\n, weighs 175lbs, and has 13\" feet size. He has been to juvie countless times");
    }

    private static Person initializeUncleBob() {
        return new Person("UncleBob",
                "You try to ask Uncle Bob what happened but he's quiet. He seems like he's still processing what just occurred. He has been lazily searching for jobs and has relied on his brother for several years now. He was born on February 3rd, 1975, is 5'11\", weighs 170lbs, and has 12\" feet size. He is not married.");
    }

    /*****************************************************************
     * Helper methods to help system work.
     ******************************************************************/
    // Gets input from the user
    // separates the input into each word (determined by whitespace)
    // returns an array with each word an element of the array.
    public static String[] getCommand() {
        scanner = new Scanner(System.in);
        System.out.println("\n------------------------------");
        System.out.print("What would you like to do? >  ");
        String input = scanner.nextLine();
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
