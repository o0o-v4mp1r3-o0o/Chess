import javax.swing.*;
import java.util.*;

public class Draw {
    public static HashMap<String, Integer> threeFoldRep = new HashMap();
    static King king = new King();
    public static int fiftyMoveMoveRule = 0;
    public static void reset(){
        threeFoldRep.clear();
        fiftyMoveMoveRule=0;
    }
    public static void constructThreefold(String Color, Player white, Player black, ArrayList<ChessLabel> enpassant,
                                          ChessLabel[][] labels, Board board) {
        StringBuilder ss = new StringBuilder();
        ss.append(Color);
        PriorityQueue<Map.Entry<Integer, int[]>> sortPieces = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getKey));
        sortPieces.addAll(white.PlayerPieces.entrySet());
        sortPieces.addAll(black.PlayerPieces.entrySet());

        while (!sortPieces.isEmpty()) {
            Map.Entry<Integer, int[]> f = sortPieces.poll();
            String x = String.valueOf(f.getKey());
            String xx = f.getValue()[0] + "" + f.getValue()[1];
            String xxx =
                    labels[f.getValue()[0]][f.getValue()[1]]!=null?labels[f.getValue()[0]][f.getValue()[1]].piece.ID:
                            "null";
            if (xxx.equals("King")) {
                ss.append(canCastle(f.getKey(),labels[f.getValue()[0]][f.getValue()[1]],labels).size());
            }
            if(xxx.equals("Pawn")){
                ss.append(checkIfPawnCanEnpassant(f.getValue()[0],f.getValue()[1],
                        labels[f.getValue()[0]][f.getValue()[1]].piece.color,labels));
            }
            String xxxx = labels[f.getValue()[0]][f.getValue()[1]].piece.color;
            ss.append(x + xx + xxx + xxxx);
        }
        threeFoldRep.put(ss.toString(),threeFoldRep.getOrDefault(ss.toString(),0)+1);
        System.out.println(threeFoldRep.toString());
        if(threeFoldRep.get(ss.toString())>2){
            JOptionPane.showMessageDialog(null,"DRAW");
            RealBoard.initGame(board);
        }
    }

    public static ArrayList<int[]> canCastle(int identifier, ChessLabel King, ChessLabel[][] labels) {
        ArrayList<int[]> addMoves = new ArrayList<>();
        if (King.piece.ID.equals("King")) {
            if (identifier == 4 || identifier == 60) {
                if (King.piece.row == 2) {
                    if (labels[King.xcoord][King.ycoord + 3].piece != null) {
                        if (labels[King.xcoord][King.ycoord + 3].piece.ID.equals("Rook") && labels[King.xcoord][King.ycoord + 3].piece.row == 2) {
                            if (labels[King.xcoord][King.ycoord + 1].piece == null && labels[King.xcoord][King.ycoord + 2].piece == null) {
                                if (King.piece.check == false) addMoves.add(new int[]{0, 2});
                            }
                        }
                    }
                    if (labels[King.xcoord][King.ycoord - 4].piece != null) {
                        if (labels[King.xcoord][King.ycoord - 4].piece.ID.equals("Rook") && labels[King.xcoord][King.ycoord - 4].piece.row == 2) {
                            if (labels[King.xcoord][King.ycoord - 3].piece == null && labels[King.xcoord][King.ycoord - 2].piece == null &&
                                    labels[King.xcoord][King.ycoord - 1].piece == null) {
                                if (King.piece.check == false) addMoves.add(new int[]{0, -2});
                            }
                        }
                    }
                }
            }
        }
        return addMoves;
    }

    public static boolean checkIfPawnCanEnpassant(int finalI, int finalR, String Color, ChessLabel[][] labels){
        if(finalR+1<8 && labels[finalI][finalR+1].piece!=null) {
            if (labels[finalI][finalR + 1].piece.enpassant) {
                if (!labels[finalI][finalR + 1].piece.color.equals(Color)) {
                    return true;
                }
            }
        }
        if(finalR-1>-1 && labels[finalI][finalR-1].piece!=null){
            if (labels[finalI][finalR - 1].piece.enpassant) {
                if (!labels[finalI][finalR - 1].piece.color.equals(Color)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean doesPlayerHaveMoves(Player player, ChessLabel[][] labels, Board board){
        for (int[] ss : player.PlayerPieces.values()) {
            int coordx = ss[0];
            int coordy = ss[1];
            ChessLabel currentpiece = labels[coordx][coordy];
            board.PawnChecker(coordx, coordy);
            if(!player.checkIfMovePutsKingInDangerPinned(player.PlayerColor.equals("Black")?board.playerWhite:
                            board.playerBlack, labels,
                    currentpiece)) {
                if (currentpiece.piece.rangedMovement.size() > 0) {
                    for (int i = 0; i < currentpiece.piece.rangedMovement.size(); i++) {
                        for (int d = 0; d < currentpiece.piece.rangedMovement.get(i).length; d++) {
                            int x = currentpiece.piece.rangedMovement.get(i)[d][0];
                            int y = currentpiece.piece.rangedMovement.get(i)[d][1];
                            if (x + coordx >= 0 && x + coordx < 8 && y + coordy >= 0 && y + coordy < 8) {
                                if (labels[coordx + x][coordy + y].piece == null) {
                                    return true;
                                } else if (labels[coordx + x][coordy + y].piece != null && !labels[coordx + x][coordy + y].piece.color.equals(labels[coordx][coordy].piece.color)) {
                                    return true;
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < currentpiece.piece.movements.length; i++) {
                        int x = currentpiece.piece.movements[i][0];
                        int y = currentpiece.piece.movements[i][1];
                        if (currentpiece.piece.ID.equals("King")) {
                            ArrayList<int[]> canMove = king.whereCanKingMove(labels, board, null,
                                    labels[coordx][coordy],
                                    player.PlayerColor.equals("White") ? board.playerBlack : board.playerWhite, coordx, coordy,
                                    null,
                                    null);
                            if (canMove.size() > 0) return true;
                        } else if (x + coordx >= 0 && x + coordx < 8 && y + coordy >= 0 && y + coordy < 8) {
                            if (labels[coordx + x][coordy + y].piece == null) return true;
                            if (labels[coordx + x][coordy + y].piece != null && !labels[coordx + x][coordy + y].piece.color.equals(currentpiece.piece.color)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    public static void fifty(ChessLabel curr,Board board){
        if(curr.piece.ID.equals("Pawn")) fiftyMoveMoveRule=0;
        fiftyMoveMoveRule++;
        if(fiftyMoveMoveRule==51){
            JOptionPane.showMessageDialog(null,"DRAW");
            RealBoard.initGame(board);
        }
        System.out.println(fiftyMoveMoveRule);
    }
}
