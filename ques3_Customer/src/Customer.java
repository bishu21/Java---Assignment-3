import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Customer {

    JFrame f;
    JLabel status;
    JButton submit;
    JTextField tea,coffee,snacks,cookies,pkg_food,name;

 //  JFrame is used for taking input from the customer
    // it is just UI technique (swing)
    Customer()
    {
        f = new JFrame();

        status = new JLabel("");
        status.setBounds(50,25,50,20);

        tea = new JTextField();
        coffee = new JTextField();
        snacks = new JTextField();
        cookies = new JTextField();
        pkg_food = new JTextField();
        name = new JTextField();
        submit=new JButton("click");//creating instance of JButton
        submit.setBounds(150,380,100, 40);//x axis, y axis, width, height

        tea.setBounds(150,50,40,20);
        coffee.setBounds(150,100,40,20);
        snacks.setBounds(150,150,40,20);
        cookies.setBounds(150,200,40,20);
        pkg_food.setBounds(150,250,40,20);
        name.setBounds(150,300,150,20);
        f.add(submit);//adding button in JFrame
        f.setSize(400,500);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);
        f.add(status);
        f.add(tea);
        f.add(coffee);
        f.add(snacks);
        f.add(cookies);
        f.add(pkg_food);
        f.add(name);

    }


// simple we made a socket in main function
    // allocate localhost and 5000 port to the socket then try to connect this socket with the server through given port
    // after connected take input from user
    // send the data from customer to server by DataInputStream
    // get a response from the server that customer is connected with the server
    public static void main(String args[]) throws IOException {


        Customer c = new Customer();

        InetAddress ip = InetAddress.getByName("localhost");

        Socket s = new Socket(ip,5000);

        if(s.isConnected())
        {
            c.status.setText("Connected");
            c.status.setForeground(Color.GREEN);
        }
        else
        {
            c.status.setText("Connection failed");
            c.status.setForeground(Color.RED);
            c.submit.setVisible(false);
        }

        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());


        c.submit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    send_order(dis,dos,c);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });


    }


    public static void send_order(DataInputStream dis,DataOutputStream dos,Customer c) throws IOException {
        String tea = c.tea.getText();
        if(tea.equals("") )
            tea = "0";
        String coffee = c.coffee.getText();
        if(coffee.equals(""))
            coffee = "0";
        String snacks = c.snacks.getText();
        if(snacks.equals(""))
            snacks = "0";
        String cookies = c.cookies.getText();
        if(cookies.equals(""))
            cookies = "0";
        String pkg_food = c.pkg_food.getText();
        if(pkg_food.equals(""))
            pkg_food = "0";
        String name = c.name.getText();
        if(pkg_food.equals(""))
            pkg_food = "user";
        dos.writeUTF(name+","+tea+","+coffee+","+snacks+","+cookies+","+pkg_food);
        System.out.println(tea+" "+coffee+" "+snacks+" "+cookies+" "+pkg_food);
        String result = dis.readUTF();

        JFrame invoice = new JFrame();
        invoice.setSize(400,500);//400 width and 500 height
        invoice.setLayout(null);//using no layout managers
        invoice.setVisible(true);

        JLabel invoice_list = new JLabel(result);
        invoice_list.setBounds(25,25,350,450);
        invoice.add(invoice_list);

        //c.status.setText(result);

    }
}
