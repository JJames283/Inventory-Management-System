package project.c482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Joshua James
 * Student ID: 011101737
 * Date Completed: August 9, 2023
 * Program Used: Intellij IDEA Community Edition 2023.2
 */

/**
 * Inventory class. The Inventory class manages the inventory items (parts, products) actions.
 */
public class Inventory {
    // List that has all parts.
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    // List that has all products.
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    // Part index.
    public static int partIndex = 1;

    // Product index.
    public static int productIndex = 1000;

    /**
     * Add part to all parts list.
     * @param newPart new part to be added to all parts list.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);

        // Auto-generate part index.
        partIndex++;
    }

    /**
     * Add product to all products.
     * @param newProduct new product to be added to all products list.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);

        // Auto-generate product index.
        productIndex++;
    }

    /**
     * Search (lookup) part using part ID.
     * @param partId part ID.
     * @return part the matched part if part ID matches a part in the all parts list.
     * @return null if there are no part ID matches in the all parts list.
     */
    public static Part lookupPart(int partId) {
        ObservableList<Part> allParts = getAllParts();
        for (int i = 0; i < allParts.size(); i++) {
            Part part = allParts.get(i);
            if(part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * Search (lookup) product using product ID.
     * @param productId product ID.
     * @return product the matched product if product ID matches a product in the all products list.
     * @return null if there are no product ID matches in the all products list.
     */
    public static Product lookupProduct(int productId) {
        ObservableList<Product> allProducts = getAllProducts();
        for (int i = 0; i < allProducts.size(); i++) {
            Product product = allProducts.get(i);
            if(product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * Search (lookup) part using part name (full or partial).
     * @param partName part name.
     * @return part either a part or multiple parts, if search part name matches are found.
     * @return null if there are no search part name matches in the all parts list.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> searchParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = getAllParts();
        for(Part parts : allParts) {
            if (parts.getName().toLowerCase().contains(partName)) {
                searchParts.add(parts);
            }
        }
        return searchParts;

    }

    /**
     * Search (lookup) part using product name (full or partial).
     * @param productName product name.
     * @return searchProducts either a product or multiple products, if search product name matches are found in all products list.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> searchProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = getAllProducts();
        for(Product products : allProducts) {
            if (products.getName().toLowerCase().contains(productName)) {
                searchProducts.add(products);
            }
        }
        return searchProducts;

    }

    /**
     * Part is updated with modified data.
     * @param index part index.
     * @param selectedPart selected part.
     */
    public static void updatePart(int index, Part selectedPart) {
        for (int i = 0; i < allParts.size(); i++) {
            Part part = allParts.get(i);
            if (part.getId() == index) {
                getAllParts().set(i, selectedPart);
                return;
            }
        }
    }


    /**
     * Product is updated with modified data.
     * @param index product index.
     * @param newProduct new product.
     */
    public static void updateProduct(int index, Product newProduct) {
        for (int i = 0; i < allProducts.size(); i++) {
            Product product = allProducts.get(i);
            if (product.getId() == index) {
                getAllProducts().set(i, newProduct);
                return;
            }
        }
    }

    /**
     * Remove (delete) part.
     * @param selectedPart selected part to be removed (deleted).
     * @return all parts list with the selected part removed (deleted).
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }


    /**
     * Remove (delete) product.
     * @param selectedProduct selected product to be removed (deleted).
     * @return all products list with the selected product removed (deleted).
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * Return all parts.
     * @return all parts list.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }


    /**
     * Return all products.
     * @return all products list.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}