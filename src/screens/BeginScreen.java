package screens;


import java.awt.Color;




public class BeginScreen extends SimpleTextScreen{
   

    @Override
    public String text() {
        return "Please press spacebar to begin.";
    }

    @Override
    public Color fontColor() {
        return Color.BLACK;
    }

}
