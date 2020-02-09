package com.company.buildings.net.client;

import com.company.Interfaces.Building;
import com.company.Interfaces.BuildingFactory;
import com.company.buildings.dwelling.Dwelling;
import com.company.buildings.dwelling.hotel.Hotel;
import com.company.buildings.factory.DwellingFactory;
import com.company.buildings.factory.HotelFactory;
import com.company.buildings.factory.OfficeFactory;
import com.company.buildings.office.Office;
import com.company.buildings.office.OfficeBuilding;
import com.company.tool_classes.BikeBugFun;
import com.company.tool_classes.Buildings;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class BinaryClient {
    private static FileWriter log = null;

    public static void main(String[] args) {
        Socket clientSocket = null;
        DataOutputStream out = null;
        DataInputStream in = null;
        FileWriter writer = null;
        String name1 = "D:\\Projects\\Java\\tasks from NetCreacker\\base construction\\src\\com\\company\\buildings\\net\\buildingsMaintenances.bin";
        String name2 = "D:\\Projects\\Java\\tasks from NetCreacker\\base construction\\src\\com\\company\\buildings\\net\\buildingsTypes.bin";
        String name3 = "D:\\Projects\\Java\\tasks from NetCreacker\\base construction\\src\\com\\company\\buildings\\net\\buildingsCosts.txt";
        String host = "127.0.0.1";
        log("Client started");
        try {
            clientSocket = new Socket(host, 7777);
            log("Socket is instantiate");
            //Firstly, have got OutputStream, and after get InputStream. Else OutputStream will close and will not can to work with it;
            out = new DataOutputStream(clientSocket.getOutputStream());
            log("OutputStream is instantiate");
            in = new DataInputStream(clientSocket.getInputStream());
            log("InputStream is instantiate");
            writer = new FileWriter(new File(name3));
            log("Writer is instantiate");
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + host);
            System.exit(1);
        } catch (IOException e) {
            System.err.println(("Couldn't get I/O for the connection to:" + host));
            System.exit(1);
        }


        log("Start to read building from file:\n\t" + name1 + "\n\t" + name2);
        Building[] buildings = read(name1, name2);
        log("Finished to read building");
        log("Client-Server work");
        try {
            for (int i = 0; i < buildings.length; ++i) {
                log("Iteration of building transfer: " + i);
                String toServer = (BinaryClient.getType(buildings[i]) + "\n");
                //log("toServer = " + toServer);
                //log("toServer = " + toServer.getBytes());
                out.writeChars(toServer);
                Buildings.outputBuilding(buildings[i], out);
                log("Wait answer form server");

                while (in.available() == 0)
                    sll(10);
                String ans = BikeBugFun.readLine(in, '\n');
                ans += (" " + buildings[i].toString() + '\n');
                //System.out.print(ans);
                writer.write(ans);
                log(ans);
            }
            out.writeChars("END TRANSMISSION\n");
            sll(1000);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
        log("Client disconnect");
        try {
            in.close();
            out.close();
            writer.close();
            //clientSocket.close();
        } catch (IOException e) {
            System.err.println("Can not to close stream or socket");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        log("NED");
    }

    public static void log(String str) {

        System.out.println(str);
//        try {
//            if (log == null)
//                log = new FileWriter(new File("BinaryClientLog.txt"));
//            log.write(str);
//            log.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.err.println(e.getMessage());
//        }
    }

    public static Building[] read(String nameOfFileWithBuildings, String nameOfFileWithType) {
        log("Reading starting");

        ArrayList<Building> buildings = new ArrayList<>();

        File fileWithBuildings = new File(nameOfFileWithBuildings);
        File fileWithType = new File(nameOfFileWithType);

        log("File is instantiate");

        try {
            InputStream inBuild = new FileInputStream(fileWithBuildings);
            DataInputStream inType = new DataInputStream(new FileInputStream(fileWithType));
            log("Streams is instantiate");

            String type;

            for (int i = 0; (type = BikeBugFun.readLine(inType, '\n')) != ""; ++i) {
                log("Begin of iteration " + i);
                /*почему то в принятой строке между символами сторт /u0000  с BufferedReader*/

                BikeBugFun.setFactory(type);
                log("Factory set on " + type);
                Building readBuild = Buildings.inputBuilding(inBuild);
                buildings.add(readBuild);
                log("Building read\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
        Building[] arrBuildings = new Building[buildings.size()];
        for (int i = 0; i < buildings.size(); ++i)
            arrBuildings[i] = buildings.get(i);

        return arrBuildings;
    }

    public static String getType(Building building) {
        String[] types = {"Dwelling", "Hotel", "Office"};
        if (building instanceof Hotel)
            return types[1];

        if (building instanceof Dwelling)
            return types[0];

        if (building instanceof OfficeBuilding)
            return types[2];

        return "";
    }

    public static void sll(long m) {
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.err.println("Never give to sleep");
        }
    }
}
