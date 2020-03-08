package com.spring.spring.entites;

import com.spring.spring.entites.enums.AgeRestriction;
import com.spring.spring.entites.enums.EditionType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "books")

public class Book extends BaseEntity{

    private AgeRestriction ageRestriction;
    private int copies;
    private String description;
    private EditionType editionType;
    private BigDecimal price;
    private LocalDate releaseDate;
    private String title;

    private Set<Category> categories;
    private Author author;

    public Book() {
    }


    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }


    @Column(name = "age_restriction")
    @Enumerated(value = EnumType.ORDINAL)

    public AgeRestriction getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(AgeRestriction ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    @Column(name = "copies",nullable = false)

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    @Column(name = "description",length = 1000)

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "edition_type",nullable = false)
    @Enumerated(value = EnumType.ORDINAL)

    public EditionType getEditionType() {
        return editionType;
    }

    public void setEditionType(EditionType editionType) {
        this.editionType = editionType;
    }

    @Column(name = "price",nullable = false)

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = "release_date")

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Column(name = "title",nullable = false,length = 50)

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
