import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.KeyEvent;


public class Application extends GraphicsProgram{
    public static final int WINDOW_WIDTH = 900;
    public static final int WINDOW_HEIGHT = 700;
    public static final int REFRESH = 50;
    public static final int EMOJI_MAX = 10;
    public static final int STARTING_ZOMBIES = 2;
    public static final String ZOMBIE_PATH = "../resources/img/zombie.gif";
    public static final String EMOJI_PATH = "../resources/img/emoji.gif";
    public static final String BACKGROUND_PATH = "../resources/img/background.jpeg";
    public static final String EXP_PATH = "../resources/img/exp.gif";
    public static ArrayList <Emoji> emojis = new ArrayList<Emoji>();
    public static ArrayList <Emoji> zombies = new ArrayList<Emoji>();
    public static ArrayList <Emoji> alive = new ArrayList<Emoji>();

    //Function that creates all emojis and adds them to the 'emojis' arrayList and its relevant arrayLists
    public void createGame(){
        for(int i=0; i<EMOJI_MAX;i++){
            if(i< STARTING_ZOMBIES){
                emojis.add(createZombie("zombie"+(i+1), ZOMBIE_PATH));
            }else{
                emojis.add(createEmoji("emoji"+(i+1), EMOJI_PATH));
            }
        }
    }

    //Function of creating emojis and inserting them in the 'alive' arrayList
    public Emoji createEmoji(String nom, String path){
        Emoji emoji = new Emoji(nom, path);
        alive.add(emoji);
        return emoji;
    }

    //Function that creates zombies and insert them in the arrayList 'zombies'
    public Emoji createZombie(String nom, String path){
        Emoji zombie = new Emoji(nom, path,true);
        zombies.add(zombie);
        return zombie;
    }

    //Function that returns true if all emojis are zombies and false if not
    public boolean compZombie(){
        if(zombies.size()==emojis.size()){
            return true;
        }
        return false;
    }

    //Function that adds all emojis in the 'emojis' arrayList to the graphical environment
    public void addEmojis(){
        for (Emoji emoji:emojis) {
            add(emoji.getImage());
        }
    }

    //Function that creates and adds the background image to the graphical environment
    public void addBackground(){
        GImage background = new GImage(BACKGROUND_PATH);
        background.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        background.sendBackward();
        add(background);
    }

    //Function that creates and adds to the graphic environment the label of the end, the background rectangle of this one and the explosions of the end
    public void labelFinal(){
        String acabat = "OH NO!!!!! L'APOCALIPSIS HA ARRIBAT";
        GLabel labelFinal = new GLabel(acabat,WINDOW_WIDTH/2, WINDOW_HEIGHT /2);
        labelFinal.setColor(Color.WHITE);

        rectFinal(labelFinal);
        labelFinal.sendToFront();
        grandFinale();

        add(labelFinal);
    }

    //Function that creates and adds the label label 'labelFinal'
    public void rectFinal(GLabel labelFinal){
        double h = labelFinal.getHeight();
        double w = labelFinal.getWidth();

        GRect rectFinal = new GRect(w+5,h+5);
        rectFinal.setFillColor(Color.DARK_GRAY);
        rectFinal.setFilled(true);
        rectFinal.setLocation(labelFinal.getX()-5,labelFinal.getY()-labelFinal.getHeight());
        rectFinal.sendToFront();

        add(rectFinal);
    }

    //Function that creates and adds end explosions
    public void grandFinale(){
        GImage exp = new GImage(EXP_PATH);
        add(exp);

    }
    //Listener to exit the application
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    public void run(){
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        addKeyListeners();
        addBackground();

        //Start the game and create all the emojis
        createGame();

        //Inserting emojis in the graphical environment
        addEmojis();

        //Game
        while(!compZombie()){
            for(Emoji emoji:emojis){
                emoji.move();
            }
            pause(REFRESH);
        }

        //Label
        labelFinal();
    }
}
