package interaction;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 8, 2011
 */
public enum PartInteraction implements ExperimentInteraction {
    Complete, //denotes when a part is complete
    Error //if some error occurred in a part NOTE: Not used for now, maybe simple throw exceptions if this occurs
}
