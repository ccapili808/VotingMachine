import java.util.HashMap;
import java.util.List;

public class Approvals {
    private String approvalName;
    private List<Candidates> approvalCandidates;
    public Approvals (String approvalName, List<Candidates> candidatesList) {
        this.approvalName = approvalName;
        this.approvalCandidates = candidatesList;
    }

    public String getApprovalName() {
        return approvalName;
    }

    public void setApprovalName(String approvalName) {
        this.approvalName = approvalName;
    }

    public List<Candidates> getApprovalCandidates() {
        return approvalCandidates;
    }

    public void setApprovalCandidates(List<Candidates> approvalCandidates) {
        this.approvalCandidates = approvalCandidates;
    }
}
