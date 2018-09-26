import java.util.ArrayList;

public class ItemQueue {

    public ArrayList<Item> items1;
    public ArrayList<Item> items2;
    public ArrayList<Item> items3;
    public ArrayList<Item> items4;
    public ArrayList<Item> items5;



    public static ItemQueue instance;
 /// single instance class
    public static ItemQueue getInstance()
    {
        if(instance == null)
        {
            instance = new ItemQueue();
        }
        return instance;
    }
 // for each item we made a array list

    // the item from the seller if available
    private ItemQueue()
    {
        items1 = new ArrayList<>();
        items2 = new ArrayList<>();
        items3 = new ArrayList<>();
        items4 = new ArrayList<>();
        items5 = new ArrayList<>();
    }

    public void add_item(Item item)
    {
        if(item.type == "tea")
            items1.add(item);
        if(item.type == "coffee")
            items2.add(item);
        if(item.type == "snacks")
            items3.add(item);
        if(item.type == "cookies")
            items4.add(item);
        if(item.type == "pkg_food")
            items5.add(item);

    }

    public void pop(Item item)
    {
        if(item.type == "tea")
            items1.remove(item);
        if(item.type == "coffee")
            items2.remove(item);
        if(item.type == "snacks")
            items3.remove(item);
        if(item.type == "cookies")
            items4.remove(item);
        if(item.type == "pkg_food")
            items5.remove(item);
    }

    public Item geti(Item item,int i)
    {
        if(item.type == "tea")
            return items1.get(i);
        if(item.type == "coffee")
            return items2.get(i);
        if(item.type == "snacks")
            return items3.get(i);
        if(item.type == "cookies")
            return items4.get(i);
        if(item.type == "pkg_food")
            return items5.get(i);

        return null;
    }

    public boolean isEmpty(Item item)
    {
        if(item.type == "tea")
            return items1.isEmpty();
        if(item.type == "coffee")
            return items2.isEmpty();
        if(item.type == "snacks")
            return items3.isEmpty();
        if(item.type == "cookies")
            return items4.isEmpty();
        if(item.type == "pkg_food")
            return items5.isEmpty();

        return true;
    }

}
