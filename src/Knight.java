public class Knight extends Piece{
    Knight(String color){
        super.color=color;
        super.code=super.color=="White"?"\u2658":"\u265E";
        super.ID="Knight";
        super.movements=new int[][]{{2,1},{2,-1},{-2,1},{-2,-1},{1,-2},{1,2},{-1,-2},{-1,2}};
    }
}
