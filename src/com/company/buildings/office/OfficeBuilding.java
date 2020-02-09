package com.company.buildings.office;

import com.company.Interfaces.Building;
import com.company.Interfaces.Floor;
import com.company.Interfaces.Space;
import com.company.exceptions.FloorIndexOutOfBoundsException;
import com.company.exceptions.SpaceIndexOutOfBoundsException;

import java.io.Serializable;
import java.util.Iterator;


public class OfficeBuilding implements Building {
    private class NodeFloor implements Serializable {
        Floor value;
        NodeFloor nextFloor;
        NodeFloor prevFloor;

        //========================================Exceptions==============================================
        private void isNodeNull(NodeFloor nextNode) throws NullPointerException {
            if (nextNode == null) {
                throw new NullPointerException("NextNode is null");
            }
        }
        //========================================Constructors============================================

        public NodeFloor() {
            this.value = null;
            this.nextFloor = null;
            this.prevFloor = null;
        }

        public NodeFloor(NodeFloor prevFloor, NodeFloor nextNode) throws NullPointerException {
            isNodeNull(nextNode);
            isNodeNull(prevFloor);
            this.nextFloor = nextNode;
            this.prevFloor = prevFloor;
        }

        public NodeFloor(NodeFloor prevFloor, NodeFloor nextNode, Floor floor) throws NullPointerException {
            isNodeNull(nextNode);
            isNodeNull(prevFloor);
            this.nextFloor = nextNode;
            this.prevFloor = prevFloor;
            this.value = floor;
        }

        //========================================Getter============================================

        public NodeFloor next() {
            return this.nextFloor;
        }

        public NodeFloor prev() {
            return this.prevFloor;
        }

        public Floor getValue() {
            return this.value;
        }

        //========================================Setter============================================
        public NodeFloor setNext(NodeFloor nextNode) {
            NodeFloor temp = this.nextFloor;
            this.nextFloor = nextNode;
            return temp;
        }

        public NodeFloor setPrev(NodeFloor prevNode) {
            NodeFloor temp = this.prevFloor;
            this.prevFloor = prevNode;
            return temp;
        }

        public Floor setValue(Floor floor) {
            Floor temp = this.value;
            this.value = floor;
            return temp;
        }
    }
    //=====================================Private_Fields============================================

    private NodeFloor head;
    private int countOfFloor;

    //====================================Private_Methods============================================


    private NodeFloor getNode(int index) {
        if (head == null) {
            System.out.println("Head is null");
            return null;
        }
        NodeFloor cur;
        int i;
        if (index < this.countOfFloor - index) {
            for (cur = head, i = -1; i < countOfFloor && i < index; ++i, cur = cur.nextFloor) ;
        } else {
            for (cur = head, i = countOfFloor; i >= 0 && i > index; --i, cur = cur.prev()) ;
        }
        return cur;
    }

    private void addNode(int index, Floor floor) {
        try {
            NodeFloor prev = getNode(index - 1);
            NodeFloor afterCur = prev.next();
            NodeFloor cur = new NodeFloor(prev, afterCur, floor);
            prev.setNext(cur);
            afterCur.setPrev(cur);
            ++this.countOfFloor;
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        }
    }

    private Floor eraseNode(int index) {
        try {
            NodeFloor prevErasable = getNode(index - 1);
            NodeFloor erasable = prevErasable.next();
            NodeFloor afterErasable = erasable.next();

            Floor value = erasable.getValue();

            prevErasable.setNext(afterErasable);
            afterErasable.setPrev(prevErasable);
            --this.countOfFloor;
            return value;
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    private NodeFloor addAfterNode(NodeFloor afterThis, Floor floor) {
        NodeFloor next = afterThis.next();
        NodeFloor newNode = new NodeFloor(afterThis, next, floor);
        afterThis.setNext(newNode);
        next.setPrev(newNode);
        return newNode;
    }
//========================================Exceptions==============================================

    private void checkIndex(int index) throws FloorIndexOutOfBoundsException {
        if (index < 0 || index >= this.countOfFloor)
            throw new FloorIndexOutOfBoundsException(String.format("Index (%d) less than zero or (more of equal) than count Of Floor (%d)", index, this.countOfFloor));
    }
//========================================Constructors============================================

    {
        //Initialization
        this.countOfFloor = 0;
        this.head = new NodeFloor();
        this.head.setNext(this.head);
        this.head.setPrev(this.head);
    }

    public OfficeBuilding(int countOfFloor, int[] countsOfSpace) throws IllegalArgumentException {
        if (countOfFloor < 1)
            throw new IllegalArgumentException(String.format("Count of Space (%d) less than 1", countOfFloor));
        if (countsOfSpace.length == 0)
            throw new IllegalArgumentException("Array of Space have not elements");
        if (countOfFloor != countsOfSpace.length)
            throw new IllegalArgumentException(String.format("Count of floors(%d) and length of array countsOfSpace(%d) are different", countOfFloor, countsOfSpace.length));

        int i;
        NodeFloor cur;
        this.countOfFloor = countOfFloor;
        for (i = 0, cur = this.head; i < countOfFloor; ++i)
            cur = addAfterNode(cur, new OfficeFloor(countsOfSpace[i]));
    }

    public OfficeBuilding(Floor[] floors) throws IllegalArgumentException {
        if (floors.length == 0)
            throw new IllegalArgumentException("Array of Space have not elements");
        int i;
        NodeFloor cur;
        this.countOfFloor = floors.length;
        for (i = 0, cur = this.head; i < floors.length; ++i)
            cur = addAfterNode(cur, floors[i]);
    }
//========================================Getter============================================


    public int getNumberOfFloors() {
        return countOfFloor;
    }

    public long getNumberOfSpaces() {
        long ans = 0;
        for (NodeFloor cur = this.head.next(); cur != this.head; cur = cur.next()) {
            Floor temp = cur.getValue();
            if (temp != null)
                ans += temp.getCountOfSpace();
        }
        return ans;
    }

    public double getTotalAreaOfSpaces() {
        double ans = 0;
        for (NodeFloor cur = this.head.next(); cur != this.head; cur = cur.next()) {
            Floor temp = cur.getValue();
            if (temp != null)
                ans += temp.getTotalArea();
        }
        return ans;
    }

    public long getTotalNumberOfRooms() {
        long ans = 0;
        for (NodeFloor cur = this.head.next(); cur != this.head; cur = cur.next()) {
            Floor temp = cur.getValue();
            if (temp != null)
                ans += temp.getTotalCountOfRoom();
        }
        return ans;
    }

    public Floor[] getFloors() {
        Floor[] floors = new Floor[this.countOfFloor];
        int i;
        NodeFloor cur;
        for (i = 0, cur = this.head.next(); i < this.countOfFloor; ++i, cur = cur.next())
            floors[i] = cur.getValue();
        return floors;
    }

    public Floor getFloor(int index) throws FloorIndexOutOfBoundsException {
        this.checkIndex(index);
        return this.getNode(index).getValue();
    }

    public Space getSpace(int number) throws SpaceIndexOutOfBoundsException {
        if (number < 0 || number >= this.getNumberOfSpaces())
            throw new SpaceIndexOutOfBoundsException(String.format("Haven't Space with number %d", number));
        for (NodeFloor cur = this.head.next(); cur != this.head; cur = cur.next()) {
            Floor temp = cur.getValue();
            if (temp != null) {
                if (number - temp.getCountOfSpace() < 0) {
                    return temp.getSpace(number);
                }
                number -= temp.getCountOfSpace();
            }
        }
        return null;
    }

//========================================Setter============================================

    public Floor setFloor(int index, Floor floor) throws FloorIndexOutOfBoundsException {
        this.checkIndex(index);
        NodeFloor cur = this.getNode(index);
        return cur.setValue(floor);
    }

    public Space setSpaceInBuilding(int number, Space Space) throws SpaceIndexOutOfBoundsException {
        if (number < 0 || number >= this.getNumberOfSpaces())
            throw new SpaceIndexOutOfBoundsException(String.format("Haven't Space with number %d", number));
        for (NodeFloor cur = this.head.next(); cur != this.head; cur = cur.next()) {
            Floor temp = cur.getValue();
            if (temp != null) {
                if (number - temp.getCountOfSpace() < 0) {
                    return temp.setSpace(number, Space);
                }
                number -= temp.getCountOfSpace();
            }
        }
        return null;
    }

//========================================Methods============================================

    public boolean insertSpaceInBuilding(int number, Space Space) throws SpaceIndexOutOfBoundsException {
        if (number < 0 || number > this.getNumberOfSpaces())
            throw new SpaceIndexOutOfBoundsException(String.format("Haven't Space with number %d", number));
        for (NodeFloor cur = this.head.next(); cur != this.head; cur = cur.next()) {
            Floor temp = cur.getValue();
            if (temp != null) {
                if (number - temp.getCountOfSpace() < 0) {
                    return temp.insertSpace(number, Space);
                }
                number -= temp.getCountOfSpace();
            }
        }
        return false;
    }

    public Space eraseSpaceInBuilding(int number) throws SpaceIndexOutOfBoundsException {
        if (number < 0 || number >= this.getNumberOfSpaces())
            throw new SpaceIndexOutOfBoundsException(String.format("Haven't Space with number %d", number));
        for (NodeFloor cur = this.head.next(); cur != this.head; cur = cur.next()) {
            Floor temp = cur.getValue();
            if (temp != null) {
                if (number - temp.getCountOfSpace() < 0) {
                    return temp.eraseSpace(number);
                }
                number -= temp.getCountOfSpace();
            }
        }
        return null;
    }

    public Space getBestSpace() {
        double max = 0;
        Space ans = null;
        for (NodeFloor cur = this.head.next(); cur != this.head; cur = cur.next()) {
            Floor temp = cur.getValue();
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
        int n = (int) this.getNumberOfSpaces();
        Space[] sorted = new Space[n];
        for (int i = 0; i < n; ++i) {
            sorted[i] = this.getSpace(i);
            if (sorted[i] == null)
                continue;
            for (int j = i; j > 0; --j) {
                if (sorted[j - 1] == null || sorted[j - 1].getArea() < sorted[j].getArea()) {
                    Space temp = sorted[j - 1];
                    sorted[j - 1] = sorted[j];
                    sorted[j] = temp;
                    continue;
                }
                break;
            }
        }
        return sorted;
    }

//========================================Object============================================


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("OfficeBuilding(" + this.countOfFloor);
        for (NodeFloor node = this.head.next(); node != this.head; node = node.next()) {
            Floor temp = node.getValue();
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
        if (!(obj instanceof OfficeBuilding))
            return false;
        OfficeBuilding ob = (OfficeBuilding) obj;
        if (this.countOfFloor != ob.countOfFloor)
            return false;
        for (NodeFloor thisNode = this.head.nextFloor, otherNode = ob.head.nextFloor;
             thisNode != this.head;
             thisNode = thisNode.nextFloor, otherNode = otherNode.nextFloor) {
            Floor thisVal = thisNode.value;
            Floor otherVal = otherNode.value;
            if (thisVal == null && otherVal == null)
                continue;
            if (thisVal == null || otherVal == null)
                return false;
            if (!thisVal.equals(otherVal))
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = this.countOfFloor;
        long sN = 17;
        long acum = 1;
        for (NodeFloor node = this.head.nextFloor; node != this.head; node = node.nextFloor, acum *= sN) {
            Floor temp = node.value;
            if (temp != null)
                hash ^= (acum * temp.hashCode());
        }
        return hash;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Floor[] floors = this.getFloors();
        Floor[] cloneFloors = new Floor[floors.length];
        for (int i = 0; i < floors.length; ++i) {
            if (floors[i] != null)
                cloneFloors[i] = (Floor) floors[i].clone();
        }
        return new OfficeBuilding(cloneFloors);
    }

    //======================================== Iterator ============================================


    @Override
    public Iterator<Floor> iterator() {
        return new Iterator<Floor>() {
            private NodeFloor cur = OfficeBuilding.this.head;
            private NodeFloor head = OfficeBuilding.this.head;

            @Override
            public boolean hasNext() {
                return cur.next() != head;
            }

            @Override
            public Floor next() {
                return cur.nextFloor.value;
            }
        };
    }
}
