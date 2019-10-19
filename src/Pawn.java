import java.awt.*;
import java.util.ArrayList;

public class Pawn extends Piece {
    private String name = "Pawn";
    private boolean firstMove = true;

    public Pawn(Color color) {
        super(color);
    }

    @Override
    public ArrayList<Square> getPossibleMoves(IBoard board) {
        ArrayList<Square> possibleMoves = new ArrayList<>();

        int currxPos = this.position.getxPos();
        int curryPos = this.position.getyPos();

        Square[][] squares = board.getSquares();
        int directionFactor = (this.color == Color.white) ? -1 : 1;



        if (currxPos + directionFactor >= 0 && currxPos + directionFactor <= 8){
            Square step = squares[currxPos + directionFactor][curryPos];
            if(step.getPiece() == null){
                possibleMoves.add(step);
                try {
                    Square twoStep = squares[currxPos + 2 * directionFactor][curryPos];
                    if (twoStep.getPiece() == null && firstMove) possibleMoves.add(twoStep);
                } catch(ArrayIndexOutOfBoundsException e){
                }
            }
        }

        // Enable diagonal conquering if there's a killable piece there
        for (int i = -1; i < 2; i+=2){
            try {
                Square diagonalSquare = squares[currxPos + directionFactor][curryPos + i];
                if (diagonalSquare.isKillable(this.color)) possibleMoves.add(diagonalSquare);
            } catch(ArrayIndexOutOfBoundsException e){
                continue;
            }
        }
        return possibleMoves;
    }

    @Override
    public boolean isPawnsFirstMove(){
        return this.firstMove;
    }

    @Override
    public void hasMoved(){
        this.firstMove = false;
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