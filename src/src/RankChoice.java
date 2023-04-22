public class RankChoice {
    private String rankedChoiceName;
    private Candidates rackChoiceCandidates;
    public RankChoice(String rankedChoiceName, Candidates candidatesList) {
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
