public class GameBoard {

    private GameObject[][] map;
    private int sizeX;
    private int sizeY;

    public GameBoard(int sizeX, int sizeY){
        this.map = new GameObject[sizeY][sizeX];
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public GameObject getObjectAt(int x, int y){
        if(x < this.sizeX && x >= 0 && y < this.sizeY && y >= 0)
            return map[y][x];
        else return  null;
    }

    public boolean addObject(GameObject obj, int x, int y){
        if(x < this.sizeX && x >= 0 && y < this.sizeY && y >= 0 && map[y][x] == null){
            map[y][x] = obj;
            return true;
        } else{
            return false;
        }
    }

    public boolean rmObject(GameObject obj, int x, int y){
        if(x < this.sizeX && x >= 0 && y < this.sizeY && y >= 0 && map[y][x] == obj){
            map[y][x] = null;
            return true;
        } else{
            return false;
        }
    }

    public boolean canMoveTo(GameObject obj, int newX, int newY){
        return (newX < this.sizeX && newX >= 0 && newY < this.sizeY && newY >= 0 &&
                map[obj.y][obj.x] == obj && map[newY][newX] == null);
    }

    public boolean moveObectTo(GameObject obj, int oldX, int oldY, int newX, int newY){
        if(newX < this.sizeX && newX >= 0 && newY < this.sizeY && newY >= 0 &&
                map[oldY][oldX] == obj && map[newY][newX] == null){
            map[oldY][oldX] = null;
            map[newY][newX] = obj;
            return true;
        } else {
            return false;
        }
    }

    public void everybodyMove(){
        for(int y = 0; y<this.sizeY; y++){
            for(int x = 0; x<this.sizeX; x++){
                if(map[y][x] != null){
                    map[y][x].move();
                }
            }
        }
    }

    public void everybodyUpdate(){
        for(int y = 0; y<this.sizeY; y++){
            for(int x = 0; x<this.sizeX; x++){
                if(map[y][x] != null){
                    map[y][x].update();
                }
            }
        }
    }

    public void everbodyDie(){
        for(int y = 0; y<this.sizeY; y++){
            for(int x = 0; x<this.sizeX; x++){
                if(Project.PLAYER.lives > 1) {
                    if (getObjectAt(x, y) != null && !(getObjectAt(x, y) instanceof Mushroom)) {
                        rmObject(Project.BOARD.getObjectAt(x, y), x, y);
                    } else if (getObjectAt(x, y) != null && (getObjectAt(x, y) instanceof Mushroom)) {
                        if(getObjectAt(x,y).lives < 3) Project.SCORE += 10;
                        (getObjectAt(x, y)).lives = 3;
                    }
                }else{
                    if (getObjectAt(x, y) != null) {
                        rmObject(Project.BOARD.getObjectAt(x, y), x, y);
                    }
                }
            }
        }
    }

    public String toString(){
        StringBuilder st = new StringBuilder();
        for(int y = 0; y<this.sizeY; y++){
            for(int x = 0; x<this.sizeX; x++){
                GameObject obj = map[y][x];
                if(obj == null){
                    st.append(".");
                } else if(obj instanceof Centipede) {
                    st.append("C");
                } else if(obj instanceof Mushroom) {
                    st.append("M");
                } else if(obj instanceof Spider) {
                    st.append("S");
                } else if(obj instanceof Beam) {
                    st.append("|");
                } else {
                    st.append("*");
                }
            }
            st.append("\n");
        }
        return st.toString();
    }
}
