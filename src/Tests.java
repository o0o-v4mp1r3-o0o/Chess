public class Tests {
    public static void main(String[] args) {
        Tests test = new Tests();
        test.doubleDiscoveredCheck();
    }
    void NoMovesLeftDrawSingleKing(){
        Board board = new Board();
        board.labels = new ChessLabel[][]{
                // white
                {new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" ")},
                {new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" ")},
                // blank squares
                {new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" ")},
                {new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" ")},
                {new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" ")},
                {new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" ")},
                // black♜♞♛♚
                {new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel("\u2655", new Queen("White")), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" ")},
                {new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel("\u2654", new King("White")), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel("\u265A", new King("Black"))}
        };
        board.display();
        board.playerWhite.initPieces(board.getLabel(),board);
        board.playerBlack.initPieces(board.getLabel(),board);
        Draw.constructThreefold("Black", board.playerWhite, board.playerBlack, board.enpassant, board.getLabel(),board);
    }
    void NoMovesLeftDrawPawns(){
        Board board = new Board();
        board.labels = new ChessLabel[][]{
                // white
                {new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "),
                        new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" ")},
                {new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "),
                        new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" ")},
                // blank squares
                {new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "),
                        new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" ")}, {new ChessLabel(" "),
                new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "),
                new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "),
                new ChessLabel(" ")}, {new ChessLabel(" "), new ChessLabel(" "),
                new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "),
                new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" ")},
                {new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "),
                        new ChessLabel(" "), new ChessLabel(" "), new ChessLabel("\u2659", new Pawn("White"))},
                // black♜♞♛♚
                {new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "),
                        new ChessLabel("\u2655", new Queen("White")), new ChessLabel(" "),
                        new ChessLabel(" "),
                        new ChessLabel("\u265F", new Pawn("Black"))},
                {new ChessLabel(" "), new ChessLabel(" "),
                        new ChessLabel(" "),
                        new ChessLabel(" "), new ChessLabel("\u2654", new King("White")), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel("\u265A", new King("Black"))}
        };
        board.display();
        board.playerWhite.initPieces(board.getLabel(),board);
        board.playerBlack.initPieces(board.getLabel(),board);
        Draw.constructThreefold("Black", board.playerWhite, board.playerBlack, board.enpassant, board.getLabel(),board);
    }
    void drawWithPin(){
        Board board = new Board();
        board.labels = new ChessLabel[][]{
                // white
                {new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "),
                        new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" ")},
                {new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "),
                        new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" ")},
                // blank squares
                {new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "),
                        new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" ")}, {new ChessLabel(" "),
                new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "),
                new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "),
                new ChessLabel(" ")}, {new ChessLabel(" "), new ChessLabel(" "),
                new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "),
                new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" ")},
                {new ChessLabel("\u265F", new Pawn("Black")), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "),
                        new ChessLabel(" "), new ChessLabel(" "), new ChessLabel("\u2659", new Pawn("White"))},
                // black♜♞♛♚
                {new ChessLabel("\u2659", new Pawn("White")), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "),
                        new ChessLabel("\u2655", new Queen("White")), new ChessLabel(" "),
                        new ChessLabel(" "),
                        new ChessLabel("\u265F", new Pawn("Black"))},
                {new ChessLabel(" "), new ChessLabel(" "),
                        new ChessLabel(" "),
                        new ChessLabel(" "), new ChessLabel("\u2654", new King("White")), new ChessLabel("\u2656", new Rook("White")), new ChessLabel("\u265E", new Knight("Black")), new ChessLabel("\u265A", new King("Black"))}
        };
        board.display();
        board.playerWhite.initPieces(board.getLabel(),board);
        board.playerBlack.initPieces(board.getLabel(),board);
        Draw.constructThreefold("Black", board.playerWhite, board.playerBlack, board.enpassant, board.getLabel(),board);
    }
    void doubleDiscoveredCheck(){
            Board board = new Board();
            board.labels = new ChessLabel[][]{
                    // white
                    {new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel("\u265A", new King("Black")), new ChessLabel(" ")},
                    {new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" ")},
                    // blank squares
                    {new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" ")},
                    {new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" ")},
                    {new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel("\u265D", new Bishop("Black")), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" ")},
                    {new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" ")},
                    // black♜♞♛♚
                    {new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "),
                            new ChessLabel(" "), new ChessLabel("\u265C", new Rook("Black")), new ChessLabel(" "), new ChessLabel(" ")},
                    {new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "), new ChessLabel(" "),
                            new ChessLabel("\u2656", new Rook("White")), new ChessLabel(" "), new ChessLabel("\u2654"
                            , new King("White")), new ChessLabel(" ")}
            };
            board.display();
            board.playerWhite.initPieces(board.getLabel(),board);
            board.playerBlack.initPieces(board.getLabel(),board);
            Draw.constructThreefold("Black", board.playerWhite, board.playerBlack, board.enpassant, board.getLabel(),board);
    }
}
