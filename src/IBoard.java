import java.awt.*;

public interface IBoard {
    Square[][] getSquares();
    boolean isCheckMate();
    void move(Square destination);
}
