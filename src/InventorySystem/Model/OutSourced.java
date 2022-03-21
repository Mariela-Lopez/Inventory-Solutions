package InventorySystem.Model;

/**
 * OutSourced class
 */
public class OutSourced extends Part {

    /**
     * company name variable.
     */
    private String companyName;

    /**
     * constructor for the new instance of an Outsourced object.
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * getter for company name.
     * @return
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * setter for company name.
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

