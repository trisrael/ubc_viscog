/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package common.logging;

import experiment.Subject;
/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Oct 13, 2011
 */
public class SubjectFilenameBuilderImpl implements SubjectFilenameBuilder{
    @Override
    public String buildFileName(Subject person, String ending) {
        return (Integer.toString(person.getNumber()) + SubjectFilenameBuilder.delimiter + person.getInitials() + SubjectFilenameBuilder.delimiter + ending);
    }

}
