package projekt_kostka_rubkia;

import java.util.Random;
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
import static java.awt.event.KeyEvent.*;
import static java.lang.Math.sqrt;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.scene.input.KeyCode.*;

class kostka_rubkia extends Applet implements KeyListener {

    static GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
    static Canvas3D canvas = new Canvas3D(config);
    static SimpleUniverse universe = new SimpleUniverse(canvas);

    private int cubeWall[][] = new int[27][6];
    TransformGroup TransformCube[];
    TransformGroup cubeBuffer[];
    private int WhereAreCubes[][][];
    Transform3D obrot1 = new Transform3D();
    Transform3D obrot2 = new Transform3D();
    private Box box;
    private BranchGroup group[];
    private BranchGroup mainGroup;
    private BranchGroup textGroup;
    private PickCanvas pickCanvas;
    private TransformGroup boxTransformGroup;
    Transform3D transform = new Transform3D();
    Vector3f position = new Vector3f();
    Vector3f position1 = new Vector3f();
    private boolean instruction = true;
   
    public void init() {
        startDrawing();
    }

    private void startDrawing() {

        setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        canvas = new Canvas3D(config);
        universe = new SimpleUniverse(canvas);
        add("Center", canvas);
        positionViewer();

        textGroup = new BranchGroup();
        textGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        textGroup.setCapability(Group.ALLOW_CHILDREN_EXTEND);
        textGroup.setCapability(Group.ALLOW_CHILDREN_WRITE);
        textGroup.setCapability(BranchGroup.ALLOW_DETACH);
        mainGroup = new BranchGroup();
        mainGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        mainGroup.setCapability(Group.ALLOW_CHILDREN_EXTEND);
        mainGroup.setCapability(Group.ALLOW_CHILDREN_WRITE);
        mainGroup.setCapability(BranchGroup.ALLOW_DETACH);

        TransformCube = new TransformGroup[27];
        cubeBuffer = new TransformGroup[27];
        WhereAreCubes = new int[3][3][3];
        group = new BranchGroup[27];

        for (int x = 0; x < 27; x++) {
            for (int wall = 0; wall < 6; wall++) {
                cubeWall[x][wall] = wall;
            }
            cubeBuffer[x] = new TransformGroup();
            cubeBuffer[x].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
            cubeBuffer[x].setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
            cubeBuffer[x].setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
            cubeBuffer[x].setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);

            group[x] = new BranchGroup();
            group[x].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
            group[x].setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
            group[x].setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
            group[x].setCapability(Group.ALLOW_CHILDREN_WRITE);
            group[x].setCapability(BranchGroup.ALLOW_DETACH);

        }
        addLights(mainGroup);

        MakeCube1();
        
        creatInstruction();
        
        universe.addBranchGraph(mainGroup);
        pickCanvas = new PickCanvas(canvas, mainGroup);
        pickCanvas.setMode(PickInfo.PICK_BOUNDS);
        canvas.addKeyListener(this);
    }

    public void MakeCube1() {
        int HowManyCubesWereCreated = 0;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                for (int z = 0; z < 3; z++) {
                    WhereAreCubes[x][y][z] = HowManyCubesWereCreated;

                    newCube(x - 1, y - 1, z - 1, HowManyCubesWereCreated);
                    // group[HowManyCubesWereCreated].addChild(TransformCube[HowManyCubesWereCreated]);
                    // mainGroup.addChild(group[HowManyCubesWereCreated]);
                    HowManyCubesWereCreated++;
                }
            }
        }
    }

    public void newCube(float xpos, float ypos, float zpos, int x) {

        // box = new Box(.495f, .495f, .495f, Primitive.GENERATE_TEXTURE_COORDS, getAppearance(new Color3f(Color.red)));
        // box.getShape(cubeWall[x][cubeWall[x][0]]).setAppearance(getAppearance(Color.BLUE));
        // box.getShape(cubeWall[x][cubeWall[x][1]]).setAppearance(getAppearance(Color.WHITE));
        // box.getShape(cubeWall[x][cubeWall[x][2]]).setAppearance(getAppearance(new Color(255, 92, 0)));
        // box.getShape(cubeWall[x][cubeWall[x][3]]).setAppearance(getAppearance(Color.RED));
        // box.getShape(cubeWall[x][cubeWall[x][4]]).setAppearance(getAppearance(Color.GREEN));
        // box.getShape(cubeWall[x][cubeWall[x][5]]).setAppearance(getAppearance(new Color3f(Color.yellow))); // 1
        box = new Box(.495f, .495f, .495f, Primitive.GENERATE_TEXTURE_COORDS, getAppearance(new Color3f(Color.red)));
        box.getShape(cubeWall[x][0]).setAppearance(getAppearance(Color.BLUE));
        box.getShape(cubeWall[x][1]).setAppearance(getAppearance(Color.WHITE));
        box.getShape(cubeWall[x][2]).setAppearance(getAppearance(new Color(255, 92, 0)));
        box.getShape(cubeWall[x][3]).setAppearance(getAppearance(Color.RED));
        box.getShape(cubeWall[x][4]).setAppearance(getAppearance(Color.GREEN));
        box.getShape(cubeWall[x][5]).setAppearance(getAppearance(new Color3f(Color.yellow))); // 1

        Transform3D transform = new Transform3D();
        Vector3f MadeRubix = new Vector3f(xpos, ypos, zpos);
        transform.setTranslation(MadeRubix);

        boxTransformGroup = new TransformGroup();
        boxTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        TransformCube[x] = new TransformGroup();
        TransformCube[x].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        TransformCube[x].setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        TransformCube[x].setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
        TransformCube[x].setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);

        boxTransformGroup.addChild(box);
        boxTransformGroup.setTransform(transform);
        TransformCube[x].addChild(boxTransformGroup);
        group[x].addChild(TransformCube[x]);
        mainGroup.addChild(group[x]);

    }

    public void positionViewer() {
        ViewingPlatform vp = universe.getViewingPlatform();

        OrbitBehavior orbit = new OrbitBehavior(canvas, OrbitBehavior.REVERSE_ROTATE);
        orbit.setSchedulingBounds(new BoundingSphere());

        Transform3D t3d = new Transform3D();
        t3d.set(new Vector3f(0.0f, 0f, 17.0f));

        vp.getViewPlatformTransform().setTransform(t3d);
        vp.setViewPlatformBehavior(orbit);
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

    public void obrocwX(TransformGroup group, double katObrotu) {
        Transform3D t3d = new Transform3D();
        t3d.set(new Vector3f(0.0f, 0.0f, 0.0f));
        obrot1.rotX(katObrotu);
        group.getTransform(t3d);
        t3d.mul(obrot1);
        group.setTransform(t3d);

        try {
            TimeUnit.MICROSECONDS.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(kostka_rubkia.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void obrocwY(TransformGroup group, double katObrotu) {
        Transform3D t3d = new Transform3D();
        t3d.set(new Vector3f(0.0f, 0f, 0.0f));
        obrot1.rotY(katObrotu);
        group.getTransform(t3d);
        t3d.mul(obrot1);
        group.setTransform(t3d);
        try {
            TimeUnit.MICROSECONDS.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(kostka_rubkia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void obrocwZ(TransformGroup group, double katObrotu) {

        Transform3D t3d = new Transform3D();
        obrot1.rotZ(katObrotu);
        group.getTransform(t3d);
        t3d.mul(obrot1);
        group.setTransform(t3d);
        try {
            TimeUnit.MICROSECONDS.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(kostka_rubkia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void RotateX(TransformGroup Cube, double katObrotu, int WhichBoxOnX, int WhichBoxOnY, int WhichBoxOnZ, int zablokuj) {
        int buffer = 0, buffor = 0;
        obrocwX(Cube, katObrotu);
        if (zablokuj == 49) {
            Transform3D transform2 = new Transform3D();
            transform2.setScale(0);
            Cube.setTransform(transform2);
            group[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]].detach();
            mainGroup.removeChild(group[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]]);
            buffer = cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][0];
            if (katObrotu < 0) {
                cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][0] = cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][4];
                cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][4] = cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][1];
                cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][1] = cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][5];
                cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][5] = buffer;

//                if (WhichBoxOnX == 0 && WhichBoxOnZ == 0) {
//                    buffor = cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][4];
//                    cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][4] = cubeWall[WhereAreCubes[WhichBoxOnX + 2][WhichBoxOnY][WhichBoxOnZ]][4];
//                    cubeWall[WhereAreCubes[WhichBoxOnX + 2][WhichBoxOnY][WhichBoxOnZ]][4] = cubeWall[WhereAreCubes[WhichBoxOnX + 2][WhichBoxOnY][WhichBoxOnZ + 2]][4];
//                    cubeWall[WhereAreCubes[WhichBoxOnX + 2][WhichBoxOnY][WhichBoxOnZ + 2]][4] = cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ + 2]][4];
//                    cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ + 2]][4] = buffor;
//                    if (WhichBoxOnX == 1 && WhichBoxOnZ == 0) {
//                        buffor = cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][4];
//                        cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][4] = cubeWall[WhereAreCubes[WhichBoxOnX + 1][WhichBoxOnY][WhichBoxOnZ + 1]][4];
//                        cubeWall[WhereAreCubes[WhichBoxOnX + 1][WhichBoxOnY][WhichBoxOnZ + 1]][4] = cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ + 2]][4];
//                        cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ + 2]][4] = cubeWall[WhereAreCubes[WhichBoxOnX - 1][WhichBoxOnY][WhichBoxOnZ + 1]][4];
//                        cubeWall[WhereAreCubes[WhichBoxOnX - 1][WhichBoxOnY][WhichBoxOnZ + 1]][4] = buffor;
//                    }
//                }
            } else {
                cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][0] = cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][5];
                cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][5] = cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][1];
                cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][1] = cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][4];
                cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][4] = buffer;
            }
            newCube(WhichBoxOnX - 1, WhichBoxOnY - 1, WhichBoxOnZ - 1,
                    WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]);

        }
    }

    public void RotateY(TransformGroup Cube, double katObrotu, int WhichBoxOnX, int WhichBoxOnY, int WhichBoxOnZ, int zablokuj) {
        int buffer = 0, buffor = 0, baffor = 0;
        obrocwY(Cube, katObrotu);
        if (zablokuj == 49) {
            Transform3D transform2 = new Transform3D();
            transform2.setScale(0);
            Cube.setTransform(transform2);
            group[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]].detach();
            mainGroup.removeChild(group[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]]);
            buffer = cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][0];
            if (katObrotu > 0) {
                cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][0] = cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][2];
                cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][2] = cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][1];
                cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][1] = cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][3];
                cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][3] = buffer;

//                if (WhichBoxOnX == 0 && WhichBoxOnZ == 0) {
//                    buffor = cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][4];
//                    cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][4] = cubeWall[WhereAreCubes[WhichBoxOnX + 2][WhichBoxOnY][WhichBoxOnZ]][4];
//                    cubeWall[WhereAreCubes[WhichBoxOnX + 2][WhichBoxOnY][WhichBoxOnZ]][4] = cubeWall[WhereAreCubes[WhichBoxOnX + 2][WhichBoxOnY][WhichBoxOnZ + 2]][4];
//                    cubeWall[WhereAreCubes[WhichBoxOnX + 2][WhichBoxOnY][WhichBoxOnZ + 2]][4] = cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ + 2]][4];
//                    cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ + 2]][4] = buffor;
//                    if (WhichBoxOnX == 1 && WhichBoxOnZ == 0) {
//                        buffor = cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][4];
//                        cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][4] = cubeWall[WhereAreCubes[WhichBoxOnX + 1][WhichBoxOnY][WhichBoxOnZ + 1]][4];
//                        cubeWall[WhereAreCubes[WhichBoxOnX + 1][WhichBoxOnY][WhichBoxOnZ + 1]][4] = cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ + 2]][4];
//                        cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ + 2]][4] = cubeWall[WhereAreCubes[WhichBoxOnX - 1][WhichBoxOnY][WhichBoxOnZ + 1]][4];
//                        cubeWall[WhereAreCubes[WhichBoxOnX - 1][WhichBoxOnY][WhichBoxOnZ + 1]][4] = buffor;
//                    }
                
            } else {
                cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][0] = cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][3];
                cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][3] = cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][1];
                cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][1] = cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][2];
                cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][2] = buffer;

//                if (WhichBoxOnX == 0 && WhichBoxOnZ == 0) {
//                    buffor = cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][4];
//                    cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][4] = cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ + 2]][4];
//                    cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ + 2]][4] = cubeWall[WhereAreCubes[WhichBoxOnX + 2][WhichBoxOnY][WhichBoxOnZ + 2]][4];
//                    cubeWall[WhereAreCubes[WhichBoxOnX + 2][WhichBoxOnY][WhichBoxOnZ + 2]][4] = cubeWall[WhereAreCubes[WhichBoxOnX + 2][WhichBoxOnY][WhichBoxOnZ]][4];
//                    cubeWall[WhereAreCubes[WhichBoxOnX + 2][WhichBoxOnY][WhichBoxOnZ]][4] = buffor;
//                }
//                if (WhichBoxOnX == 1 && WhichBoxOnZ == 0) {
//                    buffor = cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][4];
//                    cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][4] = cubeWall[WhereAreCubes[WhichBoxOnX - 1][WhichBoxOnY][WhichBoxOnZ + 1]][4];
//                    cubeWall[WhereAreCubes[WhichBoxOnX - 1][WhichBoxOnY][WhichBoxOnZ + 1]][4] = cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ + 2]][4];
//                    cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ + 2]][4] = cubeWall[WhereAreCubes[WhichBoxOnX + 1][WhichBoxOnY][WhichBoxOnZ + 1]][4];
//                    cubeWall[WhereAreCubes[WhichBoxOnX + 1][WhichBoxOnY][WhichBoxOnZ + 1]][4] = buffor;
//                }

            }
            newCube(WhichBoxOnX - 1, WhichBoxOnY - 1, WhichBoxOnZ - 1, WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]);
        } 
        
    }

    public void RotateZ(TransformGroup Cube, double katObrotu, int WhichBoxOnX, int WhichBoxOnY, int WhichBoxOnZ, int zablokuj) {
        int buffer = 0;
        obrocwZ(Cube, katObrotu);
        if (zablokuj == 49) {
            Transform3D transform2 = new Transform3D();
            transform2.setScale(0);
            Cube.setTransform(transform2);
            group[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]].detach();
            mainGroup.removeChild(group[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]]);
            buffer = cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][4];
            if (katObrotu < 0) {
                cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][4] = cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][2];
                cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][2] = cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][5];
                cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][5] = cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][3];
                cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][3] = buffer;

            } else {
                cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][4] = cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][3];
                cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][3] = cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][5];
                cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][5] = cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][2];
                cubeWall[WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]][2] = buffer;
            }
            newCube(WhichBoxOnX - 1, WhichBoxOnY - 1, WhichBoxOnZ - 1, WhereAreCubes[WhichBoxOnX][WhichBoxOnY][WhichBoxOnZ]);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    
    public void rotE(int WhichCube){
          for (int i = 0; i < 50; i++) {
                if (i == 49) {
                    WhichCube = WhereAreCubes[2][2][2];
                    WhereAreCubes[2][2][2] = WhereAreCubes[2][2][0];
                    WhereAreCubes[2][2][0] = WhereAreCubes[2][0][0];
                    WhereAreCubes[2][0][0] = WhereAreCubes[2][0][2];
                    WhereAreCubes[2][0][2] = WhichCube;
                    WhichCube = WhereAreCubes[2][2][1];
                    WhereAreCubes[2][2][1] = WhereAreCubes[2][1][0];
                    WhereAreCubes[2][1][0] = WhereAreCubes[2][0][1];
                    WhereAreCubes[2][0][1] = WhereAreCubes[2][1][2];
                    WhereAreCubes[2][1][2] = WhichCube;
                }
                for (int y = 0; y < 3; y++) {
                    for (int z = 0; z < 3; z++) {
                        RotateX(TransformCube[WhereAreCubes[2][y][z]], Math.PI / 100, 2, y, z, i);
                    }
                }
            }
    }
     public void rotR(int WhichCube){
         for (int i = 0; i < 50; i++) {
                if (i == 49) {
                    WhichCube = WhereAreCubes[2][2][0];
                    WhereAreCubes[2][2][0] = WhereAreCubes[2][2][2];
                    WhereAreCubes[2][2][2] = WhereAreCubes[2][0][2];
                    WhereAreCubes[2][0][2] = WhereAreCubes[2][0][0];
                    WhereAreCubes[2][0][0] = WhichCube;
                    WhichCube = WhereAreCubes[2][1][0];
                    WhereAreCubes[2][1][0] = WhereAreCubes[2][2][1];
                    WhereAreCubes[2][2][1] = WhereAreCubes[2][1][2];
                    WhereAreCubes[2][1][2] = WhereAreCubes[2][0][1];
                    WhereAreCubes[2][0][1] = WhichCube;
                }
                for (int y = 0; y < 3; y++) {
                    for (int z = 0; z < 3; z++) {
                        RotateX(TransformCube[WhereAreCubes[2][y][z]], -Math.PI / 100, 2, y, z, i);
                    }
                }
            }
    }
    public void rotQ(int WhichCube){
           for (int i = 0; i < 50; i++) {
                if (i == 49) {
                    WhichCube = WhereAreCubes[0][2][2];
                    WhereAreCubes[0][2][2] = WhereAreCubes[0][2][0];
                    WhereAreCubes[0][2][0] = WhereAreCubes[0][0][0];
                    WhereAreCubes[0][0][0] = WhereAreCubes[0][0][2];
                    WhereAreCubes[0][0][2] = WhichCube;
                    WhichCube = WhereAreCubes[0][2][1];
                    WhereAreCubes[0][2][1] = WhereAreCubes[0][1][0];
                    WhereAreCubes[0][1][0] = WhereAreCubes[0][0][1];
                    WhereAreCubes[0][0][1] = WhereAreCubes[0][1][2];
                    WhereAreCubes[0][1][2] = WhichCube;
                }
                for (int y = 0; y < 3; y++) {
                    for (int z = 0; z < 3; z++) {
                        RotateX(TransformCube[WhereAreCubes[0][y][z]], Math.PI / 100, 0, y, z, i);
                    }
                }
            }
    }
    public void rotW(int WhichCube){
          for (int i = 0; i < 50; i++) {
                if (i == 49) {
                    WhichCube = WhereAreCubes[0][2][0];
                    WhereAreCubes[0][2][0] = WhereAreCubes[0][2][2];
                    WhereAreCubes[0][2][2] = WhereAreCubes[0][0][2];
                    WhereAreCubes[0][0][2] = WhereAreCubes[0][0][0];
                    WhereAreCubes[0][0][0] = WhichCube;
                    WhichCube = WhereAreCubes[0][1][0];
                    WhereAreCubes[0][1][0] = WhereAreCubes[0][2][1];
                    WhereAreCubes[0][2][1] = WhereAreCubes[0][1][2];
                    WhereAreCubes[0][1][2] = WhereAreCubes[0][0][1];
                    WhereAreCubes[0][0][1] = WhichCube;
                }
                for (int y = 0; y < 3; y++) {
                    for (int z = 0; z < 3; z++) {
                        RotateX(TransformCube[WhereAreCubes[0][y][z]], -Math.PI / 100, 0, y, z, i);
                    }
                }
            }
    }
    public void rotA(int WhichCube){
          for (int i = 0; i < 50; i++) {
                if (i == 49) {
                    WhichCube = WhereAreCubes[0][2][0];
                    WhereAreCubes[0][2][0] = WhereAreCubes[2][2][0];
                    WhereAreCubes[2][2][0] = WhereAreCubes[2][2][2];
                    WhereAreCubes[2][2][2] = WhereAreCubes[0][2][2];
                    WhereAreCubes[0][2][2] = WhichCube;
                    WhichCube = WhereAreCubes[0][2][1];
                    WhereAreCubes[0][2][1] = WhereAreCubes[1][2][0];
                    WhereAreCubes[1][2][0] = WhereAreCubes[2][2][1];
                    WhereAreCubes[2][2][1] = WhereAreCubes[1][2][2];
                    WhereAreCubes[1][2][2] = WhichCube;

                }
                for (int x = 0; x < 3; x++) {
                    for (int z = 0; z < 3; z++) {
                        RotateY(TransformCube[WhereAreCubes[x][2][z]], Math.PI / 100, x, 2, z, i);
                    }
                }
            }
    }
    public void rotS(int WhichCube){
           for (int i = 0; i < 50; i++) {
                if (i == 49) {
                    WhichCube = WhereAreCubes[0][2][0];
                    WhereAreCubes[0][2][0] = WhereAreCubes[0][2][2];
                    WhereAreCubes[0][2][2] = WhereAreCubes[2][2][2];
                    WhereAreCubes[2][2][2] = WhereAreCubes[2][2][0];
                    WhereAreCubes[2][2][0] = WhichCube;
                    WhichCube = WhereAreCubes[0][2][1];
                    WhereAreCubes[0][2][1] = WhereAreCubes[1][2][2];
                    WhereAreCubes[1][2][2] = WhereAreCubes[2][2][1];
                    WhereAreCubes[2][2][1] = WhereAreCubes[1][2][0];
                    WhereAreCubes[1][2][0] = WhichCube;

                }
                for (int x = 0; x < 3; x++) {
                    for (int z = 0; z < 3; z++) {
                        RotateY(TransformCube[WhereAreCubes[x][2][z]], -Math.PI / 100, x, 2, z, i);

                    }
                }
            }
    }
    public void rotD(int WhichCube){
          for (int i = 0; i < 50; i++) {
                if (i == 49) {
                    WhichCube = WhereAreCubes[0][0][0];
                    WhereAreCubes[0][0][0] = WhereAreCubes[2][0][0];
                    WhereAreCubes[2][0][0] = WhereAreCubes[2][0][2];
                    WhereAreCubes[2][0][2] = WhereAreCubes[0][0][2];
                    WhereAreCubes[0][0][2] = WhichCube;
                    WhichCube = WhereAreCubes[1][0][0];
                    WhereAreCubes[1][0][0] = WhereAreCubes[2][0][1];
                    WhereAreCubes[2][0][1] = WhereAreCubes[1][0][2];
                    WhereAreCubes[1][0][2] = WhereAreCubes[0][0][1];
                    WhereAreCubes[0][0][1] = WhichCube;
                }
                for (int x = 0; x < 3; x++) {
                    for (int z = 0; z < 3; z++) {
                        RotateY(TransformCube[WhereAreCubes[x][0][z]], Math.PI / 100, x, 0, z, i);
                    }
                }
            }
    }
    public void rotF(int WhichCube){
          for (int i = 0; i < 50; i++) {
                if (i == 49) {
                    WhichCube = WhereAreCubes[0][0][0];
                    WhereAreCubes[0][0][0] = WhereAreCubes[0][0][2];
                    WhereAreCubes[0][0][2] = WhereAreCubes[2][0][2];
                    WhereAreCubes[2][0][2] = WhereAreCubes[2][0][0];
                    WhereAreCubes[2][0][0] = WhichCube;
                    WhichCube = WhereAreCubes[1][0][0];
                    WhereAreCubes[1][0][0] = WhereAreCubes[0][0][1];
                    WhereAreCubes[0][0][1] = WhereAreCubes[1][0][2];
                    WhereAreCubes[1][0][2] = WhereAreCubes[2][0][1];
                    WhereAreCubes[2][0][1] = WhichCube;
                }
                for (int x = 0; x < 3; x++) {
                    for (int z = 0; z < 3; z++) {
                        RotateY(TransformCube[WhereAreCubes[x][0][z]], -Math.PI / 100, x, 0, z, i);
                    }
                }
            }
    }
    public void rotZ(int WhichCube){
           for (int i = 0; i < 50; i++) {
                if (i == 49) {
                    WhichCube = WhereAreCubes[0][2][2];
                    WhereAreCubes[0][2][2] = WhereAreCubes[2][2][2];
                    WhereAreCubes[2][2][2] = WhereAreCubes[2][0][2];
                    WhereAreCubes[2][0][2] = WhereAreCubes[0][0][2];
                    WhereAreCubes[0][0][2] = WhichCube;
                    WhichCube = WhereAreCubes[1][2][2];
                    WhereAreCubes[1][2][2] = WhereAreCubes[2][1][2];
                    WhereAreCubes[2][1][2] = WhereAreCubes[1][0][2];
                    WhereAreCubes[1][0][2] = WhereAreCubes[0][1][2];
                    WhereAreCubes[0][1][2] = WhichCube;
                }
                for (int x = 0; x < 3; x++) {
                    for (int y = 0; y < 3; y++) {
                        RotateZ(TransformCube[WhereAreCubes[x][y][2]], Math.PI / 100, x, y, 2, i);
                    }
                }
            }
    }
    public void rotX(int WhichCube){
          for (int i = 0; i < 50; i++) {
                if (i == 49) {
                    WhichCube = WhereAreCubes[0][2][2];
                    WhereAreCubes[0][2][2] = WhereAreCubes[0][0][2];
                    WhereAreCubes[0][0][2] = WhereAreCubes[2][0][2];
                    WhereAreCubes[2][0][2] = WhereAreCubes[2][2][2];
                    WhereAreCubes[2][2][2] = WhichCube;
                    WhichCube = WhereAreCubes[1][2][2];
                    WhereAreCubes[1][2][2] = WhereAreCubes[0][1][2];
                    WhereAreCubes[0][1][2] = WhereAreCubes[1][0][2];
                    WhereAreCubes[1][0][2] = WhereAreCubes[2][1][2];
                    WhereAreCubes[2][1][2] = WhichCube;
                }
                for (int x = 0; x < 3; x++) {
                    for (int y = 0; y < 3; y++) {
                        RotateZ(TransformCube[WhereAreCubes[x][y][2]], -Math.PI / 100, x, y, 2, i);
                    }
                }
            }
    }
    public void rotC(int WhichCube){
           for (int i = 0; i < 50; i++) {
                if (i == 49) {
                    WhichCube = WhereAreCubes[0][2][0];
                    WhereAreCubes[0][2][0] = WhereAreCubes[2][2][0];
                    WhereAreCubes[2][2][0] = WhereAreCubes[2][0][0];
                    WhereAreCubes[2][0][0] = WhereAreCubes[0][0][0];
                    WhereAreCubes[0][0][0] = WhichCube;
                    WhichCube = WhereAreCubes[1][2][0];
                    WhereAreCubes[1][2][0] = WhereAreCubes[2][1][0];
                    WhereAreCubes[2][1][0] = WhereAreCubes[1][0][0];
                    WhereAreCubes[1][0][0] = WhereAreCubes[0][1][0];
                    WhereAreCubes[0][1][0] = WhichCube;
                }
                for (int x = 0; x < 3; x++) {
                    for (int y = 0; y < 3; y++) {
                        RotateZ(TransformCube[WhereAreCubes[x][y][0]], Math.PI / 100, x, y, 0, i);
                    }
                }
            }
    }
    public void rotV(int WhichCube){
           for (int i = 0; i < 50; i++) {
                if (i == 49) {
                    WhichCube = WhereAreCubes[0][2][0];
                    WhereAreCubes[0][2][0] = WhereAreCubes[0][0][0];
                    WhereAreCubes[0][0][0] = WhereAreCubes[2][0][0];
                    WhereAreCubes[2][0][0] = WhereAreCubes[2][2][0];
                    WhereAreCubes[2][2][0] = WhichCube;
                    WhichCube = WhereAreCubes[1][2][0];
                    WhereAreCubes[1][2][0] = WhereAreCubes[0][1][0];
                    WhereAreCubes[0][1][0] = WhereAreCubes[1][0][0];
                    WhereAreCubes[1][0][0] = WhereAreCubes[2][1][0];
                    WhereAreCubes[2][1][0] = WhichCube;
                }
                for (int x = 0; x < 3; x++) {
                    for (int y = 0; y < 3; y++) {
                        RotateZ(TransformCube[WhereAreCubes[x][y][0]], -Math.PI / 100, x, y, 0, i);
                    }
                }
            }
    }
    public void randomizeCube(int WhichCube){
         Random rand = new Random();
           int losowanie = 0;
           for (int x = 0; x < 20; x++) {
                losowanie=rand.nextInt((12 - 1) + 1) + 1;
                switch(losowanie){
                    case 1:  rotE(WhichCube); break;
                    case 2:  rotR(WhichCube); break;
                    case 3:  rotQ(WhichCube); break;
                    case 4:  rotW(WhichCube); break;
                    case 5:  rotA(WhichCube); break;
                    case 6:  rotS(WhichCube); break;
                    case 7:  rotD(WhichCube); break;
                    case 8:  rotF(WhichCube); break;
                    case 9:  rotZ(WhichCube); break;
                    case 10: rotX(WhichCube); break;
                    case 11: rotC(WhichCube); break;
                    case 12: rotV(WhichCube); break;
                }        
            }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int WhichCube = 0;
        if(instruction == true)
        {
            mainGroup.removeChild(textGroup);
            instruction = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_E) {
          rotE(WhichCube);
        } else if (e.getKeyCode() == KeyEvent.VK_R) {
          rotR(WhichCube);
        } else if (e.getKeyCode() == KeyEvent.VK_Q) {
          rotQ(WhichCube);
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
          rotW(WhichCube);  
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
          rotA(WhichCube);  
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
          rotS(WhichCube); 
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
          rotD(WhichCube);  
        } else if (e.getKeyCode() == KeyEvent.VK_F) {
          rotF(WhichCube);  
        } else if (e.getKeyCode() == KeyEvent.VK_Z) {
          rotZ(WhichCube); 
        } else if (e.getKeyCode() == KeyEvent.VK_X) {
          rotX(WhichCube); 
        } else if (e.getKeyCode() == KeyEvent.VK_C) {
          rotC(WhichCube); 
        } else if (e.getKeyCode() == KeyEvent.VK_V) {
          rotV(WhichCube); 
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
            for (int x = 0; x < 27; x++) {
                mainGroup.removeChild(group[x]);
            }
            for (int x = 0; x < 27; x++) {
            for (int wall = 0; wall < 6; wall++) {
                cubeWall[x][wall] = wall;
            }
            cubeBuffer[x] = new TransformGroup();
            cubeBuffer[x].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
            cubeBuffer[x].setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
            cubeBuffer[x].setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
            cubeBuffer[x].setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);

            group[x] = new BranchGroup();
            group[x].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
            group[x].setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
            group[x].setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
            group[x].setCapability(Group.ALLOW_CHILDREN_WRITE);
            group[x].setCapability(BranchGroup.ALLOW_DETACH);

            }
            MakeCube1();
        }else if (e.getKeyCode() == KeyEvent.VK_O) {
            randomizeCube(WhichCube); 
        }
        else if (e.getKeyCode() == KeyEvent.VK_I) {
            if(instruction == false){
            mainGroup.addChild(textGroup);
            instruction = true;
            Transform3D t3d = new Transform3D();
            t3d.set(new Vector3f(0.0f, 0f, 17.0f));

            universe.getViewingPlatform().getViewPlatformTransform().setTransform(t3d);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    
    private void addText(String string, float x, float y, float z) {
		Font3D f3d = new Font3D(new Font("TestFont", Font.PLAIN, 1),
				new FontExtrusion());
		Text3D text = new Text3D(f3d, new String(), new Point3f(0.0f,
				0.0f, 0.0f));
		 
		text.setString(string);
		Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
		Color3f blue = new Color3f(.6f, 0.2f, 0.6f);
		Appearance a = new Appearance();
		Material m = new Material(blue, blue, blue, white, 80.0f);
		m.setLightingEnable(true);
		a.setMaterial(m);

		Shape3D sh = new Shape3D();
		sh.setGeometry(text);
		sh.setAppearance(a);
		TransformGroup tg = new TransformGroup();
		Transform3D t3d = new Transform3D();
                Transform3D scale = new Transform3D();
		Vector3f v3f = new Vector3f(x, y, z);
		t3d.setTranslation(v3f);
                scale.setScale(0.5);
                t3d.mul(scale);
                tg.setTransform(t3d);
		tg.addChild(sh);
                textGroup.addChild(tg);
		
	}
    
    private void creatInstruction(){
        addText("Q: obrot lewej ściany o 90*(oś X)",-4,3.5f,3);
        addText("W: obrot lewej ściany o -90*(oś X)",-4,3,3);
        addText("E: obrot prawej ściany o 90*(oś X)",-4,2.5f,3);
        addText("R: obrot prawej ściany o -90*(oś X)",-4,2f,3);
        addText("A: obrot górnej ściany o 90*(oś Y)",-4,1.5f,3);
        addText("S: obrot górnej ściany o 90*(oś Y)",-4,1f,3);
        addText("D: obrot dolnej ściany o 90*(oś Y)",-4,0.5f,3);
        addText("F: obrot dolnej ściany o 90*(oś Y)",-4,0f,3);
        addText("Z: obrot przedniej ściany o 90*(oś Z)",-4,-0.5f,3);
        addText("X: obrot przedniej ściany o 90*(oś Z)",-4,-1f,3);
        addText("C: obrot tylnej ściany o 90*(oś Z)",-4,-1.5f,3);
        addText("V: obrot tylnej ściany o 90*(oś Z)",-4,-2f,3);
        addText("O: losowe pszemieszczenie ścian",-4,-2.5f,3);
        addText("P: Reset kostki",-4,-3f,3);
        addText("I: Pokaż mi instrukcje ponownie!!",-4,-3.5f,3);
        addText("Po wykonaniu ruchu instrukcje znikną!!",-4,-4f,3);
        mainGroup.addChild(textGroup);
    }

}

public class Projekt_kostka_rubkia {

    /**
     * Główna metoda klasy. W niej tworzony jest robot i dodawany jest
     * KeyListener;
     *
     * @param args
     */
    public static void main(String[] args) {
        kostka_rubkia Rubix = new kostka_rubkia();
        Rubix.addKeyListener(Rubix);
        MainFrame mf = new MainFrame(Rubix, 640, 480);
    }
}
