import java.awt.*;
import java.util.ArrayList;

public class Rook extends Piece {
    private String name = "Rook";

    public Rook(Color color){
        super(color);
    }

    @Override
    public ArrayList<Square> getPossibleMoves(IBoard board) {

        int currxPos = this.position.getxPos();
        int curryPos = this.position.getyPos();

        ArrayList<Square> possibleMoves = this.getLinearMoves(board, currxPos, curryPos);

        return possibleMoves;
    }

    @Override
    public String getName(){
        String name = "";
        if (this.color == Color.white) name += "W ";
        else name += "B ";
        name += this.name.substring(0,2);
        return name;
    }
}