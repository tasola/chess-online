import java.awt.*;
import java.util.ArrayList;

public abstract class Piece {

    Square position;
    public Color color;

    public Piece(Color color) {
        this.color = color;
    }

    // Subclasses need to generate their own possible moves and names
    public abstract ArrayList<Square> getPossibleMoves(IBoard board);
    public abstract String getName();

    public Color getColor(){
        return this.color;
    }

    void setPosition(Square newPosition) {
        this.position = newPosition;
    }

    ArrayList<Square> getLinearMoves(IBoard board, int xPos, int yPos){
        ArrayList<Square> linearMoves = new ArrayList<>();
        Square[][] squares = board.getSquares();

        generateLinearMoves(xPos, yPos,true, true, squares, linearMoves);
        generateLinearMoves(xPos, yPos,true, false, squares, linearMoves);
        generateLinearMoves(xPos, yPos,false, true, squares, linearMoves);
        generateLinearMoves(xPos, yPos,false, false, squares, linearMoves);

        return linearMoves;
    }

    ArrayList<Square> getDiagonalMoves(IBoard board, int xPos, int yPos){
        ArrayList<Square> diagonalMoves = new ArrayList<>();
        Square[][] squares = board.getSquares();

        this.generateDiagonalMoves(xPos, yPos,  true, true, squares, diagonalMoves);
        this.generateDiagonalMoves(xPos, yPos,  true, false, squares, diagonalMoves);
        this.generateDiagonalMoves(xPos, yPos,  false, true, squares, diagonalMoves);
        this.generateDiagonalMoves(xPos, yPos, false, false, squares, diagonalMoves);

        return diagonalMoves;
    }

    // Reduces the code repetition in getLinearMoves (4x for loops)
    private ArrayList<Square> generateLinearMoves(int xPos, int yPos, boolean xIteration, boolean positiveIteration, Square[][] squares, ArrayList<Square> linearMoves){
        int iteratorStep = positiveIteration ? 1 : -1;
        int startPos = xIteration ? xPos : yPos;

        for (int i = startPos+iteratorStep; i < 8; i += iteratorStep){
            try {
                Square targetSquare = xIteration ? squares[i][yPos] : squares[xPos][i];
                if (targetSquare.isKillable(this.color)) {
                    linearMoves.add(targetSquare);
                    break;
                } else if (targetSquare.isEmpty()) {
                    linearMoves.add(targetSquare);
                } else break;
            } catch(ArrayIndexOutOfBoundsException e){
                // Catch when i < 0
                break;
            }
        }
        return linearMoves;
    }


    // Reduces the code repetition in getDiagonalMoves (4x double for-loops)
    private ArrayList<Square> generateDiagonalMoves(int xPos, int yPos, boolean positivexIteration, boolean positiveyIteration, Square[][] squares, ArrayList<Square> diagonalMoves){
        int xiteratorStep = positivexIteration ? 1 : -1;
        int yiteratorStep = positiveyIteration ? 1 : -1;

        outer:
        for (int x = xPos+xiteratorStep; x < 8; x += xiteratorStep){
            for (int y = yPos+yiteratorStep; y < 8; y += yiteratorStep){
                try {
                    Square targetSquare = squares[x][y];
                    if (Math.abs(xPos - x) == Math.abs(yPos-y)){
                        if (targetSquare.isKillable(this.color)){
                            diagonalMoves.add(targetSquare);
                            break outer;
                        } else if (targetSquare.isEmpty()){
                            diagonalMoves.add(targetSquare);
                        } else {
                            break outer;
                        }
                    }
                } catch(ArrayIndexOutOfBoundsException e){
                    if (x < 0) break outer;
                    else break;
                }
            }
        }
        return diagonalMoves;
    }

    String getIconPath(){
        String path = "resources/";
        if (this.color == Color.white) path += "White ";
        else path += "Black ";
        path += this.getName().substring(2,3);
        path += ".png";
        return path;
    }

    // Pawn specific methods
    public boolean isPawnsFirstMove(){
        return false;
    }
    public void hasMoved(){
    }
}
