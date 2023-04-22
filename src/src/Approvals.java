import java.util.HashMap;

public class Approvals {
    private String approvalName;
    private HashMap<String,String> approvalCandidates;
    public Approvals (String approvalName, HashMap<String, String> candidatesList) {
        this.approvalName = approvalName;
        this.approvalCandidates = candidatesList;
    }

    public String getApprovalName() {
        return approvalName;
    }

    public void setApprovalName(String approvalName) {
        this.approvalName = approvalName;
    }

    public HashMap<String, String> getApprovalCandidates() {
        return approvalCandidates;
    }

    public void setApprovalCandidates(HashMap<String, String> approvalCandidates) {
        this.approvalCandidates = approvalCandidates;
    }
}
