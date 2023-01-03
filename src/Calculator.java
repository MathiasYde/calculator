import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator {
    private JFrame window;

    private void AddGridBagButton(JPanel destination, String identifier, int x, int y, GridBagConstraints constraints) {
        AddGridBagButton(destination, identifier, x, y, 1, 1, constraints);
    }

    private void AddGridBagButton(JPanel destination, String identifier, int x, int y, int width, int height, GridBagConstraints constraints) {
        JButton button = new JButton();
        button.setText(identifier);
        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println(identifier);
            }
        });
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = width;
        constraints.gridheight = height;
        destination.add(button, constraints);
    }

    public Calculator() {
        window = new JFrame("Calculator");

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        window.add(body);

        // Top area to contain display and clear button
        JPanel topArea = new JPanel();
        topArea.setLayout(new BoxLayout(topArea, BoxLayout.X_AXIS));
        topArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        topArea.setMinimumSize(new Dimension(Integer.MAX_VALUE, 80));
        topArea.setPreferredSize(new Dimension(Integer.MAX_VALUE, 80));

        // Display to show user input
        JTextField display = new JTextField();
        display.setEditable(true);
        topArea.add(display);

        // Button to clear the display content
        JButton clearDisplay = new JButton();
        clearDisplay.setText("C");
        clearDisplay.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                display.setText("");
            }
        });
        clearDisplay.setPreferredSize(new Dimension(60, Integer.MAX_VALUE));
        clearDisplay.setMinimumSize(new Dimension(40, Integer.MAX_VALUE));
        clearDisplay.setMaximumSize(new Dimension(80, Integer.MAX_VALUE));
        topArea.add(clearDisplay);

        body.add(topArea);

        JPanel bottomArea = new JPanel();
        bottomArea.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;

        AddGridBagButton(bottomArea, "1", 0, 2, constraints);
        AddGridBagButton(bottomArea, "2", 1, 2, constraints);
        AddGridBagButton(bottomArea, "3", 2, 2, constraints);
        AddGridBagButton(bottomArea, "4", 0, 1, constraints);
        AddGridBagButton(bottomArea, "5", 1, 1, constraints);
        AddGridBagButton(bottomArea, "6", 2, 1, constraints);
        AddGridBagButton(bottomArea, "7", 0, 0, constraints);
        AddGridBagButton(bottomArea, "8", 1, 0, constraints);
        AddGridBagButton(bottomArea, "9", 2, 0, constraints);
        AddGridBagButton(bottomArea, "0", 0, 3, 2, 1, constraints);

        body.add(bottomArea);

        window.setSize(400, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }
}
