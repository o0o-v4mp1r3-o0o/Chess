import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class Board extends JFrame {
    Player playerWhite = new Player("White");
    Player playerBlack = new Player("Black");
    public static int boardState = 0;
    Pawn pawn = new Pawn();
    King king = new King();
    boolean whoseTurnIsIt = true;
    ArrayList<ChessLabel> enpassant = new ArrayList();
    boolean PieceSelected = false;
    ArrayList<ChessLabel> SelectedSquares = new ArrayList();
    Set<Integer> CheckIfLegal = new HashSet();
    HashMap<ChessLabel,ArrayList<int[]>> piecesToBlockCheck = new HashMap<>();
    private ChessLabel prevSquare;
    public ChessLabel[][] labels = new ChessLabel[][]{
            // white
            {new ChessLabel("\u265C", new Rook("Black")),
            new ChessLabel("\u265E", new Knight("Black")), new ChessLabel("\u265D", new Bishop("Black")), new ChessLabel(
            "\u265B", new Queen("Black")),
            new ChessLabel("\u265A", new King("Black")), new ChessLabel("\u265D", new Bishop("Black")), new ChessLabel(
            "\u265E",
            new Knight("Black")), new ChessLabel("\u265C", new Rook("Black"))},
            {new ChessLabel("\u265F", new Pawn("Black")), new ChessLabel("\u265F", new Pawn("Black")),
                    new ChessLabel("\u265F", new Pawn("Black")), new ChessLabel("\u265F", new Pawn("Black")),
                    new ChessLabel("\u265F", new Pawn("Black")), new ChessLabel("\u265F", new Pawn("Black")),
                    new ChessLabel("\u265F", new Pawn("Black")), new ChessLabel("\u265F", new Pawn("Black"))},
            // blank squares
            {new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "),
                    new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" ")}, {new ChessLabel(" "),
            new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "),
            new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "),
            new ChessLabel(" ")}, {new ChessLabel(" "), new ChessLabel(" "),
            new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "),
            new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" ")},
            {new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "),
                    new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" ")},
            // black♜♞♛♚
            {new ChessLabel("\u2659", new Pawn("White")), new ChessLabel("\u2659", new Pawn("White")), new ChessLabel("\u2659",
                    new Pawn("White")), new ChessLabel("\u2659", new Pawn("White")),
                    new ChessLabel("\u2659", new Pawn("White")), new ChessLabel("\u2659", new Pawn("White")),
                    new ChessLabel("\u2659", new Pawn("White")),
                    new ChessLabel("\u2659", new Pawn("White"))},
            {new ChessLabel("\u2656", new Rook("White")), new ChessLabel("\u2658", new Knight("White")),
                    new ChessLabel("\u2657", new Bishop("White")),
                    new ChessLabel("\u2655", new Queen("White")), new ChessLabel("\u2654", new King("White")), new ChessLabel("\u2657",
                    new Bishop("White")), new ChessLabel("\u2658", new Knight("White")), new ChessLabel("\u2656", new Rook("White"))}
    };
    ChessLabel[][] getLabel(){
        return labels;
    }

    ChessLabel getPrevSquare(){
        return prevSquare;
    }

    void display() {
        setTitle("Chess");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container contentPane = getContentPane();
        GridLayout gridLayout = new GridLayout(8, 8);

        contentPane.setLayout(gridLayout);

        for (int i = 0; i < labels[0].length; i++) {
            for (int row = 0; row < labels.length; row++) {
                labels[i][row].set(i, row);
                contentPane.add(labels[i][row]);
                int finalI = i;
                int finalR = row;
                labels[i][row].addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent event) {
                        System.out.println(labels[finalI][finalR].identifier);
                            PossibleMoves(finalI, finalR);
                    }
                });
                labels[i][row].identifier = i * 8 + row;
                labels[i][row].xcoord=i;
                labels[i][row].ycoord=row;
            }
        }
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    void PossibleMoves(int finalI, int finalR) {
        if(!PieceSelected) {
            if(labels[finalI][finalR].piece!=null && labels[finalI][finalR].piece.canThePlayerMoveThisPiece) {
                System.out.println(labels[finalI][finalR].identifier);
                ArrayList<int[]> movingPiecePutsKingInDanger = new ArrayList();
                if(piecesToBlockCheck.size()==0) {
                    if (whoseTurnIsIt) {
                        playerWhite.removeMoves(playerBlack.PlayerPieces, labels);
                        PawnChecker(finalI, finalR);
                        if(king.howManyPiecesAttackingKing<2) {
                            movingPiecePutsKingInDanger = playerWhite.checkIfMovePutsKingInDanger(playerBlack, labels, labels[finalI][finalR]);
                            if (movingPiecePutsKingInDanger != null) {
                                for (int[] i : movingPiecePutsKingInDanger) {
                                    ChessLabel ss = new ChessLabel(labels[i[0]][i[1]]);
                                    ss.xcoord = i[0];
                                    ss.ycoord = i[1];
                                    SelectedSquares.add(ss);
                                    CheckIfLegal.add(labels[i[0]][i[1]].identifier);
                                    labels[i[0]][i[1]].setBackground(new Color(255, 255, 0));
                                }
                            }
                        }
                    }
                    if (!whoseTurnIsIt) {
                        playerBlack.removeMoves(playerWhite.PlayerPieces, labels);
                        PawnChecker(finalI, finalR);
                        if(king.howManyPiecesAttackingKing<2) {
                            movingPiecePutsKingInDanger = playerBlack.checkIfMovePutsKingInDanger(playerWhite, labels,
                                    labels[finalI][finalR]);
                            if (movingPiecePutsKingInDanger != null) {
                                for (int[] i : movingPiecePutsKingInDanger) {
                                    ChessLabel ss = new ChessLabel(labels[i[0]][i[1]]);
                                    ss.xcoord = i[0];
                                    ss.ycoord = i[1];
                                    SelectedSquares.add(ss);
                                    CheckIfLegal.add(labels[i[0]][i[1]].identifier);
                                    labels[i[0]][i[1]].setBackground(new Color(255, 255, 0));
                                }
                            }
                        }
                    }
                }
                labels[finalI][finalR].setBackground(new Color(255, 255, 0));
                prevSquare = labels[finalI][finalR];
                prevSquare.xcoord = finalI;
                prevSquare.ycoord = finalR;
                king.canCastle(prevSquare.identifier,prevSquare,labels);
                if(piecesToBlockCheck.size()>0){
                    if(piecesToBlockCheck.containsKey(labels[finalI][finalR])){
                        ArrayList<int[]> iterate = piecesToBlockCheck.get(labels[finalI][finalR]);
                        if(!playerWhite.checkIfMovePutsKingInDangerPinned(prevSquare.piece.color.equals("White")?
                                playerBlack:playerWhite,labels,labels[finalI][finalR])) {
                            for (int i = 0; i < iterate.size(); i++) {
                                int x = iterate.get(i)[0];
                                int y = iterate.get(i)[1];
                                ChessLabel ss = new ChessLabel(labels[x][y]);
                                ss.xcoord = x;
                                ss.ycoord = y;
                                SelectedSquares.add(ss);
                                CheckIfLegal.add(labels[x][y].identifier);
                                labels[x][y].setBackground(new Color(255, 255, 0));
                            }
                        }
                    }
                }else {
                    if (movingPiecePutsKingInDanger == null) {
                        if (prevSquare.piece.rangedMovement.size() > 0) {
                            for (int i = 0; i < prevSquare.piece.rangedMovement.size(); i++) {
                                for (int d = 0; d < prevSquare.piece.rangedMovement.get(i).length; d++) {
                                    int x = prevSquare.piece.rangedMovement.get(i)[d][0];
                                    int y = prevSquare.piece.rangedMovement.get(i)[d][1];
                                    if (x + finalI >= 0 && x + finalI < 8 && y + finalR >= 0 && y + finalR < 8) {
                                        if (labels[finalI + x][finalR + y].piece == null) {
                                            ChessLabel ss = new ChessLabel(labels[finalI + x][finalR + y]);
                                            ss.xcoord = finalI + x;
                                            ss.ycoord = finalR + y;
                                            SelectedSquares.add(ss);
                                            CheckIfLegal.add(labels[finalI + x][finalR + y].identifier);
                                            labels[finalI + x][finalR + y].setBackground(new Color(255, 255, 0));
                                        } else if (!labels[finalI][finalR].piece.color.equals(labels[finalI + x][finalR + y].piece.color)) {
                                            ChessLabel ss = new ChessLabel(labels[finalI + x][finalR + y]);
                                            ss.xcoord = finalI + x;
                                            ss.ycoord = finalR + y;
                                            SelectedSquares.add(ss);
                                            CheckIfLegal.add(labels[finalI + x][finalR + y].identifier);
                                            labels[finalI + x][finalR + y].setBackground(new Color(255, 255, 0));
                                            break;
                                        } else {
                                            break;
                                        }
                                    } else {
                                        break;
                                    }
                                }
                            }
                        } else {
                            if(labels[finalI][finalR].piece.ID.equals("King")){
                                ArrayList<int[]> sy = king.whereCanKingMove(labels,this,null,labels[finalI][finalR],
                                        labels[finalI][finalR].piece.color.equals("White")?playerBlack:playerWhite,
                                        finalI,finalR
                                        ,prevSquare,null);
                                if(sy!=null) {
                                    for (int i = 0; i < sy.size(); i++){
                                        int x = sy.get(i)[0];
                                        int y = sy.get(i)[1];
                                        ChessLabel ss = new ChessLabel(labels[x][y]);
                                        ss.xcoord = x;
                                        ss.ycoord = y;
                                        SelectedSquares.add(ss);
                                        CheckIfLegal.add(labels[x][y].identifier);
                                        labels[x][y].setBackground(new Color(255, 255, 0));
                                    }
                                }
                            }else {
                                for (int i = 0; i < labels[finalI][finalR].piece.movements.length; i++) {
                                    int x = labels[finalI][finalR].piece.movements[i][0];
                                    int y = labels[finalI][finalR].piece.movements[i][1];
                                    if (x + finalI >= 0 && x + finalI < 8 && y + finalR >= 0 && y + finalR < 8) {
                                        if (labels[finalI + x][finalR + y].piece == null) {
                                            ChessLabel ss = new ChessLabel(labels[finalI + x][finalR + y]);
                                            ss.xcoord = finalI + x;
                                            ss.ycoord = finalR + y;
                                            SelectedSquares.add(ss);
                                            CheckIfLegal.add(labels[finalI + x][finalR + y].identifier);
                                            labels[finalI + x][finalR + y].setBackground(new Color(255, 255, 0));
                                        } else if (!labels[finalI][finalR].piece.color.equals(labels[finalI + x][finalR + y].piece.color)) {
                                            ChessLabel ss = new ChessLabel(labels[finalI + x][finalR + y]);
                                            ss.xcoord = finalI + x;
                                            ss.ycoord = finalR + y;
                                            SelectedSquares.add(ss);
                                            CheckIfLegal.add(labels[finalI + x][finalR + y].identifier);
                                            labels[finalI + x][finalR + y].setBackground(new Color(255, 255, 0));
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
                if(king.howManyPiecesAttackingKing>1 && labels[finalI][finalR].piece.ID.equals("King")){
                    ArrayList<int[]> sy = king.whereCanKingMove(labels,this,null,labels[finalI][finalR],
                            labels[finalI][finalR].piece.color.equals("White")?playerBlack:playerWhite,
                            finalI,finalR
                            ,prevSquare,null);
                    if(sy!=null) {
                        for (int i = 0; i < sy.size(); i++){
                            int x = sy.get(i)[0];
                            int y = sy.get(i)[1];
                            ChessLabel ss = new ChessLabel(labels[x][y]);
                            ss.xcoord = x;
                            ss.ycoord = y;
                            SelectedSquares.add(ss);
                            CheckIfLegal.add(labels[x][y].identifier);
                            labels[x][y].setBackground(new Color(255, 255, 0));
                        }
                    }
                    king.howManyPiecesAttackingKing=0;
                }
                PieceSelected = true;

            }
        }else{
            if(CheckIfLegal.contains(labels[finalI][finalR].identifier)){ //if valid move is made from selected piece
                ChessLabel currentSquare = labels[finalI][finalR];
                currentSquare.update(currentSquare.piece,finalI,finalR);
                if(prevSquare.piece.ID.equals("Pawn")) Draw.fiftyMoveMoveRule=0;
                king.howManyPiecesAttackingKing=0;
                if(piecesToBlockCheck.size()>0) piecesToBlockCheck.clear();
                if(prevSquare.piece!=null){
                    if (prevSquare.piece.color.equals("White")) {
                        king.didCastle(prevSquare,currentSquare,labels, playerWhite, this);
                    }else{
                        king.didCastle(prevSquare,currentSquare,labels, playerBlack, this);
                    }
                }


                    pawn.captureByEnpassant(prevSquare, currentSquare, enpassant,prevSquare.piece.color.equals("White")?playerBlack:playerWhite);
                    if (enpassant.size() > 0 && enpassant.get(0).piece != null) {
                        enpassant.get(0).piece.enpassant = false;
                        enpassant.clear();
                    }


                    if (currentSquare.piece != null) {
                        Draw.fiftyMoveMoveRule=0;
                        if (currentSquare.piece.color.equals("White")) {
                            playerWhite.removeCapturedPiece(playerWhite.PlayerPieces, currentSquare);
                        } else {
                            playerBlack.removeCapturedPiece(playerBlack.PlayerPieces, currentSquare);
                        }
                    }
                    ChessLabel promotedSquare = pawn.canPromote(prevSquare, currentSquare);
                    if (promotedSquare != null) {
                        currentSquare.set(finalI, finalR);
                        currentSquare.update(promotedSquare.piece, finalI, finalR);
                        currentSquare.setText(promotedSquare.piece.code);
                    } else {
                        currentSquare.set(finalI, finalR);
                        currentSquare.update(prevSquare.piece, finalI, finalR);
                        currentSquare.setText(prevSquare.piece.code);
                        if (pawn.isPawnEnPassantable(prevSquare.xcoord, currentSquare.xcoord, currentSquare)) {
                            enpassant.add(currentSquare);
                        }
                    }

                if(whoseTurnIsIt) playerWhite.updatePlayerPieces(prevSquare,currentSquare);
                if(!whoseTurnIsIt) playerBlack.updatePlayerPieces(prevSquare,currentSquare);
                if(whoseTurnIsIt) playerWhite.updateMoves(playerBlack.PlayerPieces,playerWhite.PlayerPieces,labels,
                        new ArrayList());
                if(!whoseTurnIsIt) playerBlack.updateMoves(playerWhite.PlayerPieces,playerBlack.PlayerPieces,labels,
                        new ArrayList());

                Set doesCheck = king.putsInCheck(currentSquare,finalI,finalR,labels,this,
                        prevSquare.piece.color.equals("White")?playerWhite:playerBlack, prevSquare);

                if(doesCheck.size()>0){
                    if(prevSquare.piece.color.equals("White")){
                        piecesToBlockCheck = king.piecesToBlockCheck(doesCheck,currentSquare,prevSquare,playerBlack,
                                labels,
                                this, playerWhite);
                    }else{
                        piecesToBlockCheck = king.piecesToBlockCheck(doesCheck,currentSquare,prevSquare,playerWhite,
                                labels,this, playerBlack);
                    }
                }

                prevSquare.ScrubAway();
                whoseTurnIsIt = !whoseTurnIsIt;
                currentSquare.piece.row++;
                Draw.constructThreefold(currentSquare.piece.color,playerWhite,playerBlack,enpassant,labels,this);
                if(boardState==0 && piecesToBlockCheck.size()==0) {
                    if (!Draw.doesPlayerHaveMoves(currentSquare.piece.color.equals("White") ? playerBlack : playerWhite, labels
                            , this)) {
                        JOptionPane.showMessageDialog(null, "DRAW");
                        RealBoard.initGame(this);
                    }
                }
                Draw.fifty(currentSquare,this);
            }
            //unhighlight
            for(int i = 0; i < SelectedSquares.size(); i++){
                int x = SelectedSquares.get(i).xcoord;
                int y = SelectedSquares.get(i).ycoord;
                labels[SelectedSquares.get(i).xcoord][SelectedSquares.get(i).ycoord].set(x, y);

            }
            prevSquare.set(prevSquare.xcoord,prevSquare.ycoord);
            SelectedSquares.clear();
            CheckIfLegal.clear();
            PieceSelected = false;
        }
    }
    void PawnChecker(int finalI, int finalR){
        if(labels[finalI][finalR].piece.ID.equals("Pawn")){
            if(labels[finalI][finalR].piece.color.equals("White")){
                if(labels[finalI][finalR].piece.row==2) {
                    if(CanPawnCapture(finalI,finalR,2,"White")) return;
                }else{
                    if(CanPawnCapture(finalI,finalR,1,"White")) return;
                }
            }else{
                if(labels[finalI][finalR].piece.row==2) {
                    if(CanPawnCapture(finalI,finalR,2,"Black")) return;
                }else{
                    if(CanPawnCapture(finalI,finalR,1,"Black")) return;
                }
            }
        }
    }
    boolean CanPawnCapture(int finalI, int finalR, int row,String Color){
        //NEED TO FIX H PAWN
            //if (finalI - 1 >= 0 && 1 + finalI < 8 && finalR - 1 >= 0 && 1 + finalR < 8) {
                if(row==2) {
                    if (Color.equals("White")) return captureHelper("Black",finalI,finalR,2);
                    if (Color.equals("Black")) return captureHelper("White",finalI,finalR,2);
                }else{
                    if (Color.equals("White")) return captureHelper("Black",finalI,finalR,1);
                    if (Color.equals("Black")) return captureHelper("White",finalI,finalR,1);
                }
            //}
        return false;
    }
    boolean captureHelper(String Color,int finalI, int finalR,int row) {
        if(Color.equals("White")) {
            ArrayList<int[]> addMoves = canPawnMoveForward("Black",finalI,finalR,row);
            ArrayList<int[]> addEnPassantMoves = pawn.checkIfPawnCanEnpassant(finalI,finalR,"Black",labels,
                    new ArrayList<>());
            if(addEnPassantMoves.size()>0){
                if(addMoves==null) addMoves = new ArrayList<>();
                addMoves.addAll(addEnPassantMoves);
            }
            int[][] Moves1 = new int[addMoves!=null? addMoves.size() :0][2];
            if (addMoves != null) {
                for (int i = 0; i < addMoves.size(); i++) {
                    Moves1[i][0] = addMoves.get(i)[0];
                    Moves1[i][1] = addMoves.get(i)[1];
                }
            }
            labels[finalI][finalR].piece.movements = Moves1;
            if(addMoves==null) addMoves=new ArrayList();
            if (finalR+1<8&&labels[finalI + 1][finalR + 1].piece!=null&&labels[finalI + 1][finalR + 1].piece.color.equals(Color)) addMoves.add(new int[]{1, 1});
            if (finalR-1>-1&&labels[finalI + 1][finalR - 1].piece!=null&&labels[finalI + 1][finalR - 1].piece.color.equals(Color)) addMoves.add(new int[]{1, -1});
//            if (finalR-1>-1&&finalR+1<8&&labels[finalI + 1][finalR + 1].piece!=null && labels[finalI + 1][finalR + 1].piece.color.equals(Color)
//                    || finalR-1>-1&&finalR+1<8&&labels[finalI + 1][finalR - 1].piece!=null && labels[finalI + 1][finalR - 1].piece.color.equals(Color)) {
                if(labels[finalI + 1][finalR].piece==null) {
                    addMoves.add(new int[]{1, 0});
                    if (row == 2 &&finalI + 2<8&& labels[finalI + 2][finalR].piece==null) addMoves.add(new int[]{2, 0});
                }
                int[][] Moves = new int[addMoves.size()][2];
                for (int i = 0; i < addMoves.size(); i++) {
                    Moves[i][0] = addMoves.get(i)[0];
                    Moves[i][1] = addMoves.get(i)[1];
                }
                labels[finalI][finalR].piece.movements = Moves;
                return true;
            //}
        }else{
            ArrayList<int[]> addMoves = canPawnMoveForward("White",finalI,finalR,row);
            ArrayList<int[]> addEnPassantMoves = pawn.checkIfPawnCanEnpassant(finalI,finalR,"White",labels,new ArrayList<>());
            if(addEnPassantMoves.size()>0){
                if(addMoves==null) addMoves = new ArrayList<>();
                addMoves.addAll(addEnPassantMoves);
            }
            int[][] Moves1 = new int[addMoves!=null? addMoves.size() :0][2];
            if (addMoves != null) {
                for (int i = 0; i < addMoves.size(); i++) {
                    Moves1[i][0] = addMoves.get(i)[0];
                    Moves1[i][1] = addMoves.get(i)[1];
                }
            }
            labels[finalI][finalR].piece.movements = Moves1;
            if(addMoves==null) addMoves=new ArrayList();
            if (finalR-1>-1 && labels[finalI - 1][finalR - 1].piece!=null&&labels[finalI - 1][finalR - 1].piece.color.equals(Color)) addMoves.add(new int[]{-1, -1});
            if (finalR+1<8 && labels[finalI - 1][finalR + 1].piece!=null &&labels[finalI - 1][finalR + 1].piece.color.equals(Color)) addMoves.add(new int[]{-1, 1});
//            if (finalR+1<8 &&finalR-1>-1 &&labels[finalI - 1][finalR + 1].piece!=null && labels[finalI - 1][finalR + 1].piece.color.equals(Color)
//                    || finalR+1<8 &&finalR-1>-1 &&labels[finalI - 1][finalR - 1].piece!=null && labels[finalI - 1][finalR - 1].piece.color.equals(Color)) {
                if(labels[finalI - 1][finalR].piece==null) {
                    addMoves.add(new int[]{-1, 0});
                    if (row == 2 &&finalI - 2>0&& labels[finalI - 2][finalR].piece==null) addMoves.add(new int[]{-2,
                            0});
                }
                int[][] Moves = new int[addMoves.size()][2];
                for (int i = 0; i < addMoves.size(); i++) {
                    Moves[i][0] = addMoves.get(i)[0];
                    Moves[i][1] = addMoves.get(i)[1];
                }
                labels[finalI][finalR].piece.movements = Moves;
                return true;
            //}
        }
        //return false;
    }
    ArrayList<int[]> canPawnMoveForward(String Color,int finalI, int finalR,int row){
        ArrayList<int[]> addMoves = new ArrayList();
        if(Color.equals("Black")) {
            Piece ss = labels[finalI + 1][finalR].piece;
            if (labels[finalI + 1][finalR].piece==null) {
                addMoves.add(new int[]{1, 0});
                if(finalI+2<8) {
                    if (row == 2 && labels[finalI + 2][finalR].piece == null) {
                        addMoves.add(new int[]{2, 0});
                    }
                }
                return addMoves;
            }
        }else{
            if (labels[finalI - 1][finalR].piece==null) {
                addMoves.add(new int[]{-1, 0});
                if(finalI-2>0) {
                    if (row == 2 && labels[finalI - 2][finalR].piece == null) {
                        addMoves.add(new int[]{-2, 0});
                    }
                }
                return addMoves;
            }
        }
        return null;
    }

}