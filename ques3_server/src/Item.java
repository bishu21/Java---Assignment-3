public class Item extends Thread implements Runnable{

    public String type;
    public int quantity;
    public int cost;

    public int tea_price = 5;
    public int coffee_price = 8;
    public int snacks_price = 10;
    public int cookies_price = 6;
    public int pkg_food_price = 30;

    public String result;
    public int time_req = 0;

    public Item(String type,int quantity)
    {
        this.type = type;
        this.quantity = quantity;
        this.cost = 0;
    }

    // each item we run a thread
    // from each item we each item we run a thread
    // if availability of this thread quantity then get it from the seller else go to second item of the order


    @Override
    public void run()
    {

        System.out.println("Item ordered : "+this.type+" quantity : "+String.valueOf(quantity));

        if(Thread.currentThread()!= ItemQueue.getInstance().geti(this,0)) {
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //get the estimated time of arrival

        if(this.type == "tea"||this.type == "coffee")
        {
            time_req = quantity;
            if(this.type == "tea"){
                result = "quantity : "+String.valueOf(this.quantity)+" Cost : 5x"+String.valueOf(this.quantity)+" "+String.valueOf(5*this.quantity);
                cost = 5*quantity;
            }
            else
            {
                result = "quantity : "+String.valueOf(this.quantity)+" Cost : 8x"+String.valueOf(this.quantity)+" "+String.valueOf(8*this.quantity);
                cost = 8*quantity;
            }

        }
        else if(this.type == "snacks")
        {
            int stat=0;
            if(this.quantity>1)
                stat = Stock.getInstance().consume_snacks(this.quantity);
            else
                result = "none ordered";

            if(stat == -1)
            {
                result = "not available";
                this.quantity = 0;
            }

            else
            {
                result = "available";
                result = "quantity : "+String.valueOf(this.quantity)+" Cost : 10x"+String.valueOf(this.quantity)+" "+String.valueOf(10*this.quantity);
                cost = 10*quantity;
            }
        }
        else if(this.type == "cookies")
        {
            int stat=0;
            if(this.quantity>1)
                stat = Stock.getInstance().consume_cookies(this.quantity);
            else
                result = "none ordered";

            if(stat == -1){
                result = "not available";
                this.quantity = 0;
            }

            else{
                result = "available";
                result = "quantity : "+String.valueOf(this.quantity)+" Cost : 6x"+String.valueOf(this.quantity)+" "+String.valueOf(6*this.quantity);
                cost = 6*quantity;
            }

        }
        else if(this.type == "pkg_food")
        {
            int stat=0;
            if(this.quantity>1)
                stat = Stock.getInstance().consume_pkg_food(this.quantity);
            else
                result = "none ordered";

            if(stat == -1)
            {
                result = "not available";
                this.quantity = 0;
            }
            else{
                result = "available";
                result = "quantity : "+String.valueOf(this.quantity)+" Cost : 30x"+String.valueOf(this.quantity)+" "+String.valueOf(30*this.quantity);
                cost = 30*quantity;
            }

        }

        //after process

        ItemQueue.getInstance().pop(this);
        if(!ItemQueue.getInstance().isEmpty(this))
            if(ItemQueue.getInstance().geti(this,0).getState() == State.TIMED_WAITING)
                ItemQueue.getInstance().geti(this,0).interrupt();



    }

}
