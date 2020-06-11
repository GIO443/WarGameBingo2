import java.awt.*;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class Infantry extends Unit {
    public Infantry(int startTeam, String startName, Point startPosition, String startType, int startDirection){
        //creates a unit
        //basic stats
        team = startTeam;
        name = startName;
        type = startType;
        direction = startDirection;
        position = startPosition;
        morale = 100;
        //unit type based stats
        if(type.equals("Line")){
            pop = 200;
            attackDice = (int) (50*pop/200+0.5);
            range = 320;
            moveSpeed = 120; //2m/s
            //Size based on formation and pop
            length = (int)(pop/3 +0.5); //length is along rows, always 3 rows, will round appropriately
            width = 3; //width along columns

            //adding attacks to attackList
            attackList.add("Rank");
            attackList.add("Melee");
        }
        if(type.equals("Light")){
            pop = 100;
            attackDice = (int) (50*pop/100+0.5);
            range = 450;
            moveSpeed = 180; //2m/s
            //Size based on formation and pop
            length = (int)((pop/4 +0.5)*2); //length is along rows, always 3 rows, will round appropriately
            width = 4; //width along columns

            //adding attacks to attackList
            attackList.add("Rank");
            attackList.add("Skirmish");
            attackList.add("Melee");
        }
        if(type.equals("Grenadier")){
            pop = 200;
            range = 320;
            attackDice = (int) (50*pop/200+0.5);
            moveSpeed = 120; //1m/s for 1 min
            //Size based on formation and pop
            length = (int)(pop/3 +0.5); //length is along rows, always 3 rows, will round appropriately
            width = 3; //width along columns

            //adding attacks to attackList
            attackList.add("Rank");
            attackList.add("Melee");
            attackList.add("Grenade");
        }
    }

    public void attack(Unit target, String attackType) {
        int damage = 0;
        //if a grenadier or line infantry:
        this.attackDice = (int) (50*this.pop/200+0.5);
        //if a light infantry
        if(this.type.equals("Light")){
            this.attackDice = (int) (50*this.pop/100+0.5);
        }

        boolean legal = false;
        boolean rangeTrue = false;
        double dist = (java.lang.Math.sqrt(Math.pow(target.position.x - this.position.x, 2) + Math.pow(target.position.y - this.position.y, 2)));
        for (String e : attackList) {
            if (e.contains(attackType)) legal = true;
        }
        if (morale < 50) legal = false;
        if (range >= (java.lang.Math.sqrt(Math.pow(target.position.x - this.position.x, 2) + Math.pow(target.position.y - this.position.y, 2)))) {
            rangeTrue = true;
        }


        //Adding modifiers
        double modifier = 1; //attackdice * modifier = end attack
        //Sets modifier
        if (this.type.equals("Light")) {
            if ((target.type.equals("Grenadier") || target.type.equals("Line")) & attackType.equals("Skirmish")) {
                modifier = modifier * 1.2;
            }
        } else if (this.type.equals("Line")) {
            if (target.type.equals("Light")) {
                modifier = modifier * 0.75;
            }
        } else if (this.type.equals("Grenadier")) {
            if ((attackType.equals("Grenade") & (!target.type.equals("Light")))) {
                modifier = modifier * 1.5;
            }
            if (target.type.equals("Light") & (attackType.equals("Grenade"))) {
                modifier = modifier * 0.8;
            }
        }
        if (rangeTrue) {
            if (legal) { // if legal, move on to damage
                //rolls for attack
                damage = (int) (Math.random() * this.attackDice * modifier + 0.5);
                target.pop = target.pop - damage;
                //tests to see if target died, if so set pop = 0
                if (target.pop <= 0) {
                    target.pop = 0;
                    System.out.println(target.type + " " + target.name + " is destroyed.");
                }
                //Outputs result of attack
                System.out.println("Damage to " + target.type + " " + target.name + " by " + this.type + " " + this.name + " is " + damage + ".");
                System.out.println(target.type + " " + target.name + "'s pop is now " + target.pop + ".");
                System.out.println("The modifier was " + modifier + " and the attack dice is 1d" + attackDice);
                System.out.println(" ");
            } else
                System.out.println("Illegal attack: could be attacking same team, improper attack name, target out of range");
        }
        else{
            System.out.println("Target is out of range.");
        }
    }


}
