public class RankChoice extends Item{
    private Candidates rackChoiceCandidates;
    public RankChoice(String rankedChoiceName, Candidates candidatesList, int itemID, String sectionName, String itemType) {
        super(itemType, rankedChoiceName, sectionName, itemID);
        this.rackChoiceCandidates = candidatesList;
    }

    public Candidates getRackChoiceCandidates() {
        return rackChoiceCandidates;
    }
}
