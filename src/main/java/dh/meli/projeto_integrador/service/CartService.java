package dh.meli.projeto_integrador.service;

import dh.meli.projeto_integrador.dto.dtoInput.ProductDto;

import dh.meli.projeto_integrador.dto.dtoOutput.CartOutputDto;
import dh.meli.projeto_integrador.dto.dtoOutput.CartProductsOutputDto;
import dh.meli.projeto_integrador.dto.dtoOutput.TotalPriceDto;

import dh.meli.projeto_integrador.dto.dtoOutput.UpdateStatusDto;
import dh.meli.projeto_integrador.enumClass.PurchaseOrderStatusEnum;
import dh.meli.projeto_integrador.exception.CartAlreadyFinishedException;
import dh.meli.projeto_integrador.exception.CartNotFoundException;
import dh.meli.projeto_integrador.dto.dtoInput.CartDto;
import dh.meli.projeto_integrador.dto.dtoOutput.TotalPriceDto;
import dh.meli.projeto_integrador.exception.ForbiddenException;

import dh.meli.projeto_integrador.exception.ResourceNotFoundException;
import dh.meli.projeto_integrador.model.*;
import dh.meli.projeto_integrador.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class responsible for business rules and communication with the Cart Repository layer
 * @author Gabriela Azevedo, Rafael Cavalcante.
 * @version 0.0.1
 */
@Service
public class CartService implements ICartService {

    /**
     * Dependency Injection of the Cart Repository.
     */
    @Autowired
    private ICartRepository cartRepository;

    @Autowired
    private IBatchRepository batchRepository;

    /**
     * Dependency Injection of the BatchCart Repository.
     */
    @Autowired
    private IProductCartRepository productCartRepository;

    /**
     * Dependency Injection of the Batch Repository.
     */
    @Autowired
    private IProductRepository productRepository;

    /**
     * Dependency Injection of the Customer Repository.
     */
    @Autowired
    private ICustomerRepository customerRepository;

    /**
     * Method that receives an object of type CartDto, build the cart object and saves on the Cart table.
     * @param cartDto
     * @return
     */
    public Cart buildCart(CartDto cartDto) {
        Customer customerById = customerRepository.findById(cartDto.getBuyerId()).get();
        Cart cart = Cart.builder()
                .date(cartDto.getDate())
                .status(cartDto.getOrderStatus())
                .customer(customerById)
                .build();

        return cartRepository.save(cart);
    }

    /**
     * Method that receives an object of type Cart and a List of objects of type ProductDto and saves the data on the BatchCart table.
     * @param savedCart an object of type Cart
     * @param productsList a list of objects of type ProductDto
     */
    public void buildProductCart(Cart savedCart, List<ProductDto> productsList) {
        productsList.forEach(product -> {
            Product productById = productRepository.findById(product.getProductId()).get();
            Batch batchById = batchRepository.findByProduct(productById);

                if (product.getQuantity() > batchById.getCurrentQuantity()) {
                   throw new ForbiddenException(String.format("The product: %s does not have enough quantity in stock.", productById.getName()));
                }

            ProductCart productCart = ProductCart.builder()
                    .cart(savedCart)
                    .product(productById)
                    .quantity(product.getQuantity())
                    .build();
            productCartRepository.save(productCart);
        });
    }

    /**
     * Method that receives a list of type ProductDto and calculates the total price of the cart products.
     * @param productsList List of objects of type ProductDto
     * @return an object of type TotalPriceDto with an attribute totalPrice of type Double.
     */
    public TotalPriceDto totalCartPrice(List<ProductDto> productsList) {
        TotalPriceDto total = new TotalPriceDto(0.0);

        productsList.forEach(product -> {
            Product productById = productRepository.findById(product.getProductId()).get();
            Batch batchById = batchRepository.findByProduct(productById);
            total.setTotalPrice(batchById.getProduct().getPrice() + total.getTotalPrice());
        });
        return total;
    }

    /**
     * Method that calls the other methods of this class and persists the info of the carts on the database and returns the total price for the user.
     * @param cartDto an object of type CartDto
     * @return an object of type TotalPriceDto with an attribute totalPrice of type Double.
     */
    @Override
    @Transactional
    public TotalPriceDto createCart(CartDto cartDto) {
        Cart savedCart = buildCart(cartDto);
        List<ProductDto> productsList = cartDto.getProducts();
        buildProductCart(savedCart, productsList);
        return totalCartPrice(productsList);
    }

    /**
<<<<<<< HEAD
     * A method that recive a list of ProductCart fetch the products and create a list of CartProductsOutputDto.
     * @param cartProducts a list of objects of type ProductCart.
     * @return a list of CartProductsOutputDto.
     */
    private List<CartProductsOutputDto> createCartProductList (List<ProductCart> cartProducts) {
        List<CartProductsOutputDto> cartProductsDtos = new ArrayList<>();
        for (ProductCart productCart : cartProducts) {
            Product product = productRepository.findById(productCart.getProduct().getId()).get();
            cartProductsDtos.add(CartProductsOutputDto.builder()
                    .name(product.getName())
                    .type(product.getType())
                    .price(product.getPrice())
                    .quantity(productCart.getQuantity())
                    .subtotal(product.getPrice() * productCart.getQuantity())
                    .build()
            );
        }
        return cartProductsDtos;
    }

    /**
     * An auxiliar method that receives a list of type CartProductsOutputDto and calculates the total price of the cart products.
     * @param cartProductsDtos List of objects of type CartProductsOutputDto
     * @return a Double totalPrice.
     */
    private Double calculateCartTotal(List<CartProductsOutputDto> cartProductsDtos){
        Double totalPrice = 0.0;
        for (CartProductsOutputDto productCartDto : cartProductsDtos) {
            totalPrice += productCartDto.getSubtotal();
        }
        return totalPrice;
    }

    /**
     * Method that handles the request to fetch a cart in the database and return an Dto with the relevant information.
     * @param id a Long with the id of the cart requested
     * @return an object of type CartOutputDto with all the information regarding the cart requested.
     */
    public CartOutputDto getCartById(Long id) {
        Optional<Cart> cart = cartRepository.findById(id);

        if (cart.isEmpty())
            throw new ResourceNotFoundException(String.format("Could not find valid cart for id %d", id));

        Customer customer = customerRepository.findById(cart.get().getCustomer().getId()).get();

        List<ProductCart> cartProducts = new ArrayList<>(cart.get().getProductCarts());

        List<CartProductsOutputDto> cartProductsDtos = createCartProductList(cartProducts);

        Double total = calculateCartTotal(cartProductsDtos);
        return CartOutputDto.builder()
                .customerName(customer.getName())
                .status(cart.get().getStatus())
                .date(cart.get().getDate())
                .products(cartProductsDtos)
                .total(total)
                .build();
    }
    /**
     * Method that calls the other methods of this class and persists the info of the carts on the database and returns the total price for the user.
     * @param id of type Long
     * @return an object of type UpdateStatusDto with an attribute message of type String.
     */
    public UpdateStatusDto updateStatusCart(Long id){
        Cart existCart = cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException("Cart not found with this id"));

        if(existCart.getStatus() == PurchaseOrderStatusEnum.FINISHED) throw new CartAlreadyFinishedException("Cart already Finished");

        existCart.setStatus(PurchaseOrderStatusEnum.FINISHED);

        cartRepository.save(existCart);

        return new UpdateStatusDto("Cart Finished successfully");

    }
}
