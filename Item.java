import java.util.ArrayList;

/*
Item.java
For use in the Final project for COSC 236.
Based on starter code first developed by Prof. Dastyni Loksa

This class represents an item that can be interacted with by the player.  
*/
public class Item {
    String name;
    String description;
    ArrayList<String> status = new ArrayList<String>();

    public Item() {
    };

    public Item(String n, String d) {
        name = n;
        description = d;
    }

    public String getDesc() {
        return description;
    }
}
