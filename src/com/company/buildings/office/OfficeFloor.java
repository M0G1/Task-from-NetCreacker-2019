package com.company.buildings.office;

import com.company.Interfaces.Floor;
import com.company.Interfaces.Space;
import com.company.exceptions.SpaceIndexOutOfBoundsException;

import java.io.Serializable;
import java.util.Iterator;


public class OfficeFloor implements Floor {
    private class NodeOffice implements Serializable {
        Space value;
        NodeOffice nextOffice;

        //========================================Exceptions==============================================
        private void isNodeNull(NodeOffice nextNode) throws NullPointerException {
            if (nextNode == null) {
                System.err.println("NextNode is null");
                throw new NullPointerException("NextNode is null");
            }
        }
        //========================================Constructors============================================

        public NodeOffice() {
            this.value = null;
            this.nextOffice = null;
        }

        public NodeOffice(NodeOffice nextNode) throws NullPointerException {
            isNodeNull(nextNode);
            this.nextOffice = nextNode;
        }

        public NodeOffice(NodeOffice nextNode, Space space) throws NullPointerException {
            isNodeNull(nextNode);
            this.nextOffice = nextNode;
            this.value = space;
        }

        //========================================Getter============================================

        public NodeOffice next() {
            return this.nextOffice;
        }

        public Space getValue() {
            return this.value;
        }

        //========================================Setter============================================
        public NodeOffice setNext(NodeOffice nextNode) {
            NodeOffice temp = this.nextOffice;
            this.nextOffice = nextNode;
            return temp;
        }

        public Space setValue(Space space) {
            Space temp = this.value;
            this.value = space;
            return temp;
        }
    }

//=====================================Private_Fields============================================

    private NodeOffice head;
    private int countOfOffice;
//====================================Private_Methods============================================

    private NodeOffice getNode(int index) throws NullPointerException {
        if (head == null) {
            throw new NullPointerException("Empty floor");
        }
        NodeOffice cur;
        int i;
        for (cur = head, i = -1; i < countOfOffice && i < index; ++i, cur = cur.nextOffice) ;
        return cur;
    }

    private void addNode(int index, Space space) {
        try {
            NodeOffice prev = getNode(index - 1);
            NodeOffice afterCur = prev.next();
            NodeOffice cur = new NodeOffice(afterCur, space);
            prev.setNext(cur);
            ++this.countOfOffice;
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        }
    }

    private Space eraseNode(int index) {
        try {
            NodeOffice prevErasable = getNode(index - 1);
            NodeOffice erasable = prevErasable.next();
            Space value = erasable.getValue();
            prevErasable.setNext(erasable.next());
            --this.countOfOffice;
            return value;
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    private NodeOffice addAfterNode(NodeOffice afterThis, Space space) {
        NodeOffice next = afterThis.next();
        NodeOffice newNode = new NodeOffice(next, space);
        afterThis.setNext(newNode);
        return newNode;
    }
//========================================Exceptions==============================================

    private void checkIndex(int index) throws SpaceIndexOutOfBoundsException {
        if (index < 0 || index >= this.countOfOffice)
            throw new SpaceIndexOutOfBoundsException(String.format("Index (%d) less than zero or (more of equal) than count Of Space (%d)", index, this.countOfOffice));
    }
//========================================Constructors============================================

    {
        //Initialization
        this.countOfOffice = 0;
        this.head = new NodeOffice();
        this.head.setNext(this.head);
    }

    public OfficeFloor(int countOfOffice) throws IllegalArgumentException {
        if (countOfOffice < 1)
            throw new IllegalArgumentException(String.format("Count of Space (%d) less than 1", countOfOffice));
        int i;
        NodeOffice cur;
        this.countOfOffice = countOfOffice;
        for (i = 0, cur = this.head; i < countOfOffice; ++i)
            cur = addAfterNode(cur, null);
    }

    public OfficeFloor(Space[] offices) throws IllegalArgumentException {
        if (offices.length == 0 || offices == null)
            throw new IllegalArgumentException("Array of space have not elements");
        int i;
        NodeOffice cur;
        this.countOfOffice = offices.length;
        for (i = 0, cur = this.head; i < offices.length; ++i)
            cur = addAfterNode(cur, offices[i]);
    }
//========================================Getter============================================

    public int getCountOfSpace() {
        return countOfOffice;
    }

    public double getTotalArea() {
        double ans = 0;
        for (NodeOffice cur = this.head.next(); cur != this.head; cur = cur.next()) {
            Space temp = cur.getValue();
            if (temp != null)
                ans += temp.getArea();
        }
        return ans;
    }

    public long getTotalCountOfRoom() {
        long ans = 0;
        for (NodeOffice cur = this.head.next(); cur != this.head; cur = cur.next()) {
            Space temp = cur.getValue();
            if (temp != null)
                ans += temp.getCountOfRooms();
        }
        return ans;
    }

    public Space[] getSpaces() {
        Space[] offices = new Space[this.countOfOffice];
        int i;
        NodeOffice cur;
        for (i = 0, cur = this.head.next(); i < this.countOfOffice; ++i, cur = cur.next())
            offices[i] = cur.getValue();
        return offices;
    }

    public Space getSpace(int index) throws SpaceIndexOutOfBoundsException {
        this.checkIndex(index);
        return this.getNode(index).getValue();
    }


//========================================Setter============================================

    public Space setSpace(int index, Space space) throws SpaceIndexOutOfBoundsException {
        this.checkIndex(index);
        NodeOffice cur = this.getNode(index);
        return cur.setValue(space);
    }

//========================================Methods============================================

    public boolean insertSpace(int index, Space space) throws SpaceIndexOutOfBoundsException {
        if (index < 0 || index > this.countOfOffice)
            throw new SpaceIndexOutOfBoundsException(String.format("Index (%d) less than zero or more  than count Of Space (%d)", index, this.countOfOffice));
        this.addNode(index, space);
        return true;
    }

    public Space eraseSpace(int index) throws SpaceIndexOutOfBoundsException {
        this.checkIndex(index);
        return this.eraseNode(index);
    }

    public Space getBestSpace() {
        double max = 0;
        Space ans = null;
        for (NodeOffice cur = this.head.next(); cur != this.head; cur = cur.next()) {
            Space temp = cur.getValue();
            if (temp != null && temp.getArea() > max) {
                ans = temp;
                max = temp.getArea();
            }
        }
        return ans;
    }


    //========================================Object============================================

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("OfficeFloor(" + this.countOfOffice);
        for (NodeOffice node = this.head.nextOffice; node != this.head; node = node.nextOffice) {
            Space temp = node.getValue();
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
        if (!(obj instanceof OfficeFloor))
            return false;
        OfficeFloor of = (OfficeFloor) obj;
        if (this.countOfOffice != of.countOfOffice)
            return false;
        for (NodeOffice thisNode = this.head.nextOffice, otherNode = of.head.nextOffice;
             thisNode != this.head;
             thisNode = thisNode.nextOffice, otherNode = otherNode.nextOffice) {
            Space thisVal = thisNode.value;
            Space otherVal = otherNode.value;
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
        int hash = this.countOfOffice;
        long sN = 11;
        long acum = 1;
        for (NodeOffice node = this.head.nextOffice; node != this.head; node = node.nextOffice, acum *= sN) {
            Space temp = node.getValue();
            if (temp != null)
                hash ^= (sN * temp.hashCode());
        }
        return hash;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Space[] spaces = this.getSpaces();
        Space[] cloneSpaces = new Space[spaces.length];
        for (int i = 0; i < spaces.length; ++i) {
            if (spaces[i] != null)
                cloneSpaces[i] = (Space) spaces[i].clone();
        }
        return new OfficeFloor(cloneSpaces);
    }

//======================================== Iterator ============================================

    @Override
    public Iterator<Space> iterator() {
        return new Iterator<Space>() {
            private NodeOffice cur = OfficeFloor.this.head;
            private NodeOffice head = OfficeFloor.this.head;

            @Override
            public boolean hasNext() {
                return cur.next() != head;
            }

            @Override
            public Space next() {
                return cur.next().value;
            }
        };
    }


    //========================================Compare===========================================

    @Override
    public int compareTo(Floor o) {
        return this.getCountOfSpace() - o.getCountOfSpace();
    }
}
