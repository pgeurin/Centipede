import javax.swing.*;

public class Player extends GameObject {

    public Player() {
        super(Project.DWIDTH/2,Project.DHEIGHT, 3, "img/player.png");
        this.lives = 3;
    }

    public void mouseMoved(int x, int y){
        this.nextx = x/Project.PSIZE;
        this.nexty = y/Project.PSIZE;
    }

    public void mouseClicked(int x, int y){
        Beam bm = new Beam(x/Project.PSIZE,y/Project.PSIZE);
    }

    public void move(){
        //player is odd;
    }

    @Override
    public void update(){
        this.x = this.nextx;
        this.y = this.nexty;
        GameObject target = Project.BOARD.getObjectAt(this.x, this.y);
        if(target != null && !(target instanceof Mushroom)){
            getHit();
        }
    }

    @Override
    public void getHit(){
        Project.BOARD.everbodyDie();
        if(lives == 1){
            this.x = (int)((Project.DWIDTH-2) /2.5);
            this.y = (int)(Project.DHEIGHT/(1.75));
            this.sprite = new ImageIcon("img/playerDead.png").getImage().getScaledInstance(Project.PSIZE*14, Project.PSIZE*8,  java.awt.Image.SCALE_SMOOTH);//graphic;

        }
        this.lives--;

        //System.out.println(lives);
        if(this.lives != 0){
            Project.placeSpider();
            Project.placeCentipede();
        }
    }
}
