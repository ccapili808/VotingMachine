import java.util.HashMap;
import java.util.List;

public class RankChoices {
    private String rankedChoiceName;
    private Candidates rackChoiceCandidates;
    public RankChoices (String rankedChoiceName, Candidates candidatesList) {
        this.rankedChoiceName = rankedChoiceName;
        this.rackChoiceCandidates = candidatesList;
    }

    public String getRankedChoiceName() {
        return rankedChoiceName;
    }

    public void setRankedChoiceName(String rankedChoiceName) {
        this.rankedChoiceName = rankedChoiceName;
    }


    public Candidates getRackChoiceCandidates() {
        return rackChoiceCandidates;
    }

    public void setRackChoiceCandidates(Candidates rackChoiceCandidates) {
        this.rackChoiceCandidates = rackChoiceCandidates;
    }
}
