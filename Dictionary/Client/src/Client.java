/**
 * username: ailinz1
 * Student number: 874810
 * name: Ailin Zhang
 */
import java.io.*;
import java.net.*;

public class Client {
    public static Socket clientSocket;
    public static DataInputStream inStream;
    public static DataOutputStream outStream;
    public static int comNum;


    public static void main (String args[]) {
        String host = "localhost";//set default ip and port
        int port = 1254;
        if (args.length!=2){
            System.out.println("Connecting with default host and port...");
        }
        else{
            host = args[0];
            port = Integer.parseInt(args[1]);
        }
        System.out.println("Using ip: "+ host + " and port: "+ port);


        Dictionary dicWindow = new Dictionary();

        try{

            while(dicWindow.getStatus()) {//when window is open, send request
                clientSocket = new Socket(host, port);
                inStream = new DataInputStream(clientSocket.getInputStream());
                outStream = new DataOutputStream(clientSocket.getOutputStream());
                String result = null;
                if((result=inStream.readUTF())!=null){
//                    dicWindow.showMessageDialog(result);
                    switch (comNum){
                    case 1://search

                        String res1 = result;
                        String res2 = inStream.readUTF();
                        if (res1.equals("0")){//throw message
                            dicWindow.showMessageDialog(res2);
                        }else if(res1.equals("1")){//show meaning
                            dicWindow.setOutputField(res2);
                        }
                        break;
                    case 2://add
                        dicWindow.showMessageDialog(result);
                        break;
                    case 3://remove
                        dicWindow.showMessageDialog(result);
                        break;
                }
                }
                inStream.close();
                outStream.close();
                clientSocket.close();
            }

        }catch (UnknownHostException e) {
            e.printStackTrace();
            dicWindow.showMessageDialog("Sorry, cannot figure given host and port...");
            Client.close();
        } catch (IOException e) {
            e.printStackTrace();
            dicWindow.showMessageDialog("Sorry, cannot access server...");
            Client.close();
        }


    }



    public static void close(){
        System.exit(0);
    }

}
