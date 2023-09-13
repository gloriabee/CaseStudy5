package se233.chapter5;

import javafx.embed.swing.JFXPanel;
import javafx.scene.input.KeyCode;
import org.junit.Before;
import org.junit.Test;
import se233.chapter5.controller.DrawingLoop;
import se233.chapter5.controller.GameLoop;
import se233.chapter5.model.Character;
import se233.chapter5.view.Platform;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CharacterTest {
    private Character floatingCharacter;
    private ArrayList<Character> characerListUnderTest;
    private Platform platformUnderTest;
    private GameLoop gameLoopUnderTest;
    private DrawingLoop drawingLoopUnderTest;
    private Method updateMethod,redrawMethod;

    @Before
    public void setUp(){
        JFXPanel jfxPanel=new JFXPanel();
        floatingCharacter=new Character(30,30,0,0,KeyCode.A,KeyCode.D,KeyCode.W);
        characerListUnderTest=new ArrayList<Character>();
        characerListUnderTest.add(floatingCharacter);
        platformUnderTest=new Platform();
        gameLoopUnderTest=new GameLoop(platformUnderTest);
        try{
            updateMethod=GameLoop.class.getDeclaredMethod("update",ArrayList.class);
            redrawMethod=DrawingLoop.class.getDeclaredMethod("paint", ArrayList.class);
            updateMethod.setAccessible(true);
            redrawMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            updateMethod=null;
            redrawMethod=null;
        }

    }
    @Test
    public void characterInitialValuesShouldMatchConstructorArguments(){
        assertEquals("Initial x",30,floatingCharacter.getX(),0);
        assertEquals("Initial y",30,floatingCharacter.getY(),0);
        assertEquals("Offset x",0,floatingCharacter.getOffsetX(),0.0);
        assertEquals("Offset y",0,floatingCharacter.getOffsetY(),0.0);
        assertEquals("Left key",KeyCode.A,floatingCharacter.getLeftKey());
        assertEquals("Right key",KeyCode.D,floatingCharacter.getRightKey());
        assertEquals("Up Key",KeyCode.W,floatingCharacter.getUpKey());
    }

    public void characterShouldMoveToTheLeftAfterTheLeftKeyIsPressed() throws IllegalAccessException, InvocationTargetException,NoSuchFieldException{
        Character characterUnderTest=characerListUnderTest.get(0);
        int startX=characterUnderTest.getX();
        platformUnderTest.getKeys().add(KeyCode.A);
        updateMethod.invoke(gameLoopUnderTest,characerListUnderTest);
        redrawMethod.invoke(drawingLoopUnderTest,characerListUnderTest);
        Field isMoveLeft=characterUnderTest.getClass().getDeclaredField("isMoveLeft");
        isMoveLeft.setAccessible(true);
        assertTrue("Controler:Left key pressing is acknowledged",platformUnderTest.getKeys().isPressed(KeyCode.A));
        assertTrue("Model: Character moving left state is set",isMoveLeft.getBoolean(characterUnderTest));
        assertTrue("View: Character is moving left",characterUnderTest.getX()<startX);

    }

}
