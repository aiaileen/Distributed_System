/**
 * username: ailinz1
 * Student number: 874810
 * name: Ailin Zhang
 */

import java.io.*;
import java.lang.Thread;
import java.net.*;

public class Server {
    private Socket socket;
    // Identifies the user number connected
    public static int requestCount = -1;


    public String loadFile(){
//      Loading dictionary file
        String dictionary = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("dictionary.txt"));

            dictionary = reader.readLine();
            reader.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error: dictionary.txt does not exit");
            Server.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: Reading from dictionary.txt is failed");
            Server.close();
        }
        return dictionary;
    }

    public void writeFile(String file) {
 //      update dictionary file
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("dictionary.txt"));
            writer.write(file);
            writer.close();

        } catch (IOException e) {
            System.out.println("Writing to dictionary file is failed.");
            Server.close();
        }
    }


    public static void main(String[] args) {

//       Initialize the GUI
        ServerWindow serverWindow = new ServerWindow();


//        Initialize the server
        int port = 1254;
        if (args.length != 2) {
            System.out.println("Using default port...");

        } else try {
            port = Integer.parseInt(args[0]);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Invalid argument");

        }
        System.out.println("Opening port: " + port);
        System.out.println("Server is running. Waiting for client connection...");

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverWindow.setStatus("Server is running on: "+serverSocket.getInetAddress()+" Port: "
                    +serverSocket.getLocalPort());


//            Server is ready waiting for Client's connection
            while (true) try {
                Socket newClient = serverSocket.accept();
//                newClient.setSoTimeout(100000);
                requestCount++;
                serverWindow.setRequestNumber(requestCount);

                Thread thread = new Thread(new HandleThread(newClient,serverWindow));
                thread.start();


                } catch (IOException e) {
                    e.printStackTrace();
                    serverWindow.setStatus("Client "+ serverSocket.getInetAddress()+
                            serverSocket.getLocalPort()+" is offline");
                    System.out.println("Client is offline");
                }



            } catch (IOException e) {
                e.printStackTrace();
                serverWindow.setStatus("Sorry, Socket is not available");
                System.out.println("Sorry, Socket is not available");
        }
    }


    public static void close() {
        System.exit(0);
    }

}

