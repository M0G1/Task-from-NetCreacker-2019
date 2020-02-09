package com.company.buildings.dwelling;

import com.company.Interfaces.Building;
import com.company.Interfaces.Floor;
import com.company.Interfaces.Space;
import com.company.exceptions.*;

import java.util.Iterator;


public class Dwelling implements Building {
    private Floor[] floors;

//========================================Constructors============================================

    public Dwelling(int countOfFloors, int[] countOfSpaces) throws IllegalArgumentException {
        if (countOfFloors != countOfSpaces.length) {
            throw new IllegalArgumentException(String.format("Length of countOfSpaces(%d) and number countOfFloors(%d) are different", countOfSpaces.length, countOfFloors));
        }
        this.floors = new Floor[countOfFloors];
        for (int i = 0; i < countOfFloors; ++i)
            floors[i] = new DwellingFloor(countOfSpaces[i]);
    }

    public Dwelling(Floor[] floors) {
        this.floors = floors;
    }


//========================================Getter============================================

    public int getNumberOfFloors() {
        if (this.floors == null)
            return 0;
        return this.floors.length;
    }

    public long getNumberOfSpaces() {
        if (this.floors == null)
            return 0;
        long sum = 0;
        for (Floor floor : this.floors) {
            if (floor != null)
                sum += floor.getCountOfSpace();
        }
        return sum;
    }

    public double getTotalAreaOfSpaces() {
        if (this.floors == null)
            return 0;
        double sum = 0;
        for (Floor floor : this.floors) {
            if (floor != null)
                sum += floor.getTotalArea();
        }
        return sum;
    }

    public long getTotalNumberOfRooms() {
        if (this.floors == null)
            return 0;
        long sum = 0;
        for (Floor floor : this.floors) {
            if (floor != null)
                sum += floor.getTotalCountOfRoom();
        }
        return sum;
    }

    public Floor getFloor(int index) throws FloorIndexOutOfBoundsException {
        if (index < 0 || index >= this.getNumberOfFloors()) {
            throw new FloorIndexOutOfBoundsException(String.format("Incorrect index of floor(%d)", index));
        }
        return this.floors[index];
    }

    public Floor[] getFloors() {
        return this.floors;
    }

    public Floor getDwellingFloor(int index) throws FloorIndexOutOfBoundsException, NullPointerException {
        if (this.floors == null)
            throw new NullPointerException("No floors");
        if (index >= this.getNumberOfFloors() || index < 0)
            throw new FloorIndexOutOfBoundsException(String.format("Incorrect index of floor(%d)", index));

        return this.floors[index];
    }

    /**
     * метод получения объекта квартиры по ее номеру в доме.
     */
    public Space getSpace(int number) throws SpaceIndexOutOfBoundsException, NullPointerException {
        if (this.floors == null)
            throw new NullPointerException("No floors");
        if (number >= this.getNumberOfSpaces() || number < 0)
            throw new SpaceIndexOutOfBoundsException(String.format("Incorrect number(%d)", number));
        for (int i = 0; i < this.getNumberOfFloors(); ++i) {
            if (this.floors[i] != null) {
                if (number - this.floors[i].getCountOfSpace() < 0) {
                    return this.floors[i].getSpace(number);
                }
                number -= this.floors[i].getCountOfSpace();
            }
        }
        return null;
    }

//========================================Setter============================================

    /** */
    public Floor setFloor(int number, Floor floor) throws FloorIndexOutOfBoundsException, NullPointerException {
        if (number >= this.getNumberOfFloors() || number < 0)
            throw new FloorIndexOutOfBoundsException(String.format("Incorrect number(%d)", number));
        if (this.floors == null)
            throw new NullPointerException("No floors");

        Floor temp = this.floors[number];
        this.floors[number] = floor;
        return temp;
    }

    public Space setSpaceInBuilding(int number, Space Space) throws SpaceIndexOutOfBoundsException, NullPointerException {
        if (number >= this.getNumberOfSpaces() || number < 0)
            throw new SpaceIndexOutOfBoundsException(String.format("Incorrect number(%d)", number));
        if (this.floors == null)
            throw new FloorIndexOutOfBoundsException(String.format("Incorrect number(%d)", number));
        Space temp = null;
        for (int i = 0; i < this.getNumberOfFloors(); ++i) {
            if (this.floors[i] != null) {
                if (number - this.floors[i].getCountOfSpace() < 0) {
                    return this.floors[i].setSpace(number, Space);
                }
                number -= this.floors[i].getCountOfSpace();
            }
        }
        return temp;
    }


//========================================Methods============================================

    public boolean insertSpaceInBuilding(int number, Space Space) throws SpaceIndexOutOfBoundsException, NullPointerException {
        if (number < 0 || number > this.getTotalNumberOfRooms()) {
            throw new SpaceIndexOutOfBoundsException(String.format("Haven't Space with this number(%d)", number));
        }
        if (this.floors == null)
            throw new NullPointerException("No floors");
        for (int i = 0; i < this.getNumberOfFloors(); ++i) {
            if (number - this.floors[i].getCountOfSpace() < 0) {
                return this.floors[i].insertSpace(number, Space);
            }
            number -= this.floors[i].getCountOfSpace();
        }
        Floor temp = this.floors[this.getNumberOfFloors() - 1];
        return temp.insertSpace(temp.getCountOfSpace(), Space);
    }

    public Space eraseSpaceInBuilding(int number) throws NullPointerException {
        if (this.floors == null)
            throw new NullPointerException("No floors");
        for (int i = 0; i < this.getNumberOfFloors(); ++i) {
            if (number - this.floors[i].getCountOfSpace() < 0) {
                return this.floors[i].eraseSpace(number);
            }
            number -= this.floors[i].getCountOfSpace();
        }
        return null;
    }

    public Space getBestSpace() {
        if (this.floors == null)
            return null;
        double max = 0;
        Space ans = null;
        for (int i = 0; i < this.getNumberOfFloors(); ++i) {
            Floor temp = this.getDwellingFloor(i);
            if (temp != null) {
                Space mayBeMax = temp.getBestSpace();
                if (mayBeMax.getArea() > max) {
                    max = mayBeMax.getArea();
                    ans = mayBeMax;
                }
            }
        }
        return ans;
    }

    public Space[] getSortSpacesOnArea() {
        if (this.floors == null)
            return null;
        Space[] sorted = new Space[(int) this.getNumberOfSpaces()];
        int lastFreeIndex = 0;
        for (Floor floor : this.floors) {
            if (floor == null)
                continue;
            for (int i = 0; i < floor.getCountOfSpace(); ++i) {
                sorted[lastFreeIndex] = floor.getSpace(i);
                if (sorted[lastFreeIndex] == null)
                    continue;
                for (int t = lastFreeIndex - 1; t >= 0; --t) {
                    if (sorted[t] == null || sorted[t].getArea() < sorted[t + 1].getArea()) {
                        Space temp = sorted[t + 1];
                        sorted[t + 1] = sorted[t];
                        sorted[t] = temp;
                        continue;
                    }
                    break;
                }
            }
        }
        return sorted;
    }
//========================================Object============================================


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Dwelling(" + this.floors.length);
        for (int i = 0; i < this.floors.length; ++i) {
            Floor temp = this.floors[i];
            str.append(", ");
            if (temp != null) {
                str.append(temp.toString());
            } else
                str.append("null");
        }
        str.append(')');
        return str.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Dwelling))
            return false;
        Dwelling dw = (Dwelling) obj;
        if (dw.floors.length != this.floors.length)
            return false;
        for (int i = 0; i < this.floors.length; ++i) {
            Floor thisFloor = this.getFloor(i);
            Floor otherFloor = dw.getFloor(i);
            if (thisFloor == null && otherFloor == null)
                continue;
            if (thisFloor == null || otherFloor == null)
                return false;
            if (!thisFloor.equals(otherFloor))
                return false;
        }
        return true;
    }


    @Override
    public int hashCode() {
        int hash = this.getNumberOfFloors();
        long sN = 13;
        long acum = 1;
        for (int i = 0; i < this.getNumberOfFloors(); ++i, acum *= sN) {
            if (this.getFloor(i) != null)
                hash ^= (this.getFloor(i).hashCode() * acum);
        }
        return hash;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Floor[] cloneFloors = new Floor[this.floors.length];
        for (int i = 0; i < floors.length; ++i) {
            if (floors[i] != null)
                cloneFloors[i] = (Floor) this.floors[i].clone();
        }
        return new Dwelling(cloneFloors);
    }


//======================================== Iterator ============================================

    @Override
    public Iterator<Floor> iterator() {
        return new Iterator<Floor>() {
            private Dwelling dw = Dwelling.this;
            private int index = 0;
            @Override
            public boolean hasNext() {
                return index < dw.getNumberOfFloors();
            }

            @Override
            public Floor next() {
                return dw.getFloor(index++);
            }
        };
    }
}
