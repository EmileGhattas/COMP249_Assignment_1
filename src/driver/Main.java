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
 *
 * The program ensures that all user inputs are validated. For example, when adding a vehicle,
 * only the following types are accepted (case-insensitive): "Diesel Truck", "Electric Car",
 * "Gasoline Car", and "Electric Truck". If an invalid type is entered, the user is prompted again.
 *
 * For each vehicle, type-specific attributes are prompted:
 *   - Truck: maximum capacity (kg)
 *   - Electric Car & Electric Truck: maximum autonomy range (km)
 *   - Diesel Truck: fuel tank capacity (liters)
 *   - Gasoline Car: maximum number of passengers
 */

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    // Global arrays for storing vehicles and clients during the session
    private static Vehicle[] vehicles;
    private static Client[] clients;
    private static int vehicleCount = 0;
    private static int clientCount = 0;

    public static void main(String[] args) {
        displayWelcomeMessage();
        System.out.println("-----------------------------------------");
        System.out.println("| Choose interface mode:                |");
        System.out.println("| 1 - Menu-Driven Interface             |");
        System.out.println("| 2 - Predefined Scenario (Testing)     |");
        System.out.println("-----------------------------------------");
        int mode = getValidInt("Enter mode (1 or 2): ");

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

    // Displays a welcome message.
    private static void displayWelcomeMessage() {
        System.out.println("*************************************************************");
        System.out.println("               WELCOME TO EMILE AND ZEIDAN'S                 ");
        System.out.println("          ROYAL RENTAL VEHICLE MANAGEMENT SYSTEM             ");
        System.out.println("*************************************************************\n");
    }

    // Initializes the arrays based on user input.
    private static void initializeSystem() {
        int maxVehicles = getPositiveInt("Enter the maximum number of vehicles: ");
        vehicles = new Vehicle[maxVehicles];

        int maxClients = getPositiveInt("Enter the maximum number of clients: ");
        clients = new Client[maxClients];
    }

    // Displays the main menu and processes user selections.
    private static void mainMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n========== Main Menu ==========");
            System.out.println("1 - Vehicle Management");
            System.out.println("2 - Client Management");
            System.out.println("3 - Leasing Operations");
            System.out.println("4 - Additional Operations");
            System.out.println("5 - Exit");
            System.out.println("===============================");
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
    private static void vehicleManagement() {
        System.out.println("\n--- Vehicle Management ---");
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

    // Adds a vehicle with prompts for common and type-specific attributes.
    private static void addVehicle() {
        if (vehicleCount >= vehicles.length) {
            System.out.println("Vehicle storage is full.");
            return;
        }

        String type;
        while (true) {
            type = getNonEmptyString("Enter vehicle type (Diesel Truck, Electric Car, Gasoline Car, Electric Truck): ");
            if (type.equalsIgnoreCase("Diesel Truck") ||
                    type.equalsIgnoreCase("Electric Car") ||
                    type.equalsIgnoreCase("Gasoline Car") ||
                    type.equalsIgnoreCase("Electric Truck")) {
                break;
            } else {
                System.out.println("Invalid vehicle type. Please enter exactly: Diesel Truck, Electric Car, Gasoline Car, Electric Truck.");
            }
        }

        String make = getNonEmptyString("Enter make: ");
        String model = getNonEmptyString("Enter model: ");
        int year = getValidInt("Enter year: ");

        Vehicle vehicle = null;
        if (type.equalsIgnoreCase("Diesel Truck")) {
            int maxCapacity = getValidInt("Enter maximum capacity (kg): ");
            int fuelTankCapacity = getValidInt("Enter fuel tank capacity (liters): ");
            vehicle = new DieselTruck(make, model, year, maxCapacity, fuelTankCapacity);
        } else if (type.equalsIgnoreCase("Electric Truck")) {
            int maxCapacity = getValidInt("Enter maximum capacity (kg): ");
            int autonomyRange = getValidInt("Enter maximum autonomy range (km): ");
            vehicle = new ElectricTruck(make, model, year, maxCapacity, autonomyRange);
        } else if (type.equalsIgnoreCase("Gasoline Car")) {
            int maxPassengers = getValidInt("Enter maximum number of passengers: ");
            vehicle = new GasolineCar(make, model, year, maxPassengers);
        } else if (type.equalsIgnoreCase("Electric Car")) {
            int maxPassengers = getValidInt("Enter maximum number of passengers: ");
            int autonomyRange = getValidInt("Enter maximum autonomy range (km): ");
            vehicle = new ElectricCar(make, model, year, maxPassengers, autonomyRange);
        }

        vehicles[vehicleCount++] = vehicle;
        // Confirmation message with make and plate number.
        System.out.printf("Vehicle added successfully: Make: %s - Plate Number: %s%n",
                vehicle.getMake(), vehicle.getPlateNumber());
    }

    // Deletes a vehicle by plate number.
    private static void deleteVehicle() {
        String plateNumber = getNonEmptyString("Enter the plate number of the vehicle to delete: ");
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

    // Updates an existing vehicle's common and type-specific attributes.
    private static void updateVehicle() {
        String plateNumber = getNonEmptyString("Enter the plate number of the vehicle to update: ");
        int index = findVehicleIndexByPlate(plateNumber);
        if (index == -1) {
            System.out.println("Vehicle not found.");
            return;
        }
        Vehicle vehicle = vehicles[index];
        System.out.println("\nCurrent vehicle information:");
        System.out.println(formatVehicle(vehicle));

        String newMake = getNonEmptyString("Enter new make: ");
        String newModel = getNonEmptyString("Enter new model: ");
        int newYear = getValidInt("Enter new year: ");
        vehicle.setMake(newMake);
        vehicle.setModel(newModel);
        vehicle.setYear(newYear);

        if (vehicle instanceof DieselTruck) {
            DieselTruck dt = (DieselTruck) vehicle;
            int newMaxCapacity = getValidInt("Enter new maximum capacity (kg): ");
            int newFuelTankCapacity = getValidInt("Enter new fuel tank capacity (liters): ");
            dt.setMaxCapacity(newMaxCapacity);
            dt.setFuelTankCapacity(newFuelTankCapacity);
        } else if (vehicle instanceof ElectricTruck) {
            ElectricTruck et = (ElectricTruck) vehicle;
            int newMaxCapacity = getValidInt("Enter new maximum capacity (kg): ");
            int newAutonomyRange = getValidInt("Enter new maximum autonomy range (km): ");
            et.setMaxCapacity(newMaxCapacity);
            et.setAutonomyRange(newAutonomyRange);
        } else if (vehicle instanceof GasolineCar) {
            GasolineCar gc = (GasolineCar) vehicle;
            int newMaxPassengers = getValidInt("Enter new maximum number of passengers: ");
            gc.setMaxPassengers(newMaxPassengers);
        } else if (vehicle instanceof ElectricCar) {
            ElectricCar ec = (ElectricCar) vehicle;
            int newMaxPassengers = getValidInt("Enter new maximum number of passengers: ");
            int newAutonomyRange = getValidInt("Enter new maximum autonomy range (km): ");
            ec.setMaxPassengers(newMaxPassengers);
            ec.setAutonomyRange(newAutonomyRange);
        }
        System.out.println("\nVehicle updated successfully:");
        System.out.println(formatVehicle(vehicle));
    }

    // Lists vehicles by category using the neat format.
    private static void listVehiclesByCategory() {
        String category = getNonEmptyString("Enter vehicle category to list (Diesel Truck, Electric Car, Gasoline Car, Electric Truck): ");
        System.out.println("\n--- Vehicles in Category: " + category + " ---");
        boolean found = false;
        for (int i = 0; i < vehicleCount; i++) {
            // Compare class names ignoring spaces.
            String className = vehicles[i].getClass().getSimpleName();
            if (className.replaceAll(" ", "").equalsIgnoreCase(category.replaceAll(" ", ""))) {
                System.out.println(formatVehicle(vehicles[i]));
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
        System.out.println("\n--- Client Management ---");
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

    // Adds a client with confirmation.
    private static void addClient() {
        if (clientCount >= clients.length) {
            System.out.println("Client storage is full.");
            return;
        }
        String name = getNonEmptyString("Enter client name: ");
        String id = getNonEmptyString("Enter client ID: ");
        Client client = new Client(name, id);
        clients[clientCount++] = client;
        System.out.printf("Client added successfully: Name: %s - ID: %s%n", client.getName(), client.getId());
    }

    // Edits a client's information.
    private static void editClient() {
        String id = getNonEmptyString("Enter the client ID to edit: ");
        int index = findClientIndexById(id);
        if (index == -1) {
            System.out.println("Client not found.");
            return;
        }
        Client client = clients[index];
        System.out.println("Current client information: " + client.getName() + " - ID: " + client.getId());
        String newName = getNonEmptyString("Enter new name: ");
        client.setName(newName);
        System.out.printf("Client updated successfully: Name: %s - ID: %s%n", client.getName(), client.getId());
    }

    // Deletes a client by ID.
    private static void deleteClient() {
        String id = getNonEmptyString("Enter the client ID to delete: ");
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
        System.out.println("\n--- Leasing Operations ---");
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
            System.out.printf("Vehicle leased successfully: Make: %s - Plate: %s, leased to %s - ID: %s%n",
                    vehicle.getMake(), vehicle.getPlateNumber(),
                    clients[clientIndex].getName(), clients[clientIndex].getId());
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
            System.out.printf("Vehicle returned successfully: Make: %s - Plate: %s%n",
                    vehicle.getMake(), vehicle.getPlateNumber());
        }
    }

    // Displays all vehicles leased by a specific client using the custom format.
    private static void showVehiclesLeasedByClient() {
        String clientId = getNonEmptyString("Enter client ID: ");
        int clientIndex = findClientIndexById(clientId);
        if (clientIndex == -1) {
            System.out.println("Client not found.");
            return;
        }
        Client client = clients[clientIndex];
        System.out.println("\n--- Vehicles Leased by " + client.getName() + " (ID: " + client.getId() + ") ---");
        boolean found = false;
        for (int i = 0; i < vehicleCount; i++) {
            if (vehicles[i].isLeased() && vehicles[i].getLeasedBy().equals(client)) {
                System.out.println(formatVehicle(vehicles[i]));
                found = true;
            }
        }
        if (!found) {
            System.out.println("No vehicles leased by this client.");
        }
    }

    // Displays all leased vehicles using the custom format.
    private static void showAllLeasedVehicles() {
        System.out.println("\n--- All Leased Vehicles ---");
        boolean found = false;
        for (int i = 0; i < vehicleCount; i++) {
            if (vehicles[i].isLeased()) {
                System.out.println(formatVehicle(vehicles[i]));
                found = true;
            }
        }
        if (!found) {
            System.out.println("No vehicles are currently leased.");
        }
    }

    // ========================= Additional Operations =========================
    private static void additionalOperations() {
        System.out.println("\n--- Additional Operations ---");
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
                    System.out.printf("Diesel Truck with largest capacity: %s - Plate: %s%n",
                            largest.getMake(), largest.getPlateNumber());
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
                System.out.println("\nDeep Copied Electric Trucks:");
                for (ElectricTruck et : copied) {
                    System.out.println(formatVehicle(et));
                }
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    // ========================= Predefined Scenario (Testing) =========================
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
        Client client1 = new Client("Emile", "123");
        Client client2 = new Client("Alice", "C1001");
        Client client3 = new Client("Bob", "C1002");

        // For demonstration, add a few vehicles and clients into the global arrays.
        vehicles = new Vehicle[10];
        clients = new Client[10];
        vehicles[vehicleCount++] = dt1;
        vehicles[vehicleCount++] = et1;
        vehicles[vehicleCount++] = gc1;
        clients[clientCount++] = client1;
        clients[clientCount++] = client2;
        clients[clientCount++] = client3;

        // Display vehicles using the neat format.
        System.out.println("\n--- Displaying Vehicles ---");
        for (int i = 0; i < vehicleCount; i++) {
            System.out.println(formatVehicle(vehicles[i]));
        }

        // Display clients neatly.
        System.out.println("\n--- Displaying Clients ---");
        for (int i = 0; i < clientCount; i++) {
            System.out.printf("Client: %-15s | ID: %-10s%n", clients[i].getName(), clients[i].getId());
        }

        // Simulate leasing.
        dt1.leaseTo(client1);
        System.out.println("\n--- Vehicles Leased by " + client1.getName() + " (ID: " + client1.getId() + ") ---");
        for (int i = 0; i < vehicleCount; i++) {
            if (vehicles[i].isLeased() && vehicles[i].getLeasedBy().equals(client1)) {
                System.out.println(formatVehicle(vehicles[i]));
            }
        }
    }

    // ========================= Input Checking Methods =========================
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

    // ========================= Formatting Helper =========================
    // Returns a neat, custom formatted string for each vehicle.
    private static String formatVehicle(Vehicle v) {
        String formatted;
        if (v instanceof DieselTruck) {
            DieselTruck dt = (DieselTruck) v;
            formatted = String.format("Make: %-15s | Plate: %-8s | Model: %-10s | Year: %4d | Capacity: %4d kg | Fuel Tank: %3d L",
                    dt.getMake(), dt.getPlateNumber(), dt.getModel(), dt.getYear(), dt.getMaxCapacity(), dt.getFuelTankCapacity());
        } else if (v instanceof ElectricTruck) {
            ElectricTruck et = (ElectricTruck) v;
            formatted = String.format("Make: %-15s | Plate: %-8s | Model: %-10s | Year: %4d | Capacity: %4d kg | Autonomy: %3d km",
                    et.getMake(), et.getPlateNumber(), et.getModel(), et.getYear(), et.getMaxCapacity(), et.getAutonomyRange());
        } else if (v instanceof GasolineCar) {
            GasolineCar gc = (GasolineCar) v;
            formatted = String.format("Make: %-15s | Plate: %-8s | Model: %-10s | Year: %4d | Passengers: %2d",
                    gc.getMake(), gc.getPlateNumber(), gc.getModel(), gc.getYear(), gc.getMaxPassengers());
        } else if (v instanceof ElectricCar) {
            ElectricCar ec = (ElectricCar) v;
            formatted = String.format("Make: %-15s | Plate: %-8s | Model: %-10s | Year: %4d | Passengers: %2d | Autonomy: %3d km",
                    ec.getMake(), ec.getPlateNumber(), ec.getModel(), ec.getYear(), ec.getMaxPassengers(), ec.getAutonomyRange());
        } else {
            formatted = String.format("Make: %-15s | Plate: %-8s | Model: %-10s | Year: %4d",
                    v.getMake(), v.getPlateNumber(), v.getModel(), v.getYear());
        }
        return formatted;
    }

    // ========================= Additional Operations Helper =========================
    private static DieselTruck getLargestTruck(DieselTruck[] dieselTrucks) {
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

    private static ElectricTruck[] copyVehicles(ElectricTruck[] electricTrucks) {
        if (electricTrucks == null) return null;
        ElectricTruck[] copy = new ElectricTruck[electricTrucks.length];
        for (int i = 0; i < electricTrucks.length; i++) {
            if (electricTrucks[i] != null) {
                copy[i] = new ElectricTruck(electricTrucks[i]); // using copy constructor
            }
        }
        return copy;
    }
}