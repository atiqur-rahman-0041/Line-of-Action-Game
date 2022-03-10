import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;


public class Menu extends BasicGameState{

//    String mouse = "No Input";

    public Menu(int state){

    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{

    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{

//        g.drawString(mouse, 50,50);

        g.drawString("Select Game Mode", 300,280);
        g.drawRect(290,270,170,40);

        g.drawString("1 vs 1 (8 Tiles)", 300,350);
        g.drawRect(290,340,170,40);

        g.drawString("1 vs 1 (6 Tiles)", 300,400);
        g.drawRect(290,390,170,40);

        g.drawString("1 vs AI (8 Tiles)", 300,450);
        g.drawRect(290,440,170,40);

        g.drawString("1 vs AI (6 Tiles)", 300,500);
        g.drawRect(290,490,170,40);

    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
        Input input = gc.getInput();

        int xpos = Mouse.getX();
        int ypos = 800 - Mouse.getY();

//        mouse = "x: " + xpos + " y: " + ypos;

        if (xpos > 290 && xpos < 460){
            if (ypos > 340 && ypos < 380){
                if (input.isMouseButtonDown(0)){
                    sbg.enterState(1);
                }
            }

            if (ypos > 390 && ypos < 430){
                if (input.isMouseButtonDown(0)){
                    sbg.enterState(2);
                }
            }

            if (ypos > 440 && ypos < 480){
                if (input.isMouseButtonDown(0)){
                    sbg.enterState(3);
                }
            }

            if (ypos > 490 && ypos < 530){
                if (input.isMouseButtonDown(0)){
                    sbg.enterState(4);
                }
            }

        }
    }

    public int getID(){
        return 0;
    }
}
