public class Queen extends Piece{
    Queen(String color){
        super.color=color;
        super.code=super.color=="White"?"\u2655":"\u265B";
        super.ID="Queen";
        super.movements=new int[][]{{1,1},{2,2},{3,3},{4,4},{5,5},{6,6},{7,7},
                {1,-1},{2,-2},{3,-3},{4,-4},{5,-5},{6,-6},{7,-7},
                {-1,1},{-2,2},{-3,3},{-4,4},{-5,5},{-6,6},{-7,7},
                {-1,-1},{-2,-2},{-3,-3},{-4,-4},{-5,-5},{-6,-6},{-7,-7},
                {0,1},{0,2},{0,3},{0,4},{0,5},{0,6},{0,7},
                {0,-1},{0,-2},{0,-3},{0,-4},{0,-5},{0,-6},{0,-7},
                {1,0},{2,0},{3,0},{4,0},{5,0},{6,0},{7,0},
                {-1,0},{-2,0},{-3,0},{-4,0},{-5,0},{-6,0},{-7,0}};
        initRangedMovement();
    }
    void initRangedMovement(){
        super.rangedMovement.add(new int[][]{{1,1},{2,2},{3,3},{4,4},{5,5},{6,6},{7,7}});
        super.rangedMovement.add(new int[][]{{1,-1},{2,-2},{3,-3},{4,-4},{5,-5},{6,-6},{7,-7}});
        super.rangedMovement.add(new int[][]{{-1,-1},{-2,-2},{-3,-3},{-4,-4},{-5,-5},{-6,-6},{-7,-7}});
        super.rangedMovement.add(new int[][]{{-1,1},{-2,2},{-3,3},{-4,4},{-5,5},{-6,6},{-7,7}});
        super.rangedMovement.add(new int[][]{{0,1},{0,2},{0,3},{0,4},{0,5},{0,6},{0,7}});
        super.rangedMovement.add(new int[][]{{0,-1},{0,-2},{0,-3},{0,-4},{0,-5},{0,-6},{0,-7}});
        super.rangedMovement.add(new int[][]{{1,0},{2,0},{3,0},{4,0},{5,0},{6,0},{7,0}});
        super.rangedMovement.add(new int[][]{{-1,0},{-2,0},{-3,0},{-4,0},{-5,0},{-6,0},{-7,0}});
    }
}
