import java.awt.EventQueue;
import javax.swing.JFrame;

public class Project extends JFrame{

    public Project(){
        add(new Render());
        setTitle("Centipede");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(DWIDTH*PSIZE, (DHEIGHT+1)*PSIZE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public static void gameInit(){
        placeSpider();
        placeMush(mushroomdensity);
        placeCentipede();

    }

    public static void main(String[] args){
        mushroomdensity = Double.parseDouble(args[0]);

        EventQueue.invokeLater(() -> {
            Project ex = new Project();
            ex.setVisible(true);
        });
    }
    private static double mushroomdensity;
    public static final int DWIDTH = 32;
    public static final int DHEIGHT = 32;
    public static final int CENTIPEDELENGTH = 8;
    public static final int PLAYERAREA = 5;
    public static final int PSIZE = 24; //pixels per grid element
    public static final GameBoard BOARD = new GameBoard(DWIDTH,DHEIGHT);
    public static final Player PLAYER = new Player();
    public static int SCORE = 0;


    public static void placeMush(double arg){
        for(int k = 1; k < DHEIGHT-PLAYERAREA-1; k++) {
            for (int i = 1; i < DWIDTH-1; i++) {
                if (Math.random() < arg &&
                        BOARD.getObjectAt((i==0? i : i-1) % DWIDTH, (k==0? k: k-1) % DHEIGHT) == null &&
                        BOARD.getObjectAt((i+1) % DWIDTH, (k==0? k: k-1) % DHEIGHT) == null) {
                    Mushroom mush = new Mushroom(i,k);
                }
            }
        }
    }

    public static void placeCentipede(){
        Centipede prevc = null;
        for(int i = DWIDTH - CENTIPEDELENGTH; i<=DWIDTH-1; i++){
            Centipede c = new Centipede(i,0,prevc);
            prevc = c;
        }
    }

    public static void placeSpider() {
        int x = (int) (Math.random() * (DWIDTH-1));
        int y = (int) (Math.random() * (DHEIGHT - 4));
        while( BOARD.getObjectAt(x,y) != null ) {
            x = (int) (Math.random() * (DWIDTH-1));
            y = (int) (Math.random() * (DHEIGHT - 4));
        }
        Spider spid = new Spider(x, y);
    }
}
