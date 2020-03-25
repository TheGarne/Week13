package Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private ServerSocket serverSocket;
    ArrayList<ServerConnection> connections = new ArrayList<ServerConnection>();

    public Server(){
        try{
            serverSocket = new ServerSocket(6060);
            System.out.println("Server started...");

            while(true){
                Socket socket = serverSocket.accept();
                ServerConnection serverConnection = new ServerConnection(socket, this);
                serverConnection.start();
                connections.add(serverConnection);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try{
                serverSocket.close();
            } catch (Exception e) {
                e.printStackTrace();            }
        }
    }
}
