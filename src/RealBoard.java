public class RealBoard {
    public static void main(String[] args) {
        initGame();
    }
    public static void initGame(Board current){
        current.dispose();
        Board board = new Board();
        board.display();
        board.playerWhite.initPieces(board.getLabel(),board);
        board.playerBlack.initPieces(board.getLabel(),board);
        Draw.reset();
        Draw.constructThreefold("Black", board.playerWhite, board.playerBlack, board.enpassant, board.getLabel(),board);
    }
    public static void initGame(){
        Board board = new Board();
        board.display();
        board.playerWhite.initPieces(board.getLabel(),board);
        board.playerBlack.initPieces(board.getLabel(),board);
        Draw.constructThreefold("Black", board.playerWhite, board.playerBlack, board.enpassant, board.getLabel(),board);
    }
}
