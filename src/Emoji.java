import acm.graphics.GImage;
import acm.graphics.GRectangle;

import java.util.Random;


public class Emoji {

    public static final int IMG_WIDTH = 100;
    public static final int IMG_HEIGHT = 100;
    public static final double IMG_SCALE = 2;
    public Random rnd = new Random();

    private boolean zombie = false;
    private GImage image;
    private String name;
    private int angle = 0;


    //Builders
    public Emoji(String name, String imagePATH){
        this.name = name;
        this.image = new GImage(imagePATH);
        this.zombie =false;
        this.getImage().setSize(IMG_HEIGHT,IMG_WIDTH);

        double h=(rnd.nextInt(Application.WINDOW_HEIGHT));
        double w=(rnd.nextInt(Application.WINDOW_WIDTH));
        this.getImage().setLocation(h,w);
    }
    public Emoji(String name, String imagePATH, boolean zombie){
        this.name = name;
        this.image= new GImage(imagePATH);
        this.zombie = zombie;
        this.getImage().setLocation(0,0);
        this.getImage().setSize(IMG_HEIGHT,IMG_WIDTH);
    }

    //Functions

    //Function that generates a random movement of the emojis, in case of exceeding the screen they appear in the opposite position of which they have exceeded
    public void move(){
        long timeMS = System.currentTimeMillis();
        long interval = timeMS%1000;

        //Angles de moviment
        double angleRads = Math.toRadians(angle);
        double newPosX = this.getX() + Math.cos(angleRads)*10;
        double newPosY = this.getY() - Math.sin(angleRads)*10;

        if(interval>900){ this.angle = rnd.nextInt(250);}

        //Change of position in case of exceeding the limits
        if(newPosX+(this.getImage().getWidth()/2) > Application.WINDOW_WIDTH){
            newPosX = 0 +(this.getImage().getWidth());
        }else if(newPosX +(this.getImage().getWidth()/2) < 0){
            newPosX = Application.WINDOW_WIDTH - (this.getImage().getWidth());
        }

        if(newPosY + (this.getImage().getHeight()/2) > Application.WINDOW_HEIGHT){
            newPosY = 0 +(this.getImage().getHeight());
        }else if(newPosY + (this.getImage().getHeight()/2) < 0){
            newPosY = Application.WINDOW_HEIGHT - (this.getImage().getHeight());;
        }


        //Change of position
        this.getImage().setLocation(newPosX, newPosY);

        // In case of being a zombie it is checked if it collides with a non-zombie object and if it is not it bites it
        if(this.isZombie()){
            this.bite();
        }
    }

    /* Function that determines if a zombie emoji has touched a live one and if so transforms it into a zombie, removes it from the 'alive' arrayList
     * and add it to the 'zombies' arrayList
     */
    public void bite(){
        for(int i=0;i<Application.alive.size();i++) {
            GRectangle rectangle1 = this.getImage().getBounds();
            GRectangle rectangle2 = Application.alive.get(i).getImage().getBounds();

            if (rectangle1.intersects(rectangle2)) {
                Application.alive.get(i).setZombie(true); 
                Application.zombies.add(Application.alive.get(i));
                Application.alive.remove(i);
            }
        }
    }

    //Getters and Setters
    public boolean isZombie() {
        return zombie;
    }

    //In case of being a zombie, the image also changes
    public void setZombie(boolean zombie) {
        this.zombie = zombie;
        if (this.isZombie()){
            this.image.setImage(Application.ZOMBIE_PATH);
            this.getImage().setSize(IMG_HEIGHT,IMG_WIDTH);
        }
    }

    public GImage getImage() {
        return image;
    }

    public void setImage(GImage image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX(){return this.getImage().getX();}

    public double getY(){return this.getImage().getY();}

}
