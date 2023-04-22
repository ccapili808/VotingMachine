import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Sections {
    private String sectionName;
    private ArrayList<Contest> sectionContests;
    private ArrayList<Approval> sectionApprovals;
    private ArrayList<RankChoices> sectionRankChoices;
    private ArrayList<Proposition> sectionProposition;
    public Sections (String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
}
