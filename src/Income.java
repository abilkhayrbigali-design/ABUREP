public class Income {
    private int totalIncome;
    private int salaryExpense;
    private int lightExpense;

    public void setTotalIncome(int totalIncome) { this.totalIncome = totalIncome; }
    public void setSalaryExpense(int salaryExpense) { this.salaryExpense = salaryExpense; }
    public void setLightExpense(int lightExpense) { this.lightExpense = lightExpense; }

    public int getTotalIncome() { return totalIncome; }
    public int getSalaryExpense() { return salaryExpense; }
    public int getLightExpense() { return lightExpense; }

    public int calculateNetIncome() {
        return totalIncome - (salaryExpense + lightExpense);
    }
}