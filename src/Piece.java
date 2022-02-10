import java.util.ArrayList;

class Piece {
    String code = " ";
    String ID = "";
    String color;
    int[][] movements;
    int row = 2;
    boolean enpassant = false;
    boolean canThePlayerMoveThisPiece = false;
    boolean check = false;
    ArrayList<int[][]> rangedMovement = new ArrayList();
}
