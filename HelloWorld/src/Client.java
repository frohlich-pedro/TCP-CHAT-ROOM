import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class Client {
    private static final int PORT = 3030;
    private static final String INET_ADDR = "192.168.0.250";
    private BufferedReader in;
    private PrintWriter out;
    private JFrame frame = new JFrame("Chat");
    private JTextField textField = new JTextField(40);
    private JTextArea messageArea = new JTextArea(8, 40);

    public Client() {
        textField.setEditable(false);
        messageArea.setEditable(false);

        Font font = new Font("Arial", Font.PLAIN, 20); // Aqui você pode ajustar o tipo e o tamanho da fonte
        textField.setFont(font);
        messageArea.setFont(font);

        frame.getContentPane().add(textField, BorderLayout.NORTH);
        frame.getContentPane().add(new JScrollPane(messageArea), BorderLayout.CENTER);
        frame.pack();

        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                out.println(textField.getText());
                textField.setText("");
            }
        });
    }

    private void run() {
        try (Socket socket = new Socket(INET_ADDR, PORT)) {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            textField.setEditable(true);

            String message;
            while ((message = in.readLine()) != null) {
                messageArea.append(message + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
        client.frame.setResizable(false);
        client.frame.setSize(640, 480);
        client.run();
    }
}
