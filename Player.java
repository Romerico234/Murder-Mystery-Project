public class Player {
    String name;
    String description;
    Item[] itemscollected = new Item[3]; 

    public Player() {
    };

    public Player(String n, String d) {
        name = n;
        description = d;
    }

    public String getDesc() {
        return description;
    }
    public String printInventory(){
        return "collected";
    }
}