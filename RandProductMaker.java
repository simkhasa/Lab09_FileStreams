import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

public class RandProductMaker extends JFrame {
    private JTextField idField, nameField, descriptionField, costField, recordCountField;
    private int recordCount = 0;
    private final String PATH_NAME = "src/ProductData.bin";
    private final int RECORD_SIZE = 124;

    public RandProductMaker() {
        setTitle("Random Product Maker");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        add(new JLabel("ID:"));
        idField = new JTextField();
        add(idField);

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Description:"));
        descriptionField = new JTextField();
        add(descriptionField);

        add(new JLabel("Cost:"));
        costField = new JTextField();
        add(costField);

        add(new JLabel("Record Count:"));
        recordCountField = new JTextField("0");
        recordCountField.setEditable(false);
        add(recordCountField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener((ActionEvent e) -> addProduct());
        add(addButton);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener((ActionEvent e) -> System.exit(0));
        add(quitButton);
    }

    private void addProduct() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String description = descriptionField.getText().trim();
        double cost = Double.parseDouble(costField.getText().trim());

        if (id.isEmpty() || name.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out.");
            return;
        }

        saveProductData(recordCount, name, description, id, cost);
        recordCount++;
        recordCountField.setText(String.valueOf(recordCount));

        idField.setText("");
        nameField.setText("");
        descriptionField.setText("");
        costField.setText("");
        JOptionPane.showMessageDialog(this, "Product added successfully.");
    }

    private void saveProductData(long recNum, String name, String desc, String ID, double cost) {
        try (RandomAccessFile raf = new RandomAccessFile(PATH_NAME, "rw")) {;
            raf.seek(recNum * RECORD_SIZE);
            raf.write(String.format("%35s", name).getBytes(StandardCharsets.UTF_8));
            raf.write(String.format("%65s", desc).getBytes(StandardCharsets.UTF_8));
            raf.write(String.format("%6s", ID).getBytes(StandardCharsets.UTF_8));
            raf.writeDouble(cost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new RandProductMaker().setVisible(true);
    }
}