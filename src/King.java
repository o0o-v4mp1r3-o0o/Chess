import javax.swing.*;
import java.awt.*;
import java.util.*;

public class King extends Piece {
    King(String color) {
        super.color = color;
        super.code = super.color == "White" ? "\u2654" : "\u265A";
        super.ID = "King";
        super.movements = new int[][]{{1, 1}, {-1, 1}, {-1, -1}, {0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, -1}};
    }

    int[][] kingCantCastleMoves = new int[][]{{1, 1}, {-1, 1}, {-1, -1}, {0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, -1}};
    int howManyPiecesAttackingKing=0;
    King() {
    }

    void canCastle(int identifier, ChessLabel King, ChessLabel[][] labels) {
        ArrayList<int[]> addMoves = new ArrayList<>();
        boolean cancastle = false;
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
                    int[][] newMoves = new int[kingCantCastleMoves.length + addMoves.size()][2];
                    int castleiterator = 0;
                    for (int[] i : newMoves) {
                        if (castleiterator < kingCantCastleMoves.length) {
                            i[0] = kingCantCastleMoves[castleiterator][0];
                            i[1] = kingCantCastleMoves[castleiterator][1];
                            castleiterator++;
                            continue;
                        } else {
                            i[0] = addMoves.get(0)[0];
                            i[1] = addMoves.get(0)[1];
                            addMoves.remove(0);
                        }
                    }
                    King.piece.movements = newMoves;
                    cancastle = true;
                }
            }
            if (!cancastle) King.piece.movements = kingCantCastleMoves;
        }
    }

    void didCastle(ChessLabel prevSquare, ChessLabel currentSquare, ChessLabel[][] labels, Player player, Board board) {
        int x = currentSquare.xcoord;
        int y = currentSquare.ycoord;
        int difference = currentSquare.ycoord - prevSquare.ycoord;
        if (prevSquare.piece.ID.equals("King")) {
            if (Math.abs(currentSquare.ycoord - prevSquare.ycoord) == 2 && currentSquare.piece == null) {
                if (prevSquare.piece.color.equals("White")) {
                    if (difference > 0) {
                        player.updatePlayerPieces(labels[x][y + 1], labels[x][y - 1]);
                        labels[x][y - 1].update(labels[x][y + 1].piece, x, y - 1);
                        labels[x][y + 1].ScrubAway();
                        labels[x][y - 1].setText("\u2656");
                    } else {
                        player.updatePlayerPieces(labels[x][y - 2], labels[x][y + 1]);
                        labels[x][y + 1].update(labels[x][y - 2].piece, x, y + 1);
                        labels[x][y - 2].ScrubAway();
                        labels[x][y + 1].setText("\u2656");
                    }
                } else {
                    if (difference > 0) {
                        player.updatePlayerPieces(labels[x][y + 1], labels[x][y - 1]);
                        labels[x][y - 1].update(labels[x][y + 1].piece, x, y - 1);
                        labels[x][y + 1].ScrubAway();
                        labels[x][y - 1].setText("\u265C");
                    } else {
                        player.updatePlayerPieces(labels[x][y - 2], labels[x][y + 1]);
                        labels[x][y + 1].update(labels[x][y - 2].piece, x, y + 1);
                        labels[x][y - 2].ScrubAway();
                        labels[x][y + 1].setText("\u265C");
                    }
                }
            }
        }
    }

    Set<Integer> putsInCheck(ChessLabel pieceInQuestion, int finalI, int finalR, ChessLabel[][] labels, Board board,
                             Player player, ChessLabel prevSquare) {

        Set<Integer> addAllMoves = new HashSet();
        if(pieceInQuestion.piece.ID.equals("King")) return addAllMoves;
        for(Map.Entry<Integer,int[]> ss : player.PlayerPieces.entrySet()) {
            int coordx = ss.getValue()[0];
            int coordy = ss.getValue()[1];
            if(labels[coordx][coordy]==prevSquare) continue;
            if (labels[coordx][coordy].piece.rangedMovement.size() > 0) {
                for (int i = 0; i < labels[coordx][coordy].piece.rangedMovement.size(); i++) {
                    Set<Integer> addMoves = new HashSet<>();
                    for (int d = 0; d < labels[coordx][coordy].piece.rangedMovement.get(i).length; d++) {
                        int x = labels[coordx][coordy].piece.rangedMovement.get(i)[d][0];
                        int y = labels[coordx][coordy].piece.rangedMovement.get(i)[d][1];
                        if (x + coordx >= 0 && x + coordx < 8 && y + coordy >= 0 && y + coordy < 8) {
                            if (labels[coordx + x][coordy + y].piece == null || labels[coordx + x][coordy + y]==prevSquare) {
                                addMoves.add(labels[coordx + x][coordy + y].identifier);
                            } else if (labels[coordx + x][coordy + y].piece != null && labels[coordx + x][coordy + y].piece.ID.equals("King") &&
                                    !labels[coordx + x][coordy + y].piece.color.equals(labels[coordx][coordy].piece.color)) {
                                addMoves.add(labels[coordx][coordy].identifier);
                                addAllMoves.addAll(addMoves);
                                howManyPiecesAttackingKing++;
                                break;
                            } else {
                                break;
                            }
                        }
                    }
                }
            } else {
                Set<Integer> addMoves = new HashSet<>();
                    if (labels[coordx][coordy].piece.ID.equals("Pawn")) {
                        if (labels[coordx][coordy].piece.color.equals("White")) {
                            int[][] addthis = new int[][]{{-1, -1}, {-1, 1}};
                            labels[coordx][coordy].piece.movements = addthis;
                        } else {
                            int[][] addthis = new int[][]{{1, -1}, {1, 1}};
                            labels[coordx][coordy].piece.movements = addthis;
                        }
                    }
                for (int i = 0; i < labels[coordx][coordy].piece.movements.length; i++) {
                    int x = labels[coordx][coordy].piece.movements[i][0];
                    int y = labels[coordx][coordy].piece.movements[i][1];
                    if (x + coordx >= 0 && x + coordx < 8 && y + coordy >= 0 && y + coordy < 8) {
                        if (labels[coordx + x][coordy + y].piece != null && labels[coordx + x][coordy + y].piece.ID.equals("King") &&
                                !labels[coordx + x][coordy + y].piece.color.equals(pieceInQuestion.piece.color)) {
                            addMoves.add(labels[coordx][coordy].identifier);
                            addAllMoves.addAll(addMoves);
                            howManyPiecesAttackingKing++;
                            break;
                        }
                    }
                }
            }
        }
        return addAllMoves;
    }

    HashMap<ChessLabel,ArrayList<int[]>> piecesToBlockCheck(Set<Integer> putsInCheck, ChessLabel current,
                                                            ChessLabel prev, Player player,
                                                          ChessLabel[][] labels,
                                                 Board board, Player oppositePlayer){
        HashMap<ChessLabel,ArrayList<int[]>> addMoves = new HashMap();
        ChessLabel King=null;
        if(howManyPiecesAttackingKing<2) {
            for (int[] ss : player.PlayerPieces.values()) {
                int coordx = ss[0];
                int coordy = ss[1];
                ChessLabel currentpiece = labels[coordx][coordy];
                board.PawnChecker(coordx, coordy);
                if(player.checkIfMovePutsKingInDangerPinned(player.PlayerColor.equals("Black")?board.playerWhite:
                                board.playerBlack, labels,
                        currentpiece)) continue;
                if (labels[coordx][coordy].piece.ID.equals("King")) {
                    King = labels[coordx][coordy];
                    continue;
                }
                if (currentpiece.piece.rangedMovement.size() > 0) {
                    for (int i = 0; i < currentpiece.piece.rangedMovement.size(); i++) {
                        for (int d = 0; d < currentpiece.piece.rangedMovement.get(i).length; d++) {
                            int x = currentpiece.piece.rangedMovement.get(i)[d][0];
                            int y = currentpiece.piece.rangedMovement.get(i)[d][1];
                            if (x + coordx >= 0 && x + coordx < 8 && y + coordy >= 0 && y + coordy < 8) {
                                if (putsInCheck.contains(labels[coordx + x][coordy + y].identifier)) {
                                    if (!addMoves.containsKey(labels[coordx][coordy])) {
                                        addMoves.put(labels[coordx][coordy], new ArrayList());
                                    }
                                    addMoves.get(labels[coordx][coordy]).add(new int[]{coordx + x, coordy + y});
                                    if(labels[coordx + x][coordy + y].piece != null) break;
                                } else if (labels[coordx + x][coordy + y].piece != null) {
                                    break;
                                } else if(labels[coordx + x][coordy + y].piece == null){
                                    continue;
                                }else {
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < currentpiece.piece.movements.length; i++) {
                        int x = currentpiece.piece.movements[i][0];
                        int y = currentpiece.piece.movements[i][1];
                        if(currentpiece.piece.ID.equals("Pawn")){
                            ArrayList enPassantMove = board.pawn.checkIfPawnCanEnpassant(coordx,coordy,
                                    currentpiece.piece.color,labels,
                                    new ArrayList());
                            if (!addMoves.containsKey(labels[coordx][coordy])&&enPassantMove.size()>0) {
                                addMoves.put(labels[coordx][coordy], new ArrayList());
                            }
                            if(enPassantMove.size()>0) addMoves.get(labels[coordx][coordy]).add(new int[]{coordx + x, coordy + y});
                        }
                        if (x + coordx >= 0 && x + coordx < 8 && y + coordy >= 0 && y + coordy < 8) {
                            if (putsInCheck.contains(labels[coordx + x][coordy + y].identifier)) {
                                if (!addMoves.containsKey(labels[coordx][coordy])) {
                                    addMoves.put(labels[coordx][coordy], new ArrayList());
                                }
                                addMoves.get(labels[coordx][coordy]).add(new int[]{coordx + x, coordy + y});
                            } else if (labels[coordx + x][coordy + y].piece != null) {
                                continue;
                            }
                        }
                    }
                }
            }
        }
            for (int[] ss : player.PlayerPieces.values()) {
                int coordx = ss[0];
                int coordy = ss[1];
                if (labels[coordx][coordy].piece.ID.equals("King")) {
                    King = labels[coordx][coordy];
                    break;
                }
            }

        ArrayList exit = whereCanKingMove(labels,board,current,King,oppositePlayer,
                King.xcoord,
                King.ycoord,prev,player);
        if(isItMate(addMoves,exit)){
            prev.ScrubAway();
            JOptionPane.showMessageDialog(null,"GAME OVER");
            RealBoard.initGame(board);
        }
        addMoves.put(King,exit);
        return addMoves;
    }

    public Set<Integer> setAttackedSquares(ChessLabel[][] labels, Board board, Player player,
                                           ChessLabel attackingPiece,ChessLabel prevSquare){
        if(attackingPiece!=null) player.PlayerPieces.put(attackingPiece.identifier,new int[]{attackingPiece.xcoord, attackingPiece.ycoord});
        Set<Integer> attackedSquares = new HashSet();
        for(Map.Entry<Integer,int[]> ss : player.PlayerPieces.entrySet()) {
            int finalI = ss.getValue()[0];
            int finalR = ss.getValue()[1];
            if(labels[finalI][finalR].piece.ID.equals("Pawn")){
                if(labels[finalI][finalR].piece.color.equals("White")){
                    int[][] addthis = new int[][]{{-1,-1},{-1,1}};
                    labels[finalI][finalR].piece.movements=addthis;
                }else{
                    int[][] addthis = new int[][]{{1,-1},{1,1}};
                    labels[finalI][finalR].piece.movements=addthis;
                }
            }
            //board.PawnChecker(finalI,finalR);
            if (labels[finalI][finalR].piece.rangedMovement.size() > 0) {
                for (int i = 0; i < labels[finalI][finalR].piece.rangedMovement.size(); i++) {
                    for (int d = 0; d < labels[finalI][finalR].piece.rangedMovement.get(i).length; d++) {
                        int x = labels[finalI][finalR].piece.rangedMovement.get(i)[d][0];
                        int y = labels[finalI][finalR].piece.rangedMovement.get(i)[d][1];
                        if (x + finalI >= 0 && x + finalI < 8 && y + finalR >= 0 && y + finalR < 8) {
                            if (labels[finalI + x][finalR + y].piece == null || labels[finalI + x][finalR + y].piece.ID.equals("King") || labels[finalI + x][finalR + y]==prevSquare) {
                                attackedSquares.add(labels[finalI + x][finalR + y].identifier);
                            } else if (!labels[finalI][finalR].piece.color.equals(labels[finalI + x][finalR + y].piece.color)) {
                                attackedSquares.add(labels[finalI + x][finalR + y].identifier);
                                break;
                            } else {
                                attackedSquares.add(labels[finalI + x][finalR + y].identifier);
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
            } else {
                //board.PawnChecker(finalI,finalR);

                for (int i = 0; i < labels[finalI][finalR].piece.movements.length; i++) {
                    int x = labels[finalI][finalR].piece.movements[i][0];
                    int y = labels[finalI][finalR].piece.movements[i][1];
                    if (x + finalI >= 0 && x + finalI < 8 && y + finalR >= 0 && y + finalR < 8) {
                        attackedSquares.add(labels[finalI + x][finalR + y].identifier);
                    }
                }
            }
        }
        return attackedSquares;
    }

    boolean isItMate(HashMap canBlock, ArrayList whereCanKingMove){
        if(canBlock.size()==0 && whereCanKingMove.size()==0){
            Board.boardState++;
            return true;
        }
        if(howManyPiecesAttackingKing>1 && whereCanKingMove.size()==0) return true;
        return false;
    }
    ArrayList<int[]> whereCanKingMove(ChessLabel[][] labels, Board board, ChessLabel attackingPiece,
                                      ChessLabel King, Player player, int finalI, int finalR, ChessLabel prevSquare,
                                      Player currplayer){
        Set<Integer> attackedSquares = setAttackedSquares(labels,board,player,attackingPiece,prevSquare);
        ArrayList<int[]> ans = new ArrayList();
        for(int i = 0; i < King.piece.movements.length; i++){
            int x = King.piece.movements[i][0];
            int y = King.piece.movements[i][1];
            if (x + finalI >= 0 && x + finalI < 8 && y + finalR >= 0 && y + finalR < 8) {
                if(labels[x + finalI][y + finalR].piece!=null && labels[x + finalI][y + finalR].piece.color.equals(King.piece.color)) continue;
                if(!attackedSquares.contains(labels[x + finalI][y + finalR].identifier)){
                    ans.add(new int[]{x + finalI,y + finalR});
                }
            }
        }
        return ans;
    }
}
