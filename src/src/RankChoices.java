import java.util.HashMap;

public class RankChoices {
    private String rankedChoiceName;
    private HashMap<String, String> rackChoiceCandidates;
    public RankChoices (String rankedChoiceName, HashMap<String, String> candidatesList) {
        this.rankedChoiceName = rankedChoiceName;
        this.rackChoiceCandidates = candidatesList;
    }

    public String getRankedChoiceName() {
        return rankedChoiceName;
    }

    public void setRankedChoiceName(String rankedChoiceName) {
        this.rankedChoiceName = rankedChoiceName;
    }

    public HashMap<String, String> getCandidatesList() {
        return rackChoiceCandidates;
    }

    public void setCandidatesList(HashMap<String, String> candidatesList) {
        this.rackChoiceCandidates = candidatesList;
    }
}
