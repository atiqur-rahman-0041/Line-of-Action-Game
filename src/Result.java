import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Result extends BasicGameState {


    public Result(int state){

    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
        g.setColor(Color.yellow);
        g.fillRect(290,400,170,40);
        g.setColor(Color.black);
        g.drawString("Game Over", 300,410);
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{

    }

    public int getID(){
        return 5;
    }
}