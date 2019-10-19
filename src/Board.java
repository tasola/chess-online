import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Board extends JPanel implements IBoard, ActionListener {

    private Game game;
    private Square[][] squares;
    private boolean whiteTurn = true;

    // Variables to keep track of the possibleMovesPreview
    private ArrayList<Color> originalColors = new ArrayList<>();
    private ArrayList<Square> possibleMoves = new ArrayList<>();
    private Color thisSquareColor;
    private Piece thisPiece;
    private Square thisSquare;
    private boolean previewClick = true;


    Board(Game game, int xDim, int yDim){
        this.squares = new Square[xDim][yDim];
        this.game = game;

        for (int x = 0; x < xDim; x++){
            for (int y = 0; y < yDim; y++){

                boolean isWhite = ((x + y) % 2 == 0);

                Color squareColor = isWhite ? Color.white : Color.lightGray;
                Square square = new Square(x, y, squareColor);
                squares[x][y] = square;

                square.setOpaque(true);
                square.setBorderPainted(false);
                square.setMargin(new Insets(0, -1, 0, -20));
                square.setHorizontalAlignment(SwingConstants.LEFT);
                square.setPreferredSize(new Dimension(80, 80));
                square.setBackground(squareColor);
                square.addActionListener(this);

                this.add(squares[x][y]);
            }
        }
    }

    public Square[][] getSquares(){
        return this.squares;
    }

    // Is not used at the moment
    public boolean isCheckMate(){
        return false;
    }

    private Color currentColor(){
        if (whiteTurn) return Color.white;
        return Color.black;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Square selectedSquare = (Square) e.getSource();

        // If first click and you click your own piece, enablePreview
        if (previewClick && !selectedSquare.isConquerable(currentColor())){
            previewClick = false;
            this.enablePreview(selectedSquare);
        }
        // If second click and you click a square to which you can move, move there
        else if (!previewClick && possibleMoves.contains(selectedSquare)) {
            previewClick = true;
            this.disablePreview();
            if (selectedSquare.isConquerable(currentColor())){
                this.move(selectedSquare);
            }
            originalColors.clear();
        }
        // If second click and you did not click a square to which you can move, disablePreview.
        else if (!previewClick){
            previewClick = true;
            this.disablePreview();
        }
    }

    private void enablePreview(Square selectedSquare){
        originalColors.clear();

        thisPiece = selectedSquare.getPiece();
        possibleMoves = thisPiece.getPossibleMoves(this);
        thisSquare = selectedSquare;
        thisSquareColor = selectedSquare.getSquareColor();
        selectedSquare.setBackground(new Color(255, 255, 180));
        for (Square move : possibleMoves) {
            originalColors.add(move.getSquareColor());
            if (move.isConquerable(currentColor())) move.setBackground(new Color(200,255,200));
        }
    }

    private void disablePreview(){
        thisSquare.setBackground(thisSquareColor);
        for (int i = 0; i < possibleMoves.size(); i++) {
            possibleMoves.get(i).setBackground(originalColors.get(i));
        }
    }

    public void move(Square destinationSquare){
        if (!destinationSquare.isEmpty()){
            Piece killedPiece = destinationSquare.getPiece();
            game.addKill(killedPiece, whiteTurn);
        }
        if (thisPiece.isPawnsFirstMove()) thisPiece.hasMoved();
        destinationSquare.setPiece(thisPiece);
        thisSquare.setEmpty();
        whiteTurn = !whiteTurn;
        game.changeTurn(whiteTurn);
    }
}
