import javax.swing.*;
import java.awt.*;

public class ChessLabel extends JLabel {

    Font font     = new Font("DejaVu Sans", Font.PLAIN, 65);
    Color bgLight = new Color(222, 184, 135);
    Color bgDark  = new Color(139, 69, 19);
    int identifier;
    Piece piece;
    int xcoord;
    int ycoord;

    ChessLabel(String s)
    {
        super(s);
    }
    ChessLabel(String s, Piece piece)
    {
        super(s);
        this.piece=piece;
    }
    ChessLabel(ChessLabel ss)
    {
        this.piece=ss.piece;
        this.xcoord=ss.xcoord;
        this.ycoord=ss.ycoord;
    }
    ChessLabel(Piece piece, int xcoord, int ycoord)
    {
        this.piece=piece;
        this.xcoord=xcoord;
        this.ycoord=ycoord;
    }

    void set(int idx, int row)
    {
        setFont(font);
        setOpaque(true);
        setBackground((idx+row)%2 == 0 ? bgDark : bgLight);
        setHorizontalAlignment( SwingConstants.CENTER );
    }
    void update(Piece piece, int xcoord, int ycoord){
        this.piece=piece;
        this.xcoord=xcoord;
        this.ycoord=ycoord;
    }
    void ScrubAway(){
        this.piece=null;
        this.setText(null);
    }
    void coordUpdate(int xcoord, int ycoord){
        this.xcoord=xcoord;
        this.ycoord=ycoord;
    }
}
