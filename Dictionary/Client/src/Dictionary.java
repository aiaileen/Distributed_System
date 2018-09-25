/**
 * username: ailinz1
 * Student number: 874810
 * name: Ailin Zhang
 */
//This is for client GUI
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.EOFException;
import java.io.IOException;


public class Dictionary extends JFrame{
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JTextArea outputField;
    private JPanel middlePanel;
    private JButton searchButton;
    private JButton addButton;
    private JButton removeButton;
    private JTextField inputField;
    private JLabel welcomeLabel;
    private Boolean status = true;



    public Dictionary() {
        super("Dictionary");
//      initialize the input field with a hint.
        inputField.setText("Type the word here...");
        inputField.setFont(new Font("Palatino Linotype",0,14));
        inputField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                inputField.setText("");
            }
        });

        JFrame frame = new JFrame("Dictionary");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
//      check if window is still open
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                status = false;
                Client.close();
            }
        });

//      set up buttons
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] input = inputField.getText().trim().split(" ");
                if (input.length!=1){
                    showMessageDialog("Oops...It seems that you didn't type one word");
                }
                else try {
                    Client.comNum=1;
                    Client.outStream.writeUTF("search@#&" + input[0]);


                } catch (IOException e1) {
                    e1.printStackTrace();
                    showMessageDialog("Sending to server failed");
                } catch (NullPointerException | ArrayIndexOutOfBoundsException e1) {
                    e1.printStackTrace();
                    showMessageDialog("Oops...It seems that you didn't type one word");
                }
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] input1 = inputField.getText().trim().split(" ");
                String input2 = outputField.getText().trim();
                if (input1.length!=1 || input1[0].equals("")){
                    showMessageDialog("Oops...It seems that you didn't type one word");
                }else if(input2.equals("")){
                    showMessageDialog("Oops...It seems that you didn't type any meaning");
                }else try {
                    Client.comNum=2;
                    Client.outStream.writeUTF("add@#&" + input1[0] + "@#&" + input2);
                    System.out.println("add@#&" + input1[0] + "@#&" + input2);

//                    showMessageDialog(Client.inStream.readUTF());
                } catch (IOException e1) {
                    e1.printStackTrace();
                    showMessageDialog("Sending to server failed");
                } catch (NullPointerException | ArrayIndexOutOfBoundsException e1) {
                    e1.printStackTrace();
                    showMessageDialog("Oops...It seems that you didn't type one word");
                }
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] input = inputField.getText().trim().split(" ");
                if (input.length!=1 || input[0].equals("")){
                    showMessageDialog("Oops...It seems that you didn't type one word");
                }
                else try{
                    Client.comNum = 3;
                    Client.outStream.writeUTF("remove@#&" + input[0]);
                    System.out.println("remove@#&" + input[0]);
//                    showMessageDialog(Client.inStream.readUTF());

                } catch (IOException e1) {
                    e1.printStackTrace();
                    showMessageDialog("Sending to server failed");
                } catch (NullPointerException | ArrayIndexOutOfBoundsException e1) {
                    e1.printStackTrace();
                    showMessageDialog("Oops...It seems that you didn't type one word");
                }

            }
        });

    }

//    print out welcome message given by server
    public void setWelcomeLabel(String str) {
        this.welcomeLabel.setText(str);
        this.welcomeLabel.setFont(new Font("Lucida Calligraphy",Font.BOLD,14));
    }

//check if window is closed and return the status to client.
    public Boolean getStatus() {
        return status;
    }


//   print out the detailed message through message dialog.
    public void showMessageDialog(String message){
        JOptionPane.showMessageDialog(null, message,
                "Message", JOptionPane.PLAIN_MESSAGE);
    }

    public void setOutputField(String output){
        outputField.setText(output);
    }

}
