public class Stock {

    public static Stock instance;

    // single instance class

    public static Stock getInstance()
    {
        if(instance == null)
        {
            instance = new Stock();
        }
        return instance;
    }

    public int cookies;
    public int snacks;
    public int pkg_food;

    // allocate the avaibility of particular item

    private Stock()
    {
        cookies = 100;
        snacks = 100;
        pkg_food = 100;
    }

// if quantity of particular stock less than by 10 than notify to the seller

    public synchronized int consume_snacks(int snacks)
    {
        if(this.snacks>=snacks) {
            this.snacks -= snacks;
            if (this.snacks < 10) {
                //notify
            }
            return snacks;
        }
        else
        {
            return -1;
        }

    }

    public synchronized int consume_cookies(int cookies)
    {
        if(this.cookies>=cookies) {
            this.cookies -= cookies;
            if (this.cookies < 10) {
                //notify
            }
            return cookies;
        }
        else
        {
            return -1;
        }
    }

    public synchronized int consume_pkg_food(int pkg_food)
    {
        if(this.pkg_food>=pkg_food) {
            this.pkg_food -= pkg_food;
            if (this.pkg_food < 10) {
                //notify
            }
            return pkg_food;
        }
        else
        {
            return -1;
        }
    }

}
