import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Admin extends JFrame {

    // this is basically a UI class and we made UI according to the requirement

    private table model = new table();
    private JTable table;
    private JLabel cookies;
    private JLabel snacks;
    private JLabel pkg_food;

    private table filter = new table();
    private JTable filtered_table;

    private JLabel[] labels;

    private static FileWriter fw;
    private static File fc;
    private static BufferedWriter bc;

    public static Admin instance;

    public Admin() throws IOException {
        super();
        model.load();
        setTitle("Admin panel");
//JFrame.DO_NOTHING_ON_CLOSE
        fc = new File("C:\\Users\\Tamil Selvan\\IdeaProjects\\TeaKadai\\src\\database.txt");
        fw = new FileWriter(fc,true);
        bc = new BufferedWriter(fw);
        table = new JTable(model);
        filtered_table = new JTable(filter);
        JScrollPane j = new JScrollPane(table);
        j.setPreferredSize(new Dimension(1000, 700));
        getContentPane().add(j, BorderLayout.WEST);

        JPanel left = new JPanel();

        cookies = new JLabel();
        snacks = new JLabel();
        pkg_food = new JLabel();
        labels = new JLabel[3];
        for(int i=0;i<3;i++)
            labels[i] = new JLabel();
        labels[0].setText("cookies : ");
        labels[1].setText("snacks : ");
        labels[2].setText("packed items : ");

        left.add(labels[0]);
        left.add(cookies);
        left.add(labels[1]);
        left.add(snacks);
        left.add(labels[2]);
        left.add(pkg_food);

        getContentPane().add(left,BorderLayout.SOUTH);
        pack();
    }

    public static Admin getInstance() throws IOException {
        if(instance == null)
        {
            instance = new Admin();
            instance.setSize(700,800);
            instance.addWindowListener(new WindowAdapter() {
                public void WindowClosing(WindowEvent e) throws IOException {
                    //System.out.println("test");
                    bc.close();
                    instance.dispose();
                }
            });
        }
        return instance;
    }

    public void addorder(order_item order) throws IOException {
        model.addorder(order);
        bc.write(order.getName()+","+String.valueOf(order.getTea())+","+String.valueOf(order.getCoffee())+","+String.valueOf(order.getSnacks())+","+String.valueOf(order.getCookies())+","+String.valueOf(order.getPkg_food())+","+String.valueOf(order.getDate())+"\n");
        synchronized (Stock.getInstance())
        {
            cookies.setText(String.valueOf(Stock.getInstance().cookies));
            if(Stock.getInstance().cookies<=10)
                cookies.setForeground(Color.RED);
            snacks.setText(String.valueOf(Stock.getInstance().snacks));
            if(Stock.getInstance().snacks<=10)
                snacks.setForeground(Color.RED);
            pkg_food.setText(String.valueOf(Stock.getInstance().pkg_food));
            if(Stock.getInstance().pkg_food<=10)
                pkg_food.setForeground(Color.RED);
        }
    }


}

// below method is used for making a table and save it to some loaction as sir asked in the question

class table extends AbstractTableModel{
    private final List<order_item> orders = new ArrayList();

    public void load(){
        BufferedReader br = null;
        String line = "" ;

        try {

            br = new BufferedReader(new FileReader("C:\\Users\\Tamil Selvan\\IdeaProjects\\TeaKadai\\src\\database.txt"));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                order_item o = new order_item(line);
                orders.add(o);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public int getRowCount() {
        return orders.size();
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return headers[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return orders.get(rowIndex).getName();
            case 1:
                return orders.get(rowIndex).getTea();
            case 2:
                return orders.get(rowIndex).getCoffee();
            case 3:
                return orders.get(rowIndex).getSnacks();
            case 4:
                return orders.get(rowIndex).getCookies();
            case 5:
                return orders.get(rowIndex).getPkg_food();
            case 6:
                return orders.get(rowIndex).getDate();
            default:
                return null; //Must never happens
        }
    }

    private final String[] headers = {"Name", "Tea", "Coffee", "Snacks", "Cookies","Packaged foods","Time of delivery"};
    public table(){
        super();

    }

    public void addorder(order_item order) {
        orders.add(order);

        fireTableRowsInserted(orders.size() -1, orders.size() -1);
    }

    public void removeFriend(int rowIndex) {
        orders.remove(rowIndex);

        fireTableRowsDeleted(rowIndex, rowIndex);
    }

}

// this class is used for the order item shows corectly

class order_item {
    public String name;
    public int tea;
    public int coffee;
    public int snacks;
    public int cookies;
    public int pkg_food;
    public String date;
    public order_item(String input)
    {
        String[] params = input.split(",");
        name = params[0];
        tea = Integer.valueOf(params[1]);
        coffee = Integer.valueOf(params[2]);
        snacks = Integer.valueOf(params[3]);
        cookies = Integer.valueOf(params[4]);
        pkg_food = Integer.valueOf(params[5]);
        date = params[6];
    }

    public int getCoffee() {
        return coffee;
    }

    public int getCookies() {
        return cookies;
    }

    public int getPkg_food() {
        return pkg_food;
    }

    public int getSnacks() {
        return snacks;
    }

    public int getTea() {
        return tea;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}