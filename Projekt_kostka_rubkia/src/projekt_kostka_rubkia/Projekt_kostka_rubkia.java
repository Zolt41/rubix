package projekt_kostka_rubkia;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.pickfast.PickCanvas;
import com.sun.j3d.utils.universe.*;
import static java.lang.Math.sqrt;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

class kostka_rubkia extends Applet implements KeyListener {

    static GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
    static Canvas3D canvas = new Canvas3D(config);
    static SimpleUniverse universe = new SimpleUniverse(canvas);

    TransformGroup Transformacja_scianaLewa;
    TransformGroup Transformacja_scianaSrodkowaOdOsiX;
    TransformGroup Transformacja_scianaSrodkowaOdOsiY;
    TransformGroup Transformacja_scianaSrodkowaOdOsiZ;
    TransformGroup Transformacja_scianaPrawa;
    TransformGroup Transformacja_scianaPrzod;
    TransformGroup Transformacja_scianaTyl;
    TransformGroup Transformacja_scianaGora;
    TransformGroup Transformacja_scianaDol;
    TransformGroup transformGl; // główny TG

    Transform3D obrot1 = new Transform3D();
    Transform3D obrot2 = new Transform3D();
    Transform3D ustaw_obrot2 = new Transform3D();
    private Box box;
    private Box BoxThatWillBeUsed[][][];
    private BranchGroup group = new BranchGroup();
    private PickCanvas pickCanvas;
    private TransformGroup boxTransformGroup;
    // private TransformGroup boxTransformGroup1;
    // private TransformGroup boxTransformGroup2;
    private Matrix4d matrix = new Matrix4d();

    public void init() {
        startDrawing();
    }

    private void startDrawing() {
        BoxThatWillBeUsed = new Box[3][3][3];

        setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        canvas = new Canvas3D(config);
        universe = new SimpleUniverse(canvas);
        add("Center", canvas);
        positionViewer();
        addLights(group);

        // Transformacja_scianaSrodkowaOdOsiX = new TransformGroup();
        // Transformacja_scianaSrodkowaOdOsiX.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        // Transformacja_scianaPrawa = new TransformGroup();
        // Transformacja_scianaPrawa.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        // Transformacja_scianaLewa = new TransformGroup();
        // Transformacja_scianaLewa.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        // Transformacja_scianaSrodkowaOdOsiY = new TransformGroup();
        // Transformacja_scianaSrodkowaOdOsiY.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        // Transformacja_scianaGora = new TransformGroup();
        // Transformacja_scianaGora.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        // Transformacja_scianaDol = new TransformGroup();
        // Transformacja_scianaDol.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        // Transformacja_scianaSrodkowaOdOsiZ = new TransformGroup();
        // Transformacja_scianaSrodkowaOdOsiZ.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        // Transformacja_scianaPrzod = new TransformGroup();
        // Transformacja_scianaPrzod.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        // Transformacja_scianaTyl = new TransformGroup();
        // Transformacja_scianaTyl.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        MakeCube1();

        Transformacja_scianaSrodkowaOdOsiX.addChild(Transformacja_scianaSrodkowaOdOsiY);
        Transformacja_scianaSrodkowaOdOsiY.addChild(Transformacja_scianaSrodkowaOdOsiZ);
        Transformacja_scianaPrawa.addChild(Transformacja_scianaGora);
        Transformacja_scianaGora.addChild(Transformacja_scianaPrzod);
        Transformacja_scianaLewa.addChild(Transformacja_scianaDol);
        Transformacja_scianaDol.addChild(Transformacja_scianaTyl);
        boxTransformGroup.removeChild(box);
        group.addChild(Transformacja_scianaSrodkowaOdOsiX);
        group.addChild(Transformacja_scianaPrawa);
        group.addChild(Transformacja_scianaLewa);
        universe.addBranchGraph(group);

        pickCanvas = new PickCanvas(canvas, group);

        pickCanvas.setMode(PickInfo.PICK_BOUNDS);
        canvas.addKeyListener(this);

    }

    public void MakeCube1() {
        int xpos = 0, ypos = 0, zpos = 0;
        for (float x = 0; x < 3; x++) {
            for (float y = 0; y < 3; y++) {
                for (float z = 0; z < 3; z++) {
                    getScene1(x - 1, y - 1, z - 1);
                    BoxThatWillBeUsed[xpos][ypos][zpos] = box;
                    zpos++;
                }
                ypos++;
                zpos = 0;
            }
            xpos++;
            ypos = 0;
        }
    }

    public void positionViewer() {
        ViewingPlatform vp = universe.getViewingPlatform();

        OrbitBehavior orbit = new OrbitBehavior(canvas, OrbitBehavior.REVERSE_ROTATE);
        orbit.setSchedulingBounds(new BoundingSphere());

        Transform3D t3d = new Transform3D();
        t3d.set(new Vector3f(0.0f, 0f, 10.0f));

        vp.getViewPlatformTransform().setTransform(t3d);
        vp.setViewPlatformBehavior(orbit);
    }

    public void getScene1(float xpos, float ypos, float zpos) {

        box = new Box(.498f, .498f, .498f, Primitive.GENERATE_TEXTURE_COORDS, getAppearance(new Color3f(Color.red)));

        box.getShape(Box.FRONT).setAppearance(getAppearance(Color.BLUE));
        box.getShape(Box.TOP).setAppearance(getAppearance(Color.WHITE));
        box.getShape(Box.BOTTOM).setAppearance(getAppearance(new Color(255, 92, 0)));
        box.getShape(Box.RIGHT).setAppearance(getAppearance(Color.RED));
        box.getShape(Box.LEFT).setAppearance(getAppearance(Color.GREEN));
        box.getShape(Box.BACK).setAppearance(getAppearance(new Color3f(Color.yellow)));

        Transform3D transform = new Transform3D();
        Vector3f MadeRubix = new Vector3f(xpos, ypos, zpos);
        transform.setTranslation(MadeRubix);

        boxTransformGroup = new TransformGroup();
        boxTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        // boxTransformGroup1 = new TransformGroup();
        // boxTransformGroup1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        boxTransformGroup2 = new TransformGroup();
        boxTransformGroup2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        if (zpos == 0.0) {
            boxTransformGroup1.addChild(box);
            boxTransformGroup1.setTransform(transform);
            Transformacja_scianaSrodkowaOdOsiZ.addChild(boxTransformGroup1);
        } else if (zpos == 1.0) {
            boxTransformGroup.addChild(box);
            boxTransformGroup.setTransform(transform);
            Transformacja_scianaPrzod.addChild(boxTransformGroup);
        } else {
            boxTransformGroup2.addChild(box);
            boxTransformGroup2.setTransform(transform);
            Transformacja_scianaTyl.addChild(boxTransformGroup2);

        }
    }

    public static void addLights(BranchGroup group) {
        Color3f light1Color = new Color3f(0.7f, 0.8f, 0.8f);
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
        DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
        light1.setInfluencingBounds(bounds);
        group.addChild(light1);
        AmbientLight light2 = new AmbientLight(new Color3f(0.3f, 0.3f, 0.3f));
        light2.setInfluencingBounds(bounds);
        group.addChild(light2);
    }

    public static Appearance getAppearance(Color color) {
        return getAppearance(new Color3f(color));
    }

    public static Appearance getAppearance(Color3f color) {
        Appearance ap = new Appearance();
        ColoringAttributes ca = new ColoringAttributes(color, ColoringAttributes.NICEST);
        ap.setColoringAttributes(ca);
        return ap;
    }

    public void rotateOX(TransformGroup group, double katObrotu) {
        for (int i = 0; i < 250; i++) {
            Transform3D t3d = new Transform3D();
            t3d.set(new Vector3f(0.0f, 0f, 10.0f));
            obrot1.rotX(katObrotu);
            group.getTransform(t3d);
            t3d.get(matrix);
            t3d.setTranslation(new Vector3d(0.0, 0.0, 0.0));
            t3d.mul(obrot1);
            t3d.setTranslation(new Vector3d(matrix.m03, matrix.m13, matrix.m23));
            group.setTransform(t3d);
            try {
                TimeUnit.NANOSECONDS.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(kostka_rubkia.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void rotateOY(TransformGroup group, double katObrotu) {
        for (int i = 0; i < 250; i++) {
            Transform3D t3d = new Transform3D();
            t3d.set(new Vector3f(0.0f, 0f, 10.0f));
            obrot1.rotY(katObrotu);
            group.getTransform(t3d);
            t3d.get(matrix);
            t3d.setTranslation(new Vector3d(0.0, 0.0, 0.0));
            t3d.mul(obrot1);
            t3d.setTranslation(new Vector3d(matrix.m03, matrix.m13, matrix.m23));
            group.setTransform(t3d);
            try {
                TimeUnit.NANOSECONDS.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(kostka_rubkia.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void rotateOZ(TransformGroup group, double katObrotu) {
        for (int i = 0; i < 250; i++) {
            Transform3D t3d = new Transform3D();
            t3d.set(new Vector3f(0.0f, 0f, 10.0f));
            obrot1.rotZ(katObrotu);
            group.getTransform(t3d);
            t3d.get(matrix);
            t3d.setTranslation(new Vector3d(0.0, 0.0, 0.0));
            t3d.mul(obrot1);
            t3d.setTranslation(new Vector3d(matrix.m03, matrix.m13, matrix.m23));
            group.setTransform(t3d);
            try {
                TimeUnit.NANOSECONDS.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(kostka_rubkia.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_E:
            rotateOX(Transformacja_scianaPrawa, Math.PI / 500);
            break;
        case KeyEvent.VK_R:
            rotateOX(Transformacja_scianaPrawa, -Math.PI / 500);
            break;
        case KeyEvent.VK_Q:
            rotateOX(Transformacja_scianaLewa, Math.PI / 500);
            break;
        case KeyEvent.VK_W:
            rotateOX(Transformacja_scianaLewa, -Math.PI / 500);
            break;
        case KeyEvent.VK_A:
            rotateOY(Transformacja_scianazGora, Math.PI / 500);
            break;
        case KeyEvent.VK_S:
            rotateOY(Transformacja_scianaGora, -Math.PI / 500);
            break;
        case KeyEvent.VK_D:
            rotateOY(Transformacja_scianaDol, Math.PI / 500);
            break;
        case KeyEvent.VK_F:
            rotateOY(Transformacja_scianaDol, -Math.PI / 500);
            break;
        case KeyEvent.VK_Z:
            rotateOZ(Transformacja_scianaPrzod, Math.PI / 500);
            break;
        case KeyEvent.VK_X:
            rotateOZ(Transformacja_scianaPrzod, -Math.PI / 500);
            break;
        case KeyEvent.VK_C:
            rotateOZ(Transformacja_scianaTyl, Math.PI / 500);
            break;
        case KeyEvent.VK_V:
            rotateOZ(Transformacja_scianaTyl, -Math.PI / 500);
            break;

        }
        // if (e.getKeyCode() == KeyEvent.VK_E) {
        // for (int i = 0; i < 500; i++) {
        // obrocwX(Transformacja_scianaPrawa, Math.PI / 1000);
        // }
        // } else if (e.getKeyCode() == KeyEvent.VK_R) {
        // for (int i = 0; i < 500; i++) {
        // obrocwX(Transformacja_scianaPrawa, -Math.PI / 1000);
        // }
        // } else if (e.getKeyCode() == KeyEvent.VK_Q) {
        // for (int i = 0; i < 500; i++) {
        // obrocwX(Transformacja_scianaLewa, Math.PI / 1000);
        // }
        // } else if (e.getKeyCode() == KeyEvent.VK_W) {
        // for (int i = 0; i < 500; i++) {
        // obrocwX(Transformacja_scianaLewa, -Math.PI / 1000);
        // }
        // } else if (e.getKeyCode() == KeyEvent.VK_A) {
        // for (int i = 0; i < 500; i++) {
        // obrocwY(Transformacja_scianaGora, Math.PI / 1000);
        // }
        // } else if (e.getKeyCode() == KeyEvent.VK_S) {
        // for (int i = 0; i < 500; i++) {
        // obrocwY(Transformacja_scianaGora, -Math.PI / 1000);
        // }
        // } else if (e.getKeyCode() == KeyEvent.VK_D) {
        // for (int i = 0; i < 500; i++) {
        // obrocwY(Transformacja_scianaDol, Math.PI / 1000);
        // }
        // } else if (e.getKeyCode() == KeyEvent.VK_F) {
        // for (int i = 0; i < 500; i++) {
        // obrocwY(Transformacja_scianaDol, -Math.PI / 1000);
        // }
        // } else if (e.getKeyCode() == KeyEvent.VK_Z) {
        // for (int i = 0; i < 500; i++) {
        // obrocwZ(Transformacja_scianaPrzod, Math.PI / 1000);
        // }
        // } else if (e.getKeyCode() == KeyEvent.VK_X) {
        // for (int i = 0; i < 500; i++) {
        // obrocwZ(Transformacja_scianaPrzod, -Math.PI / 1000);
        // }
        // } else if (e.getKeyCode() == KeyEvent.VK_C) {
        // for (int i = 0; i < 500; i++) {
        // obrocwZ(Transformacja_scianaTyl, Math.PI / 1000);
        // }
        // } else if (e.getKeyCode() == KeyEvent.VK_V) {
        // for (int i = 0; i < 500; i++) {
        // obrocwZ(Transformacja_scianaTyl, -Math.PI / 1000);
        // }
        // }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}

public class Projekt_kostka_rubkia {

    /**
     * Główna metoda klasy. W niej tworzony jest robot i dodawany jest KeyListener;
     *
     * @param args
     */
    public static void main(String[] args) {
        kostka_rubkia Rubix = new kostka_rubkia();
        Rubix.addKeyListener(Rubix);
        MainFrame mf = new MainFrame(Rubix, 640, 480);
    }
}