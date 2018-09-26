import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Server {

    public static Calendar date = Calendar.getInstance();

    public static int time = 0;

    //admin panel declaration

 /// this class is basically used for waiting for the customer through port 5000
    // if some client try to connect then it will run thread for that client and send acknowlegement to the client
    public static void main(String args[]) throws IOException, InterruptedException {
        ServerSocket ss = new ServerSocket(5000);
        Admin admin = Admin.getInstance();
        admin.setVisible(true);
        //start order executor
        //start item executor
        System.out.println("Server started on port 5000");

        while(true)
        {
            Socket s = null;
            try{
                s = ss.accept();
                System.out.println("A new client is connected");

                DataInputStream in = new DataInputStream(s.getInputStream());
                DataOutputStream out = new DataOutputStream(s.getOutputStream());
                Thread handler = new ClientHandler(s,in,out);
                handler.start();
            }
            catch (Exception e)
            {
                s.close();
                e.printStackTrace();
            }
            Thread.sleep(50);
        }
    }

}
