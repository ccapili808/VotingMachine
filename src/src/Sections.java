import java.util.List;

public class Sections {
    private String sectionName;
    private List<Contest> sectionContests;
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
