package screens;


import java.awt.Color;

public class TestCorrectScreen extends SimpleTextScreen{

   @Override
    public String text() {
       return "Correct";
    }

    @Override
    public Color fontColor() {
       return Color.GREEN;
    }

}
