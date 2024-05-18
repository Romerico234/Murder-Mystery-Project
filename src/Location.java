import java.util.ArrayList;
import java.util.List;

public class Location extends Entities {
    private List<Item> items = new ArrayList<>();
    private List<Person> characters = new ArrayList<>();
    private String initialDesc; // Store the initial description

    public Location(String name, String baseDescription) {
        super(name, baseDescription);
        this.initialDesc = baseDescription;
        updateDescription();
    }

    public void addItem(Item item) {
        items.add(item);
        updateDescription();
    }

    public void removeItem(Item item) {
        items.remove(item);
        updateDescription();
    }

    public List<Item> getItems() {
        return new ArrayList<>(items); // Return a copy to encapsulate the internal list
    }

    public void addCharacter(Person character) {
        characters.add(character);
    }

    public List<Person> getCharacters() {
        return new ArrayList<>(characters); // Return a copy to encapsulate the internal list
    }

    private void updateDescription() {
        String desc = initialDesc + "\nItems in this location: ";
        if (!items.isEmpty()) {
            for (Item item : items) 
                desc += item.getName() + ", ";
            // Remove the last comma and space
            desc = desc.substring(0, desc.length() - 2);
        } else 
            desc += "None";
        setDescription(desc);
    }
}
