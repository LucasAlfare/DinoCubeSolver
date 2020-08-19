package solver;

import javax.swing.*;
import java.awt.*;

public class GuiTest extends JFrame {

    public GuiTest() {
        setSize(500, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Setup (state=s or moves): ");

        JButton search = new JButton("Search Solution");

        JTextArea textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(300, 100));

        search.addActionListener(e -> {
            if (!textArea.getText().isEmpty()) {
                State state = new State();
                String inputText = textArea.getText();
                if (inputText.startsWith("s")) {
                    String[] args = inputText.split(" ");
                    for (int i = 1; i < args.length; i++) {
                        state.permutation[i - 1] = Byte.parseByte(args[i]);
                    }
                } else {
                    state.applySequence(inputText);
                }

                String s = Search.search(state);
                textArea.append("\nThe solution is: ");
                textArea.append(s);

                JOptionPane.showMessageDialog(null, "Solution found. To search for other state, please, set the text field and type again.");
            }
        });

        Search.init();

        add(label);
        add(search);
        add(textArea);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new GuiTest().setVisible(true));
    }
}
