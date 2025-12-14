public class income {
    private int Total_income;
    private int Total_salary_expense;
    private int Total_light_expense;

    public income() {
        this.Total_income = 0;
        this.Total_salary_expense = 0;
        this.Total_light_expense = 0;
    }

    public int getTotal_income() {
        return Total_income;
    }

    public int getTotal_salary_expense() {
        return Total_salary_expense;
    }

    public int getTotal_light_expense() {
        return Total_light_expense;
    }

    public void setTotal_income(int total_income) {
        Total_income = total_income;
    }

    public void setTotal_salary_expense(int total_salary_expense) {
        Total_salary_expense = total_salary_expense;
    }

    public void setTotal_light_expense(int total_light_expense) {
        Total_light_expense = total_light_expense;
    }

    public int calculate_net_income() {
        int net_income = Total_income - (Total_salary_expense + Total_light_expense);
        return net_income;
    }
}