import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        // Configuración de la ventana principal
        setTitle("Wordle Menu");
        setSize(400, 250);  // Aumentamos el tamaño para mejor disposición
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configurar el fondo
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.setBackground(new Color(0, 153, 255)); // Color de fondo azul

        // Crear panel de título
        JLabel titleLabel = new JLabel("Wordle Game", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30)); // Fuente grande y negrita
        titleLabel.setForeground(Color.WHITE);  // Color del texto blanco
        backgroundPanel.add(titleLabel, BorderLayout.NORTH);

        // Crear panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10)); // Dos botones en columna con espacio entre ellos
        buttonPanel.setOpaque(false); // Hacer el panel transparente

        // Crear botones
        JButton playButton = new JButton("Play");
        JButton exitButton = new JButton("Exit");

        // Personalizar los botones
        customizeButton(playButton);
        customizeButton(exitButton);

        // Agregar los botones al panel
        buttonPanel.add(playButton);
        buttonPanel.add(exitButton);

        // Añadir el panel de botones al fondo
        backgroundPanel.add(buttonPanel, BorderLayout.CENTER);

        // Añadir el fondo al JFrame
        add(backgroundPanel);

        // Acción del botón "Play"
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Play button clicked"); // Depuración
        
                // Crear y mostrar la ventana de WordleGame
                SwingUtilities.invokeLater(() -> {
                    new WordleGame(); // Mostrar directamente WordleGame
                });
        
                dispose(); // Cerrar el menú principal
            }
        });

        // Acción del botón "Exit"
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Exit button clicked"); // Depuración
                System.exit(0);  // Terminar el programa
            }
        });
    }

    // Método para personalizar los botones
    private void customizeButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 20));  // Cambiar la fuente
        button.setForeground(Color.WHITE);  // Color del texto blanco
        button.setBackground(new Color(0, 102, 204)); // Color de fondo azul
        button.setFocusPainted(false);  // Quitar el borde al hacer clic
        button.setBorder(BorderFactory.createRaisedBevelBorder());  // Borde 3D
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("MenuPrincipal is being displayed"); // Depuración
                new MenuPrincipal().setVisible(true);  // Mostrar el menú principal
            }
        });
    }
}
