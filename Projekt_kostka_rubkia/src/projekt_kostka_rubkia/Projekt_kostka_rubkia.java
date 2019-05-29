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
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.pickfast.PickCanvas;
import com.sun.j3d.utils.universe.*;
import static java.lang.Math.sqrt;

/**
 *
 * @author Patryk
 */
public class Projekt_kostka_rubkia extends Applet implements MouseListener, MouseMotionListener {
	 
	private static final long serialVersionUID = 1L;
	private MainFrame frame;
	private Box box;
        private Box BoxThatWillBeUsed[][][];
	private int imageHeight = 512;
	private int imageWidth = 512;
	private Canvas3D canvas;
	private SimpleUniverse universe;
	private BranchGroup group = new BranchGroup();
	private PickCanvas pickCanvas;
	private int lastX=-1;
	private int lastY=-1;
	private int mouseButton = 0;
	private TransformGroup boxTransformGroup;
	private KeyListener l;
        
	public static void main(String[] args) {
              System.setProperty("sun.awt.noerasebackground", "true");
		Projekt_kostka_rubkia object = new Projekt_kostka_rubkia();		 
		object.frame = new MainFrame(object, args, object.imageWidth, object.imageHeight);
		object.validate();
	}

	public void init() {
  		startDrawing();
  	}
	private void startDrawing() {
                TransformGroup mouse = new TransformGroup();
                mouse.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
                BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 10000.0);
            
                BoxThatWillBeUsed  = new Box[3][3][3];
               
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		canvas = new Canvas3D(config);
		universe = new SimpleUniverse(canvas);
		add("Center", canvas);
		positionViewer();
		addLights(group);
                
                //kostka tak bo nie inaczej bo jestem leniwy
                //moze kiedys trafi do funkcji
                
                int xpos=0,ypos=0,zpos=0;
                for(float x=0;x<3;x++)
                {
                    for(float y=0;y<3;y++)
                    {
                        for(float z=0;z<3;z++)
                        {
                             getScene(x,y,z, mouse);
                             BoxThatWillBeUsed[xpos][ypos][zpos]  = box;
                             zpos++;
                        }
                        ypos++;
                        zpos=0;
                    }
                    xpos++;
                    ypos=0;
                }
                //koniec tej kostki bo tak bo jestem glupi 
                
                //obrot kostki wzgledem srodka uniwersum LPM
                MouseRotate behavior = new MouseRotate(mouse);
                behavior.setSchedulingBounds(bounds);
                behavior.setTransformGroup(mouse);
                mouse.addChild(behavior);
                //przesuwanie kostki bez obrotu PPM
                MouseTranslate przesMysza = new MouseTranslate(mouse);
                przesMysza.setSchedulingBounds(bounds);
                mouse.addChild(przesMysza);
                // odsuwanie/przyblizanie kostki w glebi ŚPM
                MouseZoom myszZoom = new MouseZoom(mouse);
                myszZoom.setSchedulingBounds(bounds);
                mouse.addChild(myszZoom);
                                            
                group.addChild(mouse);
		universe.addBranchGraph(group);
                
		pickCanvas = new PickCanvas(canvas, group);
		pickCanvas.setMode(PickInfo.PICK_BOUNDS);
		canvas.addMouseMotionListener(this);
		canvas.addMouseListener(this);
                canvas.addKeyListener(l);
	}
        public void positionViewer() {
		ViewingPlatform vp = universe.getViewingPlatform();
		
		Transform3D t3d = new Transform3D();
		t3d.set(new Vector3f(0.0f,0f,10.0f));
                
		vp.getViewPlatformTransform().setTransform(t3d);

	}
	public void getScene(float xpos, float ypos, float zpos,TransformGroup mouse) {
		
		box = new Box(.4993f, .4993f, .4993f, Primitive.GENERATE_TEXTURE_COORDS,getAppearance(new Color3f(Color.red)));		 
		
		box.getShape(Box.FRONT).setAppearance(getAppearance(Color.BLUE));
		box.getShape(Box.TOP).setAppearance(getAppearance(Color.WHITE));
		box.getShape(Box.BOTTOM).setAppearance(getAppearance(new Color(255,92,0))); ;
		box.getShape(Box.RIGHT).setAppearance(getAppearance(Color.RED));
		box.getShape(Box.LEFT).setAppearance(getAppearance(Color.GREEN)); 
		box.getShape(Box.BACK).setAppearance(getAppearance(new Color3f(Color.yellow))); ;
			    
                boxTransformGroup = new TransformGroup();
		boxTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		boxTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
                xpos=xpos-1f;
                ypos=ypos-1f;
                zpos=zpos-1f;
		Transform3D transform = new Transform3D();
                Vector3f MadeRubix = new Vector3f(xpos, ypos, zpos);
                transform.setTranslation(MadeRubix);
               
                boxTransformGroup.addChild(box);
                boxTransformGroup.setTransform(transform);
                
		mouse.addChild(boxTransformGroup);               		
	}
	
	public static void addLights(BranchGroup group) {
		Color3f light1Color = new Color3f(0.7f, 0.8f, 0.8f);
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),100.0);
		Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
		DirectionalLight light1 = new DirectionalLight(light1Color,light1Direction);
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
		Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
		Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
		Appearance ap = new Appearance();
		Texture texture = new Texture2D();
		TextureAttributes texAttr = new TextureAttributes();
		texAttr.setTextureMode(TextureAttributes.MODULATE);
		texture.setBoundaryModeS(Texture.WRAP);
		texture.setBoundaryModeT(Texture.WRAP);
		texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 0.0f, 0.0f));
		Material mat = new Material(color, black, color, white, 70f);
		ap.setTextureAttributes(texAttr);
		ap.setMaterial(mat);
		ap.setTexture(texture);	 
		ColoringAttributes ca = new ColoringAttributes(color,
				ColoringAttributes.NICEST);
		ap.setColoringAttributes(ca);
		return ap;
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent event) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {		
	}

	@Override
	public void mouseDragged(MouseEvent event) {		
		
	}
	
	@Override
	public void mouseMoved(MouseEvent arg0) {	
	}
}
