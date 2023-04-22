import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Storage {
    private Header headerInfo;
    private ArrayList<Section> electionSections = new ArrayList<>();
    public Storage() {
        try {
            parseSetUp();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };
    private void saveVote() {

    }
    private void parseSetUp() throws IOException {
        ProcessBuilder builder = new ProcessBuilder("ballot-parser.exe");
        builder.redirectInput(new File("example_ballot.txt"));
        builder.redirectOutput(new File("ballot.json"));
        builder.start();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new File("ballot.json"));

        //header object
        JsonNode headerNode = rootNode.get("header");
        String date = headerNode.get("date").asText();
        String instructions = headerNode.get("instructions").asText();
        String title = headerNode.get("title").asText();
        headerInfo = new Header(date,instructions,title);

        //section object
        JsonNode sectionsNode = rootNode.get("sections");
        for (JsonNode sectionNode : sectionsNode) {
            String sectionName = sectionNode.get("sectionName").asText();
            JsonNode itemsNode = sectionNode.get("items");
            ArrayList<Contest> sectionContests = new ArrayList<>();
            ArrayList<Proposition> sectionPropositions = new ArrayList<>();
            ArrayList<RankChoice> sectionRankChoices = new ArrayList<>();
            ArrayList<Approval> sectionApprovals = new ArrayList<>();
            //item objects
            for (JsonNode itemNode : itemsNode) {
                String tag = itemNode.get("tag").asText();

                switch (tag) {
                    case "Contest":
                        JsonNode contestNode = itemNode.get("contents");
                        String contestName = contestNode.get("contestName").asText();
                        boolean writeIn = contestNode.get("writeIn").asBoolean();
                        JsonNode candidatesNode = contestNode.get("candidates");
                        HashMap<String,String> contestCandidates = new HashMap<>();
                        for (JsonNode candidateNode : candidatesNode) {
                            String name = candidateNode.get("name").asText();
                            String party = candidateNode.get("party").asText();

                            // Do something with candidate information
                            contestCandidates.put(name,party);

                        }
                        Candidates candidatesC = new Candidates(contestCandidates);
                        Contest contest = new Contest(contestName, candidatesC, writeIn);
                        // Add contest to section list
                        sectionContests.add(contest);
                        break;

                    case "Proposition":
                        JsonNode propNode = itemNode.get("contents");
                        String propName = propNode.get("propName").asText();
                        String propDescription = propNode.get("propDescription").asText();
                        JsonNode optionsNode = propNode.get("options");
                        ArrayList<String> optionsList = new ArrayList<>();
                        for (JsonNode optionNode : optionsNode) {
                            String option = optionNode.asText();

                            // Add option to list
                            optionsList.add(option);
                        }
                        Proposition proposition = new Proposition(propDescription, propName, optionsList);
                        // Add proposition to section list
                        sectionPropositions.add(proposition);
                        break;

                    case "RankedChoice":
                        JsonNode rcNode = itemNode.get("contents");
                        String rcName = rcNode.get("rankedChoiceName").asText();
                        JsonNode rcChoicesNode = rcNode.get("rankedChoices");
                        HashMap<String,String> rankedCandidates = new HashMap<>();
                        for (JsonNode rcChoiceNode : rcChoicesNode) {
                            JsonNode rcCandidateNode = rcChoiceNode.get("contents");
                            String rcCandidateName = rcCandidateNode.get("name").asText();
                            String rcCandidateParty = rcCandidateNode.get("party").asText();

                            // Add candidate information to this ranked choice object
                            rankedCandidates.put(rcCandidateName,rcCandidateParty);
                        }
                        Candidates candidatesR = new Candidates(rankedCandidates);
                        RankChoice rankChoice = new RankChoice(rcName, candidatesR);

                        // Add ranked choice to section list
                        sectionRankChoices.add(rankChoice);
                        break;

                    case "Approval":
                        JsonNode appNode = itemNode.get("contents");
                        String appName = appNode.get("approvalName").asText();
                        JsonNode approvalsNode = appNode.get("approvals");
                        HashMap<String,String> approvalCandidates = new HashMap<>();
                        for (JsonNode approvalNode : approvalsNode) {
                            JsonNode approvalCandidateNode = approvalNode.get("contents");
                            String approvalCandidateName = approvalCandidateNode.get("name").asText();
                            String approvalCandidateParty = approvalCandidateNode.get("party").asText();

                            // Add candidate information to this approval object
                            approvalCandidates.put(approvalCandidateName,approvalCandidateParty);
                        }
                        Candidates candidatesA = new Candidates(approvalCandidates);
                        Approval approval = new Approval(appName, candidatesA);

                        // Add approval to section list
                        sectionApprovals.add(approval);
                        break;

                    default:
                        // Handle unknown tag value
                        System.out.println("Wrong content tag in json file");
                        break;
                }
            }

            // Create Section
            Section section = new Section(sectionName, sectionContests, sectionApprovals, sectionRankChoices, sectionPropositions);
            electionSections.add(section);
        }
    }
    private void countVote() {

    }

    private void encrypt() {

    }

    private void decrypt() {

    }

    public boolean validPriStorage() {
        return true;
    }

    public boolean validBackStorage() {
        return true;
    }

    public boolean validSetupStorage() {
        return true;
    }

    public Header getHeaderInfo() {
        return headerInfo;
    }

    public ArrayList<Section> getElectionSections() {
        return electionSections;
    }
}
