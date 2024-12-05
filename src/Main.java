import processing.core.PApplet;
import java.util.Random;

public class Main extends PApplet {
    //adding comment so i can commit
    public static Main app;
    private SudokuDisplay[][] dis;
    private Solver[][] solve;
    private int disSize = 100;
    public boolean editing = false;
    private int a;
    private int b;
    private String strValue = "";
    private int input;
    private int editX;
    private int editY;
    private boolean error = false;

    private int bit7, bit6, bit5, bit4, bit3, bit2, bit1, bit0;


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
                CellState s = CellState.DEAD;
                SudokuDisplay c = new SudokuDisplay(200+column * disSize, 200+row * disSize, disSize, row, column,0);
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


    public void mouseClicked() {
        if(mouseY >= 200 && mouseX>=200 && mouseY <= 600 && mouseX<=600) {
            a = mouseX;
            b = mouseY;
            editing = true;
            //dis[(mouseY - 200) / disSize][(mouseX-200) / disSize].handleClick();
        }
    }

    public void takeUserInput(){
    }


    public void keyPressed() {
        char k = key;
        int ascii = k;
        if(ascii<=52 && ascii>=49){
            input = ascii-48;
            if((input*10)%10 > 4){
                input = (input*10-(input*10)%10)/10;
                System.out.println("Error, does not work");
            }
            System.out.println(input);
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
            if(input != 0) {
                fill(255, 0, 0);
                text("ERROR NOT VALID INPUT", 400, 650);
                fill(0);
                System.out.println(ascii);
                System.out.println("ERROR");
            }
        }
    }
}