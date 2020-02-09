package com.company.buildings.dwelling.hotel;

import com.company.Interfaces.Space;
import com.company.buildings.dwelling.Dwelling;
import com.company.buildings.dwelling.DwellingFloor;

public class HotelFloor extends DwellingFloor {
    private static final int DEFAULT_STARS = 1;
    private int stars;

    public HotelFloor(int countOfFlats) throws IllegalArgumentException {
        super(countOfFlats);
        this.stars = DEFAULT_STARS;
    }

    public HotelFloor(Space[] spaces) {
        super(spaces);
        this.stars = DEFAULT_STARS;
    }

//========================================Getter============================================

    public int getStars() {
        return stars;
    }

//========================================Setter============================================

    public void setStars(int stars) throws IllegalArgumentException {
        if (stars >= 1 && stars <= 5) {
            this.stars = stars;
            return;
        }
        throw new IllegalArgumentException("Invalid value of stars - " + stars);

    }

//========================================Object============================================

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(String.format("HotelFloor(%d, %d", stars, this.getCountOfSpace()));
        for (int i = 0; i < this.getCountOfSpace(); ++i) {
            str.append(", ");
            Space space = this.getSpace(i);
            if (space != null)
                str.append(space.toString());
            else
                str.append("null");
        }
        str.append(')');
        return str.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof HotelFloor))
            return false;
        HotelFloor hf = (HotelFloor) obj;
        if (hf.stars != this.stars || hf.getCountOfSpace() != this.getCountOfSpace())
            return false;
        for (int i = 0; i < this.getCountOfSpace(); ++i) {
            Space thisSpace = this.getSpace(i);
            Space otherSpace = hf.getSpace(i);
            if (!thisSpace.equals(otherSpace))
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = this.stars;
        long sN = 19;
        long acum = 1;
        for (int i = 0; i < this.getCountOfSpace(); ++i, acum *= sN) {
            Space space = this.getSpace(i);
            if (space != null)
                hash ^= acum * space.hashCode();
        }
        return hash;
    }

}
