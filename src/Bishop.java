public class Bishop extends Piece{
    Bishop(String color){
        super.color=color;
        super.code=super.color=="White"?"\u2657":"\u265D";
        super.ID="Bishop";
        super.movements=new int[][]{{1,1},{2,2},{3,3},{4,4},{5,5},{6,6},{7,7},
                {1,-1},{2,-2},{3,-3},{4,-4},{5,-5},{6,-6},{7,-7},
                {-1,1},{-2,2},{-3,3},{-4,4},{-5,5},{-6,6},{-7,7},
                {-1,-1},{-2,-2},{-3,-3},{-4,-4},{-5,-5},{-6,-6},{-7,-7}};
        initRangedMovement();
    }
    void initRangedMovement(){
        super.rangedMovement.add(new int[][]{{1,1},{2,2},{3,3},{4,4},{5,5},{6,6},{7,7}});
        super.rangedMovement.add(new int[][]{{1,-1},{2,-2},{3,-3},{4,-4},{5,-5},{6,-6},{7,-7}});
        super.rangedMovement.add(new int[][]{{-1,-1},{-2,-2},{-3,-3},{-4,-4},{-5,-5},{-6,-6},{-7,-7}});
        super.rangedMovement.add(new int[][]{{-1,1},{-2,2},{-3,3},{-4,4},{-5,5},{-6,6},{-7,7}});
    }
}
