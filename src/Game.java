import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Game extends StateBasedGame{

    public static final String gamename = "Line of Action";
    public static final int menu = 0;
    public static final int onevsone_eight = 1;
    public static final int onevsone_six = 2;
    public static final int onevsAI_eight = 3;
    public static final int onevsAI_six = 4;
    public static final int result = 5;

    public Game(String gamename){
        super(gamename);
        this.addState(new Menu(menu));
        this.addState(new OneVsOneEight(onevsone_eight));
        this.addState(new OneVsOneSix(onevsone_six));
        this.addState(new OneVsAIEight(onevsAI_eight));
        this.addState(new OneVsAISix(onevsAI_six));
        this.addState(new Result(result));
    }

    public void initStatesList(GameContainer gc) throws SlickException{
        this.getState(menu).init(gc,this);
        this.getState(onevsone_eight).init(gc,this);
        this.getState(onevsone_six).init(gc,this);
        this.getState(onevsAI_eight).init(gc,this);
        this.getState(onevsAI_six).init(gc,this);
        this.getState(result).init(gc,this);
        this.enterState(menu);
    }

    public static void main(String[] args) {
        AppGameContainer appgc;
        try {
            appgc = new AppGameContainer(new Game(gamename));
            appgc.setDisplayMode(800,800,false);
            appgc.start();
        }catch (SlickException e){
            e.printStackTrace();
        }
    }
}
