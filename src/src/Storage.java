import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class Storage {
    public Storage() {};
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

        //section object
        JsonNode sectionsNode = rootNode.get("sections");
        for (JsonNode sectionNode : sectionsNode) {
            String sectionName = sectionNode.get("sectionName").asText();
            JsonNode itemsNode = sectionNode.get("items");

            //item objects
            for (JsonNode itemNode : itemsNode) {
                String tag = itemNode.get("tag").asText();

                switch (tag) {
                    case "Contest":
                        JsonNode contestNode = itemNode.get("contents");
                        String contestName = contestNode.get("contestName").asText();
                        boolean writeIn = contestNode.get("writeIn").asBoolean();
                        JsonNode candidatesNode = contestNode.get("candidates");

                        for (JsonNode candidateNode : candidatesNode) {
                            String name = candidateNode.get("name").asText();
                            String party = candidateNode.get("party").asText();

                            // Do something with candidate information
                        }

                        // Do something with contest information
                        break;

                    case "Proposition":
                        JsonNode propNode = itemNode.get("contents");
                        String propName = propNode.get("propName").asText();
                        String propDescription = propNode.get("propDescription").asText();
                        JsonNode optionsNode = propNode.get("options");

                        for (JsonNode optionNode : optionsNode) {
                            String option = optionNode.asText();

                            // Do something with option information
                        }

                        // Do something with proposition information
                        break;

                    case "RankedChoice":
                        JsonNode rcNode = itemNode.get("contents");
                        String rcName = rcNode.get("rankedChoiceName").asText();
                        JsonNode rcChoicesNode = rcNode.get("rankedChoices");

                        for (JsonNode rcChoiceNode : rcChoicesNode) {
                            JsonNode rcCandidateNode = rcChoiceNode.get("contents");
                            String rcCandidateName = rcCandidateNode.get("name").asText();
                            String rcCandidateParty = rcCandidateNode.get("party").asText();

                            // Do something with ranked choice candidate information
                        }

                        // Do something with ranked choice information
                        break;

                    case "Approval":
                        JsonNode appNode = itemNode.get("contents");
                        String appName = appNode.get("approvalName").asText();
                        JsonNode approvalsNode = appNode.get("approvals");

                        for (JsonNode approvalNode : approvalsNode) {
                            JsonNode approvalCandidateNode = approvalNode.get("contents");
                            String approvalCandidateName = approvalCandidateNode.get("name").asText();
                            String approvalCandidateParty = approvalCandidateNode.get("party").asText();

                            // Do something with approval candidate information
                        }

                        // Do something with approval information
                        break;

                    default:
                        // Handle unknown tag value
                        break;
                }
            }

            // Do something with section information
        }
    }
    private void countVote() {

    }
}
