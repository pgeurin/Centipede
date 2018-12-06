public class Spider extends GameObject{
    public Spider(int x, int y){
        super(x,y,2,"img/spider.png");
        this.hitPointValue = 100;
        Project.BOARD.addObject(this, x,y);
    }

    public void destroy(){
        super.destroy();
        Project.SCORE += 500;
    }

    public void move() {
        int deltay = (int) Math.floor(Math.random() * 3 -1);
        int deltax = (int) Math.floor(Math.random() * 3 -1);

        while( !(Project.BOARD.canMoveTo(this, this.x+deltax, this.y+deltay))) {
            deltay = (int) Math.floor(Math.random() * 3 - 1);
            deltax = (int) Math.floor(Math.random() * 3 - 1);
        }
        this.nextx = this.x+deltax;
        this.nexty = this.y+deltay;
    }
}
