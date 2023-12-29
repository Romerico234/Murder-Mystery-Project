import java.util.*;

public class Location {
    // State of the location object
    String name;
    String description;
    String here;
    Item item;
    Entities[] Entitieshere = new Entities[5];

    // The arrays could be ArrayLists for ease of use - look them up and learn how
    // you can use them.
    Item[] itemsHere = new Item[4]; // to hold all of the items in this location.

    Location[] exits; // to hold all of the locations that you can get to from this location.

    public Location() {
    };

    public Item getItem(String objectName) {
        // Find the item in itemsHere and return it.
        return itemsHere[0]; // this is not correct.
    }
}
