package dh.meli.projeto_integrador.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * Method Getter implemented by Lombok lib for get access the private attributes of the Product Class
 */
@Getter
/**
 * Method Setter implemented by Lombok lib for set access the private attributes of the Product Class
 */
@Setter
/**
 * Method builder implemented by Lombok lib
 */
@Builder
/**
 * Method Constructor with all arguments implemented by Lombok lib
 */
@AllArgsConstructor
/**
 * Method Default Constructor implemented by Lombok lib
 */
@NoArgsConstructor
/**
 * The @Entity annotation marks the Product Class as an entity bean,
 * so it must have a no-argument constructor that is visible at least with a protected scope.
 */
@Entity
/**
 * The @Table annotation is used to specify table details that will be used to persist our entities in the database.
 */
@Table(name = "product")
/**
 * Class created for modeling the Product entity on the database
 * @author Diovana Valim
 * @version 0.0.1
 * @see java.lang.Object
 */
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("product")
    private Set<Batch> batches;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "price")
    private double price;

    @Column(name = "category_name")
    private String categoryName;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    private Set<ProductCart> productCarts;
}