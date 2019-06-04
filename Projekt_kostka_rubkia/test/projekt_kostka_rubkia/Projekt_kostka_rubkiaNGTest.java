/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_kostka_rubkia;

import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.vecmath.Color3f;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Patryk
 */
public class Projekt_kostka_rubkiaNGTest {
    
    public Projekt_kostka_rubkiaNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of main method, of class Projekt_kostka_rubkia.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Projekt_kostka_rubkia.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of init method, of class Projekt_kostka_rubkia.
     */
    @Test
    public void testInit() {
        System.out.println("init");
        Projekt_kostka_rubkia instance = new Projekt_kostka_rubkia();
        instance.init();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of positionViewer method, of class Projekt_kostka_rubkia.
     */
    @Test
    public void testPositionViewer() {
        System.out.println("positionViewer");
        Projekt_kostka_rubkia instance = new Projekt_kostka_rubkia();
        instance.positionViewer();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getScene method, of class Projekt_kostka_rubkia.
     */
    @Test
    public void testGetScene() {
        System.out.println("getScene");
        float xpos = 0.0F;
        float ypos = 0.0F;
        float zpos = 0.0F;
        Projekt_kostka_rubkia instance = new Projekt_kostka_rubkia();
        instance.getScene(xpos, ypos, zpos);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addLights method, of class Projekt_kostka_rubkia.
     */
    @Test
    public void testAddLights() {
        System.out.println("addLights");
        BranchGroup group = null;
        Projekt_kostka_rubkia.addLights(group);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAppearance method, of class Projekt_kostka_rubkia.
     */
    @Test
    public void testGetAppearance_Color() {
        System.out.println("getAppearance");
        Color color = null;
        Appearance expResult = null;
        Appearance result = Projekt_kostka_rubkia.getAppearance(color);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAppearance method, of class Projekt_kostka_rubkia.
     */
    @Test
    public void testGetAppearance_Color3f() {
        System.out.println("getAppearance");
        Color3f color = null;
        Appearance expResult = null;
        Appearance result = Projekt_kostka_rubkia.getAppearance(color);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mouseClicked method, of class Projekt_kostka_rubkia.
     */
    @Test
    public void testMouseClicked() {
        System.out.println("mouseClicked");
        MouseEvent arg0 = null;
        Projekt_kostka_rubkia instance = new Projekt_kostka_rubkia();
        instance.mouseClicked(arg0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mouseEntered method, of class Projekt_kostka_rubkia.
     */
    @Test
    public void testMouseEntered() {
        System.out.println("mouseEntered");
        MouseEvent arg0 = null;
        Projekt_kostka_rubkia instance = new Projekt_kostka_rubkia();
        instance.mouseEntered(arg0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mouseExited method, of class Projekt_kostka_rubkia.
     */
    @Test
    public void testMouseExited() {
        System.out.println("mouseExited");
        MouseEvent arg0 = null;
        Projekt_kostka_rubkia instance = new Projekt_kostka_rubkia();
        instance.mouseExited(arg0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mousePressed method, of class Projekt_kostka_rubkia.
     */
    @Test
    public void testMousePressed() {
        System.out.println("mousePressed");
        MouseEvent event = null;
        Projekt_kostka_rubkia instance = new Projekt_kostka_rubkia();
        instance.mousePressed(event);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mouseReleased method, of class Projekt_kostka_rubkia.
     */
    @Test
    public void testMouseReleased() {
        System.out.println("mouseReleased");
        MouseEvent arg0 = null;
        Projekt_kostka_rubkia instance = new Projekt_kostka_rubkia();
        instance.mouseReleased(arg0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mouseDragged method, of class Projekt_kostka_rubkia.
     */
    @Test
    public void testMouseDragged() {
        System.out.println("mouseDragged");
        MouseEvent event = null;
        Projekt_kostka_rubkia instance = new Projekt_kostka_rubkia();
        instance.mouseDragged(event);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mouseMoved method, of class Projekt_kostka_rubkia.
     */
    @Test
    public void testMouseMoved() {
        System.out.println("mouseMoved");
        MouseEvent arg0 = null;
        Projekt_kostka_rubkia instance = new Projekt_kostka_rubkia();
        instance.mouseMoved(arg0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
