package common.logging;

import experiment.Subject;
/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Oct 13, 2011
 */
public class SubjectFilenameBuilderImpl implements SubjectFilenameBuilder{
    @Override
    public String buildFileName(Subject person, String ending) {
        return person.getType().toString() + SubjectFilenameBuilder.delimiter + (Integer.toString(person.getNumber()) + SubjectFilenameBuilder.delimiter + person.getInitials() + SubjectFilenameBuilder.delimiter + ending);
    }

}
