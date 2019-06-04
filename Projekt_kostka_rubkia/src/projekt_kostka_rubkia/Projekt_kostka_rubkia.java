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

class kostka_rubkia extends Applet implements KeyListener {
	 
        static GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        static Canvas3D canvas = new Canvas3D(config);
        static SimpleUniverse universe = new SimpleUniverse(canvas);
    
        TransformGroup Transformacja_scianaLewa;
        TransformGroup Transformacja_scianaSrodkowa;
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
        private TransformGroup boxTransformGroup1;
        private TransformGroup boxTransformGroup2;
        private Matrix4d matrix = new Matrix4d();
	

	public void init() {
  		startDrawing();
  	}
	private void startDrawing() {
                BoxThatWillBeUsed  = new Box[3][3][3];
                
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		canvas = new Canvas3D(config);
		universe = new SimpleUniverse(canvas);
		add("Center", canvas);
		positionViewer();
                addLights(group);
               
                
                Transformacja_scianaSrodkowa = new TransformGroup();
                Transformacja_scianaSrodkowa.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
                Transformacja_scianaPrawa= new TransformGroup();
                Transformacja_scianaPrawa.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
                Transformacja_scianaLewa = new TransformGroup();
                Transformacja_scianaLewa.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
                
                MakeCube();
                
                group.addChild(Transformacja_scianaSrodkowa);
                
                group.addChild(Transformacja_scianaPrawa);
                group.addChild(Transformacja_scianaLewa);
                universe.addBranchGraph(group);
		
                pickCanvas = new PickCanvas(canvas, group);
		
		pickCanvas.setMode(PickInfo.PICK_BOUNDS);
                canvas.addKeyListener(this);
                
                   
	}
        public void MakeCube(){
            for(float x=0;x<3;x++)
                {
                    for(float y=0;y<3;y++)
                    {
                        for(float z=0;z<3;z++)
                        {
                             getScene(x-1,y-1,z-1);
                        }
                    }
                }
            
            
        }
        
        public void positionViewer() {
		ViewingPlatform vp = universe.getViewingPlatform();
                
		OrbitBehavior orbit = new OrbitBehavior(canvas, OrbitBehavior.REVERSE_ROTATE);
                orbit.setSchedulingBounds(new BoundingSphere());
                
		Transform3D t3d = new Transform3D();
		t3d.set(new Vector3f(0.0f,0f,10.0f));
                
		vp.getViewPlatformTransform().setTransform(t3d);
                vp.setViewPlatformBehavior(orbit);
	}
	public void getScene(float xpos, float ypos, float zpos) {
		
		box = new Box(.498f, .498f, .498f, Primitive.GENERATE_TEXTURE_COORDS,getAppearance(new Color3f(Color.red)));		 
		
		box.getShape(Box.FRONT).setAppearance(getAppearance(Color.BLUE));
		box.getShape(Box.TOP).setAppearance(getAppearance(Color.WHITE));
		box.getShape(Box.BOTTOM).setAppearance(getAppearance(new Color(255,92,0)));
		box.getShape(Box.RIGHT).setAppearance(getAppearance(Color.RED));
		box.getShape(Box.LEFT).setAppearance(getAppearance(Color.GREEN)); 
		box.getShape(Box.BACK).setAppearance(getAppearance(new Color3f(Color.yellow)));
                
		Transform3D transform = new Transform3D();
                Vector3f MadeRubix = new Vector3f(xpos, ypos, zpos);
                transform.setTranslation(MadeRubix);
             
                boxTransformGroup = new TransformGroup();
                boxTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
              
                boxTransformGroup1 = new TransformGroup();
                boxTransformGroup1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
              
                boxTransformGroup2 = new TransformGroup();
                boxTransformGroup2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
                
           if(xpos == 0.0){
              boxTransformGroup1.addChild(box);
               boxTransformGroup1.setTransform(transform);
               Transformacja_scianaSrodkowa.addChild(boxTransformGroup1);
           }
           else if(xpos == 1.0){
               boxTransformGroup.addChild(box);
               boxTransformGroup.setTransform(transform);
               Transformacja_scianaPrawa.addChild(boxTransformGroup);
           }
           else {
               
               boxTransformGroup2.addChild(box);
               boxTransformGroup2.setTransform(transform);
               Transformacja_scianaLewa.addChild(boxTransformGroup2);
              
            }
          
           
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
		Appearance ap = new Appearance();
		ColoringAttributes ca = new ColoringAttributes(color,ColoringAttributes.NICEST);
		ap.setColoringAttributes(ca);
		return ap;
	}
	
    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_E){
          MakeCube();
          Transform3D t3d = new Transform3D();
          t3d.set(new Vector3f(0.0f,0f,10.0f));
          obrot1.rotX(Math.PI/2);
          Transformacja_scianaPrawa.getTransform(t3d);
          t3d.get(matrix);
          t3d.setTranslation(new Vector3d(0.0,0.0,0.0));
          t3d.mul(obrot1);
          t3d.setTranslation(new Vector3d(matrix.m03,matrix.m13,matrix.m23));
          Transformacja_scianaPrawa.setTransform(t3d);
          
        }
         if(e.getKeyCode() == KeyEvent.VK_R){
             MakeCube();
          Transform3D t3d = new Transform3D();
          t3d.set(new Vector3f(0.0f,0f,10.0f));
          obrot1.rotX(-Math.PI/2);
          Transformacja_scianaPrawa.getTransform(t3d);
          t3d.get(matrix);
          t3d.setTranslation(new Vector3d(0.0,0.0,0.0));
          t3d.mul(obrot1);
          t3d.setTranslation(new Vector3d(matrix.m03,matrix.m13,matrix.m23));
          Transformacja_scianaPrawa.setTransform(t3d);
        }
        if(e.getKeyCode() == KeyEvent.VK_Q){
            MakeCube();
         Transform3D t3d = new Transform3D();
          t3d.set(new Vector3f(0.0f,0f,10.0f));
          obrot1.rotX(Math.PI/2);
          Transformacja_scianaLewa.getTransform(t3d);
          t3d.get(matrix);
          t3d.setTranslation(new Vector3d(0.0,0.0,0.0));
          t3d.mul(obrot1);
          t3d.setTranslation(new Vector3d(matrix.m03,matrix.m13,matrix.m23));
          Transformacja_scianaLewa.setTransform(t3d);
         
        }
         if(e.getKeyCode() == KeyEvent.VK_W){
           Transformacja_scianaLewa.removeAllChildren();
           MakeCube();
         Transform3D t3d = new Transform3D();
          t3d.set(new Vector3f(0.0f,0f,10.0f));
          obrot1.rotX(-Math.PI/2);
          Transformacja_scianaLewa.getTransform(t3d);
          t3d.get(matrix);
          t3d.setTranslation(new Vector3d(0.0,0.0,0.0));
          t3d.mul(obrot1);
          t3d.setTranslation(new Vector3d(matrix.m03,matrix.m13,matrix.m23));
          Transformacja_scianaLewa.setTransform(t3d);
         
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
       
    }
       
}
public class Projekt_kostka_rubkia {
    
    /**
     * Główna metoda klasy. W niej tworzony jest robot i dodawany jest KeyListener;
     * @param args 
     */
    public static void main(String[] args) {
        kostka_rubkia Rubix = new kostka_rubkia();
        Rubix.addKeyListener(Rubix);
        MainFrame mf = new MainFrame(Rubix, 640, 480);
    }   
}