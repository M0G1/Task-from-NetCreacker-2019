package com.company.GUI;

import com.company.Interfaces.Building;
import com.company.Interfaces.BuildingFactory;
import com.company.Interfaces.Floor;
import com.company.Interfaces.Space;
import com.company.buildings.dwelling.Dwelling;
import com.company.buildings.dwelling.DwellingFloor;
import com.company.buildings.dwelling.Flat;
import com.company.buildings.factory.DwellingFactory;
import com.company.buildings.factory.OfficeFactory;
import com.company.tool_classes.Buildings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class BuildingsGUI extends JFrame {

    private JLabel buildingInfo = new JLabel();
    private JLabel floorInfo = new JLabel();
    private JLabel spaceInfo = new JLabel();
    private JPanel buildingPlan = new JPanel();
    private Building building;
    private int selectedSpaceIndex = 0;
    private String lookAndFeel;

    private void writeInfo() {
        int floorIndex = 0;
        int spaceIndex = this.selectedSpaceIndex;
        for (int i = 0; i < building.getNumberOfFloors(); ++i) {
            if (building.getFloor(i).getCountOfSpace() <= spaceIndex) {
                spaceIndex -= building.getFloor(i).getCountOfSpace();
                ++floorIndex;
            }
            else break;
        }
        buildingInfo.setText(building.getClass().getSimpleName() + " floors: " + building.getNumberOfFloors() + " total area: " + building.getTotalAreaOfSpaces());
        Floor floor = building.getFloor(floorIndex);
        floorInfo.setText("Floor number: " + floorIndex + " spaces: " + floor.getCountOfSpace() + " total area: " + floor.getTotalArea());
        Space space = building.getSpace(selectedSpaceIndex);
        spaceInfo.setText("Space number: " + selectedSpaceIndex + " rooms: " + space.getCountOfRooms() + " total area: " + space.getArea());
    }

    private void readBuildingFromFile(BuildingFactory factory) {
        JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
        int returnValue = chooser.showOpenDialog(null);
        if(returnValue == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                FileReader reader = new FileReader(file);
                Buildings.setBuildingFactory(factory);
                building = Buildings.readBuilding(reader);
                reader.close();
                writeInfo();
                drawBuildingPlan();
            }
            catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }

    private void drawBuildingPlan() {
        buildingPlan.removeAll();
        if (building != null) {
            buildingPlan.setLayout(new BoxLayout(buildingPlan, BoxLayout.Y_AXIS));
            int spaceNumber = 0;
            for (int i = 0; i < building.getNumberOfFloors(); ++i) {
                JPanel panel = new JPanel();
                FlowLayout fl = new FlowLayout();
                fl.setAlignment(FlowLayout.LEFT);
                panel.setLayout(fl);
                panel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
                for (int j = 0; j < building.getFloor(i).getCountOfSpace(); ++j) {
                    JButton button = new JButton(String.valueOf(spaceNumber++));
                    button.addActionListener((ActionEvent e) -> {
                                selectedSpaceIndex = Integer.valueOf(button.getText());
                                writeInfo();
                            }
                    );
                    panel.add(button);
                }
                buildingPlan.add(panel);
            }
        }
    }

    private void initLookAndFeel(String lookAndFeelName) {
        switch (lookAndFeelName) {
            case "Metal":
                lookAndFeel = "javax.swing.plaf.metal.MetalLookAndFeel";
                break;

            case "Nimbus":
                lookAndFeel = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
                break;

            case "CDE/Motif":
                lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
                break;

            case "Windows":
                lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
                break;

            case "Windows Classic":
                lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel";
        }
        try {
            UIManager.setLookAndFeel(lookAndFeel);
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public BuildingsGUI(Building build) {
        building = build;

        drawComponents();
    }

    private void drawComponents() {

        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("File");

        JMenuItem item = new JMenuItem("Open dwelling");
        item.addActionListener((ActionEvent e) -> readBuildingFromFile(new DwellingFactory()));
        menu.add(item);

        item = new JMenuItem("Open office building");
        item.addActionListener((ActionEvent e) -> readBuildingFromFile(new OfficeFactory()));
        menu.add(item);

        menuBar.add(menu);

        menu = new JMenu("Style");
        ButtonGroup group = new ButtonGroup();

        JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem("Metal");
        menuItem.addActionListener((ActionEvent e) -> initLookAndFeel("Metal"));
        group.add(menuItem);
        menu.add(menuItem);

        menuItem = new JRadioButtonMenuItem("Nimbus");
        menuItem.addActionListener((ActionEvent e) -> initLookAndFeel("Nimbus"));
        group.add(menuItem);
        menu.add(menuItem);

        menuItem = new JRadioButtonMenuItem("CDE/Motif");
        menuItem.addActionListener((ActionEvent e) -> initLookAndFeel("CDE/Motif"));
        group.add(menuItem);
        menu.add(menuItem);

        menuItem = new JRadioButtonMenuItem("Windows");
        menuItem.addActionListener((ActionEvent e) -> initLookAndFeel("Windows"));
        group.add(menuItem);
        menu.add(menuItem);

        menuItem = new JRadioButtonMenuItem("Windows Classic");
        menuItem.addActionListener((ActionEvent e) -> initLookAndFeel("Windows Classic"));
        group.add(menuItem);
        menu.add(menuItem);

        menuBar.add(menu);

        drawBuildingPlan();

        Container container = new Container();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(menuBar);
        container.add(buildingInfo);
        container.add(floorInfo);
        container.add(spaceInfo);
        container.add(buildingPlan);

        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setLayout(new ScrollPaneLayout());

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(scrollPane);
        this.pack();
        this.setVisible(true);
    }
}