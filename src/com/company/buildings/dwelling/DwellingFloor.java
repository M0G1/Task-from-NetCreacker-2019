package com.company.buildings.dwelling;

import com.company.Interfaces.Floor;
import com.company.Interfaces.Space;
import com.company.exceptions.SpaceIndexOutOfBoundsException;

import java.util.Arrays;
import java.util.Iterator;


public class DwellingFloor implements Floor {
    private Space[] flats;
    private int countOfFlat;
//========================================Constructors============================================

    public DwellingFloor(int countOfFlats) throws IllegalArgumentException {
        if (countOfFlats < 1)
            throw new IllegalArgumentException("Count of Flats less than 1");
        this.countOfFlat = countOfFlats;
        this.flats = new Space[(int) (countOfFlats * 1.6)];
    }

    public DwellingFloor(Space[] flats) {
        this.flats = flats;
        this.countOfFlat = flats.length;
    }

//========================================Methods============================================

    public int getCountOfSpace() {
        if (flats != null)
            return this.countOfFlat;
        else
            return 0;
    }

    public double getTotalArea() {
        double sum = 0;
        if (flats != null) {
            for (int i = 0; i < this.countOfFlat; ++i) {
                if (this.flats[i] != null)
                    sum += this.flats[i].getArea();
            }
            return sum;
        }
        return 0;
    }

    public long getTotalCountOfRoom() {
        long sum = 0;
        if (flats != null) {
            for (int i = 0; i < this.countOfFlat; ++i) {
                if (this.flats[i] != null)
                    sum += this.flats[i].getCountOfRooms();
            }
            return sum;
        }
        return 0;
    }

    //=====================================Getter=and=Setter==========================================
    public Space[] getSpaces() {
        return Arrays.copyOf(this.flats, this.countOfFlat);
    }

    public Space getSpace(int index) throws SpaceIndexOutOfBoundsException {
        if (index >= this.countOfFlat || index < 0)
            throw new SpaceIndexOutOfBoundsException("" + index);
        if (flats != null)
            return flats[index];
        else
            return null;
    }

    public Space setSpace(int index, Space space) throws SpaceIndexOutOfBoundsException {
        if (index >= this.countOfFlat || index < 0)
            throw new SpaceIndexOutOfBoundsException("" + index);
        if (flats != null) {
            Space temp = this.flats[index];
            this.flats[index] = space;
            return temp;
        }
        return null;
    }

    public boolean insertSpace(int index, Space space) throws SpaceIndexOutOfBoundsException {
        if (index >= this.countOfFlat || index < 0)
            throw new SpaceIndexOutOfBoundsException("index: " + index);
        if (flats == null) {
            if (index == 0) {
                this.flats = new Space[2];
                this.flats[0] = space;
                this.countOfFlat = 1;
            }
            return false;
        }

        if (this.countOfFlat < this.flats.length) {
            this.flats[this.countOfFlat] = space;
            for (int i = this.countOfFlat; i > index; --i) {
                Space temp = this.flats[i];
                this.flats[i] = this.flats[i - 1];
                this.flats[i - 1] = temp;
            }
        } else {
            Space[] newFlats = new Space[(int) (this.countOfFlat * 1.6)];
            for (int i = 0; i < index; ++i)
                newFlats[i] = this.flats[i];
            newFlats[index] = space;
            for (int i = index; i < this.countOfFlat - 1; ++i)
                newFlats[i + 1] = this.flats[i];

            this.flats = newFlats;
        }
        ++countOfFlat;
        return true;
    }

    public Space eraseSpace(int index) throws SpaceIndexOutOfBoundsException {
        if (index >= this.countOfFlat || index < 0)
            throw new SpaceIndexOutOfBoundsException("" + index);
        if (flats != null) {
            Space erasable = this.flats[index];
            //свободное место не превышает квадрата золотого сечения
            if ((double) this.flats.length / this.countOfFlat > 1.6 * 1.6) {
                Space[] newFlats = new Space[(int) (this.countOfFlat * 1.6)];
                for (int i = 0; i < index; ++i)
                    newFlats[i] = this.flats[i];
                for (int i = index; i < this.countOfFlat; )
                    newFlats[i] = this.flats[i + 1];

                this.flats = newFlats;
            } else {
                for (int i = index; i < this.countOfFlat - 1; ++i)
                    this.flats[i] = this.flats[i + 1];
            }
            --this.countOfFlat;
            return erasable;
        }
        return null;
    }

    public Space getBestSpace() {
        if (flats == null) {
            return null;
        }
        double max = 0;
        Space ans = null;
        for (int i = 0; i < this.countOfFlat; ++i) {
            if (this.flats[i] != null) {
                if (this.flats[i].getArea() > max) {
                    max = this.flats[i].getArea();
                    ans = this.flats[i];
                }
            }
        }
        return ans;
    }

    //========================================Object============================================

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(String.format("DwellingFloor(%d", this.getCountOfSpace()));
        for (int i = 0; i < this.countOfFlat; ++i) {
            Space temp = this.flats[i];
            str.append(", ");
            if (temp != null) {
                str.append(temp.toString());
            } else
                str.append("null");
        }
        str.append(")");
        return str.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DwellingFloor))
            return false;
        DwellingFloor df = (DwellingFloor) obj;
        if (this.countOfFlat != df.countOfFlat)
            return false;
        for (int i = 0; i < this.countOfFlat; ++i) {
            if (this.getSpace(i) == null && df.getSpace(i) == null)
                continue;
            if (this.getSpace(i) == null || df.getSpace(i) == null)
                return false;
            if (!this.getSpace(i).equals(df.getSpace(i)))
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = this.countOfFlat;
        long sN = 7;
        long acum = 1;
        for (int i = 0; i < this.countOfFlat; ++i, acum *= sN) {
            if (this.getSpace(i) != null)
                hash ^= (this.getSpace(i).hashCode() * acum);
        }
        return hash;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Space[] spaces = new Space[this.flats.length];
        for (int i = 0; i < this.countOfFlat; ++i) {
            if (this.flats[i] != null)
                spaces[i] = (Space) this.flats[i].clone();
        }
        DwellingFloor ans = new DwellingFloor(spaces);
//        ans.flats = spaces;
//        ans.countOfFlat = this.countOfFlat;
        return ans;
    }
    //======================================== Iterator ============================================

    @Override
    public Iterator<Space> iterator() {
        return new Iterator<Space>() {
            private DwellingFloor of = DwellingFloor.this;
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < of.getCountOfSpace();
            }

            @Override
            public Space next() {
                return of.getSpace(index++);
            }
        };
    }

//========================================Compare===========================================

    @Override
    public int compareTo(Floor o) {
        return this.getCountOfSpace() - o.getCountOfSpace();
    }
}
