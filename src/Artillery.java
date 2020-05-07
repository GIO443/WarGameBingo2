import java.awt.*;
import java.util.ArrayList;

public class Artillery extends Unit{
    int gunNum;
    public Artillery(int startTeam, String startName, Point startPosition, String startType, int startDirection){
        //creates a unit
        //basic stats
        team = startTeam;
        name = startName;
        type = startType;
        direction = startDirection;
        position = startPosition;
        morale = 100;
        //unit type based stats
        if(type.equals("Siege")){
            pop = 16;
            gunNum = 4;
            attackDice = (int)(50*(pop/16)*(gunNum/4)+0.5);
            moveSpeed = 30; //0.5m/s
            range = 4000;
            //Size based on pop
            length = (int)(java.lang.Math.sqrt(pop)+0.5); //length is along rows, will round appropriately
            width = length; //width along columns, is a square so length and width are equal
            //adding attacks to attackList
            attackList.add("Direct");
            //adding ammo types to ammoList
            ammoList.add("Solid");
            ammoList.add("Grape");
        }
        if(type.equals("Horse")){
            pop = 16; //4 people per gun
            gunNum = 4;
            attackDice = (int)(50*(pop/16)*(gunNum/4)+0.5);
            range = 3000;
            moveSpeed = 60; //1m/s
            //Size based on pop
            length = (int)(java.lang.Math.sqrt(pop)+0.5); //length is along rows, will round appropriately
            width = length; //width along columns, is a square so length and width are equal
            //adding attacks to attackList
            attackList.add("Direct");
            //adding ammo types to ammoList
            ammoList.add("Solid");
            ammoList.add("Grape");
        }
        if(type.equals("Howitzer")){
            pop = 16;
            gunNum = 4;
            attackDice = (int)(50*(pop/16)*(gunNum/4)+0.5);
            range = 1800;
            moveSpeed = 40; //1m/s
            //Size based on pop
            length = (int)((gunNum*4)+0.5); //length is along rows, will round appropriately
            width = 4; //width along columns, is a square so length and width are equal
            //adding attacks to attackList
            attackList.add("Indirect");
            //adding ammo types to ammoList
            ammoList.add("Solid");
            ammoList.add("Explosive");
        }
    }

    public void attack(Unit target, String ammoType) {
        int damage = 0;
        this.attackDice = (int)(50*(this.pop/16)*(this.gunNum/4)+0.5);
        boolean legal = false;
        boolean rangeTrue = false;
        //Adding modifiers
        double modifier = 1; //attackdice * modifier = end attack
        //tests if attack is allowed
        for (String e : ammoList) {
            if (e.equals(ammoType)) legal = true;
        }
        if (morale < 50) legal = false;
        if (range >= (java.lang.Math.sqrt(Math.pow(target.position.x - this.position.x, 2) + Math.pow(target.position.y - this.position.y, 2)))) {
            rangeTrue = true;
        }

        //set modifiers
        if (ammoType.equals("Grape") & (target.type.equals("Line") || target.type.equals("Grenadier"))) {
            modifier *= 3;
        }
        if (ammoType.equals("Grape")) {
            modifier *= 2;
        }
        if (ammoType.equals("Solid")) {
            modifier *= 2;
        }
        if(ammoType.equals("Explosive")){
            modifier = modifier * 1.5;
            if(target.name.contains("Infantry")){
                modifier = modifier * 2.8;
            }
        }
        if (rangeTrue) {
            if (legal) { // if  legal moves on to damage
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
            } else System.out.println("The attack or ammo type was illegal.");

        }
        else{
            System.out.println("Target is out of range.");
        }
    }

}