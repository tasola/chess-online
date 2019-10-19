import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class Square extends JButton implements ISquare {

    Piece piece = null;
    private Color squareColor;
    private int xPos;
    private int yPos;

    public Square(int xPos, int yPos, Color squareColor){
        this.xPos = xPos;
        this.yPos = yPos;
        this.squareColor = squareColor;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public Piece getPiece(){
        return this.piece;
    }

    public void setPiece(Piece piece){
        if(piece.getClass().toString().equals("class Pawn") && (this.xPos == 7 || this.xPos == 0)){
            this.piece = new Queen(piece.getColor());
        } else {
            this.piece = piece;
        }
        String pieceText = this.piece.getName();
        this.piece.setPosition(this);
        try {
            Image img = ImageIO.read(getClass().getResource(this.piece.getIconPath()));
            this.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
            this.setText(pieceText);
        }

    }

    public Color getSquareColor(){
        return this.squareColor;
    }

    public void setEmpty(){
        this.piece = null;
        this.setText("");
        this.setIcon(null);
    }

    public boolean isEmpty(){
        if (this.piece == null) return true;
        return false;
    }

    public boolean isConquerable(Color attackingColor){
        if (this.isEmpty()) return true;
        return isKillable(attackingColor);
    }

    public boolean isKillable(Color attackingColor){
        if (!this.isEmpty() && this.getPiece().getColor() != attackingColor) return true;
        return false;
    }
}
