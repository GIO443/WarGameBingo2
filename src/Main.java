import java.awt.*;
import java.util.*;

public class Main {
    static Scanner userInput = new Scanner(System.in);
    static ArrayList<Unit> UnitList1 = new ArrayList<>();
    static ArrayList<Unit> UnitList2 = new ArrayList<>();
    static int infnum = 0;
    static int cavnum = 0;
    static int artnum = 0;
    static int whoesTurn = 0;

    public static void main(String[] args) {
        createGameUnitsTeam();

        while ((UnitList2.size() != 0) && (UnitList1.size() != 0)) {
            if (whoesTurn % 2 == 0)
                playTurn(1);
            else
                playTurn(2);
            whoesTurn++;

            removeDeadUnits(UnitList1);
            removeDeadUnits(UnitList2);
        }
        if (UnitList2.size() == 0) {
            sendUI("Team 1 has won!");
        } else {
            sendUI("Team 2 has won!");
        }

    }

    private static void sendUI(String message) {
        System.out.println("");
        System.out.println(message);
    }

    private static String getUIString() {
        return userInput.next();
    }

    private static int getUIInt() {
        return userInput.nextInt();
    }

    public static void createGameUnitsTeam() {
        createGameArmy(1);
        createGameArmy(2);
    }

    public static void createGameArmy(int t) {
        sendUI("Creating units for team " + t + ".");
        sendUI("Input number of infantry you want:");
        int numofInf = getUIInt();
        createGameUnits(t, "Infantry", numofInf);

        sendUI("Input number of cavalry you want:");
        int numofCav = getUIInt();
        createGameUnits(t, "Cavalry", numofCav);

        sendUI("Input number of artillery you want:");
        int numofArt = getUIInt();
        createGameUnits(t, "Artillery", numofArt);
    }

    public static void createGameUnits(int team, String kind, int numofUnit){
        for (int i = 0; i < numofUnit; i++) { // creating Units
                int dir;
                if (team == 1)
                    dir = 1;
                else
                    dir = 3;
                //User chooses position
                sendUI("Give the position x-coordinate for " + kind + (i+1));
                int xValI = getUIInt();
                sendUI("Now the y-coordinate:");
                int yValI = getUIInt();
                //User chooses type
            String[] subTypeList = new String[]{};
            switch (kind){
                case "Infantry":
                    subTypeList = Infantry.subTypeAllowed;
                    break;
                case "Cavalry":
                    subTypeList = Cavalry.subTypeAllowed;
                    break;
                case "Artillery":
                    subTypeList = Artillery.subTypeAllowed;
                    break;
            }
                sendUI("Potential types of " + kind + " are: ");
                for (int j = 0; j < subTypeList.length; j++) {
                    sendUI(subTypeList[j]);
                }
                boolean created = false;
                while (!created) {
                    sendUI("Please input one of the above:");
                    String typeU = getUIString();
                    for( int k = 0; k < subTypeList.length; k++){
                        if (typeU.equals(subTypeList[k])) {
                            created = true;
                            switch (kind) {
                                case "Infantry":
                                    createInf(team, new Point(xValI, yValI), typeU, dir);
                                    break;
                                case "Cavalry":
                                    createCav(team, new Point(xValI, yValI), typeU, dir);
                                    break;
                                case "Artillery":
                                    createArt(team, new Point(xValI, yValI), typeU, dir);
                                    break;
                            }
                        }
                    }
                    if (!created)
                        sendUI("Infantry creation failed, it will restart.");
                }
            }
    }


    public static void removeDeadUnits(ArrayList<Unit> a){
        //removes dead units
        int i = 0;
        while(i < a.size()) {
            if(a.get(i).pop <= 0){
                a.remove(i);
            }
            else i++;
        }
    }

    public static void checkRange(Unit attacker, Unit target){
        int distr = (int) (java.lang.Math.sqrt(Math.pow(attacker.position.x - target.position.x, 2) + Math.pow(attacker.position.y - target.position.y, 2)));
        if(attacker.range >= distr){
            System.out.println("Unit " + target.name + " is in range and is " + distr +" range away.");
        }
        else{
            System.out.println("Unit " + " is out of range and is " + distr + "range away.");
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
                    assert e instanceof Cavalry;
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
                            checkRange(e, r);
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
                System.out.println("Attack, Move or Nothing");
                String action = userInput.next();
                if (action.equals("Attack")) {
                    //tells user their range and then distance to target
                    System.out.println("Your range is " + e.range + ".");
                    System.out.println("Choose target:");
                    for (Unit r : UnitList2) {
                        checkRange(e, r);
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
                System.out.println("Attack, Move,or Nothing");
                String action = userInput.next();
                if (action.equals("Attack")) {
                    //tells user their range and then distance to target
                    System.out.println("Your range is " + e.range + ".");
                    System.out.println("Choose target:");
                    for (Unit r : UnitList2) {
                        checkRange(e, r);
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
                System.out.println("Attack, Move, or Nothing");
                String action = userInput.next();
                if (action.equals("Attack")) {
                    //tells user their range and then distance to target
                    System.out.println("Your range is " + e.range + ".");
                    System.out.println("Choose target:");
                    for (Unit r : UnitList1) {
                        checkRange(e, r);
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
                System.out.println("Attack, Move, or Nothing");
                String action = userInput.next();
                if (action.equals("Attack")) {
                    //tells user their range and then distance to target
                    System.out.println("Your range is " + e.range + ".");
                    System.out.println("Choose target:");
                    for (Unit r : UnitList1) {
                        checkRange(e, r);
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
                System.out.println("Attack, Move or Nothing");
                String action = userInput.next();
                if (action.equals("Attack")) {
                    //tells user their range and then distance to target
                    System.out.println("Your range is " + e.range + ".");
                    System.out.println("Choose target:");
                    for (Unit r : UnitList1) {
                        checkRange(e, r);
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

            }
            }

        }
}
