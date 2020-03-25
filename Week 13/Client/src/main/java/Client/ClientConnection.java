package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientConnection extends Thread {
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;


    public ClientConnection(Socket clientSocket, Client client){
        socket = clientSocket;
    }

    public void sendStringToServer(String text){
        try{
            dataOutputStream.writeUTF(text);
            dataOutputStream.flush();
        } catch(Exception e) {
            e.printStackTrace();
            close();
        }
    }

    @Override
    public void run(){
        try{
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            while(true){
                while(dataInputStream.available() == 0) {
                    Thread.sleep(10);
                }
                String reply = dataInputStream.readUTF();
                System.out.println(reply);
            }
        } catch(Exception e){
            e.printStackTrace();
            close();
        }
        finally {
            close();
        }
    }

    public void close(){
        try{
            dataOutputStream.close();
            dataInputStream.close();
            socket.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
