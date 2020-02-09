package com.company.tool_classes;

import com.company.Interfaces.*;
import com.company.buildings.SynchronizedFloor;
import com.company.buildings.dwelling.Dwelling;
import com.company.buildings.dwelling.DwellingFloor;
import com.company.buildings.dwelling.Flat;
import com.company.buildings.factory.DwellingFactory;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Buildings {
    static private BuildingFactory factory = new DwellingFactory();


    public static void outputBuilding(Building building, OutputStream out) {
        //поток не мы открыли не мы и закроем его. И поэтому не кладем его в токенайзер
        DataOutputStream outStream;
        try {
            outStream = new DataOutputStream(new BufferedOutputStream(out));
            /*Записанные данные о здании представляет собой последовательность чисел,
             первым из которых является количество этажей,
             далее следует количество помещений текущего этажа
             и соответствующие значения количества комнат и площадей помещений текущего этажа.
                Например, символьная запись для трехэтажного здания:
                 «3 2 3 150.0 2 100.0 1 3 250.0 3 2 140.0 1 60.0 1 50.0»
            * */
            int n = building.getNumberOfFloors();
            outStream.writeInt(n);
            for (int i = 0; i < n; ++i) {
                Floor floor = building.getFloor(i);
                //outStream.write(' ');
                outStream.writeInt(floor.getCountOfSpace());
                for (int j = 0; j < floor.getCountOfSpace(); ++j) {
                    Space space = floor.getSpace(j);
                    //outStream.write(' ');
                    outStream.writeInt(space.getCountOfRooms());
                    //outStream.write(' ');
                    outStream.writeDouble(space.getArea());
                }
            }
            outStream.writeChar('\n');
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }


    public static Building inputBuilding(InputStream in) {
        //поток не мы открыли не мы и закроем его. И поэтому не кладем его в токенайзер
        DataInputStream inStream;
        Building building = null;
        try {
            inStream = new DataInputStream(in);
            int countFloors = inStream.readInt();
            Floor[] floors = new Floor[countFloors];
            for (int i = 0; i < countFloors; ++i) {
                //inStream.readChar();
                int countOfSpace = inStream.readInt();
                Space[] flats = new Space[countOfSpace];
                for (int j = 0; j < countOfSpace; ++j) {
                    //inStream.readChar();
                    int rooms = inStream.readInt();
                    //inStream.readChar();
                    double area = inStream.readDouble();
                    flats[j] = Buildings.createSpace(rooms, area);
                }
                floors[i] = Buildings.factory.createFloor(flats);
            }
            inStream.readChar();
            building = Buildings.createBuilding(floors);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
        return building;
    }

    public static void writeBuilding(Building building, Writer out) {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(out);
            writer.write("" + building.getNumberOfFloors());
            for (int i = 0; i < building.getNumberOfFloors(); ++i) {
                Floor floor = building.getFloor(i);
                writer.write(" " + floor.getCountOfSpace());
                for (int j = 0; j < floor.getCountOfSpace(); ++j) {
                    Space space = floor.getSpace(j);
                    writer.write(" " + space.getCountOfRooms());
                    writer.write(" " + space.getArea());
                }
            }
            writer.write('\n');
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }


    public static Building readBuilding(Reader in) {
        Building building = null;
        BufferedReader reader;
        try {
            Scanner scan = new Scanner(in);
            int countFloors = scan.nextInt();
            Floor[] floors = new Floor[countFloors];
            for (int i = 0; i < countFloors; ++i) {
                int countOfSpace = scan.nextInt();
                Space[] flats = new Space[countOfSpace];
                for (int j = 0; j < countOfSpace; ++j) {
                    int countOfRooms = scan.nextInt();
                    String doubleStr = scan.next();
                    double area = Double.parseDouble(doubleStr);
                    flats[j] = Buildings.createSpace(countOfRooms, area);
                }
                floors[i] = Buildings.factory.createFloor(flats);
            }
            building = Buildings.createBuilding(floors);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
        return building;
    }


    /**
     * Don't tested
     */
    public static void writeBuildingFormat(Building building, Writer out) {
        BufferedWriter writer;
        Formatter formatter = new Formatter();
        formatter.format("%d", building.getNumberOfFloors());
        for (int i = 0; i < building.getNumberOfFloors(); ++i) {
            Floor floor = building.getFloor(i);
            formatter.format(" %d", floor.getCountOfSpace());
            for (int j = 0; j < floor.getCountOfSpace(); ++j) {
                Space space = floor.getSpace(j);
                formatter.format(" %d %f", space.getCountOfRooms(), space.getArea());
            }
        }
        formatter.format("\n");

        try {
            writer = new BufferedWriter(out);
            writer.write(formatter.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    public static <T extends Comparable<T>> void sort(T[] objects) {
        for (int i = 0; i < objects.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < objects.length; j++) {
                if (objects[j].compareTo(objects[minIndex]) < 0)
                    minIndex = j;
            }
            T swapBuf = objects[i];
            objects[i] = objects[minIndex];
            objects[minIndex] = swapBuf;
        }
    }

    public static <T> void sort(T[] object, Comparator<T> comparator) {
        Arrays.sort(object, comparator);
    }

    public static void setBuildingFactory(BuildingFactory newFactory) throws NullPointerException {
        if (newFactory == null)
            throw new NullPointerException("Fabric must be not null");
        Buildings.factory = newFactory;
    }

    public static Space createSpace(double area) {
        return Buildings.factory.createSpace(area);
    }

    public static Space createSpace(int roomsCount, double area) {
        return Buildings.factory.createSpace(roomsCount, area);
    }

    public static Floor createFloor(int spacesCount) {
        return Buildings.factory.createFloor(spacesCount);
    }

    public static Floor createFloor(Space[] spaces) {
        return Buildings.factory.createFloor(spaces);
    }

    public static Building createBuilding(int floorsCount, int[] spacesCounts) {
        return Buildings.factory.createBuilding(floorsCount, spacesCounts);
    }

    public static Building createBuilding(Floor[] floors) {
        return Buildings.factory.createBuilding(floors);
    }

    public static Floor synchronizedFloor(Floor floor) {
        if (floor instanceof SynchronizedFloor)
            return floor;
        return new SynchronizedFloor(floor);
    }
    //===========================================Reflection=============================================================

    public static Space createSpace(Class<? extends Space> tClass, double area) {
        try {
            return tClass.getConstructor(double.class).newInstance(area);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchMethodException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static Space createSpace(Class<? extends Space> tClass, double area, int roomsCount) {
        try {
            return tClass.getConstructor(double.class, int.class).newInstance(area, roomsCount);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchMethodException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static Floor createFloor(Class<? extends Floor> tClass, int spacesCount) {
        try {
            return tClass.getConstructor(int.class).newInstance(spacesCount);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchMethodException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

    }

    public static Floor createFloor(Class<? extends Floor> tClass, Space... spaces) {
        try {
            return tClass.getConstructor(Space[].class).newInstance(spaces);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchMethodException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static Building createBuilding(Class<? extends Building> tClass, int floorsCount, int... spacesCounts) {
        try {
            return tClass.getConstructor(int.class, int[].class).newInstance(floorsCount, spacesCounts);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchMethodException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static Building createBuilding(Class<? extends Building> tClass, Floor... floors) {
        try {
            return tClass.getConstructor(Floor[].class).newInstance(floors);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchMethodException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static Building inputBuilding(InputStream in, Class<? extends Building> buildingClass, Class<? extends Floor> floorClass, Class<? extends Space> spaceClass) throws IOException {
        DataInputStream dataInputStream;
        Building building = null;
        try {
            dataInputStream = new DataInputStream(in);
            int floorCount = dataInputStream.readInt();
            Floor[] floors = new Floor[floorCount];
            for (int i = 0; i < floorCount; ++i) {
                int spaceCount = dataInputStream.readInt();
                Space[] spaces = new Space[spaceCount];
                for (int j = 0; j < spaceCount; ++j)
                    spaces[j] = createSpace(spaceClass, dataInputStream.readDouble(), dataInputStream.readInt());
                floors[i] = createFloor(floorClass, spaces);
            }
            building = Buildings.createBuilding(buildingClass, floors);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
        return building;
    }

    public static Building readBuilding(Reader in, Class<? extends Building> buildingClass, Class<? extends Floor> floorClass, Class<? extends Space> spaceClass) throws IOException {
        Building building = null;

        try {
            Scanner scan = new Scanner(in);
            int floorCount = scan.nextInt();
            Floor[] floors = new Floor[floorCount];
            for (int i = 0; i < floorCount; ++i) {
                int spaceCount = scan.nextInt();
                Space[] spaces = new Space[spaceCount];
                for (int j = 0; j < spaceCount; ++j) {
                    int countOfRooms = scan.nextInt();
                    String doubleStr = scan.next();
                    double area = Double.parseDouble(doubleStr);
                    spaces[j] = createSpace(spaceClass, area, countOfRooms);
                }
                floors[i] = createFloor(floorClass, spaces);
            }
            building = createBuilding(buildingClass, floors);
        } catch (
                NoSuchElementException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
        return building;
    }
}
