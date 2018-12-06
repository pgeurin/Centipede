public class Mushroom extends GameObject {
    public Mushroom(int x, int y){
        super(x,y,3,"img/mushroom.png");
        this.hitPointValue = 1;
        Project.BOARD.addObject(this, x, y);
    }

    public void destroy(){
        super.destroy();
        Project.SCORE += 4;
    }

    public void move(){
        //mushrooms don't move.
    }
}
