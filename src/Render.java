import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Render extends JPanel implements Runnable {
    private Dimension d;
    public boolean ingame = true;
    private static final int DELAY = 77;


    public Render(){
        TAdapter TA = new TAdapter();
        addMouseMotionListener(TA);
        addMouseListener(TA);
        setFocusable(true);
        d = new Dimension(Project.DWIDTH*Project.PSIZE, (Project.DHEIGHT)*Project.PSIZE);
        setBackground(Color.black);

        setDoubleBuffered(true);
    }
    private Thread animator;

    @Override
    public void addNotify() {

        super.addNotify();

        if (animator == null || !ingame) {

            animator = new Thread(this);
            animator.start();
        }

        Project.gameInit();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(new Color(45,0,45));
        g.fillRect(0,(int)((Project.DHEIGHT-1.2)*Project.PSIZE), d.width, (int) (Project.PSIZE*1.2+3));
        Font small = new Font("Helvetica", Font.BOLD, 35);
        FontMetrics metr = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);

        if (ingame){
            g.drawString("Score: " + Project.SCORE + " , Lives: " + Project.PLAYER.lives, 10,(Project.DHEIGHT)*Project.PSIZE);

            for(int y = 0; y<Project.DHEIGHT ; y++){
                for(int x = 0; x<Project.DWIDTH; x++){
                    GameObject obj = Project.BOARD.getObjectAt(x,y);
                    if(obj != null)
                    g.drawImage(obj.sprite, obj.x*Project.PSIZE, obj.y*Project.PSIZE, this);
                }
            }
        } else {
            g.drawString("Game Over", (Project.DWIDTH*Project.PSIZE - metr.stringWidth("Game Over"))/2 ,
                    ((Project.DWIDTH - 2)*Project.PSIZE)/2);
            g.setFont(new Font("Helvetica", Font.BOLD, 25));
            g.drawString("Final Score: "+Project.SCORE, (Project.DWIDTH*Project.PSIZE - metr.stringWidth("Game Over"))/2 ,
                    ((Project.DWIDTH + 2)*Project.PSIZE)/2);
        }

        GameObject pl = Project.PLAYER;
        g.drawImage(pl.sprite, pl.x*Project.PSIZE, pl.y*Project.PSIZE, this);

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    @Override
    public void run() {
        repaint();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (ingame) {

            repaint();
            Project.BOARD.everybodyMove();
            Project.BOARD.everybodyUpdate();
            Project.PLAYER.update();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }

            beforeTime = System.currentTimeMillis();

            if(Project.PLAYER.lives == 0){
                ingame = false;
            }
        }

        gameOver();
    }

    public void gameOver() {

        Graphics g = this.getGraphics();
        this.paintComponent(g);
        //g.setColor(Color.black);
        //g.fillRect(0, 0, Project.DWIDTH*Project.PSIZE, Project.HEIGHT*Project.PSIZE);

        /*g.setColor(new Color(0, 32, 48));
        g.fillRect(50, Project.DWIDTH / 2 - 30, Project.DWIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, Project.DWIDTH / 2 - 30, Project.DWIDTH - 100, 50);*/
/*
        Font small = new Font("Helvetica", Font.BOLD, 35);
        FontMetrics metr = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString("Game Over", (Project.DWIDTH*Project.PSIZE - metr.stringWidth("Game Over"))/2 ,
                ((Project.DWIDTH - 2)*Project.PSIZE)/2);
        g.setFont(new Font("Helvetica", Font.BOLD, 25));
        g.drawString("Final Score: ", (Project.DWIDTH*Project.PSIZE - metr.stringWidth("Game Over"))/2 ,
                ((Project.DWIDTH + 2)*Project.PSIZE)/2);
        //g.drawImage(new ImageIcon("img/playerDead.png").getImage().getScaledInstance(Project.PSIZE*4, Project.PSIZE*4,  java.awt.Image.SCALE_SMOOTH), Project.DWIDTH *Project.PSIZE/2, (int)(Project.DHEIGHT *Project.PSIZE/(1.25)), this);

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
*/
        String soundName = "img/wompwomp.wav";
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private class TAdapter implements MouseListener, MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) { }

        @Override
        public void mouseMoved(MouseEvent e) {
            Project.PLAYER.mouseMoved(e.getX(), e.getY());
        }

        @Override
        public void mouseClicked(MouseEvent e) { }

        @Override
        public void mousePressed(MouseEvent e) {
            Project.PLAYER.mouseClicked(e.getX(), e.getY());
            String soundName = "img/pew.wav";
            AudioInputStream audioInputStream = null;
            try {
                audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        }

        @Override
        public void mouseReleased(MouseEvent e) { }

        @Override
        public void mouseEntered(MouseEvent e) { }

        @Override
        public void mouseExited(MouseEvent e) { }
    }
}
