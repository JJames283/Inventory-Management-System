package project.c482;

/**
 * @author Joshua James
 * Student ID: 011101737
 * Date Completed: August 9, 2023
 * Program Used: Intellij IDEA Community Edition 2023.2
 */

/**
 * InHouse class extends Part, and adds machineId attribute.
 */
public class InHouse extends Part{
    private int machineId;

    /**
     * Constructor for manufactured part.
     * @param id Part ID.
     * @param name Part name.
     * @param price Part price.
     * @param stock Part stock (quantity).
     * @param min Part inventory minimum.
     * @param max Part inventory maximum.
     * @param machineId Part machine ID.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Get (return) Machine ID of InHouse Part.
     * @return machineId.
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * Set Machine ID of InHouse part.
     * @param machineId Machine ID.
     */
    public void setMachineId(int machineId) {

        this.machineId = machineId;
    }
}
