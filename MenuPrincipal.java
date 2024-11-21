import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        setTitle("Menu Principal");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear botones
        JButton playButton = new JButton("Play");
        JButton exitButton = new JButton("Exit");

        // Crear panel para los botones
        JPanel panel = new JPanel();
        panel.add(playButton);
        panel.add(exitButton);

        // Añadir el panel al JFrame
        add(panel);

        // Acción del botón "Play"
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Play button clicked"); // Depuración
                // Crear y mostrar la ventana de WordleGame
                WordleGame wordleGame = new WordleGame();
                wordleGame.setVisible(true);  // Mostrar la ventana de WordleGame
                dispose();  // Cerrar el menú principal
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
