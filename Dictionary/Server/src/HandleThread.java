
/**
 * username: ailinz1
 * Student number: 874810
 * name: Ailin Zhang
 */
import java.io.*;
import java.net.*;
import java.util.concurrent.ThreadLocalRandom;

public class HandleThread extends Thread implements Runnable {
    private Socket socket;
    private ServerWindow serverWindow;


    public HandleThread(Socket newClient, ServerWindow serverWindow) {
        this.socket = newClient;
        this.serverWindow = serverWindow;
    }


    public void run() {
        try {
            Server server = new Server();
            DataInputStream inStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
            String input = null;
            if ((input = inStream.readUTF()) != null) {
                String[] command = input.split("@#&");
                serverWindow.setStatus("Client " + socket.getInetAddress() + " (" + socket.getPort() + ") : doing " + command[0]);


                String dictionary = server.loadFile();//load dictionary file
                Operation operation = new Operation(serverWindow, dictionary);//convert to HashMap
                if (command[0].equals("search")) {

                    String result = operation.search(command[1]);
//                          pass 2 strings to client. if first string=1,return meaning in textfield or, throw a message dialog
                    if (result.equals("@#&")) {
                        outStream.writeUTF("0");
                        outStream.writeUTF("Word does not exist.");
                    } else {
                        outStream.writeUTF("1");
                        outStream.writeUTF(result);
                    }

                } else if (command[0].equals("add")) {
                    System.out.println(command);
                    if (operation.add(command[1], command[2])) {
                        outStream.writeUTF("Successfully added.");
//              update dictionary file
                        server.writeFile(operation.updateFile(serverWindow));

                    } else {
                        outStream.writeUTF("The word already exists in dictionary.");
                    }

                } else if (command[0].equals("remove")) {
                    if (operation.remove(command[1])) {
                        outStream.writeUTF("Successfully removed.");
                        server.writeFile(operation.updateFile(serverWindow));//update dictionary
                    } else {
                        outStream.writeUTF("The word does not exist.");
                    }

                } else {
                    System.out.println("Client's command is invalid");
                    serverWindow.setStatus("Client " + socket.getInetAddress() + " (" + socket.getPort() + ") : Command is invalid");
                    outStream.writeUTF("Command is invalid");
                }
            }
            outStream.close();
            socket.close();
            int randomSleep = ThreadLocalRandom.current().nextInt(100, 1000);
            Thread.sleep(randomSleep);

        } catch (EOFException e) {
            e.printStackTrace();
            System.out.println("Client shuts down");
            serverWindow.setStatus("Client " + socket.getInetAddress() + " (" + socket.getPort() + ") : is offline");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Connection is failed");
            serverWindow.setStatus("Client " + socket.getInetAddress() + " (" + socket.getPort() + ") : is offline");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Thread is interrupted.");
            serverWindow.setStatus("Client " + socket.getInetAddress() + " (" + socket.getPort() + ") : Thread is interrupted.");
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println("Command is invalid");
            serverWindow.setStatus("Client " + socket.getInetAddress() + " (" + socket.getPort() + ") : input word is invalid");
        }
    }
}

