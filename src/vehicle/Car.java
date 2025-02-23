//-----------------------------------------------------------------
// Assignment 1
// Question: Vehicle Fleet Management & Leasing System
// Written by: Emile Ghattas (id: 40282552) Zeidan Chabo (id:40281196)
//-----------------------------------------------------------------

package vehicle;

/**
 * Assignment 1 – Car class.
 * Written by: [Your Name, Your Student ID]
 *
 * A Car has a maximum number of passengers. This class serves as the base for ElectricCar and GasolineCar.
 */
public class Car extends Vehicle {
    protected int maxPassengers;

    // Default constructor
    public Car() {
        super();
        this.maxPassengers = 0;
    }

    // Parameterized constructor
    public Car(String make, String model, int year, int maxPassengers) {
        super(make, model, year);
        this.maxPassengers = maxPassengers;
    }

    // Copy constructor
    public Car(Car other) {
        super(other);
        this.maxPassengers = other.maxPassengers;
    }

    public int getMaxPassengers() {
        return maxPassengers;
    }

    public void setMaxPassengers(int maxPassengers) {
        this.maxPassengers = maxPassengers;
    }

    @Override
    public String toString() {
        return super.toString() + ", maxPassengers=" + maxPassengers;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        Car other = (Car) obj;
        return this.maxPassengers == other.maxPassengers;
    }
}