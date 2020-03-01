import entities.Address;
import entities.Employee;
import entities.Project;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.util.*;

public class Engine implements Runnable {
    private EntityManager manager;
    private Scanner reader;

    public Engine(EntityManager manager) {
        this.manager = manager;
        this.reader = new Scanner(System.in);
    }

    @Override
    public void run() {

//        2.Remove Objects
//        removeObjects();

//        3.Remove Objects
//        containsEmployee();

//        4.Employees with Salary Over 50 000
//        employeesWithSalaryOverFiftyThousand();

//        5.Employees from Department
//        employeesFromDepartment();

//        6.Adding a New Address and Updating Employee
//        addingANewAddressAndUpdatingEmployee();

//        7.Addresses with Employee Count
//        addressesWithEmployeeCount();

//        8.Get Employee with Project
//        getEmployeeWithProject();

//        9.Find Latest 10 Projects
//        findLatestTenProjects();

//        10.Increase Salaries
//        increaseSalaries();

//        11.Remove Towns
//        removeTowns();

//        12.Find Employees by First Name
//        findEmployeesByFirstName();

//        13.Employees Maximum Salaries
//        employeesMaximumSalaries();
    }

    private void removeTowns() {

        System.out.println("Enter town for delete: ");
        String townName = this.reader.nextLine();

        this.manager.getTransaction().begin();

        List<Employee> employees = this.manager.createQuery("SELECT e FROM Employee AS e WHERE e.address.town.name = :town", Employee.class)
                .setParameter("town", townName)
                .getResultList();

        if (employees.isEmpty()){
            System.out.println("Town not found!");
            return;
        }

        for (Employee employee : employees) {
            employee.setAddress(null);
        }

        List<Address> addresses = this.manager.createQuery("SELECT a FROM Address AS a WHERE a.town.name = :town", Address.class)
                .setParameter("town", townName)
                .getResultList();

        for (Address address : addresses) {
            this.manager.remove(address);
        }

        Town town = this.manager.createQuery("SELECT t FROM Town AS t WHERE t.name = :town", Town.class)
                .setParameter("town", townName)
                .getSingleResult();

        this.manager.remove(town);

        System.out.println(String.format("%d address in %s deleted", addresses.size(), townName));

        this.manager.getTransaction().commit();
    }

    private void employeesMaximumSalaries() {

        List<Employee> employees = this.manager.createQuery("SELECT e FROM Employee AS e", Employee.class).getResultList();

        Map<String, BigDecimal> departmentAndSalary = new LinkedHashMap<>();

        for (Employee employee : employees) {
            String departmentName = employee.getDepartment().getName();
            BigDecimal salary = employee.getSalary();

            if (!departmentAndSalary.containsKey(departmentName)) {
                departmentAndSalary.put(departmentName, salary);
            } else {
                BigDecimal compareSalary = departmentAndSalary.get(departmentName);
                if (employee.getSalary().compareTo(compareSalary) > 0) {
                    departmentAndSalary.put(departmentName, employee.getSalary());
                }
            }
        }

        departmentAndSalary.entrySet().stream().filter(e ->
                e.getValue().compareTo(BigDecimal.valueOf(70000)) > 0 ||
                e.getValue().compareTo(BigDecimal.valueOf(30000)) < 0)
                .forEach(e -> System.out.println(String.format("%s %.2f", e.getKey(), e.getValue())));
    }

    private void findEmployeesByFirstName() {

        System.out.println("Please enter pattern:");
        String pattern = this.reader.nextLine();
        pattern = pattern + "%";

        manager.createQuery("SELECT e FROM Employee AS e WHERE e.firstName LIKE :pattern", Employee.class).setParameter("pattern", pattern)
                .getResultStream()
                .forEach(e -> System.out.println(String.format("%s %s - %s - ($%.2f)", e.getFirstName(), e.getLastName(), e.getJobTitle(), e.getSalary())));
    }

    private void increaseSalaries() {

        List<Employee> employees = this.manager.createQuery("SELECT e FROM Employee as e WHERE e.department.name = 'Engineering' OR e.department.name = 'Tool Design' OR e.department.name = 'Marketing'", Employee.class)
                .getResultList();

        employees.forEach(e -> this.manager.detach(e));

        for (Employee employee : employees) {

            BigDecimal salary = employee.getSalary();
            BigDecimal newSalary = salary.multiply(BigDecimal.valueOf(1.12));

            employee.setSalary(newSalary);

            System.out.println(String.format("%s %s ($%.2f)", employee.getFirstName(), employee.getLastName(), employee.getSalary()));
        }

        this.manager.getTransaction().begin();
        employees.forEach(e -> this.manager.merge(e));
        this.manager.getTransaction().commit();
    }


    private void findLatestTenProjects() {

        this.manager.createQuery("SELECT p FROM Project AS p ORDER BY p.startDate DESC ", Project.class)
                .setMaxResults(10)
                .getResultStream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(e ->
                        System.out.println(String.format("Project name: %s%n    Project Description: %s%n    Project Start Date: %s%n    Project End Date: %s",
                                e.getName(),
                                e.getDescription(),
                                e.getStartDate(),
                                e.getEndDate())));
    }

    private void getEmployeeWithProject() {

        System.out.println("Please enter employee ID: ");
        int id = this.reader.nextInt();

        this.manager.createQuery("SELECT e FROM Employee AS e WHERE e.id = :id", Employee.class)
                .setParameter("id", id)
                .getResultStream()
                .forEach(e -> System.out.println(String.format("%s %s - %s%n%s", e.getFirstName(), e.getLastName(), e.getJobTitle(), getProjectsToString(e.getProjects()))));


    }

    private String getProjectsToString(Set<Project> projects) {
        StringBuilder sb = new StringBuilder();
        projects.stream().sorted(Comparator.comparing(Project::getName)).forEach(e -> sb.append("     ").append(e.getName()).append(System.lineSeparator()));
        return sb.toString();
    }

    private void addressesWithEmployeeCount() {

        this.manager.createQuery("SELECT a FROM Address as a ORDER BY a.employees.size DESC ", Address.class)
                .setMaxResults(10)
                .getResultStream()
                .forEach(e -> System.out.println(String.format("%s, , %s - %d employees", e.getText(), e.getTown().getName(), e.getEmployees().size())));
    }

    private void addingANewAddressAndUpdatingEmployee() {
        System.out.println("Please enter employee last name: ");
        String lastName = this.reader.nextLine();

        Employee employee;

        try {
            employee = this.manager
                    .createQuery("SELECT p FROM Employee AS p WHERE p.lastName = :lastName", Employee.class).setParameter("lastName", lastName)
                    .getSingleResult();

        }catch (NoResultException ex){
            System.out.println("Person with last name \""+lastName+"\" not found!");
            return;
        }

        this.manager.getTransaction().begin();
        this.manager.detach(employee);

        Address address = new Address();
        address.setText("Vitoshka 15");

        this.manager.persist(address);

        employee.setAddress(address);

        this.manager.merge(employee);

        this.manager.flush();

        this.manager.getTransaction().commit();
    }

    private void employeesFromDepartment() {

        this.manager.createQuery("SELECT p FROM Employee AS p WHERE p.department.name = 'Research and Development' ORDER BY p.salary, p.id", Employee.class)
                .getResultStream()
                .forEach(e -> System.out.println(String.format("%s %s from %s - $%.2f", e.getFirstName(), e.getLastName(), e.getDepartment().getName(), e.getSalary())));

    }

    private void employeesWithSalaryOverFiftyThousand() {

        this.manager
                .createQuery("SELECT p FROM Employee AS p WHERE p.salary>50000", Employee.class)
                .getResultStream()
                .forEach(e -> System.out.println(e.getFirstName()));
    }

    private void containsEmployee() {
        System.out.println("Please enter name:");
        String fullName = this.reader.nextLine();

        try {

            Employee employee =
                    this.manager
                            .createQuery("SELECT p FROM Employee AS p WHERE CONCAT(p.firstName, ' ' ,p.lastName) = :name", Employee.class)
                            .setParameter("name", fullName)
                            .getSingleResult();
            System.out.println("Yes");
        } catch (NoResultException ex) {
            System.out.println("No");
        }

    }

    private void removeObjects() {

        List<Town> towns = this.manager.createQuery("SELECT t FROM Town AS t WHERE LENGTH(t.name)>5", Town.class).getResultList();

        this.manager.getTransaction().begin();

        towns.forEach(this.manager::detach);
        towns.forEach(e -> e.setName(e.getName().toLowerCase()));
        towns.forEach(this.manager::merge);

        this.manager.flush();
        this.manager.getTransaction().commit();
    }
}
