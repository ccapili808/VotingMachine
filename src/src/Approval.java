public class Approval {
    private String approvalName;
    private Candidates approvalCandidates;
    public Approval (String approvalName, Candidates candidatesList) {
        this.approvalName = approvalName;
        this.approvalCandidates = candidatesList;
    }

    public String getApprovalName() {
        return approvalName;
    }

    public void setApprovalName(String approvalName) {
        this.approvalName = approvalName;
    }


    public Candidates getApprovalCandidates() {
        return approvalCandidates;
    }

    public void setApprovalCandidates(Candidates approvalCandidates) {
        this.approvalCandidates = approvalCandidates;
    }
}
