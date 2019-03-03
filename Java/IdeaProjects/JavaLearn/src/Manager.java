public class Manager extends Employee{
    private double bonus;

    public Manager(String name, double salary, int year, int month, int day) {
        super(name, salary, year, month, day);
        bonus = 0;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    @Override
    public double getSalary() {
        return super.getSalary() + bonus;
    }

    public void test(Employee e) {
        System.out.println("choose Parent");
    }

    public void test(Manager m) {
        System.out.println("choose Child");
    }

    public boolean equals(Manager otherObject) {
        if(!super.equals(otherObject)) return false;
        Manager other = (Manager) otherObject;
        System.out.println("yes");
        return bonus == other.bonus;
    }

}