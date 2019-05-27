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
	
	public static void main(String[] args) {
              System.setProperty("sun.awt.noerasebackground", "true");
		Projekt_kostka_rubkia object = new Projekt_kostka_rubkia();		 
		object.frame = new MainFrame(object, args, object.imageWidth, object.imageHeight);
		object.validate();
	}

	public void init() {
  		startDrawing();
  	}

	public Point3d getPosition(MouseEvent event) {
		Point3d eyePos = new Point3d();
		Point3d mousePos = new Point3d();
		canvas.getCenterEyeInImagePlate(eyePos);
		canvas.getPixelLocationInImagePlate(event.getX(), event.getY(), mousePos);
		Transform3D transform = new Transform3D();
		canvas.getImagePlateToVworld(transform);
		transform.transform(eyePos);
		transform.transform(mousePos);
		Vector3d direction = new Vector3d(eyePos);
		direction.sub(mousePos);
		// three points on the plane
		Point3d p1 = new Point3d(.5, -.5, .5);
		Point3d p2 = new Point3d(.5, .5, .5);
		Point3d p3 = new Point3d(-.5, .5, .5);
		Transform3D currentTransform = new Transform3D();
		box.getLocalToVworld(currentTransform);
		currentTransform.transform(p1);
		currentTransform.transform(p2);
		currentTransform.transform(p3);		
		Point3d intersection = getIntersection(eyePos, mousePos, p1, p2, p3);
		currentTransform.invert();
		currentTransform.transform(intersection);
		return intersection;		
	}
	
	/**
	 * Returns the point where a line crosses a plane  
	 */
	Point3d getIntersection(Point3d line1, Point3d line2, 
			Point3d plane1, Point3d plane2, Point3d plane3) {
		Vector3d p1 = new Vector3d(plane1);
		Vector3d p2 = new Vector3d(plane2);
		Vector3d p3 = new Vector3d(plane3);
		Vector3d p2minusp1 = new Vector3d(p2);
		p2minusp1.sub(p1);
		Vector3d p3minusp1 = new Vector3d(p3);
		p3minusp1.sub(p1);
		Vector3d normal = new Vector3d();
		normal.cross(p2minusp1, p3minusp1);
		// The plane can be defined by p1, n + d = 0
		double d = -p1.dot(normal);
		Vector3d i1 = new Vector3d(line1);
		Vector3d direction = new Vector3d(line1);
		direction.sub(line2);
		double dot = direction.dot(normal);
		if (dot == 0) return null;
		double t = (-d - i1.dot(normal)) / (dot);
		Vector3d intersection = new Vector3d(line1);
		Vector3d scaledDirection = new Vector3d(direction);
		scaledDirection.scale(t);
		intersection.add(scaledDirection);
		Point3d intersectionPoint = new Point3d(intersection);
		return intersectionPoint;
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
		
                //pieprzona kostka tak bo nie inaczej bo jestem leniwy
                //moze kiedys trafi do funkcji
                
                //Środkowa ściana
                getScene(0,0,0,mouse);
                BoxThatWillBeUsed[1][1][1] = box;               
                getScene(0.5f,0,0,mouse);
                BoxThatWillBeUsed[2][1][1] = box;               
                getScene(0.5f,-0.5f,0,mouse);
                BoxThatWillBeUsed[2][2][1] = box;               
                getScene(0,-0.5f,0,mouse);
                BoxThatWillBeUsed[1][2][1] = box;               
                getScene(-0.5f,-0.5f,0,mouse);
                BoxThatWillBeUsed[0][2][1] = box;             
                getScene(-0.5f,0,0,mouse);
                BoxThatWillBeUsed[0][1][1] = box;               
                getScene(-0.5f,0.5f,0,mouse);
                BoxThatWillBeUsed[0][0][1] = box;               
                getScene(0,0.5f,0,mouse);
                BoxThatWillBeUsed[1][0][1] = box;               
                getScene(0.5f,0.5f,0,mouse);
                BoxThatWillBeUsed[2][0][1] = box;
                
                //Przednia ściana
                
                getScene(0,0,0.5f,mouse);
                BoxThatWillBeUsed[1][1][0] = box;                
                getScene(0.5f,0,0.5f,mouse);
                BoxThatWillBeUsed[2][1][0] = box;               
                getScene(0.5f,-0.5f,0.5f,mouse);
                BoxThatWillBeUsed[2][2][0] = box;             
                getScene(0,-0.5f,0.5f,mouse);
                BoxThatWillBeUsed[1][2][0] = box;               
                getScene(-0.5f,-0.5f,0.5f,mouse);
                BoxThatWillBeUsed[0][2][0] = box;               
                getScene(-0.5f,0,0.5f,mouse);
                BoxThatWillBeUsed[0][1][0] = box;               
                getScene(-0.5f,0.5f,0.5f,mouse);
                BoxThatWillBeUsed[0][0][0] = box;               
                getScene(0,0.5f,0.5f,mouse);
                BoxThatWillBeUsed[1][0][0] = box;               
                getScene(0.5f,0.5f,0.5f,mouse);
                BoxThatWillBeUsed[2][0][0] = box;    
                
                //Tylna ściana
                
                getScene(0,0,-0.5f,mouse);
                BoxThatWillBeUsed[1][1][2] = box;              
                getScene(0.5f,0,-0.5f,mouse);
                BoxThatWillBeUsed[2][1][2] = box;                
                getScene(0.5f,-0.5f,-0.5f,mouse);
                BoxThatWillBeUsed[2][2][2] = box;               
                getScene(0,-0.5f,-0.5f,mouse);
                BoxThatWillBeUsed[1][2][2] = box;               
                getScene(-0.5f,-0.5f,-0.5f,mouse);
                BoxThatWillBeUsed[0][2][2] = box;               
                getScene(-0.5f,0,-0.5f,mouse);
                BoxThatWillBeUsed[0][1][2] = box;               
                getScene(-0.5f,0.5f,-0.5f,mouse);
                BoxThatWillBeUsed[0][0][2] = box;                
                getScene(0,0.5f,-0.5f,mouse);
                BoxThatWillBeUsed[1][0][2] = box;               
                getScene(0.5f,0.5f,-0.5f,mouse);
                BoxThatWillBeUsed[2][0][2] = box;
                
                //koniec tej pieprzonej kostki bo tak bo jestem glupi 
                
                
                //obrot kostki wzgledem srodka uniwersum LPM
                MouseRotate behavior = new MouseRotate(mouse);
                behavior.setSchedulingBounds(bounds);
                behavior.setTransformGroup(mouse);
                mouse.addChild(behavior);
                //przesuwanie kostki bez obrotu PPM
                MouseTranslate przesMysza = new MouseTranslate(mouse);
                przesMysza.setSchedulingBounds(bounds);
                mouse.addChild(przesMysza);
                // odsuwanie/przyblizanie kostki w glebi
                MouseZoom myszZoom = new MouseZoom(mouse);
                myszZoom.setSchedulingBounds(bounds);
                mouse.addChild(myszZoom);
                
                               
                group.addChild(mouse);
		universe.addBranchGraph(group);
                
		pickCanvas = new PickCanvas(canvas, group);
		pickCanvas.setMode(PickInfo.PICK_BOUNDS);
		canvas.addMouseMotionListener(this);
		canvas.addMouseListener(this);
	}
        public void positionViewer() {
		ViewingPlatform vp = universe.getViewingPlatform();
		
		Transform3D t3d = new Transform3D();
		t3d.set(new Vector3f(0.0f,0f,5.0f));
                
		vp.getViewPlatformTransform().setTransform(t3d);

	}
	public void getScene(float xpos, float ypos, float zpos,TransformGroup mouse) {
		addLights(group);
		
		box = new Box(.248f, .248f, .248f, Primitive.GENERATE_TEXTURE_COORDS,getAppearance(new Color3f(Color.red)));		 
		
		box.getShape(Box.FRONT).setAppearance(getAppearance(Color.BLUE));
		box.getShape(Box.TOP).setAppearance(getAppearance(Color.WHITE));
		box.getShape(Box.BOTTOM).setAppearance(getAppearance(new Color(255,92,0))); ;
		box.getShape(Box.RIGHT).setAppearance(getAppearance(Color.RED));
		box.getShape(Box.LEFT).setAppearance(getAppearance(Color.GREEN)); 
		box.getShape(Box.BACK).setAppearance(getAppearance(new Color3f(Color.yellow))); ;
			    
                boxTransformGroup = new TransformGroup();
		boxTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		boxTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
                
		Transform3D transform = new Transform3D();
                Vector3f MadeRubix = new Vector3f(xpos, ypos, zpos);
                transform.setTranslation(MadeRubix);
               
                boxTransformGroup.addChild(box);
                boxTransformGroup.setTransform(transform);
                
		mouse.addChild(boxTransformGroup);               		
	}
	
	public static void addLights(BranchGroup group) {
		Color3f light1Color = new Color3f(0.7f, 0.8f, 0.8f);
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
				100.0);
		Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
		DirectionalLight light1 = new DirectionalLight(light1Color,
				light1Direction);
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
		lastX=-1;
		lastY=-1;
		mouseButton = event.getButton();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {		
	}

	@Override
	public void mouseDragged(MouseEvent event) {		
		 if (mouseButton==MouseEvent.BUTTON1) return;
		 Point3d  intersectionPoint = getPosition(event);
		 if (Math.abs(intersectionPoint.x) < 0.5 && Math.abs(intersectionPoint.y) < 0.5)  {
			 double x = (0.5 + intersectionPoint.x) * imageWidth;
			 double y = (0.5 - intersectionPoint.y) * imageHeight;			 
			 
			 int iX = (int)(x + .5);
			 int iY = (int)(y + .5);
			 if (lastX < 0) {
				 lastX = iX;
				 lastY = iY;
			 }
			
			 lastX = iX;
			 lastY = iY;
                
		 }	
	}
	
	@Override
	public void mouseMoved(MouseEvent arg0) {	
	}
}
