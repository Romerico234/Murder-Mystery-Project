import java.util.ArrayList;
import java.util.List;

public class Person extends Entities {
    private Location location;
    private List<Item> inventory = new ArrayList<>();

    public Person(String name) {
        super(name, null);
    }

    public Person(String name, String description){
        super(name, description);
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void showInventory() {
        System.out.println("Inventory:");
        for (Item item : inventory) {
            System.out.println("- " + item.getName());
        }
    }

    public List<Item> getInventory() {
        return new ArrayList<>(inventory); // Return a copy to encapsulate the internal list
    }
}
