//-----------------------------------------------------------------
// Assignment 1
// Question: Vehicle Fleet Management & Leasing System
// Written by: Emile Ghattas (id: 40282552) Zeidan Chabo (id:40281196)
//-----------------------------------------------------------------

package client;

/**
 * Assignment 1 – Client class.
 * Written by: [Your Name, Your Student ID]
 *
 * Represents a client in the RoyalRentals system.
 */
public class Client {
    private String name;
    private String id;

    // Default constructor
    public Client() {
        this.name = "DefaultName";
        this.id = "DefaultID";
    }

    // Parameterized constructor
    public Client(String name, String id) {
        this.name = name;
        this.id = id;
    }

    // Copy constructor
    public Client(Client other) {
        this.name = other.name;
        this.id = other.id;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Client [name=" + name + ", id=" + id + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Client)) return false;
        Client other = (Client) obj;
        return this.name.equals(other.name) && this.id.equals(other.id);
    }
}