import java.util.HashMap;
import java.util.List;

public class RankChoices {
    private String rankedChoiceName;
    private List<Candidates> rackChoiceCandidates;
    public RankChoices (String rankedChoiceName, List<Candidates> candidatesList) {
        this.rankedChoiceName = rankedChoiceName;
        this.rackChoiceCandidates = candidatesList;
    }

    public String getRankedChoiceName() {
        return rankedChoiceName;
    }

    public void setRankedChoiceName(String rankedChoiceName) {
        this.rankedChoiceName = rankedChoiceName;
    }

    public List<Candidates> getCandidatesList() {
        return rackChoiceCandidates;
    }

    public void setCandidatesList(List<Candidates> candidatesList) {
        this.rackChoiceCandidates = candidatesList;
    }
}
