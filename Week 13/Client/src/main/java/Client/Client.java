package Client;

import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private Scanner input = new Scanner(System.in);
    private ClientConnection clientConnection;
    String name;

    public Client(){
        System.out.println("Please enter name: ");
        name = input.nextLine();
        try {
            socket = new Socket(InetAddress.getLocalHost().getHostAddress(), 6060);
            clientConnection = new ClientConnection(socket, this);
            clientConnection.start();

            listenForInput();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listenForInput(){
        try {
            while (true) {
                while(!input.hasNextLine()){
                    Thread.sleep(10);
                }

                String text = input.nextLine();

                if(text.equalsIgnoreCase("close")){
                    break;
                }

                clientConnection.sendStringToServer(name + ": " + text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            clientConnection.close();
        }
    }
}
