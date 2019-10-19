import java.awt.*;

public interface ISquare {
    int getxPos();
    int getyPos();
    Piece getPiece();
    void setPiece(Piece piece);
    Color getSquareColor();
    void setEmpty();
    boolean isEmpty();
    boolean isConquerable(Color color);
    boolean isKillable(Color color);
}
