package project.c482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Joshua James
 *  * Student ID: 011101737
 *  * Date Completed: August 9, 2023
 *  * Program Used: Intellij IDEA Community Edition 2023.2
 */

/**
 * Product class. Product class defines and manages Products.
 */
public class Product {
    private final ObservableList<Part> productPart = FXCollections.observableArrayList();

    /**
     * ID of Product.
     */
    private int id;
    /**
     * Name of Product.
     */
    private String name;
    /**
     * Price of Product.
     */
    private double price;
    /**
     * Stock of Product (quantity, inventory).
     */
    private int stock;
    /**
     * Min (minimum) quantity of Product.
     */
    private int min;
    /**
     * Min (maximum) quantity of Product.
     */
    private int max;

    /**
     * Product constructor.
     * @param id Product id.
     * @param name Product name.
     * @param price Product price.
     * @param stock Product stock.
     * @param min Product min.
     * @param max Product max.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Get ID of Product.
     * @return product ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Get name of Product.
     * @return product name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get price of Product.
     * @return product price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Get stock of Product (quantity/how many).
     * @return product stock.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Get min (minimum) quantity of product.
     * @return product min.
     */
    public int getMin() {
        return min;
    }

    /**
     * Get max (maximum) quantity of product.
     * @return product max.
     */
    public int getMax() {
        return max;
    }

    /**
     * Set Product ID.
     * @param id product id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Set Product name.
     * @param name product name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set Product price.
     * @param price product price/cost.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Set Product name.
     * @param stock product stock (quantity).
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Set Product min (minimum).
     * @param min product min (minimum).
     */
    public void setMin(int min){
        this.min = min;
    }

    /**
     * Set Product max (maximum).
     * @param max product min (maximum).
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Delete (remove) associated part from a Product.
     * @param part part associated with a product.
     */
    public void deleteProductPart(Part part) {this.productPart.remove(part);}

    /**
     * Add (associate) a Part to Product.
     * @param part part to be added (associated) with product.
     */
    public void addProductPart(Part part) {
        productPart.add(part);
    }

    /**
     * Get all parts associated with product.
     * @return productPart all associated parts with a product.
     */
    public ObservableList<Part> getAllProductParts() {
        return productPart;
    }
}
