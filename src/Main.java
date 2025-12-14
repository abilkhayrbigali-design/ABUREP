import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final int PRICE_PER_CUSTOMER = 5000;

    public static int calculateTotalSalaries(ArrayList<employees> employees_list) {
        int total_salary = 0;
        for (int i = 0; i < employees_list.size(); i++) {
            employees emp = employees_list.get(i);
            total_salary += emp.getEmployee_salary();
        }
        return total_salary;
    }

    public static void main(String[] args) {
        String User_in ;
        Scanner scanner = new Scanner(System.in);
        ArrayList<cusstomers> cusstomers_list = new ArrayList<>();
        ArrayList<employees> employees_list = new ArrayList<>();

        income company_income = new income();


        while (true) {
            int customers_amount;
            System.out.println("Введите команду: 'add client', 'client list', 'add employee', 'list employee', 'calculate income', 'exit'");
            User_in = scanner.nextLine();
            if (User_in.equals("exit")) {
                break;
            }
            else if (User_in.equals("add client")) {
                cusstomers_list.add(new cusstomers());
                customers_amount = cusstomers_list.size();
                System.out.println("input cusstomer_id:");
                int cusstomer_id = scanner.nextInt();
                scanner.nextLine();
                cusstomers_list.get(customers_amount-1).setCusstomer_id(cusstomer_id);
                System.out.println("input cusstomer_name:");
                String cusstomer_name = scanner.nextLine();
                System.out.println("input cusstomer_age:");
                int cusstomer_age = scanner.nextInt();
                scanner.nextLine();
                System.out.println("input cusstomer_gender:");
                String cusstomer_gender = scanner.nextLine();
                System.out.println("input cusstomer_daysleft:");
                int cusstomer_daysleft = scanner.nextInt();
                scanner.nextLine();
                System.out.println("input cusstomer_Email:");
                String cusstomer_email = scanner.nextLine();
                cusstomers_list.get(customers_amount - 1).setCusstomer_name(cusstomer_name);
                cusstomers_list.get(customers_amount - 1).setCusstomer_age(cusstomer_age);
                cusstomers_list.get(customers_amount - 1).setCusstomer_gender(cusstomer_gender);
                cusstomers_list.get(customers_amount - 1).setCustomer_daysleft(cusstomer_daysleft);
                cusstomers_list.get(customers_amount - 1).setCusstomer_email(cusstomer_email);
            }
            else if (User_in.equals("client list")) {
                if (cusstomers_list.isEmpty()) {
                    System.out.println("list is empty.");}
                else{
                    for (int i = 0; i < cusstomers_list.size(); i++) {
                        System.out.print("Client ID:");
                        System.out.println(cusstomers_list.get(i).getCusstomer_id());
                        System.out.print("Client Name:");
                        System.out.println(cusstomers_list.get(i).getCusstomer_name());
                        System.out.print("Client Age:");
                        System.out.println(cusstomers_list.get(i).getCusstomer_age());
                        System.out.print("Client Email:");
                        System.out.println(cusstomers_list.get(i).getCusstomer_email());
                        System.out.print("Client Days left:");
                        System.out.println(cusstomers_list.get(i).getCustomer_daysleft());
                        System.out.print("Client Gender:");
                        System.out.println(cusstomers_list.get(i).getCusstomer_gender());
                        System.out.println("---");
                    }   }
            }
            else if (User_in.equals("add employee")) {

                employees_list.add(new employees());
                int employees_amount;
                employees_amount = employees_list.size();

                System.out.println("input employee id:");
                int employee_id = scanner.nextInt();
                scanner.nextLine();
                System.out.println("input employee name:");
                String employee_name = scanner.nextLine();
                System.out.println("input employee_place:");
                String employee_place = scanner.nextLine();
                System.out.println("input employee_salary:");
                int employee_salary = scanner.nextInt();
                scanner.nextLine();

                employees_list.get(employees_amount -1 ).setEmployee_id(employee_id);
                employees_list.get(employees_amount -1 ).setEmployee_name(employee_name);
                employees_list.get(employees_amount - 1).setEmployee_place(employee_place);
                employees_list.get(employees_amount - 1).setEmployee_salary(employee_salary);
            }
            else if (User_in.equals("list employee")) {
                if (employees_list.isEmpty()) {
                    System.out.println("list is empty.");}
                else{
                    for (int i = 0; i < employees_list.size(); i++) {
                        System.out.print("Employee ID:");
                        System.out.println(employees_list.get(i).getEmployee_id());
                        System.out.print("Employee Name:");
                        System.out.println(employees_list.get(i).getEmployee_name());
                        System.out.print("Employee Place:");
                        System.out.println(employees_list.get(i).getEmployee_place());
                        System.out.print("Employee Salary:");
                        System.out.println(employees_list.get(i).getEmployee_salary());
                        System.out.println("---");
                    }
                }
            }
            else if (User_in.equals("calculate income")) {

                int num_of_customers = cusstomers_list.size();
                int total_income = num_of_customers * PRICE_PER_CUSTOMER;

                System.out.println("Total Customers: " + num_of_customers);
                System.out.println("Calculated Total Income (5000 per customer): " + total_income);

                System.out.println("Input Total monthly light/utility expense:");
                int total_light = scanner.nextInt();
                scanner.nextLine();

                int total_salaries = calculateTotalSalaries(employees_list);

                company_income.setTotal_income(total_income);
                company_income.setTotal_light_expense(total_light);
                company_income.setTotal_salary_expense(total_salaries);

                System.out.println("Income Report");
                System.out.println("Total Income: " + company_income.getTotal_income());
                System.out.println("Total Salary Expense: " + company_income.getTotal_salary_expense());
                System.out.println("Total Light Expense: " + company_income.getTotal_light_expense());
                int net_income = company_income.calculate_net_income();
                System.out.println("Net Income: " + net_income );
            } else {
                System.out.println("error.");
            }
        }
    }
}