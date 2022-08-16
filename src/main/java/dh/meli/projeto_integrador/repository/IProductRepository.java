package dh.meli.projeto_integrador.repository;

import dh.meli.projeto_integrador.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface IProductRepository will manage data persistence for Product object instances.
 * Will read, save, update and delete data through the GET, POST, PUT and DELETE requests.
 * @author Diovana Valim, Rafael Cavalcante
 * @version 0.0.1
 */
@Repository
public interface IProductRepository extends CrudRepository<Product, Long> {

    /**
     * Method for to find a product by type
     * @param type String
     * @return an object of type Product filtered by type
     */
    List<Product> findAllByType(String type);

    /**
     * Method for to find a product by name
     * @param name String
     * @return an object of type Product filtered by name
     */
    Product findByName(String name);

    /**
     * Method for to find a product by category name
     * @param categoryName String
     * @return a list of objects of type products
     */
    List<Product> findByCategoryName(String categoryName);
}
