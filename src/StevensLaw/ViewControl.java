/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw;

import interaction.BasicInteraction;
import interaction.ExperimentInteraction;
import interaction.ExperimentInteractionEvent;
import interaction.ExperimentInteractionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Jul 17, 2011
 */
public class ViewControl implements ExperimentInteractionListener {

    private final View view;

    public ViewControl() {
        this.view = new View(); //Single Jframe where viewable portions of experiment will be rendered
    }

    @Override
    public void interactionOccured(ExperimentInteractionEvent ev) {
        //Experiment Interactions are all enum types... if it is not then an Exception will be thrown
    }
}
