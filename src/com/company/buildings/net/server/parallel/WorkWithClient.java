package com.company.buildings.net.server.parallel;

import com.company.Interfaces.Building;
import com.company.exceptions.BuildingUnderArrestException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Random;

public class WorkWithClient implements Runnable {
    private Socket clientSocket;
    private int i;

    public WorkWithClient(Socket socket, int index) {
        this.clientSocket = socket;
        this.i = index;
    }

    @Override
    public void run() {

        try {
            log("Getting streams");
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
            //InputStream in = clientSocket.getInputStream();
            log("Streams are got");

            DataInputStream socketInStream = new DataInputStream(clientSocket.getInputStream());
            Building building;

            for (int i = 0; (building = BinaryServer.readBuildFromSocket(socketInStream)) != null; ++i) {
                log("Building have read");
                Random rnd = new Random();
                String answer = "";

                log("Calculate answer");
                try {
                    if (rnd.nextInt(10) != 0)
                        answer = ((Double) BinaryServer.calcCost(building)).toString();
                    else
                        throw new BuildingUnderArrestException("Arrested " + building.toString());

                } catch (BuildingUnderArrestException e) {
                    System.err.println(e.getMessage());
                    answer = "Arrested";
                }
                answer += '\n';


                log(i + " Message post " + answer);
                out.writeChars(answer);
            }
            log("den\n");
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
