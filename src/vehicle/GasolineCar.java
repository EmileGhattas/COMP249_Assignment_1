//-----------------------------------------------------------------
// Assignment 1
// Question: Vehicle Fleet Management & Leasing System
// Written by: Emile Ghattas (id: 40282552) Zeidan Chabo (id:40281196)
//-----------------------------------------------------------------

package vehicle;

/**
 * Assignment 1 – GasolineCar class.
 * Written by: [Your Name, Your Student ID]
 *
 * A GasolineCar only has the attributes inherited from Car (including maximum number of passengers).
 * Plate numbers start with "GC" and auto-increment starting from GC1001.
 */
public class GasolineCar extends Car {
    private static int nextPlateNumber = 1001;

    // Default constructor
    public GasolineCar() {
        super();
        this.plateNumber = "GC" + nextPlateNumber++;
    }

    // Parameterized constructor
    public GasolineCar(String make, String model, int year, int maxPassengers) {
        super(make, model, year, maxPassengers);
        this.plateNumber = "GC" + nextPlateNumber++;
    }

    // Copy constructor
    public GasolineCar(GasolineCar other) {
        super(other);
        this.plateNumber = "GC" + nextPlateNumber++;
    }

    @Override
    public String toString() {
        return "GasolineCar [plateNumber=" + plateNumber + ", make=" + make + ", model=" + model +
                ", year=" + year + ", maxPassengers=" + maxPassengers +
                ", leasedBy=" + (leasedBy == null ? "None" : leasedBy) + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof GasolineCar)) return false;
        return true;
    }
}