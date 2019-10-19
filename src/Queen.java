import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class Queen extends Piece {
    private String name = "Queen";

    public Queen(Color color) {
        super(color);
    }

    @Override
    public ArrayList<Square> getPossibleMoves(IBoard board) {
        LinkedList<Square> linkedPossibleMoves = new LinkedList<Square>();

        int currxPos = this.position.getxPos();
        int curryPos = this.position.getyPos();

        ArrayList<Square> linearMoves = this.getLinearMoves(board, currxPos, curryPos);
        ArrayList<Square> diagonalMoves = this.getDiagonalMoves(board, currxPos, curryPos);

        linkedPossibleMoves.addAll(diagonalMoves);
        linkedPossibleMoves.addAll(linearMoves);

        ArrayList<Square> possibleMoves = new ArrayList<Square>(linkedPossibleMoves);

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