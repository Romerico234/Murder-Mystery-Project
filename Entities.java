public class Entities {
    String name;
    String description;
    Item[] itemscollected = new Item[5]; 

    public Entities() {
    };

    public Entities(String n, String d) {
        name = n;
        description = d;
    }

    public String getDesc() {
        return description;
    }
}