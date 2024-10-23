import java.util.ArrayList;
import java.util.Scanner;
abstract class User {
    String username;
    String password;
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public abstract boolean login(String username, String password);
}
class Admin extends User {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    
    private ArrayList<Car> carList;
    
    public Admin(String username, String password, ArrayList<Car> carList) {
        super(username, password);
        this.carList = carList;
    }
    
    @Override
    public boolean login(String username, String password) {
        return username.equalsIgnoreCase(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD);
    }
    public void addCar(String model, int seatingCapacity, double rentPerDay) {
        Car newCar = new Car(model, seatingCapacity, rentPerDay);
        carList.add(newCar);
        System.out.println("Car added successfully: " + model);
    }
    public ArrayList<Car> getCarList() {
        return carList;
    }
}
class Customer extends User {
    private ArrayList<Car> rentedCars;
    public Customer(String username, String password) {
        super(username, password);
        this.rentedCars = new ArrayList<>();
    }
    @Override
    public boolean login(String username, String password) {
        return this.username.equalsIgnoreCase(username) && this.password.equals(password);
    }
    public void displayAvailableCars(ArrayList<Car> carList) {
        System.out.println("Available Cars:");
        for (int i = 0; i < carList.size(); i++) {
            System.out.println((i + 1) + ". " + carList.get(i));
        }
    }
    public void rentCar(ArrayList<Car> carList, int familyMembers) {
        ArrayList<Car> availableCars = new ArrayList<>();
        for (Car car : carList) {
            if (car.getSeatingCapacity() >= familyMembers) {
                availableCars.add(car);
            }
        }
        if (availableCars.isEmpty()) {
            System.out.println("No cars available for " + familyMembers + " members.");
            return;
        }
        displayAvailableCars(availableCars);
        System.out.print("Select a car to rent (enter the number): ");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        if (choice > 0 && choice <= availableCars.size()) {
            Car selectedCar = availableCars.get(choice - 1);
            rentedCars.add(selectedCar);
            carList.remove(selectedCar);
            System.out.println("Car rented successfully: " + selectedCar.getModel());
        } else {
            System.out.println("Invalid selection. No car rented.");
        }
    }
    public void rentCar(ArrayList<Car> carList, double budget) {
        ArrayList<Car> availableCars = new ArrayList<>(); 
        for (Car car : carList) {
            if (car.getRentPerDay() <= budget) {
                availableCars.add(car);
            }
        }
        if (availableCars.isEmpty()) {
            System.out.println("No cars available within the budget of " + budget + ".");
            return;
        }
        displayAvailableCars(availableCars);
        System.out.print("Select a car to rent (enter the number): ");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        if (choice > 0 && choice <= availableCars.size()) {
            Car selectedCar = availableCars.get(choice - 1);
            rentedCars.add(selectedCar);
            carList.remove(selectedCar);
            System.out.println("Car rented successfully: " + selectedCar.getModel());
        } else {
            System.out.println("Invalid selection. No car rented.");
        }
    }
    public void displayRentedCars() {
        if (rentedCars.isEmpty()) {
            System.out.println("You have not rented any cars.");
        } else {
            System.out.println("Currently rented cars:");
            for (Car car : rentedCars) {
                System.out.println(car.getModel() + " | Rent per day: " + car.getRentPerDay());
            }
        }
    }
}
class Car {
    private String model;
    private int seatingCapacity;
    private double rentPerDay;
    public Car(String model, int seatingCapacity, double rentPerDay) {
        this.model = model;
        this.seatingCapacity = seatingCapacity;
        this.rentPerDay = rentPerDay;
    }
    public String getModel() {
        return model;
    }
    public int getSeatingCapacity() {
        return seatingCapacity;
    }
    public double getRentPerDay() {
        return rentPerDay;
    }
    @Override
    public String toString() {
        return "Model: " + model + ", Seating Capacity: " + seatingCapacity + ", Rent: " + rentPerDay + "/day";
    }
}
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Car> carList = new ArrayList<>();
        carList.add(new Car("Toyota Camry", 5, 5000));
        carList.add(new Car("Honda CRV", 7, 7000));
        carList.add(new Car("Ford Explorer", 7, 8000));
        carList.add(new Car("Maruti Suzuki Swift", 5, 5000));
        Admin admin = new Admin("admin", "admin123", carList);
        while (true) {
            System.out.println("\n--- Welcome to the Car Rental System! ---");
            System.out.println("1. Admin Login");
            System.out.println("2. Customer Registration");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            if (choice == 1) {
                System.out.print("Enter Admin Username: ");
                String adminUsername = sc.nextLine();
                System.out.print("Enter Admin Password: ");
                String adminPassword = sc.nextLine();
                if (admin.login(adminUsername, adminPassword)) {
                    System.out.println("Admin Login Successful!");
                    System.out.print("Enter Car Model to Add: ");
                    String model = sc.nextLine();
                    System.out.print("Enter Seating Capacity: ");
                    int capacity = sc.nextInt();
                    System.out.print("Enter Rent per day: ");
                    double rent = sc.nextDouble();
                    admin.addCar(model, capacity, rent);
                } else {
                    System.out.println("Invalid Admin Credentials.");
                }
            } else if (choice == 2) {
                System.out.print("Enter Customer Username: ");
                String customerUsername = sc.nextLine();
                System.out.print("Enter Customer Password: ");
                String customerPassword = sc.nextLine();
                Customer customer = new Customer(customerUsername, customerPassword);
                System.out.println("Customer Registration Successful!");
                while (true) {
                    System.out.println("1. Rent by family members");
                    System.out.println("2. Rent by budget");
                    System.out.println("3. Display rented cars");
                    System.out.println("4. Back to main menu");
                    System.out.print("Enter your choice: ");
                    int rentChoice = sc.nextInt();
                    if (rentChoice == 1) {
                        System.out.print("Enter Number of Family Members: ");
                        int familyMembers = sc.nextInt();
                        customer.rentCar(admin.getCarList(), familyMembers);
                    } else if (rentChoice == 2) {
                        customer.displayAvailableCars(admin.getCarList());
                        System.out.print("Enter your Budget: ");
                        double budget = sc.nextDouble();
                        customer.rentCar(admin.getCarList(), budget);
                    } else if (rentChoice == 3) {
                        customer.displayRentedCars();
                    } else if (rentChoice == 4) {
                        break;
                    } else {
                        System.out.println("Invalid choice! Please try again.");
                    }
                }
            } else if (choice == 3) {
                System.out.println("Exiting the system. Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice! Please try again.");
            }
        }
        
        sc.close();
    }
}
