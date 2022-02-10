public class Rook extends Piece{
    Rook(String color){
        super.color=color;
        super.code=super.color=="White"?"\u2656":"\u265C";
        super.ID="Rook";
        super.movements=new int[][]{{0,1},{0,2},{0,3},{0,4},{0,5},{0,6},{0,7},
                {0,-1},{0,-2},{0,-3},{0,-4},{0,-5},{0,-6},{0,-7},
                {1,0},{2,0},{3,0},{4,0},{5,0},{6,0},{7,0},
                {-1,0},{-2,0},{-3,0},{-4,0},{-5,0},{-6,0},{-7,0}};
        initRangedMovement();
    }

    void initRangedMovement(){
        super.rangedMovement.add(new int[][]{{0,1},{0,2},{0,3},{0,4},{0,5},{0,6},{0,7}});
        super.rangedMovement.add(new int[][]{{0,-1},{0,-2},{0,-3},{0,-4},{0,-5},{0,-6},{0,-7}});
        super.rangedMovement.add(new int[][]{{1,0},{2,0},{3,0},{4,0},{5,0},{6,0},{7,0}});
        super.rangedMovement.add(new int[][]{{-1,0},{-2,0},{-3,0},{-4,0},{-5,0},{-6,0},{-7,0}});
    }
}
