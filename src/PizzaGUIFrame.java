import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PizzaGUIFrame extends JFrame {
    private JRadioButton thinCrust, regularCrust, deepDishCrust;
    private JComboBox<String> sizeComboBox;
    private JCheckBox[] toppings;
    private JTextArea receiptArea;

    public PizzaGUIFrame() {
        setTitle("Pizza Order Form");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crust Panel
        JPanel crustPanel = createCrustPanel();
        add(crustPanel, BorderLayout.NORTH);

        // Size Panel
        JPanel sizePanel = createSizePanel();
        add(sizePanel, BorderLayout.CENTER);

        // Toppings Panel
        JPanel toppingsPanel = createToppingsPanel();
        add(toppingsPanel, BorderLayout.SOUTH);

        // Receipt Area
        receiptArea = new JTextArea(10, 30);
        receiptArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(receiptArea);
        add(scrollPane, BorderLayout.EAST);

        // Button Panel
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.PAGE_END);

        pack();
        setVisible(true);
    }

    private JPanel createCrustPanel() {
        JPanel crustPanel = new JPanel();
        crustPanel.setBorder(BorderFactory.createTitledBorder("Choose Crust"));
        thinCrust = new JRadioButton("Thin");
        regularCrust = new JRadioButton("Regular");
        deepDishCrust = new JRadioButton("Deep-dish");

        ButtonGroup crustGroup = new ButtonGroup();
        crustGroup.add(thinCrust);
        crustGroup.add(regularCrust);
        crustGroup.add(deepDishCrust);

        crustPanel.add(thinCrust);
        crustPanel.add(regularCrust);
        crustPanel.add(deepDishCrust);

        return crustPanel;
    }

    private JPanel createSizePanel() {
        JPanel sizePanel = new JPanel();
        sizePanel.setBorder(BorderFactory.createTitledBorder("Choose Size"));

        String[] sizes = {"Small", "Medium", "Large", "Super"};
        sizeComboBox = new JComboBox<>(sizes);
        sizePanel.add(sizeComboBox);

        return sizePanel;
    }

    private JPanel createToppingsPanel() {
        JPanel toppingsPanel = new JPanel();
        toppingsPanel.setBorder(BorderFactory.createTitledBorder("Choose Toppings"));

        String[] toppingNames = {"Cheese", "Pepperoni", "Olives", "Pineapple", "Mushrooms", "Monster Eyeballs"};
        toppings = new JCheckBox[toppingNames.length];

        for (int i = 0; i < toppingNames.length; i++) {
            toppings[i] = new JCheckBox(toppingNames[i]);
            toppingsPanel.add(toppings[i]);
        }

        return toppingsPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();

        JButton orderButton = new JButton("Order");
        JButton clearButton = new JButton("Clear");
        JButton quitButton = new JButton("Quit");

        orderButton.addActionListener(new OrderButtonListener());
        clearButton.addActionListener(new ClearButtonListener());
        quitButton.addActionListener(new QuitButtonListener());

        buttonPanel.add(orderButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);

        return buttonPanel;
    }

    private class OrderButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Compile and display order here
            String crust = thinCrust.isSelected() ? "Thin" : regularCrust.isSelected() ? "Regular" : "Deep-dish";
            String size = (String) sizeComboBox.getSelectedItem();
            StringBuilder toppingsList = new StringBuilder();
            int toppingCount = 0;
            for (JCheckBox topping : toppings) {
                if (topping.isSelected()) {
                    toppingsList.append(topping.getText()).append(", ");
                    toppingCount++;
                }
            }

            // Remove trailing comma and space
            if (toppingsList.length() > 0) {
                toppingsList.setLength(toppingsList.length() - 2);
            } else {
                toppingsList.append("None");
            }

            double basePrice = switch (size) {
                case "Small" -> 8.00;
                case "Medium" -> 12.00;
                case "Large" -> 16.00;
                case "Super" -> 20.00;
                default -> 0.00;
            };

            double subtotal = basePrice + (toppingCount * 1.00);
            double tax = subtotal * 0.07;
            double total = subtotal + tax;

            String receipt = String.format(
                    "==========================================\n" +
                            "Type of Crust & Size\t\tPrice\n" +
                            "%s %s\t\t%.2f\n" +
                            "Ingredient\t\tPrice\n" +
                            "%s\t\t%.2f\n\n" +
                            "Sub-total:\t\t%.2f\n" +
                            "Tax:\t\t\t%.2f\n" +
                            "------------------------------------------\n" +
                            "Total:\t\t\t%.2f\n" +
                            "==========================================",
                    crust, size, basePrice, toppingsList, toppingCount * 1.00, subtotal, tax, total
            );

            receiptArea.setText(receipt);
        }
    }

    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            thinCrust.setSelected(false);
            regularCrust.setSelected(false);
            deepDishCrust.setSelected(false);
            sizeComboBox.setSelectedIndex(0);
            for (JCheckBox topping : toppings) {
                topping.setSelected(false);
            }
            receiptArea.setText("");
        }
    }

    private class QuitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int confirmed = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit",
                    JOptionPane.YES_NO_OPTION);
            if (confirmed == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }
}
