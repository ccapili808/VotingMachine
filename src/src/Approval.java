import java.util.HashMap;

public class Approval extends Item{
    private Candidates approvalCandidates;
    private HashMap<String, Boolean> selections = new HashMap<>();

    public Approval (String approvalName, Candidates candidatesList, int itemID, String sectionName, String itemType) {
        super(itemType, approvalName,sectionName,itemID);
        this.approvalCandidates = candidatesList;
        for (String itemCandidate: candidatesList.getCandidatesList().keySet()
        ) {
            selections.put(itemCandidate,false);
        }
    }

    public Candidates getApprovalCandidates() {
        return approvalCandidates;
    }

}
