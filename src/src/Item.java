import java.util.HashMap;
import java.util.List;

public class Item {
    private String itemType;
    private String itemName;
    private String sectionName;
    private int itemID;

    /**
     * This method returns the item type (Contest/Proposition)
     * @return the item type
     */
    public String getItemType() {
        return itemType;
    }

    /**
     * This method returns the item name
     * @return
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * This method returns the section name that the item is in
     * @return the section name
     */
    public String getSectionName() {
        return sectionName;
    }

    /**
     * This method returns the item's ID. This is useful to navigate prompts by id
     * @return
     */
    public int getItemID() {
        return itemID;
    }

    /**
     * This method is overriden in each subclass to get the user's selections from an item.
     * @return
     */
    public String getSelection() {
        return null;
    }

    /**
     * This method is overriden in each subclass to set the user's selection for an item
     * @param name the selection string
     * @param writeIn if the selection is the write in
     */
    public void setSelection(String name, boolean writeIn) {
    }

    /**
     * This method is overriden in each subclass to reset the selections after each voting session
     */
    public void resetSelections() {
    }

    /**
     * This method is overriden in Proposition
     * @return
     */
    public List<String> getPropOptions() {
        return null;
    }

    /**
     * This method is overriden in Contest
     * @return
     */
    public HashMap<String,String> getContestOptions() {
        return null;
    }

    /**
     * This method is overriden in Contest
     * @return
     */
    public boolean isWriteIn() {
        return false;
    }

    /**
     * This method is overriden in Proposition, since it's the only item with a description
     * return an empty string otherwise
     * @return the description string
     */
    public String getDescription() {
        return "";
    }

    /**
     * Constructor for superclass Item. Used in storage when parsing json.
     * @param itemType
     * @param itemName
     * @param sectionName
     * @param itemID
     */
    public Item (String itemType, String itemName, String sectionName, int itemID) {
        this.itemType = itemType;
        this.itemName = itemName;
        this.sectionName = sectionName;
        this.itemID = itemID;
    }
}
