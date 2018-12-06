import javax.print.attribute.standard.PrinterMessageFromOperator;
import javax.swing.*;

public class Centipede extends GameObject{
    public Centipede(int x, int y, Centipede head){
        super(x,y, 2, "img/CentipedeBody.png");
        this.hitPointValue = 2;
        this.head = head;
        if(this.head == null){
            this.sprite = new ImageIcon("img/CentipedeHead.png").getImage().getScaledInstance(Project.PSIZE, Project.PSIZE,  java.awt.Image.SCALE_SMOOTH);
        }
        Project.BOARD.addObject(this, x,y);
    }
    private boolean left = true;
    public Centipede head;
    public boolean isdead = false;

    @Override
    public void move() {
        boolean radiohead = false;
        if(this.head != null && this.head.isdead){
            this.head = null;
            this.left = !this.left;
            radiohead = true;
            this.sprite = new ImageIcon("img/CentipedeHead.png").getImage().getScaledInstance(Project.PSIZE, Project.PSIZE,  java.awt.Image.SCALE_SMOOTH);
        }
        if(this.head != null) {
            this.nextx = this.head.x;
            this.nexty = this.head.y; //copy head location
            //System.out.println(this.head.nexty);
        } else {

            if(Project.BOARD.canMoveTo(this, ((this.left)? this.x - 1: this.x + 1), this.y)){
                this.nextx = ((this.left)? this.x - 1: this.x + 1);
                this.nexty = this.y;
            } else if(Project.BOARD.canMoveTo(this, this.x, this.y+(this.y!=Project.DHEIGHT-Project.PLAYERAREA ? 1 : -1))){
                this.left = !this.left;
                if(radiohead) this.left = !this.left;
                this.nexty = this.y+(this.y!=Project.DHEIGHT-Project.PLAYERAREA ? 1 : -1);
            } else {
                //System.out.println("Yikes...");
            }
        }
    }

    @Override
    public void destroy(){
        super.destroy();
        this.isdead = true;
        boolean alive = false;
        Project.SCORE += 5 - this.hitPointValue;
        for(int y = 0; y< Project.DWIDTH; y++){
            for(int x = 0; x<Project.DHEIGHT; x++){
                if(Project.BOARD.getObjectAt(x, y) != null && Project.BOARD.getObjectAt(x, y) instanceof Centipede){
                    alive = true;
                }
            }
        }
        if(!alive){
            Project.placeCentipede();
            Project.SCORE += 600 - 5;
        }
    }

    @Override
    public void update(){
        if(this.head != null) this.head.update();
        super.update();
    }

}