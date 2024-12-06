import processing.core.PApplet;
import java.util.Random;

public class Main extends PApplet {
    //adding comment so i can commit
    public static Main app;
    private SudokuDisplay[][] dis;
    private int disSize = 100;
    public boolean editing = false;
    private int a;
    private int b;
    private String strValue = "";
    private int input;
    private int editX;
    private int editY;
    private boolean error = false;
    private boolean done = false;

    private int bit7, bit6, bit5, bit4, bit3, bit2, bit1, bit0;

    private final int[][] genMatrix = { //for reference
            {1,1,1,1, 0,0,0,0,0,0,0,0,0,0,0,0,10},
            {0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,10},
            {0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,10},
            {0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,10},
            {1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,10},
            {0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,10},
            {0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,10},
            {0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,10},
            {1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0,10},
            {0,0,1,1,0,0,1,1,0,0,0,0,0,0,0,0,10},
            {0,0,0,0,0,0,0,0,1,1,0,0,1,1,0,0,10},
            {0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,1,10},
    };

    private int[][] userMatrix = genMatrix; //for use per user array


    public static void main(String[] args) {
        PApplet.main("Main");
    }

    public Main() {
        super();
        app = this;
    }
    /*
     * sets size of canvas
     */
    public void settings() {
        size(800,800);
    }

    /*
     * sets frame rate to slower
     * iterates over cells array, and gives each cell it's parameter
     */
    public void setup(){
        frameRate(10);
        Random rand = new Random();

        //create a 2D array
        //create a nested loop to iterate over you cells by row & colum
        //create an instance of a Cell, and add to the array
        dis = new SudokuDisplay[4][4];
        for(int row = 0; row < dis.length; row++){
            for(int column = 0; column < dis[row].length; column++){
                //CellState s = CellState.DEAD;
                SudokuDisplay c = new SudokuDisplay(200+column * disSize, 200+row * disSize, disSize, row, column,0,new int[] {1,2,3,4});
                dis[row][column] = c;
            }
        }
    }

    /*
     * makes sure that each cell displays
     */
    public void draw() {
        background(162, 205, 224);
        fill(0,0,0);
        textSize(100);
        textAlign(CENTER, CENTER);
        text("Sudoku!",400 ,100);
        fill(255);
        rect(200,200,400,400);
        noFill();

        for(int row = 0; row < dis.length; row++){
            for(int column = 0; column < dis[0].length; column++){
                dis[row][column].display();
            }
        }
        if(editing){
            fill(0,0,0);
            textSize(50);
            textAlign(CENTER, CENTER);
            editX = dis[(b - 200) / disSize][(a-200) / disSize].row +1;
            editY = dis[(b - 200) / disSize][(a-200) / disSize].column + 1;
            text("Enter a number from 1 to 4 for (" + editX +", " + editY + "):",400 ,700);
            noFill();
            dis[(b - 200) / disSize][(a-200) / disSize].setValue(input);
        }
    }

    public void makeUserMatrix(){
        /*runs when user finishes sudoku.

        takes userArray, which is presently equivalent to og genMatrix, and for every column
        that corresponds to a known value in the user-inputted array, that variable/column in
        the userMatrix is changed to 0 so that we don't have to solve for it in relationships.
        To keep those relationships/rows accurate, we delete the known value from the sum of each row.

        can go into draw with a boolean instead, but we only want this to run once so better as a method

         */
        for(int c = 0; c < 16; c++) {
            //int sudokuValue = dis[rowInSudoku][colInSudoku].getValue();
            int sudokuValue = dis[c/4][c - 4 * (c/4)].getValue(); //value of the block in the user sudoku that corresponds to column c in userMatrix
                //first row first col, c=0, row=0, col=0
                //first row last col, c=3, row=0, col=3
                //second row third col, c=6, row=1, col=2
                //last row last col, c=15, row=3, col=3
            if(sudokuValue != 0){ //if it was given by the user
                for(int r = 0; r < 12; r++){ //iterate through the rows of this chosen column
                    if(userMatrix[r][c] != 0) { //if in this row, this variable is part of the relationship
                        userMatrix[r][16] = userMatrix[r][16] - sudokuValue; //subtract known value from original total
                    }
                    userMatrix[r][c] = 0; //set this relationship to nothing in either case bc we alr know it, we don't need to solve for it
                }
            }
        }
    }

    public void updateUserMatrix(int row, int col){ //pass the INDEX of row and column of the value from the sudoku that we're guessing/just found/want to update userMatrix with
        int c = 4 * row + col; //going for the index of the column in userMatrix recall
        //ex. row 2 and col 2 is third element in third row of sudoku, where c should be 10
        int sudokuValue = dis[row][col].getValue(); //value of the block in the user sudoku that corresponds to column c in userMatrix
        if(sudokuValue != 0){ //if it was given by the user
            for(int r = 0; r < 12; r++){ //iterate through the rows of this chosen column
                if(userMatrix[r][c] != 0) { //if in this row, this variable is part of the relationship
                    userMatrix[r][16] = userMatrix[r][16] - sudokuValue; //subtract known value from original total
                }
                userMatrix[r][c] = 0; //set this relationship to nothing in either case bc we alr know it, we don't need to solve for it
            }
        }
    }




    public void mouseClicked() {
        if(mouseY >= 200 && mouseX>=200 && mouseY <= 600 && mouseX<=600 && done == false) {
            a = mouseX;
            b = mouseY;
            editing = true;
            //dis[(mouseY - 200) / disSize][(mouseX-200) / disSize].handleClick();
        }
    }


    public void keyPressed() {
        char k = key;
        int ascii = k;
        if(ascii<=52 && ascii>=49){ //if number 1,2,3,4
            input = ascii-48; //transforms to 1,2,3,4
            if((input*10)%10 > 4){ //if what?? if the user presses something with a fraction??
                input = (input*10-(input*10)%10)/10; //literally what is going on
                System.out.println("Error, does not work");
            }
            System.out.println(input);
        } else if(key == 'p'){//p for finishing the sudoku entering just because
            editing = false; //in case the user has clicked a value but hasn't entered e
            input = 0; //just to be clean
            done = true; //only time this is set to true. never set to false again. so that user can't click on sudoku and edit while solving
            makeUserMatrix();
            System.out.println("userMatrix is:   ");
            for(int[] rows: userMatrix){ //printing the array into console, mostly for scaffolding
                for(int value: rows){
                    System.out.print(value + " ");
                }
                System.out.println();
            }
        } else if(key == 's'){
            solve();
        } else if (ascii == 100){
            input = 0;
        } else if (key =='e'){
            for(int r = 0; r <4; r++){
                if(dis[r][editY-1].getValue() == input && r!= editX-1  && input != 0){
                    fill(255,0,0);
                    Main.app.textSize(25);
                    text("ERROR CAN'T HAVE TWO OF SAME NUMBER IN COLUMN",400 ,650);
                    fill(0);
                    error = true;
                }
            }
            for(int c = 0; c <4; c++){
                if(dis[editX-1][c].getValue() == input && c!= editY-1  && input != 0 && !error){
                    fill(255,0,0);
                    Main.app.textSize(25);
                    text("ERROR CAN'T HAVE TWO OF SAME NUMBER IN COLUMN",400 ,650);
                    fill(0);
                    error = true;
                    System.out.println("ERROR CAN'T HAVE TWO OF SAME NUMBER IN COLUMN");
                }
            }
            if(editX%2 == 0){
                if(editY%2 == 0){
                    if(dis[editX-2][editY-1].getValue() == input && input != 0 && !error){
                        fill(255,0,0);
                        Main.app.textSize(25);
                        text("ERROR CAN'T HAVE TWO OF SAME NUMBER IN SQUARE",400 ,650);
                        fill(0);
                        error = true;
                        System.out.println("ERROR CAN'T HAVE TWO OF SAME NUMBER IN SQUARE");
                    } else if(dis[editX-2][editY-2].getValue() == input && input != 0 && !error){
                        fill(255,0,0);
                        Main.app.textSize(25);
                        text("ERROR CAN'T HAVE TWO OF SAME NUMBER IN SQUARE",400 ,650);
                        fill(0);
                        error = true;
                        System.out.println("ERROR CAN'T HAVE TWO OF SAME NUMBER IN SQUARE");
                    } else if(dis[editX-1][editY-2].getValue() == input && input != 0 && !error){
                        fill(255,0,0);
                        Main.app.textSize(25);
                        text("ERROR CAN'T HAVE TWO OF SAME NUMBER IN SQUARE",400 ,650);
                        fill(0);
                        error = true;
                        System.out.println("ERROR CAN'T HAVE TWO OF SAME NUMBER IN SQUARE");
                    }
                } else {
                    if(dis[editX-2][editY-1].getValue() == input && input != 0 && !error){
                        fill(255,0,0);
                        Main.app.textSize(25);
                        text("ERROR CAN'T HAVE TWO OF SAME NUMBER IN SQUARE",400 ,650);
                        fill(0);
                        error = true;
                        System.out.println("ERROR CAN'T HAVE TWO OF SAME NUMBER IN SQUARE");
                    } else if(dis[editX-2][editY].getValue() == input && input != 0 && !error){
                        fill(255,0,0);
                        Main.app.textSize(25);
                        text("ERROR CAN'T HAVE TWO OF SAME NUMBER IN SQUARE",400 ,650);
                        fill(0);
                        error = true;
                        System.out.println("ERROR CAN'T HAVE TWO OF SAME NUMBER IN SQUARE");
                    } else if(dis[editX-1][editY].getValue() == input && input != 0 && !error){
                        fill(255,0,0);
                        Main.app.textSize(25);
                        text("ERROR CAN'T HAVE TWO OF SAME NUMBER IN SQUARE",400 ,650);
                        fill(0);
                        error = true;
                        System.out.println("ERROR CAN'T HAVE TWO OF SAME NUMBER IN SQUARE");
                    }
                }
            } else {
                if(editY%2 == 0){
                    if(dis[editX][editY-1].getValue() == input && input != 0 && !error){
                        fill(255,0,0);
                        Main.app.textSize(25);
                        text("ERROR CAN'T HAVE TWO OF SAME NUMBER IN SQUARE",400 ,650);
                        fill(0);
                        error = true;
                        System.out.println("ERROR CAN'T HAVE TWO OF SAME NUMBER IN SQUARE");
                    } else if(dis[editX][editY-2].getValue() == input && input != 0 && !error){
                        fill(255,0,0);
                        Main.app.textSize(25);
                        text("ERROR CAN'T HAVE TWO OF SAME NUMBER IN SQUARE",400 ,650);
                        fill(0);
                        error = true;
                        System.out.println("ERROR CAN'T HAVE TWO OF SAME NUMBER IN SQUARE");
                    } else if(dis[editX-1][editY-2].getValue() == input && input != 0 && !error){
                        fill(255,0,0);
                        Main.app.textSize(25);
                        text("ERROR CAN'T HAVE TWO OF SAME NUMBER IN SQUARE",400 ,650);
                        fill(0);
                        error = true;
                        System.out.println("ERROR CAN'T HAVE TWO OF SAME NUMBER IN SQUARE");
                    }
                } else {
                    if(dis[editX][editY-1].getValue() == input && input != 0 && !error){
                        fill(255,0,0);
                        Main.app.textSize(25);
                        text("ERROR CAN'T HAVE TWO OF SAME NUMBER IN SQUARE",400 ,650);
                        fill(0);
                        error = true;
                        System.out.println("ERROR CAN'T HAVE TWO OF SAME NUMBER IN SQUARE");
                    } else if(dis[editX][editY].getValue() == input && input != 0 && !error){
                        fill(255,0,0);
                        Main.app.textSize(25);
                        text("ERROR CAN'T HAVE TWO OF SAME NUMBER IN SQUARE",400 ,650);
                        fill(0);
                        error = true;
                        System.out.println("ERROR CAN'T HAVE TWO OF SAME NUMBER IN SQUARE");
                    } else if(dis[editX-1][editY].getValue() == input && input != 0 && !error){
                        fill(255,0,0);
                        Main.app.textSize(25);
                        text("ERROR CAN'T HAVE TWO OF SAME NUMBER IN SQUARE",400 ,650);
                        fill(0);
                        error = true;
                        System.out.println("ERROR CAN'T HAVE TWO OF SAME NUMBER IN SQUARE");
                    }
                }
            }

            if(error){
                input = 0;
                error = false;
            } else {
                editing = false;
                input = 0;
            }
        } else {
                fill(255, 0, 0);
                text("ERROR NOT VALID INPUT", 400, 650);
                fill(0);
                System.out.println(ascii);
                System.out.println("ERROR");

        }
    }

    public void solve(){
        rref(userMatrix);
        System.out.println("userMatrix is:   ");
        /*
        for(int[] rows: userMatrix){ //printing the array into console, mostly for scaffolding
            System.out.print("{");
            for(int value: rows){
                System.out.print(value + ", ");
            }
            System.out.print("},");
        }

         */

        for(int[] rows: userMatrix){ //printing the array into console, mostly for scaffolding
            for(int value: rows){
                System.out.print(value + " ");
            }
            System.out.println();
        }




    }






    public static void rref(int[][] matrix) {
        int lead = 0;
        int rowCount = matrix.length;
        int colCount = matrix[0].length;

        for (int r = 0; r < rowCount; r++) {
            if (lead >= colCount) {
                return;
            }
            int i = r;
            while (matrix[i][lead] == 0) {
                i++;
                if (i == rowCount) {
                    i = r;
                    lead++;
                    if (lead == colCount) {
                        return;
                    }
                }
            }

            swapRows(matrix, i, r);

            if (matrix[r][lead] != 0) {
                double divisor = matrix[r][lead];
                for (int j = 0; j < colCount; j++) {
                    matrix[r][j] /= divisor;
                }
            }

            for (int i2 = 0; i2 < rowCount; i2++) {
                if (i2 != r) {
                    double factor = matrix[i2][lead];
                    for (int j = 0; j < colCount; j++) {
                        matrix[i2][j] -= factor * matrix[r][j];
                    }
                }
            }

            lead++;
        }
    }

    public static void swapRows(int[][] matrix, int row1, int row2) {
        int[] temp = matrix[row1];
        matrix[row1] = matrix[row2];
        matrix[row2] = temp;
    }
}