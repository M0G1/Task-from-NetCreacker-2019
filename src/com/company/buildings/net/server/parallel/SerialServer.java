package com.company.buildings.net.server.parallel;

import com.company.Interfaces.Building;
import com.company.buildings.dwelling.Dwelling;
import com.company.buildings.dwelling.hotel.Hotel;
import com.company.buildings.office.OfficeBuilding;
import com.company.tool_classes.BikeBugFun;
import com.company.tool_classes.Buildings;

import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SerialServer {
    private static FileWriter log;
    private static double[] costs = {1000, 1500, 2000};

    public static void log(String str) {
        System.out.println(str);
//        try {
////            if (log == null)
////                log = new FileWriter(new File("BinaryServerLog.txt"));
////            log.write(str + '\n');
////            log.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.err.println(e.getMessage());
//        }
    }

    public static void main(String[] args) {
        ServerSocket serverSocket;
        Socket clientSocket = null;

        try {
            log("Try to open server");
            serverSocket = new ServerSocket(7777);
            log("Server is open");

            for (int i = 0; true; ++i) {
                log("Try to get connection");
                clientSocket = serverSocket.accept();
                log("Connection is got");
                SerialWorkWithClient tool = new SerialWorkWithClient(clientSocket, i);
                Thread client = new Thread(tool);
                client.start();
                log(i + " Thread started");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
        log("DEN");
    }

    public static double calcCost(Building building) {
        double totalArea = building.getTotalAreaOfSpaces();

        if (building instanceof Hotel)
            return totalArea * costs[2];

        if (building instanceof Dwelling)
            return totalArea * costs[0];

        if (building instanceof OfficeBuilding)
            return totalArea * costs[1];

        return 0;
    }

    public static Building readBuildFromSocket(ObjectInputStream in) {
        StringBuilder ans = new StringBuilder();
        try {
            char ch;
            while (in.available() == 0)
                sll(10);
            while (in.available() > 0 && (ch = in.readChar()) != '\n')
                ans.append(ch);

            String type = ans.toString();
            log("ans:" + ans);
            if (!type.equals("END TRANSMISSION")) {
                for (long i = 0; i < 100 && in.available() == 0; i += 10)
                    sll(10);
                return (Building) in.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            System.err.println("Try to read: " + ans.toString());
        }
        return null;
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
