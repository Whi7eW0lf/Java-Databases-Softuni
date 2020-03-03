package entites;

import javax.persistence.*;
import java.util.Set;

@Entity()
@Table(name = "customers")
public class Customer {
    private int id;
    private String name;
    private String email;
    private String creditCartNumber;

    private Set<Sale> sales;

    public Customer( String name, String email, String creditCartNumber) {
        this.name = name;
        this.email = email;
        this.creditCartNumber = creditCartNumber;
    }

    public Customer() {
    }

    @OneToMany(mappedBy = "customer",targetEntity = Sale.class)
    public Set<Sale> getSales() {
        return sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name",length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "credit_card_number")
    public String getCreditCartNumber() {
        return creditCartNumber;
    }

    public void setCreditCartNumber(String creditCartNumber) {
        this.creditCartNumber = creditCartNumber;
    }
}
