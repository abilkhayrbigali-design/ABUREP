import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        String User_in ;
        Scanner scanner = new Scanner(System.in);
        ArrayList<cusstomers> cusstomers_list = new ArrayList<>();


        while (true) {
            int customers_amount;
            System.out.println("Create new cusstomer?");
            User_in = scanner.nextLine();
            if (User_in.equals("exit")) {
                break;
            }
            else if (User_in.equals("add")) {
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
                System.out.println("input cusstomer_Email");
                String cusstomer_email = scanner.nextLine();
                cusstomers_list.get(customers_amount - 1).setCusstomer_name(cusstomer_name);
                cusstomers_list.get(customers_amount - 1).setCusstomer_age(cusstomer_age);
                cusstomers_list.get(customers_amount - 1).setCusstomer_gender(cusstomer_gender);
                cusstomers_list.get(customers_amount - 1).setCustomer_daysleft(cusstomer_daysleft);
                cusstomers_list.get(customers_amount - 1).setCusstomer_email(cusstomer_email);
            }
            else if (User_in.equals("List")) {
                for (int i = 0; i < cusstomers_list.size(); i++) {
                    System.out.println(cusstomers_list.get(i).getCusstomer_id());
                    System.out.println(cusstomers_list.get(i).getCusstomer_name());
                    System.out.println(cusstomers_list.get(i).getCusstomer_age());
                    System.out.println(cusstomers_list.get(i).getCusstomer_email());
                    System.out.println(cusstomers_list.get(i).getCustomer_daysleft());
                    System.out.println(cusstomers_list.get(i).getCusstomer_gender());
                }
            }
        }


    }
}