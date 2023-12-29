/*
GameState.java
For use in the Final project for COSC 236.
Based on starter code first developed by Prof. Dastyni Loksa

This is the class to hold the state of the running game and allows easy
passing of important information to methods that require data from the
state of the game.

This starter code is designed for the verbs to be stored in the commandSystem.

*/

public class GameState {
    Location frontHouse;
    CommandSystem commandSystem;
    Location masterbedroom;
    Location richardbedroom;
    Location kitchen;
    Location bathroom;
    Location livingroom;
    Location currentLocation;
    Player player;
    int row = 1;
    int col = 1;
    int itemscollected;
    int safeEnd = 0;
    int safecode = 10211225;

    Location[][] grid = new Location[2][3];

    public static int DISPLAY_WIDTH = 100;

    /*
     * GameState Constructor
     * 
     * Ideally, your game will be fully loaded and ready to play once this
     * constructor has finished running.
     * 
     * How things have been done here are just a rudementry setup to link the other
     * classes and have the
     * bare bones example of the command system. This is not a great way to
     * initilize your project.
     * 
     * You should do better!
     */
    public GameState() {
        commandSystem = new CommandSystem(this);
        currentLocation = new Location();
        grid[1][1] = currentLocation;
        currentLocation.description = "You are at the front of the house. Around you are the police and the three people inside the house when the crime occurred. They are Richard Jr. (Mr. and Mrs. Dade's only child), Joseph (Richard's older friend), and Uncle Bob (Mr. Dade's brother). What would you like to do?";

        // Create first (starting) location
        // (and store it in currentLocation so I can always referece where the player is
        // easily)

        richardbedroom = new Location();
        richardbedroom.name = "richardbedroom";
        richardbedroom.here = "You are in Richard's bedroom";
        richardbedroom.description = "Richard's room is incredibly dirty. His walls are tearing down and he has clothes and papers all over. You notice a letter resting on his cluttered desk.";
        commandSystem.addNoun("richardbedroom");
        grid[0][2] = richardbedroom;

        kitchen = new Location();
        kitchen.name = "kitchen";
        kitchen.here = "You are in the kitchen";
        kitchen.description = "They have a small kitchen with dull knives and utensils; however, it seems like one of the knives is missing. On the table is the crime report";
        commandSystem.addNoun("kitchen");
        grid[0][1] = kitchen;

        livingroom = new Location();
        livingroom.name = "livingroom";
        livingroom.here = "You are in the living room";
        livingroom.description = "Uncle Bob slept in the living room when he stayed over. There is a glass shard on the couch.";
        commandSystem.addNoun("livingroom");
        grid[1][2] = livingroom;

        frontHouse = new Location();
        frontHouse.name = "fronthouse";
        frontHouse.here = "You are in the front of the house";
        frontHouse.description = "You are at the front of the house. Around you are the police and the three people inside the house when the crime occurred. They are Richard Jr. (Mr. and Mrs. Dade's only child), Joseph (Richard's older friend), and Uncle Bob (Mr. Dade's brother). What would you like to do?";
        commandSystem.addNoun("fronthouse");
        grid[1][1] = frontHouse;

        masterbedroom = new Location();
        masterbedroom.name = "masterbedroom";
        masterbedroom.here = "You are in the master bedroom";
        masterbedroom.description = "There are blood stains and a pecuilar key on the bed sheets. As you explore the room, you stumble by Mrs. Dade's wardrobe. You excavate her closet in search of clues and interestingly find a safe. You are unable to open to safe and it seems it requires an 8-digit code.";
        commandSystem.addNoun("masterbedroom");
        grid[0][0] = masterbedroom;

        bathroom = new Location();
        bathroom.name = "bathroom";
        bathroom.here = "You are in the bathroom";
        bathroom.description = "The bathroom is oddly damaged. The mirror, toilet, cabinets, and showers are damaged. It is unknown what happened here. You notice a ripped piece of note paper on the toilet seat.";
        commandSystem.addNoun("bathroom");
        grid[1][0] = bathroom;

        // Create a demo item.
        Item letter = new Item(); // There is an error here. Remember how variable scope works and fix this.
        letter.name = "letter";
        letter.description = "The letter was written by Uncle Bob to Richards and says:\nAye, I need you to do a \nlil favor for me. Yo\npops been pressing me about getting a job so I got a\nhiree training session this\nafternoon. I need you to do my laundry while Im out. Ill really appreciate it\nbuddy. Ill get you youre favorite\nensaimadas when I get home.\nThanks again";
        richardbedroom.itemsHere[0] = letter;

        Item crimereport = new Item(); // There is an error here. Remember how variable scope works and fix this.
        crimereport.name = "crimereport";
        crimereport.description = "The Dade family lives in a one-story house. You just came from the front of the house and are currently in the kitchen. On your left is Mr. and Mrs. Dade's bedroom (masterbedroom). On your right is Richard's bedroom (richardbedroom). Behind you on your left is the bathroom. Behind you on your right is the living room. Mr. and Mrs. Dade were unfortunately found dead lying asleep in their bedroom.";
        kitchen.itemsHere[1] = crimereport;

        Item safe = new Item(); // There is an error here. Remember how variable scope works and fix this.
        safe.name = "safe";
        safe.description = "The safe requires an 8-digit code to unlock, specify the location when trying to open the safe";
        masterbedroom.itemsHere[2] = safe;

        Item shard = new Item();
        shard.name = "shard";
        shard.description = "Piece of glass from the broken mirror in the bathroom";
        livingroom.itemsHere[1] = shard;

        Item note = new Item();
        note.name = "note";
        note.description = "You look at the ripped note and notice that it says \"Month\".";
        bathroom.itemsHere[3] = note;

        Entities richard = new Entities();
        richard.name = "richard";
        richard.description = "Before you even attempt to interrogate Richard, he streams tears saying, \"I don't know what happened, I'm sorry.\" Richard has a troubled history with his parents, having continuously argued and fought them over even the minuscule things. However, it seems like he is unbelievably devasted by the death of his parents. You allow him time to console.\nDescription:\nBirthday: July 30th 2007 \nAge: 16\nHeight: 5'10\"\nWeight: 160lbs\nFeet Size: 12";
        frontHouse.Entitieshere[0] = richard;

        Entities joseph = new Entities();
        joseph.name = "joseph";
        joseph.description = "Joseph stared at the scene with utter bewilderment. He is Richard's friend. He was unliked by his parents for being a negative influence. You ask him for any intel on the murder and he responds by saying \"I have no idea how this happened. Richard never told me anything.\"\nBirthday: January 22nd, 2005\nAge: 18 years old\nHeight: 6'0\"\nWeight: 175lbs\nFeet Size: 13\"\nHas been to Juvie for countless fights in school.";
        frontHouse.Entitieshere[1] = joseph;

        Entities unclebob = new Entities();
        unclebob.name = "unclebob";
        unclebob.description = "You try to query Uncle Bob about the events that unfolded but he is expressionless. It seems like he is still processing the events that occurred in such a short period of time. He has been job-hopping for two years now and has frequently been reliant on his brother's care for many years now.\nDescription: \nBirthday: February 3rd, 1975\nAge: 48 years old\nHeight: 5'11\"\nWeight: 170lbs\nFeet Size: 12\"\nMartial Status: Unmarried";
        frontHouse.Entitieshere[2] = unclebob;

        player = new Player();

        // Add item to list of nouns so our command system knows it exists.
        commandSystem.addNoun(letter.name);
        commandSystem.addNoun(crimereport.name);
        commandSystem.addNoun(safe.name);
        commandSystem.addNoun("forward");
        commandSystem.addNoun("left");
        commandSystem.addNoun("right");
        commandSystem.addNoun("backward");
        commandSystem.addNoun("talk");
        commandSystem.addNoun("richard");
        commandSystem.addNoun("unclebob");
        commandSystem.addNoun("joseph");
        commandSystem.addNoun("shard");
        commandSystem.addNoun("open");
        commandSystem.addNoun("note");
        /*
         * Once the commandSystem knows about the item, we need to code what happens
         * with each of the commands that can happen with the item.
         * See CommandSystem line 96 for what happens if you currently "look mat"
         */
    }
}
