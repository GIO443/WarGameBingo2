import java.awt.*;
import java.util.*;

public class Main {
    static ArrayList<Unit> UnitList1 = new ArrayList<>();
    static ArrayList<Unit> UnitList2 = new ArrayList<>();
    static int infnum = 0;
    static int cavnum = 0;
    static int artnum = 0;
    static int whoesTurn = 0;
    public static void main(String[] args) {
        createGameUnits();

        while((UnitList2.size() != 0) || (UnitList1.size() != 0)){
            //removes dead units
            Iterator<Unit> itr1 = UnitList1.iterator();
            Iterator<Unit> itr2 = UnitList2.iterator();

            while (itr1.hasNext()) {
                Unit TestedUnit = itr1.next();
                if (TestedUnit.pop == 0) {
                    itr1.remove();
                }
            }
            while (itr2.hasNext()) {
                Unit TestedUnit = itr2.next();
                if (TestedUnit.pop == 0) {
                    itr2.remove();
                }
            }

            //plays turns
            if(whoesTurn%2 == 0) {
                System.out.println("");
                System.out.println("Team 1's turn.");
                playTurn(1);
                whoesTurn++;
            }

            //removed dead units
            while (itr1.hasNext()) {
                Unit TestedUnit = itr1.next();
                if (TestedUnit.pop == 0) {
                    itr1.remove();
                }
            }
            while (itr2.hasNext()) {
                Unit TestedUnit = itr2.next();
                if (TestedUnit.pop == 0) {
                    itr2.remove();
                }
            }

            //plays turns
            if((whoesTurn%2 == 1) & (UnitList2.size() != 0)) {
                System.out.println("");
                System.out.println("Team 2's turn.");
                playTurn(2);
                whoesTurn++;
            }

            //removed dead units
            while (itr1.hasNext()) {
                Unit TestedUnit = itr1.next();
                if (TestedUnit.pop == 0) {
                    itr1.remove();
                }
            }
            while (itr2.hasNext()) {
                Unit TestedUnit = itr2.next();
                if (TestedUnit.pop == 0) {
                    itr2.remove();
                }
            }
        }
        if(UnitList2.size() == 0) System.out.println("Team 1 has won!");
        else System.out.println("Team 2 has won!");


    }

    public static void createGameUnits(){
        Scanner userInput = new Scanner(System.in);
        for(int t = 1; t < 3; t++) { // which teams stuff is being created
            System.out.println("Creating units for team " + t + ".");
            System.out.println("Input number of infantry you want:");
            int numofInf = userInput.nextInt();
            System.out.println("Input number of cavalry you want:");
            int numofCav = userInput.nextInt();
            System.out.println("Input number of artillery you want:");
            int numofArt = userInput.nextInt();
            for (int i = 0; i < numofInf; i++) { // creating Infantry
                int dir;
                if (t == 1) dir = 1;
                else dir = 3;
                System.out.println("Infantry creation begun.");
                //User chooses position
                System.out.println("Give the position x-coordinate:");
                int xValI = userInput.nextInt();
                System.out.println("Now the y-coordinate:");
                int yValI = userInput.nextInt();
                //user chooses type
                System.out.println("Potential types of Infantry are: ");
                System.out.println("Line, Grenadier, and Light.");
                String typeI = userInput.next();
                if(typeI.equals("Line") || typeI.equals("Grenadier") || typeI.equals("Light")) {
                    System.out.println("Infantry creation finished.");
                    createInf(t, new Point(xValI, yValI), typeI, dir);
                }
                else{
                    System.out.println("Infantry creation failed, it will restart.");
                    i--;
                }
            }

            for (int i = 0; i < numofCav; i++) { // creating Cavalry
                int dir;
                if (t == 1) dir = 1;
                else dir = 3;
                System.out.println("Cavalry creation begun.");
                //User chooses position
                System.out.println("Give the position x-coordinate:");
                int yValC = userInput.nextInt();
                System.out.println("Now the y-coordinate:");
                int xValC = userInput.nextInt();
                //user chooses type
                System.out.println("Potential types of Cavalry are: ");
                System.out.println("Light, and Heavy.");
                String typeC = userInput.next();
                if(typeC.equals("Heavy") || typeC.equals("Light")) {
                    System.out.println("Cavalry creation finished.");
                    createCav(t, new Point(xValC, yValC), typeC, dir);
                }
                else{
                    System.out.println("Cavalry creation failed, it will restart.");
                    i--;
                }
            }

            for (int i = 0; i < numofArt; i++) { // creating Cavalry
                int dir;
                if (t == 1) dir = 1;
                else dir = 3;
                System.out.println("Artillery creation begun.");
                //User chooses position
                System.out.println("Give the position x-coordinate:");
                int yValA = userInput.nextInt();
                System.out.println("Now the y-coordinate:");
                int xValA = userInput.nextInt();
                //user chooses type
                System.out.println("Types of Artillery are: ");
                System.out.println("Siege, Horse and Howitzer.");
                String typeA = userInput.next();
                if(typeA.equals("Siege") || typeA.equals("Horse") || typeA.equals("Howitzer")) {
                    System.out.println("Artillery creation finished.");
                    createArt(t, new Point(xValA, yValA), typeA, dir);
                }
                else{
                    System.out.println("Artillery creation failed, it will restart.");
                    i--;
                }
            }
        }
    }

    public static void createInf(int team, Point pos, String type, int dir){
        infnum++;//increments inf counter
        String startName = "Infantry" + infnum;//sets name
        //attaches new infantry to given team
        if(team == 1) UnitList1.add(new Infantry(team, startName, pos, type, dir));
        if(team == 2) UnitList2.add(new Infantry(team, startName, pos, type, dir));
    }

    public static void createCav(int team, Point pos, String type, int dir){
        cavnum++;//increments inf counter
        String startName = "Cavalry" + cavnum;//sets name
        //attaches new infantry to given team
        if(team == 1) UnitList1.add(new Cavalry(team, startName, pos, type, dir));
        if(team == 2) UnitList2.add(new Cavalry(team, startName, pos, type, dir));
    }

    public static void createArt(int team, Point pos, String type, int dir){
        artnum++;//increments inf counter
        String startName = "Artillery" + artnum;//sets name
        //attaches new infantry to given team
        if(team == 1) UnitList1.add(new Artillery(team, startName, pos, type, dir));
        if(team == 2) UnitList2.add(new Artillery(team, startName, pos, type, dir));
    }

    public static void playTurn(int team){
        Scanner userInput = new Scanner(System.in);
        ArrayList<Infantry> infList = new ArrayList<>();
        ArrayList<Cavalry> cavList = new ArrayList<>();
        ArrayList<Artillery> artList = new ArrayList<>();

        if(team == 1) {
            for (Unit e : UnitList1) {
                //sorts units into even more specific arrays to use later
                if (e.name.contains("Infantry")) {
                    e.moved = false;
                    infList.add((Infantry) e);
                }
                if (e.name.contains("Cavalry")) {
                    e.moved = false;
                    cavList.add((Cavalry) e);
                }
                if (e.name.contains("Artillery")) {
                    e.moved = false;
                    artList.add((Artillery) e);
                }
            }//sorts  units
            for (Infantry e : infList) {
                //goes through each
                System.out.println("Take an action for unit " + e.name + ":");
                System.out.println("Attack, Move, or Nothing");
                String action = userInput.next();
                if (action.equals("Attack")) {
                        //tells user their range and then distance to target
                        System.out.println("Your range is " + e.range + ".");
                        System.out.println("Choose target:");
                        for (Unit r : UnitList2) {
                            int distr = (int) (java.lang.Math.sqrt(Math.pow(r.position.x - e.position.x, 2) + Math.pow(r.position.y - e.position.y, 2)));
                            System.out.println(r.name + " is " + distr + " away.");
                        }
                        String tarStr = userInput.next();
                    Unit tar = (new Infantry(team, "", new Point(0,0), "", 1));
                    for (Unit f : UnitList2) {
                        if (f.name.equals(tarStr)) {
                            tar = f;
                        }
                    }

                    System.out.println("Choose an attack from the list:");
                    for (String a : e.attackList) {
                        System.out.println(a);
                    }
                    String attackChosen = userInput.next();

                    e.attack(tar, attackChosen);
                }
                else if(action.equals("Move")){
                    while(e.moved == false) {
                        System.out.println("X-coordinate of new position:");
                        int x = userInput.nextInt();
                        System.out.println("Y-coordinate of new position:");
                        int y = userInput.nextInt();
                        int dist = (int) ((java.lang.Math.sqrt(Math.pow(x - e.position.x, 2) + Math.pow(y - e.position.y, 2))) + 0.5);
                        System.out.println("The distance to the new position is " + dist + ". Your range is " + e.moveSpeed);

                        if(dist > e.moveSpeed) System.out.println("Out of range, try again.");
                        else{
                            System.out.println(e.type + " " + e.name + " moved to Point (" + x + ", " + y + ").");
                            e.move(new Point(x,y));
                            e.moved = true;
                        }
                    }
                }

                    }//use all inf
            for (Cavalry e : cavList) {
                //goes through each
                System.out.println("Take an action for unit " + e.name + ":");
                System.out.println("Attack or Move");
                String action = userInput.next();
                if (action.equals("Attack")) {
                    //tells user their range and then distance to target
                    System.out.println("Your range is " + e.range + ".");
                    System.out.println("Choose target:");
                    for (Unit r : UnitList2) {
                        int distr = (int) (java.lang.Math.sqrt(Math.pow(r.position.x - e.position.x, 2) + Math.pow(r.position.y - e.position.y, 2)));
                        System.out.println(r.name + " is " + distr + " away.");
                    }
                    String tarStr = userInput.next();
                    Unit tar = (new Cavalry(team, "", new Point(0,0), "", 1));

                    for (Unit f : UnitList2) {
                        if (f.name.equals(tarStr)) {
                            tar = f;
                        }
                    }
                    System.out.println("Choose an attack from the list:");
                    for (String a : e.attackList) {
                        System.out.println(a);
                    }
                    String attackChosen = userInput.next();

                    e.attack(tar, attackChosen);
                    }
                else if(action.equals("Move")){
                    while(e.moved == false) {
                        System.out.println("X-coordinate of new position:");
                        int x = userInput.nextInt();
                        System.out.println("Y-coordinate of new position:");
                        int y = userInput.nextInt();
                        int dist = (int) ((java.lang.Math.sqrt(Math.pow(x - e.position.x, 2) + Math.pow(y - e.position.y, 2))) + 0.5);
                        System.out.println("The distance to the new position is " + dist + ". Your range is " + e.moveSpeed);
                        e.move(new Point(x,y));
                        if(dist > e.moveSpeed) System.out.println("Out of range, try again.");
                        else{
                            System.out.println(e.type + " " + e.name + " moved to Point (" + x + ", " + y + ").");
                            e.move(new Point(x,y));
                        }
                    }
                }

                }//use all cav
            for (Artillery e : artList) {
                //goes through each
                System.out.println("Take an action for unit " + e.name + ":");
                System.out.println("Attack or Move");
                String action = userInput.next();
                if (action.equals("Attack")) {
                    //tells user their range and then distance to target
                    System.out.println("Your range is " + e.range + ".");
                    System.out.println("Choose target:");
                    for (Unit r : UnitList2) {
                        int distr = (int) (java.lang.Math.sqrt(Math.pow(r.position.x - e.position.x, 2) + Math.pow(r.position.y - e.position.y, 2)));
                        System.out.println(r.name + " is " + distr + " away.");
                    }
                    String tarStr = userInput.next();
                    Unit tar = (new Cavalry(team, "", new Point(0,0), "", 1));

                    for (Unit f : UnitList2) {
                        if (f.name.equals(tarStr)) {
                            tar = f;
                        }
                    }

                    System.out.println("Chose an ammo type from the list:");
                    for (String a : e.ammoList) {
                        System.out.println(a);
                    }
                    String ammoChosen = userInput.next();

                    e.attack(tar, ammoChosen);
                }
                else if(action.equals("Move")){
                    System.out.println("X-coordinate of new position:");
                    int x = userInput.nextInt();
                    System.out.println("Y-coordinate of new position:");
                    int y = userInput.nextInt();
                    int dist = (int) ((java.lang.Math.sqrt(Math.pow(x - e.position.x, 2) + Math.pow(y - e.position.y, 2))) + 0.5);
                    System.out.println("The distance to the new position is " + dist + ". Your range is " + e.moveSpeed);

                    if(dist > e.moveSpeed) System.out.println("Out of range, try again.");
                    else{
                        System.out.println(e.type + " " + e.name + " moved to Point (" + x + ", " + y + ").");
                        e.move(new Point(x,y));
                        e.moved = true;
                    }
                }
            }//use all art
            }




        if(team == 2) {
            for (Unit e : UnitList2) {
                //sorts units into even more specific arrays to use later
                if (e.name.contains("Infantry")) {
                    e.moved = false;
                    infList.add((Infantry) e);
                }
                if (e.name.contains("Cavalry")) {
                    e.moved = false;
                    cavList.add((Cavalry) e);
                }
                if (e.name.contains("Artillery")) {
                    e.moved = false;
                    artList.add((Artillery) e);
                }
            }//sorts units
            for (Infantry e : infList) {
                //goes through each
                System.out.println("Take an action for unit " + e.name + ":");
                System.out.println("Attack or Move");
                String action = userInput.next();
                if (action.equals("Attack")) {
                    //tells user their range and then distance to target
                    System.out.println("Your range is " + e.range + ".");
                    System.out.println("Choose target:");
                    for (Unit r : UnitList1) {
                        int distr = (int) (java.lang.Math.sqrt(Math.pow(r.position.x - e.position.x, 2) + Math.pow(r.position.y - e.position.y, 2)));
                        System.out.println(r.type + " " + r.name + " is " + distr + " away.");
                    }
                    String tarStr = userInput.next();
                    Unit tar = (new Infantry(team, "", new Point(0,0), "", 1));
                    for (Unit f : UnitList1) {
                        if (f.name.equals(tarStr)) {
                            tar = f;
                        }
                    }

                    System.out.println("Choose an attack from the list:");
                    for (String a : e.attackList) {
                        System.out.println(a);
                    }
                    String attackChosen = userInput.next();

                    e.attack(tar, attackChosen);
                }
                else if(action.equals("Move")){
                    while(e.moved == false) {
                        System.out.println("X-coordinate of new position:");
                        int x = userInput.nextInt();
                        System.out.println("Y-coordinate of new position:");
                        int y = userInput.nextInt();
                        int dist = (int) ((java.lang.Math.sqrt(Math.pow(x - e.position.x, 2) + Math.pow(y - e.position.y, 2))) + 0.5);
                        System.out.println("The distance to the new position is " + dist + ". Your range is " + e.moveSpeed);

                        if (dist > e.moveSpeed) System.out.println("Out of range, try again.");
                        else {
                            System.out.println(e.type + " " + e.name + " moved to Point (" + x + ", " + y + ").");
                            e.move(new Point(x, y));
                            e.moved = true;
                        }
                    }
                }


            }//use all inf
            for (Cavalry e : cavList) {
                //goes through each
                System.out.println("Take an action for unit " + e.name + ":");
                System.out.println("Attack or Move");
                String action = userInput.next();
                if (action.equals("Attack")) {
                    //tells user their range and then distance to target
                    System.out.println("Your range is " + e.range + ".");
                    System.out.println("Choose target:");
                    for (Unit r : UnitList1) {
                        int distr = (int) (java.lang.Math.sqrt(Math.pow(r.position.x - e.position.x, 2) + Math.pow(r.position.y - e.position.y, 2)));
                        System.out.println(r.type + " " + r.name + " is " + distr + " away.");
                    }
                    String tarStr = userInput.next();
                    Unit tar = (new Cavalry(team, "", new Point(0,0), "", 1));

                    for (Unit f : UnitList1) {
                        if (f.name.equals(tarStr)) {
                            tar = f;
                        }
                    }
                    System.out.println("Choose an attack from the list:");
                    for (String a : e.attackList) {
                        System.out.println(a);
                    }
                    String attackChosen = userInput.next();

                    e.attack(tar, attackChosen);
                }
                else if(action.equals("Move")) {
                    while (e.moved == false) {
                        System.out.println("X-coordinate of new position:");
                        int x = userInput.nextInt();
                        System.out.println("Y-coordinate of new position:");
                        int y = userInput.nextInt();
                        int dist = (int) ((java.lang.Math.sqrt(Math.pow(x - e.position.x, 2) + Math.pow(y - e.position.y, 2))) + 0.5);
                        System.out.println("The distance to the new position is " + dist + ". Your range is " + e.moveSpeed);

                        if (dist > e.moveSpeed) System.out.println("Out of range, try again.");
                        else {
                            System.out.println(e.type + " " + e.name + " moved to Point (" + x + ", " + y + ").");
                            e.move(new Point(x, y));
                            e.moved = true;
                        }
                    }
                }

            }//use all cav
            for (Artillery e : artList) {
                //goes through each
                System.out.println("Take an action for unit " + e.name + ":");
                System.out.println("Attack or Move");
                String action = userInput.next();
                if (action.equals("Attack")) {
                    //tells user their range and then distance to target
                    System.out.println("Your range is " + e.range + ".");
                    System.out.println("Choose target:");
                    for (Unit r : UnitList1) {
                        int distr = (int) (java.lang.Math.sqrt(Math.pow(r.position.x - e.position.x, 2) + Math.pow(r.position.y - e.position.y, 2)));
                        System.out.println(r.type + " " + r.name + " is " + distr + " away.");
                    }
                    String tarStr = userInput.next();
                    Unit tar = (new Cavalry(team, "", new Point(0,0), "", 1));

                    for (Unit f : UnitList1) {
                        if (f.name.equals(tarStr)) {
                            tar = f;
                        }
                    }

                    System.out.println("Chose an ammo type from the list:");

                    for (String a : e.ammoList) {
                        System.out.println(a);
                    }
                    String ammoChosen = userInput.next();

                    e.attack(tar, ammoChosen);
                }
                else if(action.equals("Move")) {
                    while (e.moved == false) {
                        System.out.println("X-coordinate of new position:");
                        int x = userInput.nextInt();
                        System.out.println("Y-coordinate of new position:");
                        int y = userInput.nextInt();
                        int dist = (int) ((java.lang.Math.sqrt(Math.pow(x - e.position.x, 2) + Math.pow(y - e.position.y, 2))) + 0.5);
                        System.out.println("The distance to the new position is " + dist + ". Your range is " + e.moveSpeed);

                        if (dist > e.moveSpeed) System.out.println("Out of range, try again.");
                        else {
                            System.out.println(e.type + " " + e.name + " moved to Point (" + x + ", " + y + ").");
                            e.move(new Point(x, y));
                            e.moved = true;
                        }
                    }
                }

            }//use all art
            }

        }
}
