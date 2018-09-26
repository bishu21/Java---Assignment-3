import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;

public class ClientHandler extends Thread{

    public DataOutputStream out;
    public DataInputStream in;
    public Socket s;

    public ClientHandler(Socket s, DataInputStream in, DataOutputStream out) throws IOException, InterruptedException {
        //get the value from user and service the request

        this.s = s;
        this.out = out;
        this.in = in;

    }
    // here we pushed order in queue and exceuted in the FCFS manner
    // basically this class is client handler class

    @Override
    public void run()
    {

        System.out.println("client handler started");
        while(true)
        {
            String input = null;
            try {
                input = in.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Order order = new Order(input);

            //push order to queue
            OrderQueue.getInstance().push_back(order);
            order.start();
            try {
                order.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                out.writeUTF("<html>"+order.result+" estimated time of arrival : "+String.valueOf(order.et.getTime())+"</html>");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(order.result+"\n estimated time of arrival : "+String.valueOf(order.et.getTime()));
        }

    }

}