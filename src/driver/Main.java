package driver;

import client.Client;
import vehicle.*;
import java.util.Scanner;

/*
 * Assignment 1 - Object-Oriented Programming II - COMP 249
 * Written by: [Your Name]
 *
 * This driver program is designed for RoyalRentals employees to manage the vehicle fleet,
 * client records, leases, and returns. The program offers a menu-driven interface as well as a
 * predefined (hard-coded) testing scenario. It allows the user to:
 *   - Manage vehicles (add, delete, update, list by category)
 *   - Manage clients (add, edit, delete)
 *   - Perform leasing operations (lease, return, view leases)
 *   - Execute additional operations:
 *       • Find the diesel truck with the largest capacity (getLargestTruck)
 *       • Create a deep copy of an electric trucks array (copyVehicles)
 * The predefined scenario also creates sample vehicles and clients, displays their information,
 * tests the equals() method in various cases, creates arrays for each vehicle type, and calls
 * the additional operations.
 */

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    // Global arrays for the menu-driven interface (if used)
    private static Vehicle[] vehicles;
    private static Client[] clients;
    private static int vehicleCount = 0;
    private static int clientCount = 0;

    public static void main(String[] args) {
        displayWelcomeMessage();
        System.out.println("Choose interface mode:");
        System.out.println("1 - Menu-Driven Interface");
        System.out.println("2 - Predefined Scenario (Testing)");
        int mode = getValidIntInput();

        if (mode == 1) {
            initializeSystem();
            mainMenu();
            System.out.println("Program terminated. Goodbye!");
        } else if (mode == 2) {
            predefinedScenario();
            System.out.println("Predefined scenario completed. Goodbye!");
        } else {
            System.out.println("Invalid mode selected. Exiting program.");
        }
    }

    // Display a welcome message including your name.
    private static void displayWelcomeMessage() {
        System.out.println("Welcome to RoyalRentals Vehicle Management System! (Your Name)");
    }

    // Initialize the global arrays based on user input.
    private static void initializeSystem() {
        System.out.println("Enter the maximum number of vehicles:");
        int maxVehicles = getValidIntInput();
        vehicles = new Vehicle[maxVehicles];

        System.out.println("Enter the maximum number of clients:");
        int maxClients = getValidIntInput();
        clients = new Client[maxClients];
    }

    // Main menu for the menu-driven interface.
    private static void mainMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nMain Menu:");
            System.out.println("1 - Vehicle Management");
            System.out.println("2 - Client Management");
            System.out.println("3 - Leasing Operations");
            System.out.println("4 - Additional Operations");
            System.out.println("5 - Exit");
            int choice = getValidIntInput();

            switch (choice) {
                case 1 -> vehicleManagement();
                case 2 -> clientManagement();
                case 3 -> leasingOperations();
                case 4 -> additionalOperations();
                case 5 -> exit = true;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // ========================= Vehicle Management =========================

    private static void vehicleManagement() {
        System.out.println("\nVehicle Management:");
        System.out.println("1 - Add Vehicle");
        System.out.println("2 - Delete Vehicle");
        System.out.println("3 - Update Vehicle");
        System.out.println("4 - List Vehicles by Category");
        int choice = getValidIntInput();
        switch (choice) {
            case 1 -> addVehicle();
            case 2 -> deleteVehicle();
            case 3 -> updateVehicle();
            case 4 -> listVehiclesByCategory();
            default -> System.out.println("Invalid choice.");
        }
    }

    // Adds a vehicle based on user input.
    private static void addVehicle() {
        if (vehicleCount >= vehicles.length) {
            System.out.println("Vehicle storage is full.");
            return;
        }
        System.out.println("Enter vehicle type (DieselTruck, ElectricCar, GasolineCar, ElectricTruck):");
        String type = scanner.next();
        System.out.println("Enter make:");
        String make = scanner.next();
        System.out.println("Enter model:");
        String model = scanner.next();
        System.out.println("Enter year:");
        int year = getValidIntInput();

        Vehicle vehicle = null;
        // Note: Adjust constructor parameters as per your class implementations.
        switch (type.toLowerCase()) {
            case "dieseltruck" -> vehicle = new DieselTruck(make, model, year, 2000, 100);
            case "electriccar" -> vehicle = new ElectricCar(make, model, year, 5, 400);
            case "gasolinecar" -> vehicle = new GasolineCar(make, model, year, 5);
            case "electrictruck" -> vehicle = new ElectricTruck(make, model, year, 3000, 500);
            default -> {
                System.out.println("Invalid vehicle type.");
                return;
            }
        }
        vehicles[vehicleCount++] = vehicle;
        System.out.println("Vehicle added successfully: " + vehicle);
    }

    // Deletes a vehicle by its plate number.
    private static void deleteVehicle() {
        System.out.println("Enter the plate number of the vehicle to delete:");
        String plateNumber = scanner.next();
        int index = findVehicleIndexByPlate(plateNumber);
        if (index == -1) {
            System.out.println("Vehicle not found.");
            return;
        }
        for (int i = index; i < vehicleCount - 1; i++) {
            vehicles[i] = vehicles[i + 1];
        }
        vehicleCount--;
        System.out.println("Vehicle deleted successfully.");
    }

    // Updates vehicle information.
    private static void updateVehicle() {
        System.out.println("Enter the plate number of the vehicle to update:");
        String plateNumber = scanner.next();
        int index = findVehicleIndexByPlate(plateNumber);
        if (index == -1) {
            System.out.println("Vehicle not found.");
            return;
        }
        Vehicle vehicle = vehicles[index];
        System.out.println("Current vehicle information: " + vehicle);
        System.out.println("Enter new make:");
        String newMake = scanner.next();
        System.out.println("Enter new model:");
        String newModel = scanner.next();
        System.out.println("Enter new year:");
        int newYear = getValidIntInput();

        vehicle.setMake(newMake);
        vehicle.setModel(newModel);
        vehicle.setYear(newYear);
        System.out.println("Vehicle updated successfully: " + vehicle);
    }

    // Lists vehicles by category.
    private static void listVehiclesByCategory() {
        System.out.println("Enter vehicle category to list (DieselTruck, ElectricCar, GasolineCar, ElectricTruck):");
        String category = scanner.next();
        boolean found = false;
        for (int i = 0; i < vehicleCount; i++) {
            // Compare using the simple class name.
            if (vehicles[i].getClass().getSimpleName().equalsIgnoreCase(category)) {
                System.out.println(vehicles[i]);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No vehicles found in this category.");
        }
    }

    // Helper: Finds the index of a vehicle by its plate number.
    private static int findVehicleIndexByPlate(String plateNumber) {
        for (int i = 0; i < vehicleCount; i++) {
            if (vehicles[i].getPlateNumber().equalsIgnoreCase(plateNumber)) {
                return i;
            }
        }
        return -1;
    }

    // ========================= Client Management =========================

    private static void clientManagement() {
        System.out.println("\nClient Management:");
        System.out.println("1 - Add Client");
        System.out.println("2 - Edit Client");
        System.out.println("3 - Delete Client");
        int choice = getValidIntInput();
        switch (choice) {
            case 1 -> addClient();
            case 2 -> editClient();
            case 3 -> deleteClient();
            default -> System.out.println("Invalid choice.");
        }
    }

    // Adds a new client.
    private static void addClient() {
        if (clientCount >= clients.length) {
            System.out.println("Client storage is full.");
            return;
        }
        System.out.println("Enter client name:");
        String name = scanner.next();
        System.out.println("Enter client ID:");
        String id = scanner.next();
        Client client = new Client(name, id);
        clients[clientCount++] = client;
        System.out.println("Client added successfully: " + client);
    }

    // Edits an existing client.
    private static void editClient() {
        System.out.println("Enter the client ID to edit:");
        String id = scanner.next();
        int index = findClientIndexById(id);
        if (index == -1) {
            System.out.println("Client not found.");
            return;
        }
        Client client = clients[index];
        System.out.println("Current client information: " + client);
        System.out.println("Enter new name:");
        String newName = scanner.next();
        client.setName(newName);
        System.out.println("Client updated successfully: " + client);
    }

    // Deletes a client.
    private static void deleteClient() {
        System.out.println("Enter the client ID to delete:");
        String id = scanner.next();
        int index = findClientIndexById(id);
        if (index == -1) {
            System.out.println("Client not found.");
            return;
        }
        for (int i = index; i < clientCount - 1; i++) {
            clients[i] = clients[i + 1];
        }
        clientCount--;
        System.out.println("Client deleted successfully.");
    }

    // Helper: Finds the index of a client by its ID.
    private static int findClientIndexById(String id) {
        for (int i = 0; i < clientCount; i++) {
            if (clients[i].getId().equalsIgnoreCase(id)) {
                return i;
            }
        }
        return -1;
    }

    // ========================= Leasing Operations =========================

    private static void leasingOperations() {
        System.out.println("\nLeasing Operations:");
        System.out.println("1 - Lease Vehicle");
        System.out.println("2 - Return Vehicle");
        System.out.println("3 - Show All Vehicles Leased by a Client");
        System.out.println("4 - Show All Leased Vehicles");
        int choice = getValidIntInput();
        switch (choice) {
            case 1 -> leaseVehicle();
            case 2 -> returnVehicle();
            case 3 -> showVehiclesLeasedByClient();
            case 4 -> showAllLeasedVehicles();
            default -> System.out.println("Invalid choice.");
        }
    }

    // Lease a vehicle to a client.
    private static void leaseVehicle() {
        System.out.println("Enter client ID for leasing:");
        String clientId = scanner.next();
        int clientIndex = findClientIndexById(clientId);
        if (clientIndex == -1) {
            System.out.println("Client not found.");
            return;
        }
        System.out.println("Enter vehicle plate number to lease:");
        String plateNumber = scanner.next();
        int vehicleIndex = findVehicleIndexByPlate(plateNumber);
        if (vehicleIndex == -1) {
            System.out.println("Vehicle not found.");
            return;
        }
        Vehicle vehicle = vehicles[vehicleIndex];
        if (vehicle.isLeased()) {
            System.out.println("Vehicle is already leased.");
        } else {
            vehicle.leaseTo(clients[clientIndex]);
            System.out.println("Vehicle leased successfully to client: " + clients[clientIndex]);
        }
    }

    // Return a leased vehicle.
    private static void returnVehicle() {
        System.out.println("Enter vehicle plate number to return:");
        String plateNumber = scanner.next();
        int vehicleIndex = findVehicleIndexByPlate(plateNumber);
        if (vehicleIndex == -1) {
            System.out.println("Vehicle not found.");
            return;
        }
        Vehicle vehicle = vehicles[vehicleIndex];
        if (!vehicle.isLeased()) {
            System.out.println("Vehicle is not currently leased.");
        } else {
            vehicle.returnVehicle();
            System.out.println("Vehicle returned successfully.");
        }
    }

    // Display all vehicles leased by a specific client.
    private static void showVehiclesLeasedByClient() {
        System.out.println("Enter client ID:");
        String clientId = scanner.next();
        int clientIndex = findClientIndexById(clientId);
        if (clientIndex == -1) {
            System.out.println("Client not found.");
            return;
        }
        Client client = clients[clientIndex];
        System.out.println("Vehicles leased by " + client + ":");
        boolean found = false;
        for (int i = 0; i < vehicleCount; i++) {
            if (vehicles[i].isLeased() && vehicles[i].getLeasedBy().equals(client)) {
                System.out.println(vehicles[i]);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No vehicles leased by this client.");
        }
    }

    // Display all leased vehicles.
    private static void showAllLeasedVehicles() {
        System.out.println("All leased vehicles:");
        boolean found = false;
        for (int i = 0; i < vehicleCount; i++) {
            if (vehicles[i].isLeased()) {
                System.out.println(vehicles[i]);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No vehicles are currently leased.");
        }
    }

    // ========================= Additional Operations =========================

    // getLargestTruck: Returns the diesel truck with the largest capacity from an array.
    public static DieselTruck getLargestTruck(DieselTruck[] dieselTrucks) {
        DieselTruck largest = null;
        for (DieselTruck dt : dieselTrucks) {
            if (dt != null) {
                if (largest == null || dt.getMaxCapacity() > largest.getMaxCapacity()) {
                    largest = dt;
                }
            }
        }
        return largest;
    }

    // copyVehicles: Returns a deep copy of the given array of electric trucks.
    public static ElectricTruck[] copyVehicles(ElectricTruck[] electricTrucks) {
        if (electricTrucks == null) return null;
        ElectricTruck[] copy = new ElectricTruck[electricTrucks.length];
        for (int i = 0; i < electricTrucks.length; i++) {
            if (electricTrucks[i] != null) {
                copy[i] = new ElectricTruck(electricTrucks[i]); // using the copy constructor
            }
        }
        return copy;
    }

    // Additional operations menu (for menu-driven interface)
    private static void additionalOperations() {
        System.out.println("\nAdditional Operations:");
        System.out.println("1 - Get Diesel Truck with Largest Capacity");
        System.out.println("2 - Deep Copy Electric Trucks Array");
        int choice = getValidIntInput();
        switch (choice) {
            case 1 -> {
                // Extract diesel trucks from global vehicles array.
                int count = 0;
                for (int i = 0; i < vehicleCount; i++) {
                    if (vehicles[i] instanceof DieselTruck)
                        count++;
                }
                DieselTruck[] dieselTrucks = new DieselTruck[count];
                int idx = 0;
                for (int i = 0; i < vehicleCount; i++) {
                    if (vehicles[i] instanceof DieselTruck)
                        dieselTrucks[idx++] = (DieselTruck) vehicles[i];
                }
                DieselTruck largest = getLargestTruck(dieselTrucks);
                if (largest != null)
                    System.out.println("Diesel Truck with largest capacity: " + largest);
                else
                    System.out.println("No diesel trucks available.");
            }
            case 2 -> {
                // Extract electric trucks from global vehicles array.
                int count = 0;
                for (int i = 0; i < vehicleCount; i++) {
                    if (vehicles[i] instanceof ElectricTruck)
                        count++;
                }
                ElectricTruck[] electricTrucks = new ElectricTruck[count];
                int idx = 0;
                for (int i = 0; i < vehicleCount; i++) {
                    if (vehicles[i] instanceof ElectricTruck)
                        electricTrucks[idx++] = (ElectricTruck) vehicles[i];
                }
                ElectricTruck[] copied = copyVehicles(electricTrucks);
                System.out.println("Electric trucks have been deep-copied:");
                for (ElectricTruck et : copied) {
                    System.out.println(et);
                }
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    // ========================= Predefined Scenario (Testing) =========================

    // This method creates sample vehicles and clients, displays them, tests equals(),
    // creates arrays for each vehicle type, and calls getLargestTruck() and copyVehicles().
    private static void predefinedScenario() {
        System.out.println("\n--- Predefined Scenario ---");

        // Create sample vehicles (at least 3 of each type)
        DieselTruck dt1 = new DieselTruck("Ford", "F-150", 2020, 2500, 150);
        DieselTruck dt2 = new DieselTruck("Chevy", "Silverado", 2019, 2600, 160);
        DieselTruck dt3 = new DieselTruck("RAM", "1500", 2021, 2400, 155);

        ElectricTruck et1 = new ElectricTruck("Tesla", "Cybertruck", 2022, 3500, 500);
        ElectricTruck et2 = new ElectricTruck("Rivian", "R1T", 2022, 3400, 480);
        ElectricTruck et3 = new ElectricTruck("Bollinger", "B2", 2023, 3300, 470);

        ElectricCar ec1 = new ElectricCar("Tesla", "Model 3", 2021, 5, 350);
        ElectricCar ec2 = new ElectricCar("Nissan", "Leaf", 2020, 5, 300);
        ElectricCar ec3 = new ElectricCar("Chevy", "Bolt", 2021, 5, 320);

        GasolineCar gc1 = new GasolineCar("Honda", "Civic", 2018, 5);
        GasolineCar gc2 = new GasolineCar("Toyota", "Corolla", 2019, 5);
        GasolineCar gc3 = new GasolineCar("Ford", "Focus", 2020, 5);

        // Create sample clients (at least 3)
        Client client1 = new Client("Alice", "C1001");
        Client client2 = new Client("Bob", "C1002");
        Client client3 = new Client("Charlie", "C1003");

        // Display vehicles using toString()
        System.out.println("\n--- Displaying Vehicles ---");
        System.out.println(dt1);
        System.out.println(dt2);
        System.out.println(dt3);
        System.out.println(et1);
        System.out.println(et2);
        System.out.println(et3);
        System.out.println(ec1);
        System.out.println(ec2);
        System.out.println(ec3);
        System.out.println(gc1);
        System.out.println(gc2);
        System.out.println(gc3);

        // Display clients
        System.out.println("\n--- Displaying Clients ---");
        System.out.println(client1);
        System.out.println(client2);
        System.out.println(client3);

        // Test equals() method:
        System.out.println("\n--- Testing equals() Method ---");
        // Case 1: Different classes
        System.out.println("Comparing DieselTruck dt1 and ElectricCar ec1 (should be false): " + dt1.equals(ec1));
        // Case 2: Same class but different attribute values
        DieselTruck dtDifferent = new DieselTruck("Ford", "F-150", 2021, 2500, 150);
        System.out.println("Comparing DieselTruck dt1 and dtDifferent (different year, should be false): " + dt1.equals(dtDifferent));
        // Case 3: Same class with identical attribute values (except plate number should be auto-assigned so equals ignores it)
        DieselTruck dtIdentical = new DieselTruck(dt1);
        System.out.println("Comparing DieselTruck dt1 and its copy (should be true): " + dt1.equals(dtIdentical));

        // Create arrays for each type of vehicle and one for all vehicles
        DieselTruck[] dieselTrucks = { dt1, dt2, dt3 };
        ElectricTruck[] electricTrucks = { et1, et2, et3 };
        ElectricCar[] electricCars = { ec1, ec2, ec3 };
        GasolineCar[] gasolineCars = { gc1, gc2, gc3 };
        Vehicle[] allVehicles = { dt1, dt2, dt3, et1, et2, et3, ec1, ec2, ec3, gc1, gc2, gc3 };

        // Call getLargestTruck() on diesel trucks array.
        DieselTruck largestDiesel = getLargestTruck(dieselTrucks);
        System.out.println("\nDiesel Truck with the largest capacity: " + (largestDiesel != null ? largestDiesel : "None found"));

        // Call copyVehicles() on electric trucks array.
        ElectricTruck[] copiedElectricTrucks = copyVehicles(electricTrucks);
        System.out.println("\nDeep copied Electric Trucks:");
        for (ElectricTruck et : copiedElectricTrucks) {
            System.out.println(et);
        }
    }

    // ========================= Utility Method =========================

    // Ensures that only a valid integer is read.
    private static int getValidIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input! Please enter a number.");
            scanner.next();
        }
        return scanner.nextInt();
    }
}