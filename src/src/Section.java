import java.util.ArrayList;

public class Section {
    private String sectionName;
    private ArrayList<Contest> sectionContests;
    private ArrayList<Approval> sectionApprovals;
    private ArrayList<RankChoice> sectionRankChoices;
    private ArrayList<Proposition> sectionProposition;

    public ArrayList<Item> getSectionItems() {
        return sectionItems;
    }

    private ArrayList<Item> sectionItems;

    public Section(String sectionName, ArrayList<Item> items) {
        this.sectionName = sectionName;
        this.sectionItems = items;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public ArrayList<Contest> getSectionContests() {
        return sectionContests;
    }

    public void setSectionContests(ArrayList<Contest> sectionContests) {
        this.sectionContests = sectionContests;
    }

    public ArrayList<Approval> getSectionApprovals() {
        return sectionApprovals;
    }

    public void setSectionApprovals(ArrayList<Approval> sectionApprovals) {
        this.sectionApprovals = sectionApprovals;
    }

    public ArrayList<RankChoice> getSectionRankChoices() {
        return sectionRankChoices;
    }

    public void setSectionRankChoices(ArrayList<RankChoice> sectionRankChoices) {
        this.sectionRankChoices = sectionRankChoices;
    }

    public ArrayList<Proposition> getSectionProposition() {
        return sectionProposition;
    }

    public void setSectionProposition(ArrayList<Proposition> sectionProposition) {
        this.sectionProposition = sectionProposition;
    }
}