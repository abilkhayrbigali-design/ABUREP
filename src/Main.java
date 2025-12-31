import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final int PRICE_PER_CUSTOMER = 5000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Customer> customers = new ArrayList<>();
        ArrayList<Employee> employees = new ArrayList<>();
        Income incomeReport = new Income();

        while (true) {
            System.out.println("input command: 'add client', 'client list', 'add employee', 'list employee', 'sort', 'search', 'calculate income', 'exit'");
            String input = scanner.nextLine();

            if (input.equals("exit")) break;


            switch (input) {
                case "add client":
                    System.out.println("input customer id:");
                    int cId = scanner.nextInt(); scanner.nextLine();
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
                    customers.add(new Customer(cId, cName, cAge, cGender, cDays, cEmail));
                    break;

                case "client list":
                    if (customers.isEmpty()) System.out.println("list is empty.");
                    else customers.forEach(System.out::println);
                    break;

                case "add employee":
                    System.out.println("input employee id:");
                    int eId = scanner.nextInt(); scanner.nextLine();
                    System.out.println("input employee name:");
                    String eName = scanner.nextLine();
                    System.out.println("input employee position:");
                    String ePos = scanner.nextLine();
                    System.out.println("input employee salary:");
                    int eSalary = scanner.nextInt(); scanner.nextLine();
                    employees.add(new Employee(eId, eName, ePos, eSalary));
                    break;

                case "list employee":
                    if (employees.isEmpty()) System.out.println("list is empty.");
                    else employees.forEach(System.out::println);
                    break;

                case "sort":
                    customers.sort(Comparator.comparing(Person::getName));
                    System.out.println("Customers sorted by name.");
                    break;

                case "search":
                    System.out.println("Enter ID to search:");
                    int searchId = scanner.nextInt(); scanner.nextLine();
                    customers.stream()
                            .filter(c -> c.getId() == searchId)
                            .findFirst()
                            .ifPresentOrElse(System.out::println, () -> System.out.println("Not found."));
                    break;

                case "calculate income":
                    int totalSalaries = employees.stream().mapToInt(Employee::getSalary).sum();
                    int revenue = customers.size() * PRICE_PER_CUSTOMER;
                    System.out.println("Total Customers: " + customers.size());
                    System.out.println("Input Light Expense:");
                    int light = scanner.nextInt(); scanner.nextLine();

                    incomeReport.setTotalIncome(revenue);
                    incomeReport.setSalaryExpense(totalSalaries);
                    incomeReport.setLightExpense(light);

                    System.out.println("Income Report:");
                    System.out.println("Total Revenue: " + incomeReport.getTotalIncome());
                    System.out.println("Net Income: " + incomeReport.calculateNetIncome());
                    break;

                default:
                    System.out.println("error.");
            }
        }
    }
}