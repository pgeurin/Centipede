import java.awt.*;
import javax.swing.*;

public abstract class GameObject {

    public GameObject(int stx, int sty, int stlives , String graphic){
        this.x = stx;
        this.nextx = stx;
        this.y = sty;
        this.nexty = sty;
        this.lives = stlives;
        this.sprite = new ImageIcon(graphic).getImage().getScaledInstance(Project.PSIZE, Project.PSIZE,  java.awt.Image.SCALE_SMOOTH);//graphic;
    }

    public GameObject(){
        this(0,0,1,"");
    }

    public boolean dying;
    public int dx;
    public boolean visible;
    public int lives;
    public int x;
    public int y;
    protected int nextx;
    protected int nexty;
    public Image sprite; //TODO: this will most likely change

    public abstract void move();
    protected int hitPointValue = 0;

    public void update(){
        /*if(this instanceof Centipede && ((Centipede) this).head != null){
            ((Centipede) this).head.update();
        }*/
        if(Project.BOARD.moveObectTo(this, this.x, this.y, this.nextx, this.nexty)) {
            this.x = this.nextx;
            this.y = this.nexty;
        }
        if(this.lives == 0){
            this.destroy();
        }
    }

    public void destroy(){
        Project.BOARD.rmObject(this, this.x, this.y);
    }

    //talk shit
    public void getHit(){
        Project.SCORE  += this.hitPointValue;
        this.lives--;
        if(this.lives == 0){
            this.destroy();
        }
    }

}
