import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

public class Storage {
    private Header headerInfo;
    private List<Section> electionSections = new ArrayList<Section>();
    private static final String password = "123456789";
    private static final String salt = "abcdefgh";

    private HashMap<String, HashMap<String,Integer>> voteTotals = new HashMap<>();

    /**
     * Constructor for Storage. calls parseSetUp();
     */
    public Storage() {
        try {
            File myObj = new File("voteResults.txt");
            if (myObj.createNewFile()) {
                //System.out.println("File created: " + myObj.getName());
            } else {
                //System.out.println("File already exists.");
            }
        } catch (IOException e) {
            //System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            parseSetUp();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

    /**
     * This method encrypts and saves an individual voting sessions selections.
     * The vote includes a date and time stamp at the top, and voting sessions are split
     * by an empty line.
     */
    public void saveVote() {
        try {
            File myObj = new File("allVotes.txt");
            if (myObj.createNewFile()) {
                //System.out.println("File created: " + myObj.getName());
            } else {
                //System.out.println("File already exists.");
            }
        } catch (IOException e) {
            //System.out.println("An error occurred.");
            e.printStackTrace();
        }
//        for (Section section: Main.getBallot()
//             ) {
//            for (Item item: section.getSectionItems()
//                 ) {
//                if(item.getItemName().equals("Governor")) {
//                    item.setSelection("Steven Johnson", false);
//                }
//            }
//        }
        try(FileWriter fw = new FileWriter("allVotes.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            out.println(encrypt(dtf.format(now)));
            for (Section section: Main.getBallot()
            ) {
                for (Item item: section.getSectionItems()
                ) {
                    if(item.getItemType().equals("Proposition") || item.getItemType().equals("Contest")) {
                        if(item.getSelection() != null) {
                            out.println(encrypt(item.getItemName() + ": " + item.getSelection()));
                        }
                    }
                }
            }
            out.println();
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
        countVote();

    }

    /**
     * This method runs ballot-parser.exe to parse example_ballot.txt and create our setup json file.
     * It then parses the json file and organizes the ballot sections and items.
     * Additionally, it reads voteResults.txt to get the voting results up to this point in case
     * there was a power outage.
     * @throws IOException
     */
    private void parseSetUp() throws IOException {
        ProcessBuilder builder = new ProcessBuilder("ballot-parser.exe");
        builder.redirectInput(new File("example_ballot.txt"));
        builder.redirectOutput(new File("ballot.json"));
        builder.start();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new File("ballot.json"));
        int i = 1;
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
            ArrayList<Item> items = new ArrayList<>();
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
                        Contest contest = new Contest(contestName, candidatesC, writeIn, i, sectionName, tag);
                        i++;
                        // Add contest to section list
                        items.add(contest);
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
                        Proposition proposition = new Proposition(propDescription, propName, optionsList, i, sectionName,tag);
                        i++;
                        // Add proposition to section list
                        items.add(proposition);
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
                        RankChoice rankChoice = new RankChoice(rcName, candidatesR,i, sectionName, tag);
                        i++;

                        // Add ranked choice to section list
                        items.add(rankChoice);
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
                        Approval approval = new Approval(appName, candidatesA,i,sectionName,tag);
                        i++;

                        // Add approval to section list
                        items.add(approval);
                        break;

                    default:
                        // Handle unknown tag value
                        System.out.println("Wrong content tag in json file");
                        break;
                }

            }
            // Create Section
            Section section = new Section(sectionName, items);
            electionSections.add(section);
        }
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("voteResults.txt"));
            String line = reader.readLine();
            HashMap<String,Integer> itemVotes = new HashMap<>();
            String itemName = "";
            while (line != null) {
                line=decryptString(line);
                if (line.contains(":")) {
                    itemVotes = new HashMap<>();
                    itemName = line.split(":")[0];
                }
                else if (line.contains(" ")) {
                    String[] itemLine = line.split(" ");
                    itemVotes.put(itemLine[0], Integer.parseInt(itemLine[1]));
                }
                else if (line.isBlank()) {
                    voteTotals.put(itemName, itemVotes);
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method adds the current voting session's selections to the total vote counts, encrypts them and
     * saves them to voteResults.txt
     */
    public void countVote() {
        for (Section section: Main.getBallot()
             ) {
            for (Item item: section.getSectionItems()
                 ) {
                if (item.getSelection() != null) {
                    if (voteTotals.containsKey(item.getItemName())) {
                        if (voteTotals.get(item.getItemName()).containsKey(item.getSelection())) {
                            int prevVote = voteTotals.get(item.getItemName()).get(item.getSelection());
                            voteTotals.get(item.getItemName()).put(item.getSelection(), prevVote + 1);
                        } else {
                            voteTotals.get(item.getItemName()).put(item.getSelection(), 1);
                        }
                    } else {
                        HashMap<String, Integer> temp = new HashMap<String, Integer>();
                        voteTotals.put(item.getItemName(), temp);
                        voteTotals.get(item.getItemName()).put(item.getSelection(), 1);
                    }
                }
            }
        }
        try {
            File myObj = new File("voteResults.txt");
            if (myObj.createNewFile()) {
                //System.out.println("File created: " + myObj.getName());
            } else {
                //System.out.println("File already exists.");
                myObj.delete();
                myObj.createNewFile();
            }
            BufferedReader reader;
            try(FileWriter fw = new FileWriter("voteResults.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw))
            {
                for (String itemName: voteTotals.keySet()
                     ) {
                    out.println(encrypt(itemName+":"));
                    for (String option: voteTotals.get(itemName).keySet()
                         ) {
                        out.println(encrypt(option + " " + voteTotals.get(itemName).get(option)));
                    }
                    out.println();
                }
            } catch (IOException e) {
                //exception handling left as an exercise for the reader
            }
        } catch (IOException e) {
            //System.out.println("An error occurred.");
            e.printStackTrace();
        }
        /**
         * This decrypts the results for showcasing the encryption/decryption and that
         * the count is working properly.
         */
        try {
            File myObj = new File("voteResultsDecrypted.txt");
            if (myObj.createNewFile()) {
                //System.out.println("File created: " + myObj.getName());
            } else {
                //System.out.println("Overwriting file");
                myObj.delete();
                myObj.createNewFile();
            }
            BufferedReader reader;
            try(FileWriter fw = new FileWriter("voteResultsDecrypted.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw))
            {
                try {
                    reader = new BufferedReader(new FileReader("voteResults.txt"));
                    String line = reader.readLine();

                    while (line != null) {
                        //System.out.println(line);
                        //System.out.println(decryptString(line));
                        out.println(decryptString(line));
                        // read next line
                        line = reader.readLine();
                    }

                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                //exception handling left as an exercise for the reader
            }
        } catch (IOException e) {
            //System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    /**
     * This method encrypts a string using AES and a password.
     * The encryption/decryption password for each machine should be provided
     * to election officials
     * @param strToEncrypt the string to encrypt
     * @return the encrypted string
     */
    private String encrypt(String strToEncrypt) {
        try
        {
            /* Declare a byte array. */
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            /* Create factory for secret keys. */
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            /* PBEKeySpec class implements KeySpec interface. */
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            /* Retruns encrypted value. */
            return Base64.getEncoder()
                    .encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        }
        catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e)
        {
            //System.out.println("Error occured during encryption: " + e.toString());
        }
        return null;
    }

    /**
     * This method decrypts the encrypted vote file to "decryptedVotes.txt" for auditing purposes.
     */
    public void decrypt() {
        try {
            File myObj = new File("decryptedVotes.txt");
            if (myObj.createNewFile()) {
                //System.out.println("File created: " + myObj.getName());
            } else {
                //System.out.println("Overwriting file");
                myObj.delete();
                myObj.createNewFile();
            }
            BufferedReader reader;
            try(FileWriter fw = new FileWriter("decryptedVotes.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw))
            {
                try {
                    reader = new BufferedReader(new FileReader("allVotes.txt"));
                    String line = reader.readLine();

                    while (line != null) {
                        //System.out.println(line);
                        //System.out.println(decryptString(line));
                        out.println(decryptString(line));
                        // read next line
                        line = reader.readLine();
                    }

                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                //exception handling left as an exercise for the reader
            }
        } catch (IOException e) {
            //System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * This method decrypts a given string using the decryption password. The password
     * for each voting machine should be given to election officials.
     * @param strToDecrypt
     * @return
     */
    public String decryptString(String strToDecrypt) {
        try
        {
            /* Declare a byte array. */
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            /* Create factory for secret keys. */
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            /* PBEKeySpec class implements KeySpec interface. */
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            /* Retruns decrypted value. */
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));

        }
        catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e)
        {
            //System.out.println("Error occured during decryption: " + e.toString());
        }
        return null;
    }

    /**
     * This method just returns true for the demo
     * @return true
     */
    public boolean validPriStorage() {
        return true;
    }

    /**
     * This method just returns true for the demo
     * @return true
     */
    public boolean validBackStorage() {
        return true;
    }

    /**
     * This method just returns true for the demo
     * @return true
     */
    public boolean validSetupStorage() {
        return true;
    }

    /**
     * This method returns the header info for the election
     * @return
     */
    public Header getHeaderInfo() {
        return headerInfo;
    }

    /**
     * This method returns the election's sections
     * @return the list of sections
     */
    public List<Section> getElectionSections() {
        return electionSections;
    }
}
