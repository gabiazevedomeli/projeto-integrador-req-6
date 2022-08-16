package dh.meli.projeto_integrador.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

/**
 * Method Getter implemented by Lombok lib for get access the private attributes of the Batch Class
 */
@Getter
/**
 * Method Setter implemented by Lombok lib for set the private attributes of the Batch Class
 */
@Setter
/**
 * The @Entity annotation marks the Batch Class as an entity bean,
 * so it must have a no-argument constructor that is visible at least with a protected scope.
 */
@Entity
/**
 * The @Table annotation is used to specify table details that will be used to persist our entities in the database.
 */
@Table(name = "batch")
/**
 * Method builder implemented by Lombok lib
 */
@Builder
/**
 * Method Default Constructor implemented by Lombok lib
 */
@NoArgsConstructor
/**
 * Method Constructor with all arguments implemented by Lombok lib
 */
@AllArgsConstructor
/**
 * Class created for modeling the Batch entity on the database
 * @author Diovana Valim
 * @version 0.0.1
 * @see java.lang.Object
 */
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "current_temperature")
    private float currentTemperature;

    @Column(name = "minimum_temperature")
    private float minimumTemperature;

    @Column(name = "initial_quantity")
    private int initialQuantity;

    @Column(name = "current_quantity")
    private int currentQuantity;

    @Column(name = "manufacturing_date")
    private LocalDate manufacturingDate;

    @Column(name = "manufacturing_time")
    private LocalTime manufacturingTime;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @JsonIgnoreProperties("batches")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_entry_id", referencedColumnName = "id")
    @JsonIgnoreProperties("batches")
    private OrderEntry orderEntry;

}
