package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ServerConnection extends Thread{

    private Socket socket;
    private Server server;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private boolean running = true;

    public ServerConnection(Socket socket, Server server){
        super("ServerConnectionThread");
        this.socket = socket;
        this.server = server;
    }

    public void sendStringToClient(String text) {
        try{
            dataOutputStream.writeUTF(text);
            dataOutputStream.flush();
        } catch (Exception e){
            e.printStackTrace();
            close();
        }
    }

    public void sendStringToAllClients(String text) {
        for(int i = 0; i < server.connections.size(); i++) {
            ServerConnection serverConnection = server.connections.get(i);
            serverConnection.sendStringToClient(text);
        }
    }

    @Override
    public void run(){
        try{
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            while(running) {
                while(dataInputStream.available() == 0) {
                    Thread.sleep(10);
                }
                String textIn = dataInputStream.readUTF();
                sendStringToAllClients(textIn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            close();
        }
    }

    public void close() {
        try{
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
