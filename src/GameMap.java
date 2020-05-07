import java.awt.Canvas;
import java.awt.Graphics;
import javax.swing.JFrame;

public class GameMap extends Canvas {
    public static void main(String[] args) {
        JFrame frame = new JFrame("My Drawing");
        Canvas canvas = new GameMap();
        canvas.setSize(400, 400);
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
    }

    public void paint(Graphics g) {

    }
}
