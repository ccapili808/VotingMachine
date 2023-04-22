import java.util.ArrayList;

public class Section {
    private String sectionName;
    private ArrayList<Contest> sectionContests;
    private ArrayList<Approval> sectionApprovals;
    private ArrayList<RankChoice> sectionRankChoices;
    private ArrayList<Proposition> sectionProposition;
    public Section(String sectionName, ArrayList<Contest> sectionContests, ArrayList<Approval> sectionApprovals, ArrayList<RankChoice> sectionRankChoices, ArrayList<Proposition> sectionProposition) {
        this.sectionName = sectionName;
        this.sectionContests = sectionContests;
        this.sectionApprovals = sectionApprovals;
        this.sectionRankChoices = sectionRankChoices;
        this.sectionProposition = sectionProposition;
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