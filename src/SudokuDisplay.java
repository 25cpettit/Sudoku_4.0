/*
 * The purpose of the Cell class is to define each cell and make sure they follow the rules that are provided for them
 */
public class SudokuDisplay {
    //adding comment so i can commit
    private int x;
    private int y;
    private int size;
    public int row;
    public int column;
    private int value;
    private int[] psblvalues;


    public SudokuDisplay (int x, int y, int size, int row, int column,int value, int[] psblvalues){
        super();
        this.x = x;
        this.y = y;
        this.size = size;
        this.row = row;
        this.column = column;
        this.value = value;
        this.psblvalues = psblvalues;
    }
    /*
     * applyRules makes the rules be applied to each cell. In Main, it is called to iterate over each cell, so their rules are followed.
     * @param cells is the array of cells that is seen on screen
     */

    public int getValue(){
        return value;
    }
    public void setValue(int a){
        value = a;
    }
    /*
     * gets called over each cell to make it pop up on screen & sets it's color depending on it's state
     */
    public void display(){
        Main.app.rect(x, y, size, size);
        Main.app.fill(0);
        Main.app.textSize(50);
        Main.app.textAlign(Main.app.CENTER,Main.app.CENTER);
        if(value == 0){
            Main.app.fill(255, 255, 255);
        }
        Main.app.text(value,x+50,y+50);
        Main.app.noFill();
    }

    public void removePsbl(int index){
    }

    /*
     * returns state of cell
     * @return cellState is telling whatever calls it the CellState it is currently at
     */

}
