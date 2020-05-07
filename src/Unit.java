import java.awt.*;
import java.util.ArrayList;

public class Unit extends Main{
    String name;
    Point position;
    String type;
    int length;
    int width;
    int pop;
    int morale;
    int attackDice; // 1d(attackdice)
    int moveSpeed;
    int direction; //1 - up, 2 - right, 3 - down, 4 - left
    int range;
    int team;
    boolean moved = false;
    ArrayList<String> attackList = new ArrayList<>();
    ArrayList<String> ammoList = new ArrayList<>();

    public void move(Point newPosition) {
        //using distance formula to find if new point is legal then moves
        if (moveSpeed >= (java.lang.Math.sqrt(Math.pow(newPosition.x - this.position.x, 2) + Math.pow(newPosition.y - this.position.y, 2)))) {
            position = newPosition;
            this.position = newPosition;
            this.moved = true;
        } else {
            System.out.println("New position is to far away and out of reach.");
            this.moved = false;
        }
    }

}
