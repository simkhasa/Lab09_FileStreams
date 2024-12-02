import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class RandProductSearch extends JFrame {
    private static final String PATH_NAME = "src/ProductData.bin";
    private static final int RECORD_SIZE = 124;
    private static ArrayList<Product> products = new ArrayList<>();
    private JTextField searchField;
    private JTextArea resultArea;

    public RandProductSearch() {
        setTitle("Random Product Search");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Search Panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(1, 2));
        inputPanel.add(new JLabel("Enter partial product name:"));
        searchField = new JTextField();
        inputPanel.add(searchField);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchProducts());
        searchPanel.add(inputPanel, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        add(searchPanel, BorderLayout.NORTH);

        // Result Area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        // Quit Button
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));
        add(quitButton, BorderLayout.SOUTH);
    }

    private void searchProducts() {
        String partialName = searchField.getText().trim().toLowerCase();
        if (partialName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a partial product name.");
            return;
        }

        ArrayList<Product> foundProducts = products.stream()
                .filter(p ->p.getName().contains(partialName))
                .collect(Collectors.toCollection(ArrayList::new));

        String result = "";
        for (Product p : foundProducts) {
            result += p + "\n";
        }
        resultArea.setText(result);
    }

    private static void readProductData(RandomAccessFile file, int recNum) {
        try {
            file.seek(recNum * RECORD_SIZE);

            byte[] nameBytes = new byte[35];
            file.read(nameBytes);
            String name = new String(nameBytes, StandardCharsets.UTF_8).trim();

            byte[] descBytes = new byte[65];
            file.read(descBytes);
            String desc = new String(descBytes, StandardCharsets.UTF_8).trim();

            byte[] idBytes = new byte[6];
            file.read(idBytes);
            String id = new String(idBytes, StandardCharsets.UTF_8).trim();

            double cost = file.readDouble();

            products.add(new Product(id, name, desc, cost));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try (RandomAccessFile raf = new RandomAccessFile(PATH_NAME, "r")) {
            for (int i = 0; i < raf.length() / RECORD_SIZE; i++) {
                readProductData(raf, i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        new RandProductSearch().setVisible(true);
    }
}