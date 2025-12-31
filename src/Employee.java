public class Employee extends Person {
    private String position;
    private int salary;

    public Employee(int id, String name, String position, int salary) {
        super(id, name);
        this.position = position;
        this.salary = salary;
    }

    public String getPosition() { return position; }
    public int getSalary() { return salary; }

    @Override
    public String toString() {
        return super.toString() + ", Position: " + position + ", Salary: " + salary;
    }
}