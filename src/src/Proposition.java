import java.util.List;

public class Proposition {
    /**
     * For the proposition type of votes
     */
    private String propDescription;
    private String propName;
    private List<String> option;
    public Proposition (String propDescription, String propName, List<String> option) {
        this.propDescription = propDescription;
        this.propName = propName;
        this.option = option;
    }

    public String getPropDescription() {
        return propDescription;
    }

    public void setPropDescription(String propDescription) {
        this.propDescription = propDescription;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public List<String> getOption() {
        return option;
    }

    public void setOption(List<String> option) {
        this.option = option;
    }
}
