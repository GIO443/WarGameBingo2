import java.awt.*;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class Cavalry extends Unit{
    public Cavalry(int startTeam, String startName, Point startPosition, String startType, int startDirection){
        //creates a unit
        //basic stats
        team = startTeam;
        name = startName;
        type = startType;
        direction = startDirection;
        position = startPosition;
        morale = 100;
        //unit type based stats
        if(type.equals("Heavy")){
            pop = 50;
            attackDice = (int) (50*pop/50+0.5);
            moveSpeed = 1440; //48m/s
            range = moveSpeed;
            //Size based on pop
            length = (int)(java.lang.Math.sqrt(pop)+0.5); //length is along rows, will round appropriately
            width = length; //width along columns, is a square so length and width are equal
            //adding attacks to attackList
            attackList.add("Charge");
        }
        if(type.equals("Light")){
            pop = 50;
            attackDice = (int)(50*pop/50+0.5);
            range = 300;
            moveSpeed = 1440; //2m/s
            //Size based on pop
            length = (int)(java.lang.Math.sqrt(pop)+0.5); //length is along rows, will round appropriately
            width = length; //width along columns, is a square so length and width are equal
            //adding attacks to attackList
            attackList.add("Charge");
            attackList.add("Skirmish");
        }

    }

    public void attack(Unit target, String attackType) {
        int damage = 0;
        this.attackDice = (int) (50*this.pop/50+0.5);
        boolean legal = false;
        boolean rangeTrue = false;
        double dist = (java.lang.Math.sqrt(Math.pow(target.position.x - this.position.x, 2) + Math.pow(target.position.y - this.position.y, 2)));
        for (String e : attackList) {
            if (e.equals(attackType)) legal = true;
        }
        if (morale < 50) legal = false;
        if(attackType.equals("Charge")){
            range = moveSpeed;
        }
        if(attackType.equals("Skirmish")){
            range = 300;
        }
        if (range >= (java.lang.Math.sqrt(Math.pow(target.position.x - this.position.x, 2) + Math.pow(target.position.y - this.position.y, 2)))) {
            rangeTrue = true;
        }
        //Adding modifiers
        double modifier = 1; //attackdice * modifier = end attack
        //Sets modifier
        if (this.type.equals("Heavy")) {
            if (target.type.equals("Light") & attackType.equals("Charge")) {
                modifier = modifier * 2;
            }
            if ((40 <= dist) & attackType.equals("Charge")) {
                modifier = modifier * 2.5;
            }
        } else if (this.type.equals("Light")) {
            if (target.type.equals("Light") & attackType.equals("Charge")) {
                modifier = modifier * 1.5;
            }
            if (target.type.equals("Light") & attackType.equals("Skirmish")) {
                modifier = modifier * 0.85;
            }
            if ((40 <= dist) & attackType.equals("Charge")) {
                modifier = modifier * 2;
            }
        }

        if (rangeTrue) {
            if (legal) { // if legal move on to damage
                //rolls for attack
                damage = (int) (Math.random() * this.attackDice * modifier + 0.5);
                target.pop = target.pop - damage;
                //tests to see if target died, if so set pop = 0
                if (target.pop < 0) {
                    target.pop = 0;
                    System.out.println(target.type + " " + target.name + " is destroyed.");
                }
                //Outputs result of attack
                System.out.println("Damage to " + target.type + " " + target.name + " by " + this.type + " " + this.name + " is " + damage + ".");
                System.out.println(target.type + " " + target.name + "'s pop is now " + target.pop + ".");
                System.out.println("The modifier was " + modifier + " and the attack dice is 1d" + attackDice);
                System.out.println(" ");
            } else System.out.println("Improper attack name.");
        }
        else{
            System.out.println("Target is out of range.");
        }
    }
}