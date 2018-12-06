public class Beam extends GameObject {
    public Beam(int x, int y) {
        super(x,y,2,"img/beam.png");
        Project.BOARD.addObject(this, x,y);
    }

    public void move(){
        if(Project.BOARD.canMoveTo(this, this.x, this.y - 1)) {
            this.nexty = this.y - 1;
        } else {
            GameObject target = Project.BOARD.getObjectAt(this.x, this.y-1);
            if(target != null){
                target.getHit();
            }
            this.destroy();
        }
    }
}
