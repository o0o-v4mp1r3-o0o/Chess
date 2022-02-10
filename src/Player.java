import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Player {
    String PlayerColor;
    HashMap<Integer,int[]> PlayerPieces = new HashMap();
    HashMap<Integer,int[]> RangedPieces = new HashMap();
    Player(String Color){
        this.PlayerColor = Color;
    }
    void initPieces(ChessLabel[][] board, Board RealBoard){
        for (int i = 0; i < board[0].length; i++) {
            for (int row = 0; row < board.length; row++) {
                if(board[i][row].piece!=null && board[i][row].piece.color.equals(PlayerColor)){
                    if(board[i][row].piece.ID.equals("Bishop") || board[i][row].piece.ID.equals("Rook") || board[i][row].piece.ID.equals("Queen")){
                        RangedPieces.put(board[i][row].identifier,new int[]{i,row});
                    }
                    PlayerPieces.put(board[i][row].identifier,new int[]{i,row});
                    if(PlayerColor.equals("White")) board[i][row].piece.canThePlayerMoveThisPiece = true;
                }
            }
        }
    }
    void updatePlayerPieces(ChessLabel previousSquare, ChessLabel currentSquare){
        PlayerPieces.remove(previousSquare.identifier);
        PlayerPieces.put(currentSquare.identifier,new int[]{currentSquare.xcoord,currentSquare.ycoord});
        if(RangedPieces.containsKey(previousSquare.identifier)){
            RangedPieces.remove(previousSquare.identifier);
            RangedPieces.put(currentSquare.identifier,new int[]{currentSquare.xcoord, currentSquare.ycoord});
        }
    }
    void removeMoves(HashMap<Integer,int[]> PlayerPieces,ChessLabel[][] board){
        for (int i = 0; i < board[0].length; i++) {
            for (int row = 0; row < board.length; row++) {
                if(PlayerPieces.containsKey(board[i][row].identifier)) {
                    board[i][row].piece.canThePlayerMoveThisPiece = false;
                }
            }
        }
    }
    void removeCapturedPiece(HashMap<Integer,int[]> PlayerPieces,ChessLabel piece){
        PlayerPieces.remove(piece.identifier);
        if(RangedPieces.containsKey(piece.identifier)){
            RangedPieces.remove(piece.identifier);
        }
    }
    void updateMoves(HashMap<Integer,int[]> PlayerPieces,HashMap<Integer,int[]> PlayerPieces1,ChessLabel[][] board,
                     ArrayList doesCheck){
        for (int i = 0; i < board[0].length; i++) {
            for (int row = 0; row < board.length; row++) {
                if(PlayerPieces.containsKey(board[i][row].identifier)) {
                    if(board[i][row].piece== null) PlayerPieces.remove(board[i][row].piece);
                    if(board[i][row].piece!= null) board[i][row].piece.canThePlayerMoveThisPiece = true;
                }
                if(PlayerPieces1.containsKey(board[i][row].identifier)) {
                    if(board[i][row].piece== null) PlayerPieces.remove(board[i][row].piece);
                    if(board[i][row].piece!= null) board[i][row].piece.canThePlayerMoveThisPiece = false;
                }
            }
        }
    }

    ArrayList<int[]> checkIfMovePutsKingInDanger(Player player, ChessLabel[][] labels, ChessLabel desiredPieceToMove){
        ArrayList<int[]> validMoves = new ArrayList<>();
        if(desiredPieceToMove.piece.ID.equals("King") || desiredPieceToMove.piece==null) return null;
        for(Map.Entry<Integer,int[]> pieceCoords : player.RangedPieces.entrySet()){
            int[] c = pieceCoords.getValue();
            for (int i = 0; i < labels[c[0]][c[1]].piece.rangedMovement.size(); i++) {
                Set<Integer> attackedSquares = new HashSet();
                attackedSquares.add(labels[c[0]][c[1]].identifier);
                for(int d = 0; d < labels[c[0]][c[1]].piece.rangedMovement.get(i).length; d++) {
                    int x = labels[c[0]][c[1]].piece.rangedMovement.get(i)[d][0];
                    int y = labels[c[0]][c[1]].piece.rangedMovement.get(i)[d][1];
                    if (x + c[0] >= 0 && x + c[0] < 8 && y + c[1] >= 0 && y + c[1] < 8) {
                        if(labels[c[0]+x][c[1]+y].piece == null) attackedSquares.add(labels[c[0]+x][c[1]+y].identifier);
                        if (labels[c[0]+x][c[1]+y].piece != null && labels[c[0]+x][c[1]+y].identifier != desiredPieceToMove.identifier) {
                            if (labels[c[0]+x][c[1]+y].piece.color.equals(labels[c[0]][c[1]].piece.color)){
                                break;
                            }else if(!labels[c[0]+x][c[1]+y].piece.color.equals(labels[c[0]][c[1]].piece.color)) {
                                attackedSquares.add(labels[c[0]+x][c[1]+y].identifier);
                                if (labels[c[0]+x][c[1]+y].piece.ID.equals("King")){
                                    for (int v = 0; v < desiredPieceToMove.piece.movements.length; v++) {
                                        int xx = desiredPieceToMove.piece.movements[v][0];
                                        int yy = desiredPieceToMove.piece.movements[v][1];
                                        int xxx = xx + desiredPieceToMove.xcoord;
                                        int yyy = yy + desiredPieceToMove.ycoord;
                                        if (xxx >= 0 && xx + xxx < 8 && yyy >= 0 && yyy < 8) {
                                            if(labels[xxx][yyy].piece!=null && labels[xxx][yyy].piece.ID.equals(
                                                    "King")) continue;
                                            if(attackedSquares.contains(labels[xxx][yyy].identifier)) {
                                                validMoves.add(new int[]{xxx, yyy});
                                            }
                                        }
                                    }
                                    return validMoves;
                                }else{
                                    attackedSquares.add(labels[c[0]+x][c[1]+y].identifier);
                                    break;
                                }
                            }
                        }
                    }else{
                        break;
                    }
                }
            }
        }
        return null;
    }
    boolean checkIfMovePutsKingInDangerPinned(Player player, ChessLabel[][] labels, ChessLabel desiredPieceToMove){
        if(desiredPieceToMove.piece.ID.equals("King") || desiredPieceToMove.piece==null) return false;
        for(Map.Entry<Integer,int[]> pieceCoords : player.RangedPieces.entrySet()){
            int[] c = pieceCoords.getValue();
            //boolean isPiecePinned = false;
            for (int i = 0; i < labels[c[0]][c[1]].piece.rangedMovement.size(); i++) {
                boolean isPiecePinned = false;
                for(int d = 0; d < labels[c[0]][c[1]].piece.rangedMovement.get(i).length; d++) {
                    int x = labels[c[0]][c[1]].piece.rangedMovement.get(i)[d][0];
                    int y = labels[c[0]][c[1]].piece.rangedMovement.get(i)[d][1];
                    if (x + c[0] >= 0 && x + c[0] < 8 && y + c[1] >= 0 && y + c[1] < 8) {
                        if(labels[c[0]+x][c[1]+y].piece!=null) {
                            if (labels[c[0] + x][c[1] + y].piece.ID.equals("King") && !labels[c[0]][c[1]].piece.color.equals(desiredPieceToMove.piece.color)) {
                                if (isPiecePinned) return true;
                            }
                            if (labels[c[0] + x][c[1] + y].piece!=null && isPiecePinned) break;
                            if (labels[c[0]][c[1]].piece.color.equals(desiredPieceToMove.piece.color)) break;

                            if (labels[c[0] + x][c[1] + y].identifier == desiredPieceToMove.identifier) {
                                isPiecePinned = true;
                            }
                        }
                    }else{
                        break;
                    }
                }
            }
        }
        return false;
    }
}
