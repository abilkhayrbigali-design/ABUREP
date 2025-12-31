public class Customer extends Person {
    private int age;
    private String gender;
    private int daysLeft;
    private String email;

    public Customer(int id, String name, int age, String gender, int daysLeft, String email) {
        super(id, name);
        this.age = age;
        this.gender = gender;
        this.daysLeft = daysLeft;
        this.email = email;
    }

    public int getAge() { return age; }
    public String getGender() { return gender; }
    public int getDaysLeft() { return daysLeft; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return super.toString() + ", Age: " + age + ", Gender: " + gender + ", Days Left: " + daysLeft + ", Email: " + email;
    }
}