/* I declare that this code is my own work */
/* Author Euan Goodbrand egoodbrand1@sheffield.ac.uk */
import com.jogamp.opengl.GL3;
import gmaths.Mat4;
import gmaths.Vec3;
import gmaths.Mat4Transform;
import java.lang.Math;

public class Egg{

    private final GL3 gl;
    private final Model sphere;
    private final SGNode eggStart;
    private final TransformNode eggPositionCentre;
    public TransformNode eggRotate;

    public Egg(GL3 gl, Model sphere){
        this.gl = gl;
        this.sphere = sphere;

        //Start egg tree
        eggStart = new NameNode("Egg Start");

        //Position the egg in centre of scene
        eggPositionCentre = new TransformNode("Position egg", Mat4Transform.translate(0f, 0f, 0f));

        //Node for transforming egg
        TransformNode transformEgg = new TransformNode("Transform Egg",Mat4Transform.translate(0f,0f,0f));

        //Egg node
        NameNode egg = new NameNode("Egg");

        //Create mesh
        Mat4 mesh = Mat4Transform.translate(0f,0f,0f);

        //Scale Sphere to look like egg
        mesh = Mat4.multiply(Mat4Transform.scale(1.5f,2.5f,1.5f), Mat4Transform.translate(0f,1.5f,0f));

        //Node for rotating the egg
        eggRotate =  new TransformNode("Transform Egg", mesh);
        ModelNode eggObject = new ModelNode("Egg", sphere);

        //Egg hierarchal tree scene graph
        eggStart.addChild(eggPositionCentre);
            eggPositionCentre.addChild(transformEgg);
                transformEgg.addChild(egg);
                    egg.addChild(eggRotate);
                       eggRotate.addChild(eggObject);

        eggStart.update();

    }

    //Function to render the egg
    public void render(){
        eggStart.draw(gl);
        eggRotate();
    }

    //Function to make egg rotate and jump
    public void eggRotate() {
        double elapsedTime = getSeconds();
        float jumpHeight=(float)Math.sin(0);
        float rotateAngle = (180f+90f*(float)Math.sin(elapsedTime * 2)/8 - 180);
        jumpHeight = (float)(Math.sin(elapsedTime)/100);

        Mat4 mesh = eggRotate.getTransform();
        mesh = Mat4.multiply(mesh, Mat4Transform.rotateAroundY(-rotateAngle));
        mesh = Mat4.multiply(mesh, Mat4Transform.translate(0f, jumpHeight, 0f ));

        eggRotate.setTransform(mesh);
        eggRotate.update();
    }

    //Returns the time passed in the system.
    private double getSeconds() {
        return System.currentTimeMillis()/1000.0;
    }





}
