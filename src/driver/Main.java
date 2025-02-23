//-----------------------------------------------------------------
// Assignment 1
// Question: Vehicle Fleet Management & Leasing System
// Written by: Emile Ghattas (id: 40282552) Zeidan Chabo (id:40281196)
//-----------------------------------------------------------------

package driver;

import client.Client;
import vehicle.*;
import java.util.Scanner;

/*
 * This driver program is designed for RoyalRentals employees to manage the vehicle fleet,
 * client records, leases, and returns. The program offers both a menu-driven interface
 * and a predefined testing scenario.
 *
 * Main functionalities include:
 *   - Vehicle Management (add, delete, update, list by category)
 *   - Client Management (add, edit, delete)
 *   - Leasing Operations (lease, return, view leased vehicles)
 *   - Additional Operations (find largest diesel truck, deep-copy electric trucks array)
 */

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    // Global arrays for storing vehicles and clients during the menu-driven session
    private static Vehicle[] vehicles;
    private static Client[] clients;
    private static int vehicleCount = 0;
    private static int clientCount = 0;

    public static void main(String[] args) {
        // Display a welcome message that includes your name
        displayWelcomeMessage();

        // Prompt the user to choose the interface mode
        System.out.println("Choose interface mode:");
        System.out.println("1 - Menu-Driven Interface");
        System.out.println("2 - Predefined Scenario (Testing)");
        int mode = getValidInt("Enter mode (1 or 2): ");

        if (mode == 1) {
            // Initialize the system arrays and run the menu interface
            initializeSystem();
            mainMenu();
            System.out.println("Program terminated. Goodbye!");
        } else if (mode == 2) {
            // Run the predefined scenario for testing
            predefinedScenario();
            System.out.println("Predefined scenario completed. Goodbye!");
        } else {
            System.out.println("Invalid mode selected. Exiting program.");
        }
    }

    // Displays a welcome message that includes your name.
    private static void displayWelcomeMessage() {
        System.out.println("Welcome to Emile and Zeidan's RoyalRentals Vehicle Management System!");
    }

    // Initializes the arrays for vehicles and clients based on user input.
    private static void initializeSystem() {
        int maxVehicles = getPositiveInt("Enter the maximum number of vehicles: ");
        vehicles = new Vehicle[maxVehicles];

        int maxClients = getPositiveInt("Enter the maximum number of clients: ");
        clients = new Client[maxClients];
    }

    // Displays the main menu and processes user choices.
    private static void mainMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nMain Menu:");
            System.out.println("1 - Vehicle Management");
            System.out.println("2 - Client Management");
            System.out.println("3 - Leasing Operations");
            System.out.println("4 - Additional Operations");
            System.out.println("5 - Exit");
            int choice = getValidInt("Enter your choice: ");

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

    // Provides the vehicle management submenu.
    private static void vehicleManagement() {
        System.out.println("\nVehicle Management:");
        System.out.println("1 - Add Vehicle");
        System.out.println("2 - Delete Vehicle");
        System.out.println("3 - Update Vehicle");
        System.out.println("4 - List Vehicles by Category");
        int choice = getValidInt("Enter your choice: ");
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
        String type = getNonEmptyString("Enter vehicle type (DieselTruck, ElectricCar, GasolineCar, ElectricTruck): ");
        String make = getNonEmptyString("Enter make: ");
        String model = getNonEmptyString("Enter model: ");
        int year = getValidInt("Enter year: ");

        Vehicle vehicle = null;
        // Create the vehicle based on the type provided
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

    // Deletes a vehicle identified by its plate number.
    private static void deleteVehicle() {
        String plateNumber = getNonEmptyString("Enter the plate number of the vehicle to delete: ");
        int index = findVehicleIndexByPlate(plateNumber);
        if (index == -1) {
            System.out.println("Vehicle not found.");
            return;
        }
        // Shift elements to remove the deleted vehicle
        for (int i = index; i < vehicleCount - 1; i++) {
            vehicles[i] = vehicles[i + 1];
        }
        vehicleCount--;
        System.out.println("Vehicle deleted successfully.");
    }

    // Updates the attributes of an existing vehicle.
    private static void updateVehicle() {
        String plateNumber = getNonEmptyString("Enter the plate number of the vehicle to update: ");
        int index = findVehicleIndexByPlate(plateNumber);
        if (index == -1) {
            System.out.println("Vehicle not found.");
            return;
        }
        Vehicle vehicle = vehicles[index];
        System.out.println("Current vehicle information: " + vehicle);
        String newMake = getNonEmptyString("Enter new make: ");
        String newModel = getNonEmptyString("Enter new model: ");
        int newYear = getValidInt("Enter new year: ");

        vehicle.setMake(newMake);
        vehicle.setModel(newModel);
        vehicle.setYear(newYear);
        System.out.println("Vehicle updated successfully: " + vehicle);
    }

    // Lists vehicles filtered by the provided category.
    private static void listVehiclesByCategory() {
        String category = getNonEmptyString("Enter vehicle category to list (DieselTruck, ElectricCar, GasolineCar, ElectricTruck): ");
        boolean found = false;
        for (int i = 0; i < vehicleCount; i++) {
            if (vehicles[i].getClass().getSimpleName().equalsIgnoreCase(category)) {
                System.out.println(vehicles[i]);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No vehicles found in this category.");
        }
    }

    // Helper: Finds and returns the index of a vehicle by its plate number.
    private static int findVehicleIndexByPlate(String plateNumber) {
        for (int i = 0; i < vehicleCount; i++) {
            if (vehicles[i].getPlateNumber().equalsIgnoreCase(plateNumber)) {
                return i;
            }
        }
        return -1;
    }

    // ========================= Client Management =========================

    // Provides the client management submenu.
    private static void clientManagement() {
        System.out.println("\nClient Management:");
        System.out.println("1 - Add Client");
        System.out.println("2 - Edit Client");
        System.out.println("3 - Delete Client");
        int choice = getValidInt("Enter your choice: ");
        switch (choice) {
            case 1 -> addClient();
            case 2 -> editClient();
            case 3 -> deleteClient();
            default -> System.out.println("Invalid choice.");
        }
    }

    // Adds a new client based on user input.
    private static void addClient() {
        if (clientCount >= clients.length) {
            System.out.println("Client storage is full.");
            return;
        }
        String name = getNonEmptyString("Enter client name: ");
        String id = getNonEmptyString("Enter client ID: ");
        Client client = new Client(name, id);
        clients[clientCount++] = client;
        System.out.println("Client added successfully: " + client);
    }

    // Edits an existing client's details.
    private static void editClient() {
        String id = getNonEmptyString("Enter the client ID to edit: ");
        int index = findClientIndexById(id);
        if (index == -1) {
            System.out.println("Client not found.");
            return;
        }
        Client client = clients[index];
        System.out.println("Current client information: " + client);
        String newName = getNonEmptyString("Enter new name: ");
        client.setName(newName);
        System.out.println("Client updated successfully: " + client);
    }

    // Deletes a client by its ID.
    private static void deleteClient() {
        String id = getNonEmptyString("Enter the client ID to delete: ");
        int index = findClientIndexById(id);
        if (index == -1) {
            System.out.println("Client not found.");
            return;
        }
        // Shift elements to remove the deleted client
        for (int i = index; i < clientCount - 1; i++) {
            clients[i] = clients[i + 1];
        }
        clientCount--;
        System.out.println("Client deleted successfully.");
    }

    // Helper: Finds and returns the index of a client by its ID.
    private static int findClientIndexById(String id) {
        for (int i = 0; i < clientCount; i++) {
            if (clients[i].getId().equalsIgnoreCase(id)) {
                return i;
            }
        }
        return -1;
    }

    // ========================= Leasing Operations =========================

    // Provides the leasing operations submenu.
    private static void leasingOperations() {
        System.out.println("\nLeasing Operations:");
        System.out.println("1 - Lease Vehicle");
        System.out.println("2 - Return Vehicle");
        System.out.println("3 - Show All Vehicles Leased by a Client");
        System.out.println("4 - Show All Leased Vehicles");
        int choice = getValidInt("Enter your choice: ");
        switch (choice) {
            case 1 -> leaseVehicle();
            case 2 -> returnVehicle();
            case 3 -> showVehiclesLeasedByClient();
            case 4 -> showAllLeasedVehicles();
            default -> System.out.println("Invalid choice.");
        }
    }

    // Leases a vehicle to a client.
    private static void leaseVehicle() {
        String clientId = getNonEmptyString("Enter client ID for leasing: ");
        int clientIndex = findClientIndexById(clientId);
        if (clientIndex == -1) {
            System.out.println("Client not found.");
            return;
        }
        String plateNumber = getNonEmptyString("Enter vehicle plate number to lease: ");
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

    // Returns a leased vehicle.
    private static void returnVehicle() {
        String plateNumber = getNonEmptyString("Enter vehicle plate number to return: ");
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

    // Displays all vehicles leased by a specific client.
    private static void showVehiclesLeasedByClient() {
        String clientId = getNonEmptyString("Enter client ID: ");
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

    // Displays all leased vehicles.
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

    // Returns the DieselTruck with the largest capacity from an array.
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

    // Returns a deep copy of an array of ElectricTrucks using the copy constructor.
    public static ElectricTruck[] copyVehicles(ElectricTruck[] electricTrucks) {
        if (electricTrucks == null) return null;
        ElectricTruck[] copy = new ElectricTruck[electricTrucks.length];
        for (int i = 0; i < electricTrucks.length; i++) {
            if (electricTrucks[i] != null) {
                copy[i] = new ElectricTruck(electricTrucks[i]); // using copy constructor
            }
        }
        return copy;
    }

    // Provides the additional operations submenu.
    private static void additionalOperations() {
        System.out.println("\nAdditional Operations:");
        System.out.println("1 - Get Diesel Truck with Largest Capacity");
        System.out.println("2 - Deep Copy Electric Trucks Array");
        int choice = getValidInt("Enter your choice: ");
        switch (choice) {
            case 1 -> {
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

    // Executes a predefined scenario that creates sample vehicles and clients, tests methods,
    // and demonstrates additional operations.
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
        // Case 3: Same class with identical attribute values (except plate number)
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

    // ========================= Input Checking Methods =========================

    // Reusable method to get a valid integer from the user.
    private static int getValidInt(String prompt) {
        int num;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                num = scanner.nextInt();
                scanner.nextLine(); // consume newline
                return num;
            } else {
                System.out.println("Invalid input! Please enter a valid integer.");
                scanner.nextLine(); // discard invalid input
            }
        }
    }

    // Reusable method to get a positive integer.
    private static int getPositiveInt(String prompt) {
        int num;
        while (true) {
            num = getValidInt(prompt);
            if (num > 0) {
                return num;
            } else {
                System.out.println("Input must be positive. Please try again.");
            }
        }
    }

    // Reusable method to get a non-empty string.
    private static String getNonEmptyString(String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            } else {
                System.out.println("Input cannot be empty. Please try again.");
            }
        }
    }
}