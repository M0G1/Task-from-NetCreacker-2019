package com.company.buildings.dwelling.hotel;

import com.company.Interfaces.Floor;
import com.company.Interfaces.Space;
import com.company.buildings.dwelling.Dwelling;
import com.company.buildings.dwelling.Flat;

public class Hotel extends Dwelling {
    public Hotel(int countOfFloors, int[] countOfSpaces) throws IllegalArgumentException {
        super(countOfFloors, countOfSpaces);
    }

    public Hotel(Floor[] floors) {
        super(floors);
    }


//========================================Getter============================================

    public int getStarsHotel() {
        int max = 0;
        for (int i = 0; i < this.getNumberOfFloors(); ++i) {
            Floor floor = this.getFloor(i);
            if (floor instanceof HotelFloor) {
                HotelFloor hFloor = (HotelFloor) floor;
                max = Math.max(max, hFloor.getStars());
            }
        }
        return max;
    }

    public Space getBestSpace() {
        Space ans = null;
        double max = 0;
        for (int i = 0; i < this.getNumberOfFloors(); ++i) {
            //ask about it
            Floor floor = this.getFloor(i);
            if (floor instanceof HotelFloor) {
                double coeff = 0.25 * ((HotelFloor) floor).getStars();
                for (Space space : floor) {
                    if (space instanceof Flat) {
                        double mayBeMax = space.getArea() * coeff;
                        if (mayBeMax > max) {
                            max = mayBeMax;
                            ans = space;
                        }
                    }
                }
            }
        }
        return ans;
    }

//========================================Object============================================

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(String.format("Hotel(%d, %d", this.getStarsHotel(), this.getNumberOfFloors()));
        for (int i = 0; i < this.getNumberOfFloors(); ++i) {
            str.append(" ,");
            Floor floor = this.getFloor(i);
            if (floor != null)
                str.append(floor.toString());
            else
                str.append("null");
        }
        return str.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Hotel))
            return false;
        Hotel hotel = (Hotel) obj;
        if (hotel.getStarsHotel() != this.getStarsHotel()
                || hotel.getNumberOfFloors() != this.getNumberOfFloors())
            return false;
        for (int i = 0; i < this.getNumberOfFloors(); ++i) {
            Floor thisFloor = this.getFloor(i);
            Floor otherFloor = hotel.getFloor(i);
            if (!thisFloor.equals(otherFloor))
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = this.getStarsHotel();
        long sN = 21;
        long acum = 1;
        for (int i = 0; i < this.getNumberOfFloors(); ++i, acum *= sN) {
            Floor floor = this.getFloor(i);
            if (floor != null)
                hash ^= acum * floor.hashCode();
        }
        return hash;
    }

}
