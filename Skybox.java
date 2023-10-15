/* I declare that this code is my own work */
/* Author Euan Goodbrand egoodbrand1@sheffield.ac.uk */
import com.jogamp.opengl.GL3;
import gmaths.Mat4;
import gmaths.Vec3;

import gmaths.Mat4Transform;

public class Skybox{

    private final GL3 gl;
    private final Model skybox;
    private SGNode skyboxStart;

    public TransformNode rotateNode;

    double startTime;

    //Skybox class
    public Skybox(GL3 gl, Model skybox, double startTime){
        this.gl = gl;
        this.skybox = skybox;
        this.sceneGraph();
        this.startTime = startTime;
    }

    private void sceneGraph(){
        
        //Begins skybox model
        skyboxStart = new NameNode("Skybox Start");

        //Puts skybox in centre of scene
        TransformNode transformSkybox = new TransformNode("Transform Skybox",Mat4Transform.translate(0,0f,0));

        //Skybox Node
        NameNode skyboxNode = new NameNode("skybox");

        //Creates mesh for skybox
        Mat4 mesh = Mat4Transform.translate(0,0f,0);
        mesh = Mat4.multiply(Mat4Transform.scale(50,50,50), Mat4Transform.translate(0,0,0));

        //Transform node for rotating skybox
        rotateNode =  new TransformNode("Rotate Skybox", mesh);
        
        //Skybox shape
        ModelNode skyboxObject = new ModelNode("Skybox Model", skybox);

        //Hierarchal tree scene graph for skybox
        skyboxStart.addChild(transformSkybox);
            transformSkybox.addChild(skyboxNode);
                skyboxNode.addChild(rotateNode);
                    rotateNode.addChild(skyboxObject);

        skyboxStart.update();

    }

    //Function to render skybox
    public void render(){
        skyboxStart.draw(gl);
        skyboxRotate();
    }


    //Fucntion to rotate skybox
    public void skyboxRotate() {

        double elapsedTime = getSeconds()- startTime;

        Mat4 mesh = rotateNode.getTransform();
        mesh = Mat4.multiply(mesh, Mat4Transform.rotateAroundY(0.3f));

        rotateNode.setTransform(mesh);
        rotateNode.update();

    }

    //Fucntion to return time of system
    private double getSeconds() {
        return System.currentTimeMillis()/1000.0;
    }





}
