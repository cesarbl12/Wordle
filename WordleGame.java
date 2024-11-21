import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class WordleGame extends JFrame {
    private static final int MAX_ATTEMPTS = 5;
    private static String targetWord;
    private static int currentAttempt = 0;
    private static ArrayList<JPanel> gridPanels = new ArrayList<>();
    private static JLabel displayLabel;

    public WordleGame() {
        // Leer palabras del archivo y seleccionar una palabra aleatoria
        ArrayList<String> words = loadWordsFromFile("Words.txt");
        if (words.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El archivo Words.txt está vacío o no se encuentra.");
            return;
        }
        targetWord = getRandomWord(words);

        // Crear el JFrame principal
        JFrame frame = new JFrame("Wordle Game");
         // cambiar logo de pestaña
         setIconImage(new ImageIcon(getClass().getResource("/logo.png")).getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 700);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(30, 30, 30)); // Fondo oscuro para mayor contraste
        


        // Panel para el grid 5x5 de JPanels
        JPanel gridPanel = new JPanel(new GridLayout(MAX_ATTEMPTS, 5, 5, 5));
        gridPanel.setBackground(new Color(50, 50, 50)); // Fondo del grid más oscuro
        for (int i = 0; i < MAX_ATTEMPTS * 5; i++) {
            JPanel panel = new JPanel();
            panel.setBackground(Color.WHITE); // Cuadros en blanco
            panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Bordes más gruesos
            gridPanels.add(panel);
            gridPanel.add(panel);
        }

        // Panel para mostrar las letras clickeadas
        JPanel displayPanel = new JPanel();
        displayLabel = new JLabel(" ");
        displayLabel.setFont(new Font("Arial", Font.BOLD, 24));
        displayLabel.setHorizontalAlignment(SwingConstants.CENTER);
        displayLabel.setForeground(Color.WHITE); // Letra blanca para mejor visibilidad
        displayPanel.setLayout(new BorderLayout());
        displayPanel.setPreferredSize(new Dimension(500, 50));
        displayPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        displayPanel.setBackground(new Color(60, 60, 60)); // Fondo más oscuro para el área de texto
        displayPanel.add(displayLabel, BorderLayout.CENTER);

        // Panel para el teclado de botones
        JPanel keyboardPanel = new JPanel(new GridLayout(4, 7, 5, 5));
        keyboardPanel.setBackground(new Color(50, 50, 50)); // Fondo oscuro para el teclado
        for (char c = 'A'; c <= 'Z'; c++) {
            JButton button = new JButton(String.valueOf(c));
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setBackground(new Color(90, 90, 90)); // Fondo gris para los botones
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false); // Quitar el borde de enfoque del botón
            button.setBorder(BorderFactory.createBevelBorder(0)); // Borde de botón más elegante
            button.setOpaque(true);
            keyboardPanel.add(button);

            // Acción al presionar una letra
            button.addActionListener(e -> {
                if (displayLabel.getText().length() <= 5) {
                    displayLabel.setText(displayLabel.getText() + button.getText());
                }
            });
        }

        // Botón "Borrar"
        JButton deleteButton = new JButton("Del");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.setBackground(new Color(220, 50, 50)); // Fondo rojo para el botón borrar
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        deleteButton.setOpaque(true);
        keyboardPanel.add(deleteButton);

        // Acción del botón "Borrar"
        deleteButton.addActionListener(e -> {
            String currentText = displayLabel.getText();
            if (!currentText.isEmpty()) {
                displayLabel.setText(currentText.substring(0, currentText.length() - 1));
            }
        });

        // Botón "Enter"
        JButton enterButton = new JButton("Enter");
        enterButton.setFont(new Font("Arial", Font.BOLD, 14));
        enterButton.setBackground(new Color(0, 150, 0)); // Fondo verde para el botón Enter
        enterButton.setForeground(Color.WHITE);
        enterButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        enterButton.setOpaque(true);
        keyboardPanel.add(enterButton);

        // Acción del botón "Enter"
        enterButton.addActionListener(e -> processAttempt());

        // Añadir los paneles al JFrame
        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(displayPanel, BorderLayout.NORTH);
        frame.add(keyboardPanel, BorderLayout.SOUTH);

        // Mostrar la ventana
        frame.setVisible(true);
    }

    private static ArrayList<String> loadWordsFromFile(String fileName) {
        ArrayList<String> words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.length() == 5) { // Solo palabras de 5 caracteres
                    words.add(line.toUpperCase());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

    private static String getRandomWord(ArrayList<String> words) {
        Random random = new Random();
        return words.get(random.nextInt(words.size()));
    }

    private static void processAttempt() {
        if (currentAttempt >= MAX_ATTEMPTS) {
            JOptionPane.showMessageDialog(null, "Has agotado todos tus intentos. La palabra era: " + targetWord);
            return;
        }

        String userWord = displayLabel.getText().trim(); // Asegúrate de quitar espacios en blanco
        if (userWord.length() < 5) {
            JOptionPane.showMessageDialog(null, "Debes ingresar una palabra de 5 letras.");
            return;
        }

        // Procesar cada letra ingresada
        for (int i = 0; i < 5; i++) {
            char userChar = userWord.charAt(i); // Letra actual ingresada
            JPanel currentPanel = gridPanels.get(currentAttempt * 5 + i);

            // Configurar la letra en el JPanel
            currentPanel.setLayout(new BorderLayout());
            currentPanel.removeAll(); // Eliminar cualquier componente anterior
            JLabel letterLabel = new JLabel(String.valueOf(userChar), SwingConstants.CENTER);
            letterLabel.setFont(new Font("Arial", Font.BOLD, 20));
            letterLabel.setForeground(Color.BLACK);

            currentPanel.add(letterLabel, BorderLayout.CENTER);

            // Forzar repintado para evitar cualquier problema de visualización
            currentPanel.revalidate();
            currentPanel.repaint();

            // Cambiar el color del JPanel según las condiciones
            if (targetWord.charAt(i) == userChar) {
                currentPanel.setBackground(Color.GREEN); // Letra correcta y posición correcta
            } else if (targetWord.contains(String.valueOf(userChar))) {
                currentPanel.setBackground(Color.YELLOW); // Letra correcta pero posición incorrecta
            } else {
                currentPanel.setBackground(Color.RED); // Letra incorrecta
            }
        }

        currentAttempt++; // Pasar al siguiente intento
        displayLabel.setText(""); // Limpiar el área de texto después de procesar

        // Verificar si la palabra ingresada es correcta
        if (userWord.equals(targetWord)) {
            JOptionPane.showMessageDialog(null, "¡Felicidades! Adivinaste la palabra: " + targetWord);
            System.exit(0);
        } else if (currentAttempt == MAX_ATTEMPTS) {
            JOptionPane.showMessageDialog(null, "Lo siento, has perdido. La palabra era: " + targetWord);
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WordleGame()); // Iniciar el juego
    }
}
