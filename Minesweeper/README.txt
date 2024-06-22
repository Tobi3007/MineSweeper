Minesweeper Game
This is a simple Minesweeper game implemented in Java using Swing for the graphical user interface. Follow the instructions below to compile and run the game on your local machine.

Prerequisites
Java Development Kit (JDK): Ensure you have JDK 8 or later installed. You can download it from the Oracle website or use OpenJDK.
Project Structure
The project structure is as follows:

Minesweeper/
│
├── src/
│   └── game/
│       ├── Board.java
│       └── Minesweeper.java
│
├── resources/
│   ├── 0.png
│   ├── 1.png
│   ├── 2.png
│   ├── 3.png
│   ├── 4.png
│   ├── 5.png
│   ├── 6.png
│   ├── 7.png
│   ├── 8.png
│   ├── 9.png
│   ├── 10.png
│   ├── 11.png
│   └── 12.png
└── README.txt

1.Compilation
Navigate to the Project Directory: Open your terminal or command prompt and navigate to the Minesweeper directory where the src folder is located.

cd path/to/Minesweeper

2.Compile the Java Files: Use the javac command to compile the .java files in the src directory. The command below compiles all .java files and places the .class files in a bin directory.

mkdir -p bin
javac -d bin -sourcepath src src/game/*.java

-d bin: Specifies the output directory for the compiled .class files.
-sourcepath src: Specifies the source directory for the .java files.

Running the Game
Navigate to the bin Directory: Move to the bin directory where the compiled files are located.

cd bin

Run the Game: Use the java command to run the Minesweeper class. Ensure you are in the bin directory before running this command.

java game.Minesweeper

Troubleshooting
-Class Not Found: If you encounter a ClassNotFoundException, ensure that the src directory is properly structured and that you compiled the .java files correctly.
-Images Not Loading: Ensure that the images are placed correctly in the resources directory and that their paths in the Board class match the actual paths.
Customizing the Game
-Change Board Size: You can modify the N_ROWS and N_COLS constants in the Board class to change the size of the game board.
-Change Number of Mines: Modify the N_MINES constant in the Board class to adjust the difficulty by changing the number of mines.

Notes
-Resizing: The game window is set to be non-resizable to maintain the layout and avoid graphical issues.
-Graphics: Ensure the .png files for the cell states are available in the resources directory as expected by the game.
Enjoy playing Minesweeper!