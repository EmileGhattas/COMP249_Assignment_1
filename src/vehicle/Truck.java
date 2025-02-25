//-----------------------------------------------------------------
// Assignment 1
// Question: Vehicle Fleet Management & Leasing System
// Written by: Emile Ghattas (id: 40282552) Zeidan Chabo (id:40281196)
//-----------------------------------------------------------------

package vehicle;

public class Truck extends Vehicle {
    protected int maxCapacity;

    // Default constructor
    public Truck() {
        super();
        this.maxCapacity = 0;
    }

    // Parameterized constructor
    public Truck(String make, String model, int year, int maxCapacity) {
        super(make, model, year);
        this.maxCapacity = maxCapacity;
    }

    // Copy constructor
    public Truck(Truck other) {
        super(other);
        this.maxCapacity = other.maxCapacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    @Override
    public String toString() {
        return super.toString() + ", maxCapacity=" + maxCapacity;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        Truck other = (Truck) obj;
        return this.maxCapacity == other.maxCapacity;
    }
}