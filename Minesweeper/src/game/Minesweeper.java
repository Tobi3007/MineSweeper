//Doan Nhan Hau _ ITCSIU21129
package game;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * The Minesweeper class sets up the main window for the Minesweeper game.
 * It creates a JFrame containing a game board and a status label.
 */

public class Minesweeper extends JFrame {
    private JLabel status;
    
    /**
     * Constructs the Minesweeper game window.
     * Initializes the status label and adds the game board.
     */
    public Minesweeper() {
        // Initialize the status label
        status = new JLabel("");

        // Add the status label at the bottom of the window
        add(status, BorderLayout.SOUTH);

        // Create and add the game board
        add(new Board(status));

        // Prevent resizing the window to maintain the game layout
        setResizable(false);

        // Pack the components within the frame
        pack();

        // Set the title of the window
        setTitle("Minesweeper");

        // Center the window on the screen
        setLocationRelativeTo(null);

        // Close the application when the window is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * The main method launches the Minesweeper game.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        // Create an instance of Minesweeper and make it visible
        Minesweeper ms = new Minesweeper();
        ms.setVisible(true);
    }
}
