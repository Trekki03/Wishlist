package de.trekki03.gui;

import org.json.JSONArray;
import org.json.JSONObject;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MainWindow extends JFrame {

    private JSONArray itemsArray;

    private JPanel mainPanel;
    private JComboBox<String> itemList;
    private JButton getLinkButton;
    private JButton addItemButton;
    private JButton deleteItemButton;
    private JLabel priceLabel;

    public MainWindow(String title) {
        super(title);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        getLinkButton.addActionListener(linkButtonActionListener);
        addItemButton.addActionListener(addButtonActionListener);
        deleteItemButton.addActionListener(deleteButtonActionListener);
        itemList.addActionListener(itemListActionListener);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveToJson();
            }
        });

        readJson();
        addItemsToList(getItemList());

    }

    ActionListener linkButtonActionListener = e -> {
        String link = getLink((String) itemList.getSelectedItem());
        StringSelection stringSelection = new StringSelection(link);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    };

    ActionListener addButtonActionListener = e -> {
        JDialog addItemDialog = new AddItemDialog();
        addItemDialog.setVisible(true);
    };

    ActionListener deleteButtonActionListener = e -> {
        int index = getIndexOfItem((String) itemList.getSelectedItem());
        if (index == -1) {
            return;
        }
        itemsArray.remove(index);
        System.out.println(itemList.getSelectedIndex());
        itemList.removeItemAt(itemList.getSelectedIndex());
    };

    ActionListener itemListActionListener = e -> {
        if (itemList.getItemCount() == 0) {
            priceLabel.setText("");
        } else {
            Object selected = itemList.getSelectedItem();
            priceLabel.setText("" + getPrice((String) selected));
        }
    };


    void readJson() {
        try {
            File file = new File(System.getProperty("user.home") +
                    System.getProperty("file.separator") + ".tobias_wishlist" +
                    System.getProperty("file.separator") + "data.json");

            if (!file.exists()) {
                file.getParentFile().mkdirs();
                FileWriter fileWriter = new FileWriter(file.getPath());
                JSONObject data = new JSONObject();
                JSONArray placeholderArray = new JSONArray();
                JSONObject placeholderObject = new JSONObject();
                placeholderObject.put("name", "Add New Object");
                placeholderObject.put("price", 123);
                placeholderObject.put("link", "this can be removed");
                placeholderArray.put(placeholderObject);
                data.put("items", placeholderArray);
                fileWriter.write(data.toString());
                fileWriter.close();
            }
            String content = Files.readString(Paths.get(file.toURI()));
            JSONObject json = new JSONObject(content);
            itemsArray = json.getJSONArray("items");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ArrayList<String> getItemList() {

        ArrayList<String> items = new ArrayList<>();

        for (int i = 0; i < itemsArray.length(); i++) {
            items.add(itemsArray.getJSONObject(i).getString("name"));
        }

        return items;
    }

    void addItemsToList(ArrayList<String> items) {
        for (String item : items) {
            itemList.addItem(item);
        }
    }

    int getPrice(String name) {
        for (int i = 0; i < itemsArray.length(); i++) {
            if (itemsArray.getJSONObject(i).getString("name").equals(name)) {
                return itemsArray.getJSONObject(i).getInt("price");
            }
        }
        return -1;
    }

    String getLink(String name) {
        for (int i = 0; i < itemsArray.length(); i++) {
            if (itemsArray.getJSONObject(i).getString("name").equals(name)) {
                return itemsArray.getJSONObject(i).getString("link");
            }
        }
        return "-1";
    }

    int getIndexOfItem(String name) {
        for (int i = 0; i < itemsArray.length(); i++) {
            if (itemsArray.getJSONObject(i).getString("name").equals(name)) {
                return i;
            }
        }
        return -1;
    }

    void saveToJson() {
        try {
            FileWriter file = new FileWriter(System.getProperty("user.home") +
                    System.getProperty("file.separator") + ".tobias_wishlist" +
                    System.getProperty("file.separator") + "data.json");
            JSONObject data = new JSONObject();
            data.put("items", itemsArray);
            file.write(data.toString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        itemList = new JComboBox();
        mainPanel.add(itemList, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        priceLabel = new JLabel();
        priceLabel.setText("price");
        mainPanel.add(priceLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        getLinkButton = new JButton();
        getLinkButton.setText("Get Link");
        mainPanel.add(getLinkButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addItemButton = new JButton();
        addItemButton.setText("Add New Item");
        mainPanel.add(addItemButton, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteItemButton = new JButton();
        deleteItemButton.setText("Delete Item");
        mainPanel.add(deleteItemButton, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

    public class AddItemDialog extends JDialog {
        private JLabel nameLabel;
        private JLabel priceLabel;
        private JLabel linkLabel;

        private JTextField nameField;
        private JTextField priceField;
        private JTextField linkField;

        private JButton abortButton;
        private JButton addButton;
        private JPanel contentPane;

        public AddItemDialog() {
            this.setContentPane(contentPane);
            this.setSize(400, 200);
            abortButton.addActionListener(e -> dispose());

            addButton.addActionListener(e -> {
                if (nameField.getText().isBlank() || linkField.getText().isBlank() ||
                        !priceField.getText().matches("[0-9]+")) {
                    JOptionPane.showMessageDialog(this, "Price has to be a number", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!testIfItemExists(nameField.getText())) {
                    JSONObject dataset = new JSONObject();
                    dataset.put("name", nameField.getText());
                    dataset.put("price", Integer.parseInt(priceField.getText()));
                    dataset.put("link", linkField.getText());
                    itemsArray.put(dataset);
                    itemList.addItem(nameField.getText());
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Item already exists", "Duplication Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        boolean testIfItemExists(String name) {
            for (int i = 0; i < itemsArray.length(); i++) {
                if (name.equals(itemsArray.getJSONObject(i).getString("name"))) {
                    return true;
                }
            }
            return false;
        }


        {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
            $$$setupUI$$$();
        }

        /**
         * Method generated by IntelliJ IDEA GUI Designer
         * >>> IMPORTANT!! <<<
         * DO NOT edit this method OR call it in your code!
         *
         * @noinspection ALL
         */
        private void $$$setupUI$$$() {
            contentPane = new JPanel();
            contentPane.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));
            nameField = new JTextField();
            contentPane.add(nameField, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
            priceField = new JTextField();
            contentPane.add(priceField, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
            linkField = new JTextField();
            contentPane.add(linkField, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
            addButton = new JButton();
            addButton.setText("Add");
            contentPane.add(addButton, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            abortButton = new JButton();
            abortButton.setText("Abort");
            contentPane.add(abortButton, new com.intellij.uiDesigner.core.GridConstraints(3, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            nameLabel = new JLabel();
            nameLabel.setText("Name:");
            contentPane.add(nameLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            priceLabel = new JLabel();
            priceLabel.setText("Preis:");
            contentPane.add(priceLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            linkLabel = new JLabel();
            linkLabel.setText("Link:");
            contentPane.add(linkLabel, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        }

        /**
         * @noinspection ALL
         */
        public JComponent $$$getRootComponent$$$() {
            return contentPane;
        }
    }
}
