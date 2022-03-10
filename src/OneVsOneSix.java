import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import java.util.ArrayList;

public class OneVsOneSix extends BasicGameState {

    Board gameBoard;
    ArrayList<BoardPiece> whiteBoardPieces;
    ArrayList<BoardPiece> blackBoardPieces;
    ArrayList<Integer[]> possibleMoves;

    int initX;
    int initY;


    public OneVsOneSix(int state){

    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        gameBoard = new Board(6);
        whiteBoardPieces = gameBoard.getWhiteBoardPieces();
        blackBoardPieces = gameBoard.getBlackBoardPieces();
        possibleMoves = new ArrayList<>();
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{

        g.setColor(Color.gray);
        g.fillRect(40,50,55,40);
        g.setColor(Color.black);
        g.drawString("Menu", 50,60);

        g.setColor(Color.gray);
        g.fillRect(290,20,170,40);
        g.setColor(Color.black);
        g.drawString("1 vs 1 (6 Tiles)", 300,30);

        g.setColor(Color.gray);
        g.fillRect(160,210,480,480);
        g.setColor(Color.black);

        for (int i=1;i<=5;i++){
            int y = 210 + 80*i;
            g.drawLine(160,y,640,y);

            int x = 160 + 80*i;
            g.drawLine(x,210,x,690);
        }

        g.setColor(Color.white);
        for (BoardPiece boardPiece: whiteBoardPieces) {
            g.fillOval(boardPiece.getxPosition(),boardPiece.getyPosition(),50,50);
        }

        g.setColor(Color.black);
        for (BoardPiece boardPiece: blackBoardPieces) {
            g.fillOval(boardPiece.getxPosition(),boardPiece.getyPosition(),50,50);
        }

        g.setColor(Color.white);
        if (possibleMoves.size() > 0){
            if (possibleMoves.get(0)[0] == -100){
                g.drawString("No Legal Move", 600,60);
            }
            else {
                g.drawString(possibleMoves.size() + " Legal Move", 600,60);
                for (Integer[] move: possibleMoves) {
                    g.setColor(Color.black);
                    g.drawString("Hint", 160 + 80 * move[1], 210 + 80 * move[0]);
                }

            }
        } else {
            g.drawString("MoveList Size is Zero", 600,60);
        }

    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
        Input input = gc.getInput();

        int xpos = Mouse.getX();
        int ypos = 800 - Mouse.getY();

        if ((xpos > 40 && xpos < 95) && (ypos > 60 && ypos < 100)){
            if (input.isMouseButtonDown(0)){
                sbg.enterState(0);
            }
        }

        if (gameBoard.isWinnerWhite() || gameBoard.isWinnerBlack()){
            if (gameBoard.isWinnerWhite()) System.out.println("White Wins");
            else if (gameBoard.isWinnerBlack()) System.out.println("Black Wins");
//            System.out.println("HashMapSize: " + Board.boardChecker.size());
            gc.sleep(8000);
            sbg.init(gc);
            sbg.enterState(5);
        } else {
            if (possibleMoves.size() > 0){
                if (possibleMoves.get(0)[0] != -100){
                    if (input.isMouseButtonDown(0)) {
                        for (Integer[] move : possibleMoves) {
                            int startX = 160 + 80 * move[1];
                            int endX = startX + 80;
                            int startY = 210 + 80 * move[0];
                            int endY = startY + 80;
                            if ((xpos > startX && xpos < endX) && (ypos > startY && ypos < endY)) {
                                gameBoard.moveBoardPiece(initY, initX, move[0], move[1]);
                                whiteBoardPieces = gameBoard.getWhiteBoardPieces();
                                blackBoardPieces = gameBoard.getBlackBoardPieces();
                                possibleMoves = new ArrayList<>();
                                break;
                            }
                        }
                    }
                }
            }


            if ((xpos > 160 && xpos < 640) && (ypos > 210 && ypos < 690)){
                if (input.isMouseButtonDown(0)){
                    int row =0;
                    int column=0;

                    for (int i=0;i<gameBoard.boardSize;i++){
                        if (xpos > 160 + 80*i && xpos < 160 + 80*(i+1)){
                            column = i;
                        }
                        if (ypos > 210+ 80*i && ypos < 210 + 80*(i+1)){
                            row = i;
                        }
                    }
                    possibleMoves = gameBoard.getPossibleMoves(row,column);
                    if (possibleMoves.size() > 0){
                        for (int i=0;i<8;i++){
                            if (xpos > 160 + 80*i && xpos < 160 + 80*(i+1)){
                                initX = i;
                            }
                            if (ypos > 210+ 80*i && ypos < 210 + 80*(i+1)){
                                initY = i;
                            }
                        }
                    }
                }
            }
        }

        gc.sleep(100);

    }

    public int getID(){
        return 2;
    }
}