package StevensLevel;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Aug 3, 2011
 */
public enum State {
    WAITING, //Waiting for specific user interaction
    IN_TRANSITION,
    IN_PROGRESS, //During a task, will be in this state
    COMPLETE
}
