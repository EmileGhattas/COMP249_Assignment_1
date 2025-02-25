//-----------------------------------------------------------------
// Assignment 1
// Question: Vehicle Fleet Management & Leasing System
// Written by: Emile Ghattas (id: 40282552) Zeidan Chabo (id:40281196)
//-----------------------------------------------------------------

package vehicle;

public class DieselTruck extends Truck {
    private int fuelTankCapacity;
    private static int nextPlateNumber = 1001; // starting number

    // Default constructor
    public DieselTruck() {
        super();
        this.fuelTankCapacity = 0;
        this.plateNumber = "DT" + nextPlateNumber++;
    }

    // Parameterized constructor
    public DieselTruck(String make, String model, int year, int maxCapacity, int fuelTankCapacity) {
        super(make, model, year, maxCapacity);
        this.fuelTankCapacity = fuelTankCapacity;
        this.plateNumber = "DT" + nextPlateNumber++;
    }

    // Copy constructor
    public DieselTruck(DieselTruck other) {
        super(other);
        this.fuelTankCapacity = other.fuelTankCapacity;
        this.plateNumber = "DT" + nextPlateNumber++;
    }

    public int getFuelTankCapacity() {
        return fuelTankCapacity;
    }

    public void setFuelTankCapacity(int fuelTankCapacity) {
        this.fuelTankCapacity = fuelTankCapacity;
    }

    @Override
    public String toString() {
        return "DieselTruck [plateNumber=" + plateNumber + ", make=" + make + ", model=" + model +
                ", year=" + year + ", maxCapacity=" + maxCapacity +
                ", fuelTankCapacity=" + fuelTankCapacity + ", leasedBy=" + (leasedBy == null ? "None" : leasedBy) + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof DieselTruck)) return false;
        DieselTruck other = (DieselTruck) obj;
        return this.fuelTankCapacity == other.fuelTankCapacity;
    }
}