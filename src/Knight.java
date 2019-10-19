import java.awt.*;
import java.util.ArrayList;

public class Knight extends Piece {
    private String name = "Night";
    private int[][] moves = {
            {-2, 1},
            {-1, 2},
            {1, 2},
            {2, 1},
            {2, -1},
            {1, -2},
            {-1, -2},
            {-2, -1}
    };

    public Knight(Color color){
        super(color);
    }

    @Override
    public ArrayList<Square> getPossibleMoves(IBoard board) {
        ArrayList<Square> possibleMoves = new ArrayList<>();

        int currxPos = this.position.getxPos();
        int curryPos = this.position.getyPos();

        Square[][] boardSquares = board.getSquares();

        // Attempt to add each possible move. If it is outside of the board; ignore and continue.
        for (int i = 0; i < moves.length; i++){
            try{
                int possiblexPos = currxPos + moves[i][0];
                int possibleyPos = curryPos + moves[i][1];
                possibleMoves.add(boardSquares[possiblexPos][possibleyPos]);
            } catch(ArrayIndexOutOfBoundsException e){
                continue;
            }
        }
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
