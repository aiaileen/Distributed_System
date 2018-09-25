/**
 * username: ailinz1
 * Student number: 874810
 * name: Ailin Zhang
 */

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class ServerWindow {
    public JTextArea statusArea;
    private JPanel mainPanel;
    private JScrollPane scroll;
    private JLabel statusLabel;
    private JLabel reqNumLabel;
    private JLabel dictNumLabel;

    public ServerWindow() {
        JFrame frame = new JFrame("ServerWindow");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
//      check when window is closed, shut down the server
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Server.close();
            }
        });

    }
    public void setStatus(String message) {
        statusArea.append("\r\n"+message);
//        statusArea.paintImmediately(statusArea.getX(), statusArea.getY(), statusArea.getWidth(), statusArea.getHeight());
    }

    public void setRequestNumber(int num) {
        reqNumLabel.setText("Request Count: "+num);
    }

    public void setDictNumber(int num) {
        dictNumLabel.setText("Dictionary Words: "+num);
    }

}
