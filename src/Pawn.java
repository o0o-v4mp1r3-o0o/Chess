import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Pawn extends Piece{
    Pawn(String color){
        super.color=color;
        super.code=super.color=="White"?"\u2659":"\u265F";
        super.ID="Pawn";
    }
    Pawn(){}
    boolean isPawnEnPassantable(int prevsquare, int currSquare, ChessLabel pieceMoved){
        if(pieceMoved.piece.ID.equals("Pawn")){
            if(Math.abs(prevsquare-currSquare)==2){
                pieceMoved.piece.enpassant=true;
                return true;
            }
        }
        return false;
    }

    ArrayList<int[]> checkIfPawnCanEnpassant(int finalI, int finalR, String Color, ChessLabel[][] labels,
                                             ArrayList<int[]> addMoves){
        if(finalR+1<8 && labels[finalI][finalR+1].piece!=null) {
            if (labels[finalI][finalR + 1].piece.enpassant) {
                if (!labels[finalI][finalR + 1].piece.color.equals(Color)) {
                    if(Color.equals("White")) addMoves.add(new int[]{-1, 1});
                    if(Color.equals("Black")) addMoves.add(new int[]{1, 1});
                }
            }
        }
        if(finalR-1>-1 && labels[finalI][finalR-1].piece!=null){
            if (labels[finalI][finalR - 1].piece.enpassant) {
                if (!labels[finalI][finalR - 1].piece.color.equals(Color)) {
                    if(Color.equals("White")) addMoves.add(new int[]{-1, -1});
                    if(Color.equals("Black")) addMoves.add(new int[]{1, -1});
                }
            }
        }
        return addMoves;
    }

    void captureByEnpassant(ChessLabel prevSquare, ChessLabel currentSquare, ArrayList<ChessLabel> ep, Player player){
        if(prevSquare.piece.ID.equals("Pawn")){
            if(Math.abs(currentSquare.xcoord - prevSquare.xcoord)==1&&Math.abs(currentSquare.ycoord - prevSquare.ycoord)==1 && currentSquare.piece==null){
                player.PlayerPieces.remove(ep.get(0).identifier);
                ep.get(0).ScrubAway();
            }
        }
    }

    ChessLabel canPromote(ChessLabel prevSquare, ChessLabel currentSquare){
        if(prevSquare.piece.ID.equals("Pawn")){
            if(currentSquare.xcoord==0){
                ChessLabel newRook = new ChessLabel(new Rook("White"),currentSquare.xcoord,currentSquare.ycoord);
                ChessLabel newKnight = new ChessLabel(new Knight("White"),currentSquare.xcoord,currentSquare.ycoord);
                ChessLabel newBishop = new ChessLabel(new Bishop("White"),currentSquare.xcoord,currentSquare.ycoord);
                ChessLabel newQueen = new ChessLabel(new Queen("White"),currentSquare.xcoord,currentSquare.ycoord);
                Object[] options = { newRook.piece.code, newKnight.piece.code, newBishop.piece.code,
                        newQueen.piece.code };
                JOptionPane promo = new JOptionPane();
                promo.setFont(new Font("DejaVu Sans", Font.PLAIN, 65));
                int choice = promo.showOptionDialog(null, null, "PROMOTION",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options, options[0]);
                if(choice==0) return newRook;
                if(choice==1) return newKnight;
                if(choice==2) return newBishop;
                if(choice==3) return newQueen;
            }else if(currentSquare.xcoord==7){
                ChessLabel newRook = new ChessLabel(new Rook("Black"),currentSquare.xcoord,currentSquare.ycoord);
                ChessLabel newKnight = new ChessLabel(new Knight("Black"),currentSquare.xcoord,currentSquare.ycoord);
                ChessLabel newBishop = new ChessLabel(new Bishop("Black"),currentSquare.xcoord,currentSquare.ycoord);
                ChessLabel newQueen = new ChessLabel(new Queen("Black"),currentSquare.xcoord,currentSquare.ycoord);
                Object[] options = { newRook.piece.code, newKnight.piece.code, newBishop.piece.code,
                        newQueen.piece.code };
                JOptionPane promo = new JOptionPane();
                promo.setFont(new Font("DejaVu Sans", Font.PLAIN, 65));
                int choice = promo.showOptionDialog(null, null, "PROMOTION",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options, options[0]);
                if(choice==0) return newRook;
                if(choice==1) return newKnight;
                if(choice==2) return newBishop;
                if(choice==3) return newQueen;
            }
        }
        return null;
    }
}
