package com.company.tool_classes;

import com.company.Interfaces.BuildingFactory;
import com.company.buildings.factory.DwellingFactory;
import com.company.buildings.factory.HotelFactory;
import com.company.buildings.factory.OfficeFactory;

import java.io.DataInputStream;
import java.io.IOException;

public class BikeBugFun {
    /*return string from bite file before symbol delimiter. Pointer points to the character after*/
    public static String readLine(DataInputStream in, char delimiter) {
        StringBuilder ans = new StringBuilder();
        try {
            char ch;
            while (in.available() > 0 && (ch = in.readChar()) != delimiter)
                ans.append(ch);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            System.err.println("Try to read: " + ans.toString());
            return "";
        }
        if (ans.length() == 0)
            return "";
        return ans.toString();
    }

    /**
     * types = {"Dwelling", "Hotel", "Office"}
     */
    public static boolean setFactory(String type) {
        String[] types = {"Dwelling", "Hotel", "Office"};
        BuildingFactory[] factories = {
                new DwellingFactory(),
                new HotelFactory(),
                new OfficeFactory()
        };
        for (int j = 0; j < 3; ++j) {
            if (type.equals(types[j])) {
                Buildings.setBuildingFactory(factories[j]);
                return true;
            }
        }
        System.err.println("Unexpected string: " + type + ". Type must be one of {\"Dwelling\", \"Hotel\", \"Office\"}");
        return false;
    }
}
