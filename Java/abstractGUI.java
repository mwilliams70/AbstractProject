import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class abstractGUI {
    public boolean connected;

    public abstractGUI() {
        accountGeneration abstractDB = new accountGeneration();
        abstractDB.connect("abstract_project", "root", "student");

        JFrame gui = new JFrame("Abstract Project");
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setSize(800, 600);
        gui.setLocation(500, 500);

        JPanel app = new JPanel();
        app.setLayout(new GridLayout(0, 1));

        JButton modifyBtn = new JButton("Modify Interests");

        modifyBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JPanel main = new JPanel();
                main.setLayout(new GridLayout(3, 2));
                JLabel int1 = new JLabel("Interest 1: ");
                JLabel int2 = new JLabel("Interest 2: ");
                JLabel int3 = new JLabel("Interest 3: ");

                String[] interests = abstractDB.getInterest(1, "faculty");

                JTextField userInt1 = new JTextField(interests[0]);
                JTextField userInt2 = new JTextField(interests[1]);
                JTextField userInt3 = new JTextField(interests[2]);

                main.add(int1);
                main.add(userInt1);
                main.add(int2);
                main.add(userInt2);
                main.add(int3);
                main.add(userInt3);

                JOptionPane.showMessageDialog(null, main, "Modify", JOptionPane.QUESTION_MESSAGE);
            }
        });

        app.add(modifyBtn);
        gui.add(app);
        gui.setVisible(true);

    }
    public static void main(String[] args) {
        new abstractGUI();
    }
}
