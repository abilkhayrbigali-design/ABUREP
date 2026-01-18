import java.sql.DriverManager;
import java.util.*;
import java.util.stream.Collectors;
import java.sql.*;

public class Main {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "1907";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/gym_data_base";

    private static final int PRICE_PER_CUSTOMER = 5000;

    public static void main(String[] args  ) throws Exception{

        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        Scanner scanner = new Scanner(System.in);
        ArrayList<Customer> customers = new ArrayList<>();
        ArrayList<Employee> employees = new ArrayList<>();
        Income incomeReport = new Income();

        while (true) {
            System.out.println("input command: 'add client', 'client list', 'add employee', 'list employee', 'search client', 'calculate income', 'exit'");
            String input = scanner.nextLine();

            if (input.equals("exit")) break;

            switch (input) {
                case "add client":
                    System.out.println("input customer name:");
                    String cName = scanner.nextLine();
                    System.out.println("input customer age:");
                    int cAge = scanner.nextInt(); scanner.nextLine();
                    System.out.println("input customer gender:");
                    String cGender = scanner.nextLine();
                    System.out.println("input customer days left:");
                    int cDays = scanner.nextInt(); scanner.nextLine();
                    System.out.println("input customer email:");
                    String cEmail = scanner.nextLine();

                    String insertClient = "INSERT INTO client (name, age, gender, days_left, email) VALUES (?, ?, ?, ?, ?)";

                    try (PreparedStatement pstmt = connection.prepareStatement(insertClient)) {
                        pstmt.setString(1, cName);
                        pstmt.setInt(2, cAge);
                        pstmt.setString(3, cGender);
                        pstmt.setInt(4, cDays);
                        pstmt.setString(5, cEmail);

                        pstmt.executeUpdate();
                        System.out.println("Client saved to database!");
                    } catch (SQLException e) {
                        System.out.println("Error saving client: " + e.getMessage());
                    }
                    break;

                case "client list":
                    String selectClients = "SELECT * FROM client";

                    try (Statement statement = connection.createStatement();
                         ResultSet result = statement.executeQuery(selectClients)) {

                        System.out.println("--- Client List ---");
                        boolean hasRows = false;

                        while (result.next()) {
                            hasRows = true;
                            System.out.println(
                                    "ID: " + result.getInt("id") +
                                            " | Name: " + result.getString("name") +
                                            " | Age: " + result.getInt("age") +
                                            " | Gender: " + result.getString("gender") +
                                            " | Days left: " + result.getInt("days_left") +
                                            " | Email: " + result.getString("email")
                            );
                        }

                        if (!hasRows) System.out.println("Database table 'client' is empty.");

                    } catch (SQLException e) {
                        System.out.println("Error loading clients: " + e.getMessage());
                    }
                    break;

                case "add employee":
                    int eId = employees.size();
                    System.out.println("input employee name:");
                    String eName = scanner.nextLine();
                    System.out.println("input employee position:");
                    String ePos = scanner.nextLine();
                    System.out.println("input employee salary:");
                    int eSalary = scanner.nextInt(); scanner.nextLine();
                    employees.add(new Employee(eId, eName, ePos, eSalary));

                    String insertSql = "INSERT INTO employee (name, position, salary) VALUES (?, ?, ?)";

                    try (PreparedStatement pstmt = connection.prepareStatement(insertSql)) {
                        pstmt.setString(1, eName);
                        pstmt.setString(2, ePos);
                        pstmt.setInt(3, eSalary);

                        int rowsInserted = pstmt.executeUpdate();
                        if (rowsInserted > 0) {
                            System.out.println("Employee added to database successfully!");
                        }
                    } catch (SQLException e) {
                        System.out.println("Error adding employee: " + e.getMessage());
                    }
                    break;

                case "list employee":
                    String selectSql = "SELECT * FROM employee";

                    try (Statement statement = connection.createStatement();
                         ResultSet result = statement.executeQuery(selectSql)) {

                        System.out.println("--- Employee List from DB ---");
                        boolean hasData = false;

                        while (result.next()) {
                            hasData = true;
                            System.out.println(
                                    "ID: " + result.getInt("id")
                                            + " | Name: " + result.getString("name")
                                            + " | Position: " + result.getString("position")
                                            + " | Salary: " + result.getInt("salary")
                            );
                        }

                        if (!hasData) {
                            System.out.println("Database table is empty.");
                        }

                    } catch (SQLException e) {
                        System.out.println("Error reading data: " + e.getMessage());
                    }
                    break;

                case "search client":
                    System.out.println("Enter Client ID to search:");
                    int searchId = scanner.nextInt();
                    scanner.nextLine();

                    String searchSql = "SELECT * FROM client WHERE id = ?";

                    try (PreparedStatement pstmt = connection.prepareStatement(searchSql)) {
                        pstmt.setInt(1, searchId);

                        try (ResultSet rs = pstmt.executeQuery()) {
                            if (rs.next()) {
                                System.out.println("Found: ID " + rs.getInt("id") +
                                        ", Name: " + rs.getString("name") +
                                        ", Email: " + rs.getString("email"));
                            } else {
                                System.out.println("Client with ID " + searchId + " not found in database.");
                            }
                        }
                    } catch (SQLException e) {
                        System.out.println("Search error: " + e.getMessage());
                    }
                    break;

                case "calculate income":
                    int totalSalaries = 0;
                    int customerCount = 0;

                    try {
                        String salarySql = "SELECT SUM(salary) FROM employee";
                        try (Statement st = connection.createStatement();
                             ResultSet rs = st.executeQuery(salarySql)) {
                            if (rs.next()) {
                                totalSalaries = rs.getInt(1);
                            }
                        }

                        String customerSql = "SELECT COUNT(*) FROM client";
                        try (Statement st = connection.createStatement();
                             ResultSet rs = st.executeQuery(customerSql)) {
                            if (rs.next()) {
                                customerCount = rs.getInt(1);
                            }
                        }

                        int revenue = customerCount * PRICE_PER_CUSTOMER;

                        System.out.println("Total Customers: " + customerCount);
                        System.out.println("Input Light Expense:");
                        int light = scanner.nextInt();
                        scanner.nextLine();

                        incomeReport.setTotalIncome(revenue);
                        incomeReport.setSalaryExpense(totalSalaries);
                        incomeReport.setLightExpense(light);

                        System.out.println("\n--- Income Report ---");
                        System.out.println("Total Revenue: " + incomeReport.getTotalIncome());
                        System.out.println("Salary Expenses: " + totalSalaries);
                        System.out.println("Light Expenses: " + light);
                        System.out.println("Net Income: " + incomeReport.calculateNetIncome());

                    } catch (SQLException e) {
                        System.out.println("Database error during calculation: " + e.getMessage());
                    }
                    break;



                case "delete employee":
                    System.out.println("input ID :");
                    int delEmpId = scanner.nextInt();
                    scanner.nextLine();

                    String deleteEmpSql = "DELETE FROM employee WHERE id = ?";

                    try (PreparedStatement pstmt = connection.prepareStatement(deleteEmpSql)) {
                        pstmt.setInt(1, delEmpId);

                        int affectedRows = pstmt.executeUpdate();
                        if (affectedRows > 0) {
                            System.out.println("Employee with ID " + delEmpId + " Deleted successfully!.");
                        } else {
                            System.out.println("Employee with ID " + delEmpId + " not deleted successfully!");
                        }
                    } catch (SQLException e) {
                        System.out.println("Error " + e.getMessage());
                    }
                    break;

                case "delete client":
                    System.out.println("input ID :");
                    int delClientId = scanner.nextInt();
                    scanner.nextLine();

                    String deleteClientSql = "DELETE FROM client WHERE id = ?";

                    try (PreparedStatement pstmt = connection.prepareStatement(deleteClientSql)) {
                        pstmt.setInt(1, delClientId);

                        int affectedRows = pstmt.executeUpdate();
                        if (affectedRows > 0) {
                            System.out.println("Client with ID " + delClientId + " Deleted successfully!.");
                        } else {
                            System.out.println("client with ID wasnt deleted successfully!.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Error " + e.getMessage());
                    }
                    break;

                default:
                    System.out.println("error.");
            }
        }
    }
}