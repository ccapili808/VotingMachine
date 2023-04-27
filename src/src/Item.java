public class Item {
    private String itemType;
    private String itemName;

    public String getItemType() {
        return itemType;
    }

    public String getItemName() {
        return itemName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public int getItemID() {
        return itemID;
    }

    private String sectionName;
    private int itemID;

    public Item (String itemType, String itemName, String sectionName, int itemID) {
        this.itemType = itemType;
        this.itemName = itemName;
        this.sectionName = sectionName;
        this.itemID = itemID;
    }
}