import java.util.ArrayList;
import java.util.List;

public class OrderQueue {

    public List<Order> orders;

    public static OrderQueue instance;

    // single instance class is used

    public static OrderQueue getInstance()
    {
        if(instance == null)
            instance = new OrderQueue();
        return instance;
    }

    // maintain the order in queue as given in the question that follow FCFS procedure

    public OrderQueue()
    {
        orders = new ArrayList<>();
    }

    public void push_back(Order order)
    {
        orders.add(order);
    }

    public void pop()
    {
        orders.remove(0);
    }

}
