// Doan Nhan Hau_ITCSIU21129

package game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The Board class represents the game board for a Minesweeper game.
 * It manages the game state, handles user input, and updates the display.
 */
public class Board extends JPanel {

    // Number of images used for different cell states
    private final int NUM_IMAGES = 13;
    // Size of each cell in the grid
    private final int CELL_SIZE = 15;

    // Constants representing cell states
    private final int COVER_FOR_CELL = 10;
    private final int MARK_FOR_CELL = 10;
    private final int EMPTY_CELL = 0;
    private final int MINE_CELL = 9;
    private final int COVERED_MINE_CELL = MINE_CELL + COVER_FOR_CELL;
    private final int MARKED_MINE_CELL = COVERED_MINE_CELL + MARK_FOR_CELL;

    // Constants for drawing cell states
    private final int DRAW_MINE = 9;
    private final int DRAW_COVER = 10;
    private final int DRAW_MARK = 11;
    private final int DRAW_WRONG_MARK = 12;

    // Game configuration constants
    private final int N_MINES = 40;
    private final int N_ROWS = 16;
    private final int N_COLS = 16;

    // Board dimensions
    private final int BOARD_WIDTH = N_COLS * CELL_SIZE + 1;
    private final int BOARD_HEIGHT = N_ROWS * CELL_SIZE + 1;

    // Array to store the state of each cell
    private int[] field;
    // Game state flag
    private boolean inGame;
    // Counter for remaining mines
    private int minesLeft;
    // Array to store images for different cell states
    private Image[] img;

    // Total number of cells
    private int allCells;
    // Label to display game status
    private final JLabel status;   

    /**
     * Constructor for Board.
     * @param status The JLabel to display the game status.
     */
    public Board(JLabel status) {
        this.status = status;
        initBoard();
    }

    /**
     * Initializes the game board.
     * Loads images and sets up the board dimensions.
     */
    private void initBoard() {
        // Set the preferred size of the game board
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        img = new Image[NUM_IMAGES];

        // Load images for cell states
        for (int i = 0; i < NUM_IMAGES; i++) {
            var path = "src/resources/" + i + ".png";
            img[i] = (new ImageIcon(path)).getImage();
        }

        // Add mouse listener to handle mouse input
        addMouseListener(new MinesAdapter());
        // Start a new game
        newGame();
    }

    /**
     * Starts a new game.
     * Resets the game state, places mines, and updates the status.
     */
    private void newGame() {
        int cell;
        var random = new Random();
        inGame = true;
        minesLeft = N_MINES;
        allCells = N_ROWS * N_COLS;
        field = new int[allCells];

        // Cover all cells initially
        for (int i = 0; i < allCells; i++) {
            field[i] = COVER_FOR_CELL;
        }

        // Update the status label
        status.setText(Integer.toString(minesLeft));

        // Place mines randomly on the board
        int i = 0;
        while (i < N_MINES) {
            int position = (int) (allCells * random.nextDouble());

            if ((position < allCells) && (field[position] != COVERED_MINE_CELL)) {
                int current_col = position % N_COLS;
                field[position] = COVERED_MINE_CELL;
                i++;

                // Increment the mine count in surrounding cells
                if (current_col > 0) {
                    cell = position - 1 - N_COLS;
                    if (cell >= 0 && field[cell] != COVERED_MINE_CELL) {
                        field[cell] += 1;
                    }
                    cell = position - 1;
                    if (cell >= 0 && field[cell] != COVERED_MINE_CELL) {
                        field[cell] += 1;
                    }
                    cell = position + N_COLS - 1;
                    if (cell < allCells && field[cell] != COVERED_MINE_CELL) {
                        field[cell] += 1;
                    }
                }

                cell = position - N_COLS;
                if (cell >= 0 && field[cell] != COVERED_MINE_CELL) {
                    field[cell] += 1;
                }

                cell = position + N_COLS;
                if (cell < allCells && field[cell] != COVERED_MINE_CELL) {
                    field[cell] += 1;
                }

                if (current_col < (N_COLS - 1)) {
                    cell = position - N_COLS + 1;
                    if (cell >= 0 && field[cell] != COVERED_MINE_CELL) {
                        field[cell] += 1;
                    }
                    cell = position + N_COLS + 1;
                    if (cell < allCells && field[cell] != COVERED_MINE_CELL) {
                        field[cell] += 1;
                    }
                    cell = position + 1;
                    if (cell < allCells && field[cell] != COVERED_MINE_CELL) {
                        field[cell] += 1;
                    }
                }
            }
        }
    }

    /**
     * Recursively uncovers empty cells around a given cell.
     * @param j The index of the cell to start uncovering from.
     */
    private void find_empty_cells(int j) {
        int current_col = j % N_COLS;
        int cell;

        if (current_col > 0) {
            cell = j - N_COLS - 1;
            if (cell >= 0 && field[cell] > MINE_CELL) {
                field[cell] -= COVER_FOR_CELL;
                if (field[cell] == EMPTY_CELL) {
                    find_empty_cells(cell);
                }
            }
            cell = j - 1;
            if (cell >= 0 && field[cell] > MINE_CELL) {
                field[cell] -= COVER_FOR_CELL;
                if (field[cell] == EMPTY_CELL) {
                    find_empty_cells(cell);
                }
            }
            cell = j + N_COLS - 1;
            if (cell < allCells && field[cell] > MINE_CELL) {
                field[cell] -= COVER_FOR_CELL;
                if (field[cell] == EMPTY_CELL) {
                    find_empty_cells(cell);
                }
            }
        }

        cell = j - N_COLS;
        if (cell >= 0 && field[cell] > MINE_CELL) {
            field[cell] -= COVER_FOR_CELL;
            if (field[cell] == EMPTY_CELL) {
                find_empty_cells(cell);
            }
        }

        cell = j + N_COLS;
        if (cell < allCells && field[cell] > MINE_CELL) {
            field[cell] -= COVER_FOR_CELL;
            if (field[cell] == EMPTY_CELL) {
                find_empty_cells(cell);
            }
        }

        if (current_col < (N_COLS - 1)) {
            cell = j - N_COLS + 1;
            if (cell >= 0 && field[cell] > MINE_CELL) {
                field[cell] -= COVER_FOR_CELL;
                if (field[cell] == EMPTY_CELL) {
                    find_empty_cells(cell);
                }
            }
            cell = j + N_COLS + 1;
            if (cell < allCells && field[cell] > MINE_CELL) {
                field[cell] -= COVER_FOR_CELL;
                if (field[cell] == EMPTY_CELL) {
                    find_empty_cells(cell);
                }
            }
            cell = j + 1;
            if (cell < allCells && field[cell] > MINE_CELL) {
                field[cell] -= COVER_FOR_CELL;
                if (field[cell] == EMPTY_CELL) {
                    find_empty_cells(cell);
                }
            }
        }
    }

    /**
     * Draws the game board.
     * @param g The Graphics context in which to paint.
     */
    @Override
    public void paintComponent(Graphics g) {
        // Counter for uncovered cells
        int uncover = 0;

        // Iterate over each cell to draw its current state
        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < N_COLS; j++) {
                int cell = field[(i * N_COLS) + j];

                // End game if a mine is uncovered
                if (inGame && cell == MINE_CELL) {
                    inGame = false;
                }

                // Determine the cell's image based on the game state
                if (!inGame) {
                    if (cell == COVERED_MINE_CELL) {
                        cell = DRAW_MINE;
                    } else if (cell == MARKED_MINE_CELL) {
                        cell = DRAW_MARK;
                    } else if (cell > COVERED_MINE_CELL) {
                        cell = DRAW_WRONG_MARK;
                    } else if (cell > MINE_CELL) {
                        cell = DRAW_COVER;
                    }
                } else {
                    if (cell > COVERED_MINE_CELL) {
                        cell = DRAW_MARK;
                    } else if (cell > MINE_CELL) {
                        cell = DRAW_COVER;
                        uncover++;
                    }
                }

                // Draw the image for the cell
                g.drawImage(img[cell], (j * CELL_SIZE), (i * CELL_SIZE), this);
            }
        }

        // Update the game status based on the game state
        if (uncover == 0 && inGame) {
            inGame = false;
            status.setText("You won!");
        } else if (!inGame) {
            status.setText("Game Over!");
        }
    }

    /**
     * Inner class to handle mouse events.
     */
    private class MinesAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            // Calculate the clicked cell's row and column
            int cCol = x / CELL_SIZE;
            int cRow = y / CELL_SIZE;

            boolean doRepaint = false;

            // Start a new game if the current game is over
            if (!inGame) {
                newGame();
                repaint();
            }

            // Check if the click is within the board
            if ((x < N_COLS * CELL_SIZE) && (y < N_ROWS * CELL_SIZE)) {
                // Handle right-click (marking a cell)
                if (e.getButton() == MouseEvent.BUTTON3) {
                    if (field[(cRow * N_COLS) + cCol] > MINE_CELL) {
                        doRepaint = true;

                        if (field[(cRow * N_COLS) + cCol] <= COVERED_MINE_CELL) {
                            if (minesLeft > 0) {
                                field[(cRow * N_COLS) + cCol] += MARK_FOR_CELL;
                                minesLeft--;
                                String msg = Integer.toString(minesLeft);
                                status.setText(msg);
                            } else {
                                status.setText("No marks left");
                            }
                        } else {
                            field[(cRow * N_COLS) + cCol] -= MARK_FOR_CELL;
                            minesLeft++;
                            String msg = Integer.toString(minesLeft);
                            status.setText(msg);
                        }
                    }
                } else {
                    // Handle left-click (uncovering a cell)
                    if (field[(cRow * N_COLS) + cCol] > COVERED_MINE_CELL) {
                        return;
                    }

                    if ((field[(cRow * N_COLS) + cCol] > MINE_CELL)
                            && (field[(cRow * N_COLS) + cCol] < MARKED_MINE_CELL)) {
                        field[(cRow * N_COLS) + cCol] -= COVER_FOR_CELL;
                        doRepaint = true;

                        // End game if a mine is uncovered
                        if (field[(cRow * N_COLS) + cCol] == MINE_CELL) {
                            inGame = false;
                        }

                        // Recursively uncover empty cells
                        if (field[(cRow * N_COLS) + cCol] == EMPTY_CELL) {
                            find_empty_cells((cRow * N_COLS) + cCol);
                        }
                    }
                }

                // Repaint the board if necessary
                if (doRepaint) {
                    repaint();
                }
            }
        }
    }
}
