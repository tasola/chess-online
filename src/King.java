import java.awt.*;
import java.util.ArrayList;

public class King extends Piece {
    private String name = "King";

    public King(Color color) {
        super(color);
    }

    @Override
    public ArrayList<Square> getPossibleMoves(IBoard board) {
        ArrayList<Square> possibleMoves = new ArrayList<>();

        int currxPos = this.position.getxPos();
        int curryPos = this.position.getyPos();

        Square[][] boardSquares = board.getSquares();

       for (int x = -1; x < 2; x++){
           for (int y = -1; y < 2; y++){
               try{
                   possibleMoves.add(boardSquares[currxPos+x][curryPos+y]);
               } catch(ArrayIndexOutOfBoundsException e){
                   continue;
               }
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