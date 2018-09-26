import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Order extends Thread implements Runnable {


    // each order we run a thread
    // from each order we each item we run a thread
    // if availability of this thread quantity then get it from the seller else go to second item of the order

    public int tea;
    public int coffee;
    public int snacks;
    public int cookies;
    public int pkg_food;
    public int time;
    public String name;
    public String result="";
    public Calendar et;
    public int total_cost = 0;

    public Order(String input)
    {
        String[] params = input.split(",");
        name = params[0];
        tea = Integer.valueOf(params[1]);
        coffee = Integer.valueOf(params[2]);
        snacks = Integer.valueOf(params[3]);
        cookies = Integer.valueOf(params[4]);
        pkg_food = Integer.valueOf(params[5]);
    }

    public void run()
    {
        System.out.println("New order received ");
        if (this!= OrderQueue.getInstance().orders.get(0)) {
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //execute order here
        //push item to queue

        Item t_tea=null,t_coffee=null,t_snacks=null,t_cookies=null,t_pkg_food=null;

        if(tea > 0)
        {
             t_tea = new Item("tea",tea);
            ItemQueue.getInstance().add_item(t_tea);
            t_tea.start();


            try {
                t_tea.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(coffee> 0)
        {
             t_coffee = new Item("coffee",coffee);
            ItemQueue.getInstance().add_item(t_coffee);
            t_coffee.start();


            try {
                t_coffee.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(snacks > 0)
        {
             t_snacks = new Item("snacks",snacks);
            ItemQueue.getInstance().add_item(t_snacks);
            t_snacks.start();


            try {
                t_snacks.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(cookies > 0)
        {
             t_cookies = new Item("cookies",cookies);
            ItemQueue.getInstance().add_item(t_cookies);
            t_cookies.start();


            try {
                t_cookies.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(pkg_food > 0)
        {
             t_pkg_food = new Item("pkg_food",pkg_food);
            ItemQueue.getInstance().add_item(t_pkg_food);
            t_pkg_food.start();


            try {
                t_pkg_food.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        result = "";
         time = 0;
        if(t_tea!=null)
        {
            time = t_tea.time_req;
            total_cost+=t_tea.cost;
            result+="Tea :            "+t_tea.result+"<br>";
        }
        if(t_coffee!=null)
        {
            total_cost+=t_coffee.cost;
            time = Math.max(time,t_coffee.time_req);
            result+="coffee :         "+t_coffee.result+"<br>";

        }

        time+=2;

        if(t_snacks!=null)
        {
            total_cost+=t_snacks.cost;
            result +="Snacks :        "+t_snacks.result+"<br>";
        }
        if(t_cookies!=null)
        {
            total_cost+=t_cookies.cost;
            result +="Cookies :       "+t_cookies.result+"<br>";
        }
        if(t_pkg_food!=null)
        {
            total_cost+=t_pkg_food.cost;
            result +="Packaged food : "+t_pkg_food.result+"<br>";
        }
        //calculate time here

        Calendar pres = Calendar.getInstance();
        Calendar sys = Server.date;

        if(pres.compareTo(sys)>0)
        {
            long t = pres.getTimeInMillis();
            Date d = new Date(t+(time*60000));
            pres.setTime(d);
            Server.date = pres;
        }
        else
        {
            long t = sys.getTimeInMillis();
            Date d = new Date(t+(time*60000));
            pres.setTime(d);
            Server.date = pres;
        }

        // here we noted the date of completion of order beacause we required these thing in the invoices
        et = Server.date;
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(et.getTime());


        String invoice = name+","+String.valueOf(this.tea)+","+String.valueOf(this.coffee)+","+String.valueOf(this.snacks)+","+String.valueOf(this.cookies)+","+String.valueOf(this.pkg_food)+","+date;
        order_item o = new order_item(invoice);
        try {
            Admin.getInstance().addorder(o);
        } catch (IOException e) {
            e.printStackTrace();
        }

        result+="Total Cost:                               "+String.valueOf(total_cost)+"<br>";
        OrderQueue.getInstance().pop();
        if(!OrderQueue.getInstance().orders.isEmpty())
            if(OrderQueue.getInstance().orders.get(0).getState() == State.TIMED_WAITING)
                OrderQueue.getInstance().orders.get(0).interrupt();

        //send the invoice to the customer with expected time of the delivery

    }

}
