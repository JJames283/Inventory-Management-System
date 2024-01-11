package project.c482;

/**
 * @author Joshua James
 * Student ID: 011101737
 * Date Completed: August 9, 2023
 * Program Used: Intellij IDEA Community Edition 2023.2
 */

/**
 * Outsourced class extends Part class, and adds companyName attribute.
 */
public class Outsourced extends Part{
    private String companyName;

    /**
     * Constructor
     * @param id Part ID.
     * @param name Part name.
     * @param price Part price.
     * @param stock Part stock (quantity/inventory level).
     * @param min Part inventory min (minimum).
     * @param max Part inventory max (maximum).
     * @param companyName Part company name.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Get company name.
     * @return returns the company name of outsourced part.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Set company name.
     * @param companyName company name of outsourced part to be set.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}