import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Game extends JFrame {

    private Square[][] squares;
    private ArrayList<Piece> killedWhitePieces = new ArrayList<>();
    private ArrayList<Piece> killedBlackPieces = new ArrayList<>();
    private String winner = "";
    private boolean yourTurn;
    private Board board;

    JPanel whitePanel = new JPanel(new GridLayout(4,4));
    JPanel blackPanel = new JPanel(new GridLayout(4,4));
    JLabel turnLabel = new JLabel();

    public static void main(String[] customConnection) {
        new Game(customConnection);
    }

    Game(String[] customConnection){
        this.yourTurn = (customConnection[0].equals("2000"));
        String player = yourTurn ? "White" : "Black";
        turnLabel.setText(player);
        this.board = new Board(this,8, 8, customConnection);
        this.squares = this.board.getSquares();

        JPanel boardPanel = new JPanel();
        this.setTitle("Chess");
        this.setResizable(true);

        setSize(700, 900);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Makes sure that the program exits when the frame is closed
        this.setVisible(true);

        JPanel killPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        killPanel.setLayout(new GridLayout(1,2));
        killPanel.setPreferredSize(new Dimension(600, 150));

        whitePanel.setBackground(Color.white);
        blackPanel.setBackground(Color.lightGray);

        JPanel turnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        turnPanel.add(turnLabel);
        add(turnPanel, BorderLayout.NORTH);
        add(this.board, BorderLayout.CENTER);
        killPanel.add(whitePanel, BorderLayout.NORTH);
        killPanel.add(blackPanel, BorderLayout.NORTH);
        add(killPanel, BorderLayout.SOUTH);
        setVisible(true);

        this.createPieces();
    }

    private void createPieces(){

        for (int x = 0; x < 8; x++){
            // Create pawns
            squares[6][x].setPiece(new Pawn(Color.white));
            squares[1][x].setPiece(new Pawn(Color.black));

            // Create the rest
            createPieceAt(Color.white, x);
            createPieceAt(Color.black, x);
        }
    }

    private void createPieceAt(Color color, int x){
        int y = color == Color.white ? 7 : 0;

        if (x == 0 || x == 7) squares[y][x].setPiece(new Rook(color));
        else if (x == 1 || x == 6) squares[y][x].setPiece(new Knight(color));
        else if (x == 2 || x == 5) squares[y][x].setPiece(new Bishop(color));
        else if (x == 3) squares[y][x].setPiece(new Queen(color));
        else squares[y][x].setPiece(new King(color));
    }

    void changeTurn(boolean yourTurn){
        this.yourTurn = yourTurn;
        if(winner.length() > 0) return;
        String turn = yourTurn ? "Your" : "Opponent's";
        turnLabel.setText(turn + " turn!");
    }

    void addKill(Piece killedPiece, boolean isBlack){
        if (!isBlack) killedWhitePieces.add(killedPiece);
        else killedBlackPieces.add(killedPiece);
        addToGUI(killedPiece, isBlack);
        if(killedPiece.getClass().toString().equals("class King")){
            setWinner();
        }
    }

    private void setWinner (){
        winner = this.yourTurn ? "You" : "Opponent";
        turnLabel.setText(winner + " WON!");
    }

    private void addToGUI(Piece killedPiece, boolean isBlack){
        JPanel panel = isBlack ? whitePanel : blackPanel;

        JLabel killedLabel = new JLabel();

        try {
            Image image = ImageIO.read(getClass().getResource(killedPiece.getIconPath()));
            Image scaledImage = image.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(scaledImage);
            killedLabel.setIcon(imageIcon);
        } catch (Exception ex) {
            System.out.println(ex);
            killedLabel.setText(killedPiece.getName());
        }

        panel.add(killedLabel);
    }


}
