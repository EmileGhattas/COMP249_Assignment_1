//-----------------------------------------------------------------
// Assignment 1
// Question: Vehicle Fleet Management & Leasing System
// Written by: Emile Ghattas (id: 40282552) Zeidan Chabo (id:40281196)
//-----------------------------------------------------------------

package vehicle;

/**
 * Assignment 1 – ElectricTruck class.
 * Written by: [Your Name, Your Student ID]
 *
 * An ElectricTruck has a maximum autonomy range (in kilometers) in addition to Truck attributes.
 * Plate numbers start with "ET" and auto-increment starting from ET1001.
 */
public class ElectricTruck extends Truck {
    private int autonomyRange;
    private static int nextPlateNumber = 1001; // starting number for electric trucks

    // Default constructor
    public ElectricTruck() {
        super();
        this.autonomyRange = 0;
        this.plateNumber = "ET" + nextPlateNumber++;
    }

    // Parameterized constructor
    public ElectricTruck(String make, String model, int year, int maxCapacity, int autonomyRange) {
        super(make, model, year, maxCapacity);
        this.autonomyRange = autonomyRange;
        this.plateNumber = "ET" + nextPlateNumber++;
    }

    // Copy constructor
    public ElectricTruck(ElectricTruck other) {
        super(other);
        this.autonomyRange = other.autonomyRange;
        this.plateNumber = "ET" + nextPlateNumber++;
    }

    public int getAutonomyRange() {
        return autonomyRange;
    }

    public void setAutonomyRange(int autonomyRange) {
        this.autonomyRange = autonomyRange;
    }

    @Override
    public String toString() {
        return "ElectricTruck [plateNumber=" + plateNumber + ", make=" + make + ", model=" + model +
                ", year=" + year + ", maxCapacity=" + maxCapacity +
                ", autonomyRange=" + autonomyRange + ", leasedBy=" + (leasedBy == null ? "None" : leasedBy) + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof ElectricTruck)) return false;
        ElectricTruck other = (ElectricTruck) obj;
        return this.autonomyRange == other.autonomyRange;
    }
}