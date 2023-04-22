public class Proposition {
    private String propDescription;
    private String propName;
    private String option;
    public Proposition (String propDescription, String propName, String option) {
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

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
