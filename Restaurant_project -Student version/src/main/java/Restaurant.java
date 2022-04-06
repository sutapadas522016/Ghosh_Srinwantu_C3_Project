import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Restaurant {
    private String name;
    private String location;
    public LocalTime openingTime;
    public LocalTime closingTime;
    private List<Item> menu = new ArrayList<>();

    public Restaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public boolean isRestaurantOpen() {
        //return true;
        //DELETE ABOVE STATEMENT AND WRITE CODE HERE
        LocalTime now = LocalTime.now();
        System.out.println("Current Time: " + now);

        if (now.isAfter(openingTime) && now.isBefore(closingTime)){
            return true;
        }
        else
        {
            return false;
        }
    }

    public LocalTime getCurrentTime()
    {
        return  LocalTime.now();
    }

    public List<Item> getMenu() {
        //return null;
        //DELETE ABOVE RETURN STATEMENT AND WRITE CODE HERE
        //List list = new ArrayList<>();
        //return menu;
        return Collections.unmodifiableList(this.menu);
    }

    private Item findItemByName(String itemName){
        for(Item item: menu) {
            if(item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    public void addToMenu(String name, int price) {
        Item newItem = new Item(name,price);
        menu.add(newItem);
    }
    
    public void removeFromMenu(String itemName) throws itemNotFoundException {

        Item itemToBeRemoved = findItemByName(itemName);
        if (itemToBeRemoved == null)
            throw new itemNotFoundException(itemName);

        menu.remove(itemToBeRemoved);
    }
    public void displayDetails(){
        System.out.println("Restaurant:"+ name + "\n"
                +"Location:"+ location + "\n"
                +"Opening time:"+ openingTime +"\n"
                +"Closing time:"+ closingTime +"\n"
                +"Menu:"+"\n"+getMenu());
    }

    public String getName() {
        return name;
    }

    public int displayTotalOrder(List<String> itemsInOrder){
        int totalOrder =0;
        for (String itemName : itemsInOrder){
            Item item = this.findItemByName(itemName);
            totalOrder = totalOrder + item.getPrice();
        }
        return totalOrder;
    }

    public void displayOrderCost(List<String> itemsInOrder){
        System.out.println("Your order will cost Rs." + displayTotalOrder(itemsInOrder));
    }
}
