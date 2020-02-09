package com.company.buildings.net.server.parallel;

import com.company.Interfaces.Building;
import com.company.exceptions.BuildingUnderArrestException;

import java.io.*;
import java.net.Socket;
import java.util.Random;

public class SerialWorkWithClient implements Runnable {
    private Socket clientSocket;
    private int i;

    public SerialWorkWithClient(Socket socket, int index) {
        this.clientSocket = socket;
        this.i = index;
    }

    @Override
    public void run() {

        try {
            log("Getting streams");
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            //InputStream in = clientSocket.getInputStream();
            log("Streams are got");

            ObjectInputStream socketInStream = new ObjectInputStream(clientSocket.getInputStream());
            Building building;
            for (int i = 0; (building = SerialServer.readBuildFromSocket(socketInStream)) != null; ++i) {
                log("Building have read");
                Random rnd = new Random();
                Object answer = null;

                log("Calculate answer");
                try {
                    if (rnd.nextInt(10) != 0)
                        answer = ((Double) BinaryServer.calcCost(building));
                    else
                        throw new BuildingUnderArrestException("Arrested " + building.toString());

                } catch (BuildingUnderArrestException e) {
                    System.err.println(e.getMessage());
                    answer = e;
                }
                String strForLog = answer instanceof Double ? answer.toString() : "";
                log(i + " Message post " + strForLog);
                out.writeObject(answer);
            }
            log("den\n");
            socketInStream.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void log(String str) {
        BinaryServer.log(" " + this.i + ": " + str);
    }
}
