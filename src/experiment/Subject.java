package experiment;

import common.logging.SubjectFilenameBuilder;
import common.logging.SubjectFilenameBuilderImpl;
import experiment.ExperimentType;

/**
 * An experiment subject has both initials and a number
 * @author Tristan Goffman(tgoffman@gmail.com) Oct 12, 2011
 */
public class Subject {
    private int number;
    private SubjectFilenameBuilder builder;
    private ExperimentType type;

    public ExperimentType getType() {
        return type;
    }

    private void setType(ExperimentType type) {
        this.type = type;
    }

    public SubjectFilenameBuilder getBuilder() {
        return builder;
    }

    private void setBuilder(SubjectFilenameBuilder builder) {
        this.builder = builder;
    }

    public Subject(int number, String initials, ExperimentType type){
     this(number, initials, type, null);    
    }
    
    public Subject(int number, String initials, ExperimentType type, SubjectFilenameBuilder builder) {
        setNumber(number);
        
        setInitials(initials);
        setType(type);
        if(builder == null){
            setBuilder(new SubjectFilenameBuilderImpl());
        }
    }

    public String getInitials() {
        return initials;
    }

    private void setInitials(String initials) {
        this.initials = initials;
    }

    public int getNumber() {
        return number;
    }

    private void setNumber(int number) {
        this.number = number;
    }
    private String initials;
    
    
}
