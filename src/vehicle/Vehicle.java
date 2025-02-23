//-----------------------------------------------------------------
// Assignment 1
// Question: Vehicle Fleet Management & Leasing System
// Written by: Emile Ghattas (id: 40282552) Zeidan Chabo (id:40281196)
//-----------------------------------------------------------------

package vehicle;

import client.Client;

/**
 * Assignment 1 – Vehicle class.
 * Written by: [Your Name, Your Student ID]
 *
 * Contains common attributes for all vehicles: plateNumber, make, model, year, and leasing info.
 */
public class Vehicle {
    protected String plateNumber;
    protected String make;
    protected String model;
    protected int year;
    protected Client leasedBy; // null if not leased

    // Default constructor
    public Vehicle() {
        this.make = "DefaultMake";
        this.model = "DefaultModel";
        this.year = 1900;
        this.leasedBy = null;
    }

    // Parameterized constructor (plateNumber is auto-assigned in subclasses)
    public Vehicle(String make, String model, int year) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.leasedBy = null;
    }

    // Copy constructor (new object is not leased and gets a new plate number in subclass)
    public Vehicle(Vehicle other) {
        this.make = other.make;
        this.model = other.model;
        this.year = other.year;
        this.leasedBy = null;
    }

    // Getters and Setters
    public String getPlateNumber() {
        return plateNumber;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Client getLeasedBy() {
        return leasedBy;
    }

    public boolean isLeased() {
        return leasedBy != null;
    }

    // Lease the vehicle to a client if not already leased.
    public void leaseTo(Client client) {
        if (!isLeased()) {
            leasedBy = client;
        }
    }

    // Return the vehicle.
    public void returnVehicle() {
        leasedBy = null;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [plateNumber=" + plateNumber +
                ", make=" + make + ", model=" + model + ", year=" + year +
                ", leasedBy=" + (leasedBy == null ? "None" : leasedBy.toString()) + "]";
    }

    // Compare vehicles based on make, model, and year (ignoring plateNumber and leasing info).
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        Vehicle other = (Vehicle) obj;
        return this.make.equals(other.make) && this.model.equals(other.model) && this.year == other.year;
    }
}