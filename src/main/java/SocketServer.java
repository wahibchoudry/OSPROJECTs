import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;
public class SocketServer {
 
   public static void main(String[] args) {
        try {
            
            ServerSocket serverSocket = new ServerSocket(2000);
            System.out.println("Server started. Waiting for a client...");

            Socket socket = serverSocket.accept();
            System.out.println("Connected to client");
            
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());

            String line = "";
            while (!line.equals("Over")) {
                line = inputStream.readUTF();
            JOptionPane.showMessageDialog(null, "Message received from client: " + line, "Client Message", JOptionPane.INFORMATION_MESSAGE);

            }

            inputStream.close();
            socket.close();
            serverSocket.close();
           
        } 
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}