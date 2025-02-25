//-----------------------------------------------------------------
// Assignment 1
// Question: Vehicle Fleet Management & Leasing System
// Written by: Emile Ghattas (id: 40282552) Zeidan Chabo (id:40281196)
//-----------------------------------------------------------------

package vehicle;

public class ElectricCar extends Car {
    private int autonomyRange;
    private static int nextPlateNumber = 1001;

    // Default constructor
    public ElectricCar() {
        super();
        this.autonomyRange = 0;
        this.plateNumber = "EC" + nextPlateNumber++;
    }

    // Parameterized constructor
    public ElectricCar(String make, String model, int year, int maxPassengers, int autonomyRange) {
        super(make, model, year, maxPassengers);
        this.autonomyRange = autonomyRange;
        this.plateNumber = "EC" + nextPlateNumber++;
    }

    // Copy constructor
    public ElectricCar(ElectricCar other) {
        super(other);
        this.autonomyRange = other.autonomyRange;
        this.plateNumber = "EC" + nextPlateNumber++;
    }

    public int getAutonomyRange() {
        return autonomyRange;
    }

    public void setAutonomyRange(int autonomyRange) {
        this.autonomyRange = autonomyRange;
    }

    @Override
    public String toString() {
        return "ElectricCar [plateNumber=" + plateNumber + ", make=" + make + ", model=" + model +
                ", year=" + year + ", maxPassengers=" + maxPassengers +
                ", autonomyRange=" + autonomyRange + ", leasedBy=" + (leasedBy == null ? "None" : leasedBy) + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof ElectricCar)) return false;
        ElectricCar other = (ElectricCar) obj;
        return this.autonomyRange == other.autonomyRange;
    }
}