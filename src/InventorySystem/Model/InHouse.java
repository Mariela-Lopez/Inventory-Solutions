package InventorySystem.Model;

/**
 * inHouse part class.
 */
public class InHouse extends Part {

    /**
     * variable for machine id.
     */
    private int machineId;

    /**
     * constructor for the new instance of an inhouse object.
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineId
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * getter that return machine id.
     * @return
     */
    public int getMachineId() {

        return machineId;
    }

    /**
     * setter that sets machine id.
     * @param machineId
     */
    public void setMachineId(int machineId) {

        this.machineId = machineId;
    }
}
