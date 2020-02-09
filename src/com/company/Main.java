package com.company;


import com.company.GUI.BuildingsGUI;
import com.company.Interfaces.*;
import com.company.buildings.dwelling.*;
import com.company.buildings.dwelling.hotel.Hotel;
import com.company.buildings.dwelling.hotel.HotelFloor;
import com.company.buildings.office.*;
import com.company.buildings.threads.*;
import com.company.exceptions.*;
import com.company.tool_classes.Buildings;
import com.company.tool_classes.PlacementExchanger;

import java.io.*;
import java.util.Arrays;
import java.util.Random;

import static java.lang.Math.min;

public class Main {

    public static void main(String[] args) {
        // write your code here
        //checkTask2();
        //checkTask3();
        //checkTask4();
        //checkTask5();
        //checkTask7_2();
        //generateForTask8();
        checkTask9();
    }

    /**
     * return:Dwelling
     */
    public static Building checkTask2(boolean creating) {
        try {
            DwellingFloor df = null;
            if (!creating) {
                /*Flat*/
                Flat f = new Flat();
                System.out.println(f.toString());

                f.setArea(10);
                f.setCountOfRooms(1);
                System.out.println(f.toString());

                /*DwellingFloor*/
                Flat[] flats = {f, new Flat(60), new Flat(100, 3)};
                df = new DwellingFloor(flats);
                System.out.println(df.toString());
            }
            /*Dwelling*/
            Random rand = new Random();
            int n = 4;//rand.nextInt(3) + 1;
            DwellingFloor[] dfArr = new DwellingFloor[n + 1];
            for (int i = 0; i < n + 1; ++i) {
                int k = 3;// rand.nextInt(3) + 1;
                dfArr[i] = new DwellingFloor(k);
                for (int j = 0; j < k; ++j)
                    dfArr[i].setSpace(j, new Flat(20 * (j + 1), (j + 1)));
            }

            Dwelling dwelling = new Dwelling(dfArr);
            System.out.println(dwelling.toString());
            /*Dwelling methods*/
            if (!creating) {
                Space[] flats1 = dwelling.getSortSpacesOnArea();
                DwellingFloor df2 = new DwellingFloor(flats1);
                df2.toString();

                System.out.println("getNumberOfFloors()" + dwelling.getNumberOfFloors());
                System.out.println(dwelling.eraseSpaceInBuilding(3));
                System.out.println(dwelling.getDwellingFloor(0).toString());
                System.out.println("getBestSpace()" + dwelling.getBestSpace());
                System.out.println("getTotalNumberOfRooms()" + dwelling.getTotalNumberOfRooms());
                System.out.println("getNumberOfFlats()" + dwelling.getNumberOfSpaces());
                System.out.println("getTotalAreaOfFlats" + dwelling.getTotalAreaOfSpaces());
                System.out.println("getTotalNumberOfRooms()" + dwelling.getTotalNumberOfRooms());
                System.out.println("getDwellingFloor" + dwelling.getDwellingFloor(0).toString());
                System.out.println("setFlatInDwelling" + dwelling.setSpaceInBuilding(2, new Flat()));
                System.out.println("getFlat" + dwelling.getSpace(2).toString());
                System.out.println("setDwellingFloor" + dwelling.setFloor(dwelling.getNumberOfFloors() - 1, df));

                System.out.println(dwelling.toString());
                System.out.println("insertFlatInDwelling" + dwelling.insertSpaceInBuilding(5, new Flat(1, 1)));
                System.out.println(dwelling.toString());
            }
            return dwelling;
        } catch (FloorIndexOutOfBoundsException | InvalidSpaceAreaException | InvalidRoomsCountException | SpaceIndexOutOfBoundsException | NullPointerException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * return:OfficeBuilding
     */
    public static Building checkTask3(boolean creating) {
        try {
            if (!creating) {
                /*Office*/
                Office f = new Office();
                System.out.println(f.toString());

                f.setArea(10);
                f.setCountOfRooms(1);
                System.out.println(f.toString());

                /*OfficeFloor*/
                Office[] office = {f, new Office(60), new Office(100, 3)};
                OfficeFloor of = new OfficeFloor(office);
                System.out.println(of.toString());
            }

            /*OfficeBuilding*/
            Random rand = new Random();
            int n = rand.nextInt(3) + 1;
            System.out.println("n= " + n);

            Floor[] dfArr = new OfficeFloor[n];
            for (int i = 0; i < n; ++i) {
                int k = rand.nextInt(3) + 1;
                System.out.println("i =" + i + " k = " + k);
                dfArr[i] = new OfficeFloor(k);
                for (int j = 0; j < k; ++j)
                    dfArr[i].setSpace(j, new Office(20 * (j + 1), (j + 1)));
            }

            OfficeBuilding dwelling = new OfficeBuilding(dfArr);
            System.out.println(dwelling.toString());


            return dwelling;
        } catch (FloorIndexOutOfBoundsException | InvalidSpaceAreaException | InvalidRoomsCountException | SpaceIndexOutOfBoundsException | NullPointerException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static void checkTask4() {
        try {
            Dwelling dwelling = (Dwelling) checkTask2(true);
            OfficeBuilding officeB = (OfficeBuilding) checkTask3(true);
            for (int i = 0; i < min(dwelling.getNumberOfFloors(), officeB.getNumberOfFloors()); ++i) {
                if (PlacementExchanger.isExchangeableFloor(dwelling.getFloor(i), officeB.getFloor(i)))
                    PlacementExchanger.exchangeBuildingFloors(dwelling, i, officeB, i);
            }

            System.out.println("dwelling = " + dwelling.toString());
            System.out.println("officeB = " + officeB.toString());
            Floor floor1 = dwelling.getDwellingFloor(0);
            Floor floor2 = officeB.getFloor(0);
            for (int i = 0; i < min(floor1.getCountOfSpace(), floor2.getCountOfSpace()); ++i) {
                if (PlacementExchanger.isExchangeableSpace(floor1.getSpace(i), floor2.getSpace(i)))
                    PlacementExchanger.exchangeFloorRooms(floor1, i, floor2, i);
            }
            System.out.println("floor1 = " + floor1.toString());
            System.out.println("floor2 = " + floor2.toString());


            System.out.println("Serializable, read, write");
            File obj = new File("obj");
            File one = new File("1.bin");
            File two = new File("2.txt");

            ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(obj));
            ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(obj));

            objOut.writeObject(officeB);
            Building build = null;
            build = (Building) objIn.readObject();

            System.out.println("Building = " + build.toString());

            Writer writer = new FileWriter(two);
            Reader rader = new FileReader(two);

            Buildings.writeBuilding(build, writer);
            Building build3 = Buildings.readBuilding(rader);
            System.out.println("build3 = " + build3.toString());

            OutputStream outStr1 = new FileOutputStream(one);
            InputStream inStr1 = new FileInputStream(one);

            Buildings.outputBuilding(build, outStr1);
            Building build2 = Buildings.inputBuilding(inStr1);


        } catch (InexchangeableSpacesException | InexchangeableFloorsException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("IOExceptions");
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    public static void checkTask5() {
        OfficeBuilding ob = (OfficeBuilding) checkTask3(true);
        Dwelling dw = (Dwelling) checkTask2(true);
        if (ob == null || dw == null)
            return;
        System.out.println("\n\n");
        System.out.println(dw.toString());
        System.out.println(ob.toString());
        System.out.println("dw.hashCode() " + dw.hashCode());
        System.out.println("ob.hashCode() " + ob.hashCode());
        System.out.println("ob.equals(dw)" + ob.equals((Object) dw));
        try {
            Building cloneDw = (Building) dw.clone();
            Building cloneOb = (Building) ob.clone();
            System.out.println("cloneDw.equals(dw) " + cloneDw.equals(dw));
            System.out.println("cloneOb.equals(ob) " + cloneOb.equals(ob));

            System.out.println("cloneDw.hashCode() " + cloneDw.hashCode());
            System.out.println("cloneOb.hashCode() " + cloneOb.hashCode());


            System.out.println(dw.toString());
            System.out.println(ob.toString());
            System.out.println("clone " + cloneDw.toString());
            System.out.println("clone " + cloneOb.toString());

        } catch (CloneNotSupportedException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * return:Hotel
     */
    public static Building checkTask6() {
        Building b = checkTask2(true);
        Floor[] floors = new Floor[b.getNumberOfFloors()];
        for (int i = 0; i < b.getNumberOfFloors(); ++i) {
            System.out.println("\t" + Arrays.toString(b.getFloor(i).getSpaces()));
            floors[i] = new HotelFloor(b.getFloor(i).getSpaces());
            ((HotelFloor) floors[i]).setStars((i % 5) + 1);
        }
        return new Hotel(floors);
        //Buildings.sort(b.getFloor(0).getSpaces(), (Space a1, Space a2)->{return a2.getCountOfRooms() - a1.getCountOfRooms()});
    }

    public static void checkTask7() {
        Building dw = checkTask2(true);
        System.out.println('\n');
        if (dw == null)
            return;
        Random rnd = new Random();
        int randpmInt = rnd.nextInt(dw.getNumberOfFloors());
        Floor floor = dw.getFloor(0);
        Cleaner cleaner = new Cleaner(floor);
        Repairer repairer = new Repairer(floor);
        cleaner.setPriority(Thread.MAX_PRIORITY);
        repairer.setPriority(Thread.NORM_PRIORITY);
        cleaner.start();
        repairer.start();
        cleaner.interrupt();
        repairer.interrupt();
    }

    public static void checkTask7_2() {
        Building dw = checkTask2(true);
        System.out.println('\n');
        if (dw == null)
            return;
        Random rnd = new Random();
        int randpmInt = rnd.nextInt(dw.getNumberOfFloors());
        Floor floor = dw.getFloor(0);
        QueueSemaphore sem = new QueueSemaphore(0);
        QueueSemaphore sem2 = new QueueSemaphore(0);
        SequentialRepairer repairer = new SequentialRepairer(floor, sem, sem2);
        SequentialCleaner cleaner = new SequentialCleaner(floor, sem2, sem);
        Thread th1 = new Thread(repairer);
        Thread th2 = new Thread(cleaner);
        th1.start();
        th2.start();
        sem.release();
    }

    public static void generateForTask8() {
        Random rnd = new Random();
        int countBuildings = rnd.nextInt(11) + 7;
        Building[] buildings = new Building[countBuildings];

        String name1 = "D:\\Projects\\Java\\tasks from NetCreacker\\base construction\\src\\com\\company\\buildings\\net\\buildingsMaintenances.bin";
        String name2 = "D:\\Projects\\Java\\tasks from NetCreacker\\base construction\\src\\com\\company\\buildings\\net\\buildingsTypes.bin";
        try {
            DataOutputStream outBuild = new DataOutputStream(new FileOutputStream(new File(name1)));
            DataOutputStream outType = new DataOutputStream(new FileOutputStream(new File(name2)));
            String type = "";
            for (int i = 0; i < countBuildings; ++i) {
                switch (i % 3) {
                    case 0: {
                        buildings[i] = checkTask2(true);
                        type = "Dwelling";
                        break;
                    }
                    case 2: {
                        buildings[i] = checkTask6();
                        type = "Hotel";
                        break;
                    }
                    case 1: {
                        buildings[i] = checkTask3(true);
                        type = "Office";
                        break;
                    }
                }
                System.out.println(i + " " + buildings[i].toString());
                outType.writeChars(type);
                outType.writeChar('\n');
                Buildings.outputBuilding(buildings[i], outBuild);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void checkTask9() {
        BuildingsGUI b = new BuildingsGUI(checkTask2(true));
    }
}
