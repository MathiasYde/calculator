import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator {
    private JFrame window;
    private JTextField display;

    // variables to hold the rhs and lhs values
    private float leftRegister = 0;
    private float rightRegister = 0;

    // variable to hold what operator is being used
    private ActionListener currentOperand = null;

    // called when a operand button is clicked (+, -, *, /)
    private void OnOperandButtonClicked(ActionEvent e, ActionListener operand) {
        currentOperand = operand;
        leftRegister = Float.parseFloat(display.getText());
        display.setText("");
    }

    // called when the equals button is clicked
    // parse the current value in the display as a float into the right register and execute the currentOperand action
    private void OnEqualsButtonClicked(ActionEvent e) {
        rightRegister = Float.parseFloat(display.getText());
        currentOperand.actionPerformed(e);
    }

    // called when a number button is clicked (0 to 9)
    private void OnNumberButtonClicked(ActionEvent e, String identifier) {
        String currentText = display.getText();
        display.setText(currentText + identifier);
    }

    // helper method to create a button
    private JButton AddGridBagButton(JPanel destination, String identifier, int x, int y, GridBagConstraints constraints, ActionListener action) {
        return AddGridBagButton(destination, identifier, x, y, 1, 1, constraints, action);
    }

    // helper method to create a button
    private JButton AddGridBagButton(JPanel destination, String identifier, int x, int y, int width, int height, GridBagConstraints constraints, ActionListener action) {
        JButton button = new JButton();
        button.setText(identifier);
        button.addActionListener(action);
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = width;
        constraints.gridheight = height;
        destination.add(button, constraints);
        return button;
    }

    // constructor, called when this class is created

    public Calculator() {
        // java swing uses a JFrame as the main window
        window = new JFrame("Calculator");

        // the contents is inside the 'body' panel, which has an vertical BoxLayout manager
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
        display = new JTextField();
        display.setEditable(true);
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        topArea.add(display);

        // JPanel container for the clear buttons
        JPanel clearButtonContainer = new JPanel();
        clearButtonContainer.setLayout(new BoxLayout(clearButtonContainer, BoxLayout.Y_AXIS));

        // Button to clear the display content
        JButton clearDisplay = new JButton();
        clearDisplay.setText("C");
        clearDisplay.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // "reset" the calculator
                display.setText("");
                currentOperand = null;
            }
        });
        clearDisplay.setPreferredSize(new Dimension(60, 40));
        clearDisplay.setMinimumSize(new Dimension(40, 40));
        clearDisplay.setMaximumSize(new Dimension(80, 100));
        clearButtonContainer.add(clearDisplay);

        // Button to clear the last character
        JButton clearLastCharacter = new JButton();
        clearLastCharacter.setText("CE");
        clearLastCharacter.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String currentText = display.getText();
                if (currentText.length() > 0) {
                    display.setText(currentText.substring(0, currentText.length() - 1));
                }
            }
        });
        clearDisplay.setPreferredSize(new Dimension(60, 40));
        clearDisplay.setMinimumSize(new Dimension(40, 40));
        clearDisplay.setMaximumSize(new Dimension(80, 100));
        clearButtonContainer.add(clearLastCharacter);
        topArea.add(clearButtonContainer);
        body.add(topArea);

        // JPanel container for the number buttons
        JPanel bottomArea = new JPanel();
        bottomArea.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;

        // Number buttons
        AddGridBagButton(bottomArea, "1", 0, 3, constraints, new ActionListener() { public void actionPerformed(ActionEvent e){ OnNumberButtonClicked(e, "1"); }});
        AddGridBagButton(bottomArea, "2", 1, 3, constraints, new ActionListener() { public void actionPerformed(ActionEvent e){ OnNumberButtonClicked(e, "2"); }});
        AddGridBagButton(bottomArea, "3", 2, 3, constraints, new ActionListener() { public void actionPerformed(ActionEvent e){ OnNumberButtonClicked(e, "3"); }});
        AddGridBagButton(bottomArea, "4", 0, 2, constraints, new ActionListener() { public void actionPerformed(ActionEvent e){ OnNumberButtonClicked(e, "4"); }});
        AddGridBagButton(bottomArea, "5", 1, 2, constraints, new ActionListener() { public void actionPerformed(ActionEvent e){ OnNumberButtonClicked(e, "5"); }});
        AddGridBagButton(bottomArea, "6", 2, 2, constraints, new ActionListener() { public void actionPerformed(ActionEvent e){ OnNumberButtonClicked(e, "6"); }});
        AddGridBagButton(bottomArea, "7", 0, 1, constraints, new ActionListener() { public void actionPerformed(ActionEvent e){ OnNumberButtonClicked(e, "7"); }});
        AddGridBagButton(bottomArea, "8", 1, 1, constraints, new ActionListener() { public void actionPerformed(ActionEvent e){ OnNumberButtonClicked(e, "8"); }});
        AddGridBagButton(bottomArea, "9", 2, 1, constraints, new ActionListener() { public void actionPerformed(ActionEvent e){ OnNumberButtonClicked(e, "9"); }});
        AddGridBagButton(bottomArea, "0", 0, 4, 2, 1, constraints, new ActionListener() { public void actionPerformed(ActionEvent e){ OnNumberButtonClicked(e, "0"); }});

        // Operands
        AddGridBagButton(bottomArea, "=", 3, 4, constraints, new ActionListener() { public void actionPerformed(ActionEvent e){ OnEqualsButtonClicked(e); }});

        // action to divide lhs and rhs
        ActionListener divideAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                float result = leftRegister / rightRegister;
                display.setText(Float.toString(result));
            }
        };

        // action to multiply lhs and rhs
        ActionListener multiplyAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                float result = leftRegister * rightRegister;
                display.setText(Float.toString(result));
            }
        };

        // action to add rhs to lhs
        ActionListener additionAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                float result = leftRegister + rightRegister;
                display.setText(Float.toString(result));
            }
        };

        // action to subtract rhs from lhs
        ActionListener subtractionAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                float result = leftRegister - rightRegister;
                display.setText(Float.toString(result));
            }
        };

        // add the operands button to the input area
        AddGridBagButton(bottomArea, "/", 3, 0, constraints, new ActionListener() { public void actionPerformed(ActionEvent e){ OnOperandButtonClicked(e, divideAction); }});
        AddGridBagButton(bottomArea, "*", 3, 2, constraints, new ActionListener() { public void actionPerformed(ActionEvent e){ OnOperandButtonClicked(e, multiplyAction); }});
        AddGridBagButton(bottomArea, "-", 3, 1, constraints, new ActionListener() { public void actionPerformed(ActionEvent e){ OnOperandButtonClicked(e, subtractionAction);}});
        AddGridBagButton(bottomArea, "+", 3, 3, constraints, new ActionListener() { public void actionPerformed(ActionEvent e){ OnOperandButtonClicked(e, additionAction); }});

        // Operations

        // button to change the sign of the current number on the display
        AddGridBagButton(bottomArea, "+/-", 0, 0, constraints, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.setText(Float.toString(Float.parseFloat(display.getText()) * -1));
            }
        });

        // button to add a decimal point to the current number on the display
        AddGridBagButton(bottomArea, ".", 2, 4, constraints, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (display.getText().contains(".")) {
                    return;
                }
                display.setText(display.getText() + ".");
            }
        });

        // button the calculate the square root of the current number on the display
        AddGridBagButton(bottomArea, "√", 1, 0, constraints, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                float result = (float) Math.sqrt(Float.parseFloat(display.getText()));
                display.setText(Float.toString(result));
            }
        });

        // button to calculate the square of the current number on the display
        AddGridBagButton(bottomArea, "^", 2 , 0, constraints, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                float result = (float) Math.pow(Float.parseFloat(display.getText()), 2);
                display.setText(Float.toString(result));
            }
        });

        // trigonometry

        // button to change the current number on the display to radians and deg
        JButton toggleRadDeg = AddGridBagButton(bottomArea, "to rad", 4, 4, constraints, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton source = (JButton) e.getSource();
                if (source.getText().equals("to rad")) {
                    source.setText("to deg");
                    display.setText(Float.toString((float) Math.toRadians(Float.parseFloat(display.getText()))));
                } else {
                    source.setText("to rad");
                    display.setText(Float.toString((float) Math.toDegrees(Float.parseFloat(display.getText()))));
                }
            }
        });

        // button to calculate the cos to the current number on the display
        AddGridBagButton(bottomArea, "cos", 4, 0, constraints, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                float result = (float) Math.cos(Float.parseFloat(display.getText()));
                display.setText(Float.toString(result));
                toggleRadDeg.setText("to deg");
            }
        });

        // button to calculate the sin to the current number on the display
        AddGridBagButton(bottomArea, "sin", 4, 1, constraints, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                float result = (float) Math.sin(Float.parseFloat(display.getText()));
                display.setText(Float.toString(result));
                toggleRadDeg.setText("to deg");
            }
        });

        // button to calculate the tan to the current number on the display
        AddGridBagButton(bottomArea, "tan", 4, 2, constraints, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                float result = (float) Math.tan(Float.parseFloat(display.getText()));
                display.setText(Float.toString(result));
                toggleRadDeg.setText("to deg");
            }
        });

        // button to calculate the cot to the current number on the display
        AddGridBagButton(bottomArea, "cot", 4, 3, constraints, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                float result = (float) Math.cos(Float.parseFloat(display.getText())) / (float) Math.sin(Float.parseFloat(display.getText()));
                display.setText(Float.toString(result));
                toggleRadDeg.setText("to deg");
            }
        });

        body.add(bottomArea);

        window.setSize(400, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }
}