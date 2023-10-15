/* I declare that this code is my own work */
/* Author Euan Goodbrand egoodbrand1@sheffield.ac.uk */
import com.jogamp.opengl.GL3;
import gmaths.Mat4;
import gmaths.Mat4Transform;

public class Desk{

    private final GL3 gl;
    private final Model cube;
    private SGNode deskStart;

    //Desk Class
    public Desk(GL3 gl, Model cube){
        this.gl = gl;
        this.cube = cube;
        this.sceneGraph();
    }

    private void sceneGraph(){

        //Start of desk object
        deskStart = new NameNode("desk Start");
        
        //Moves desk to centre of the scene
        TransformNode deskTranslate = new TransformNode("desk transform",Mat4Transform.translate(0,0f,0));

        //Desktop
        NameNode desktop = new NameNode("desktop");
        Mat4 mesh = Mat4Transform.translate(0,5f,0);
        mesh = Mat4.multiply(Mat4Transform.scale(3,0.25f,3), Mat4Transform.translate(0,5.5f,0));
        TransformNode transformDesk =  new TransformNode("desktop transform", mesh);
        ModelNode desktopMesh = new ModelNode("Desktop Shape", cube);

        //Desk Legs
        NameNode leg1 = new NameNode("desktop");
        mesh = Mat4Transform.translate(0,7.5f,0);
        mesh = Mat4.multiply(Mat4Transform.scale(0.5f,1.5f,0.5f), Mat4Transform.translate(2.5f,0.5f,2.5f));
        TransformNode leg1Transform =  new TransformNode("leg1 transform", mesh);
        ModelNode leg1Shape = new ModelNode("Desk Leg Shape", cube);

        NameNode leg2 = new NameNode("desktop");
        mesh = Mat4Transform.translate(0,7.5f,0);
        mesh = Mat4.multiply(Mat4Transform.scale(0.5f,1.5f,0.5f), Mat4Transform.translate(-2.5f,0.5f,2.5f));
        TransformNode leg2Transform =  new TransformNode("leg2 transform", mesh);
        ModelNode leg2Shape = new ModelNode("Desk Leg Shape", cube);

        NameNode leg3 = new NameNode("desktop");
        mesh = Mat4Transform.translate(0,7.5f,0);
        mesh = Mat4.multiply(Mat4Transform.scale(0.5f,1.5f,0.5f), Mat4Transform.translate(2.5f,0.5f,-2.5f));
        TransformNode leg3Transform =  new TransformNode("leg3 transform", mesh);
        ModelNode leg3Shape = new ModelNode("Desk Leg Shape", cube);

        NameNode leg4 = new NameNode("desktop");
        mesh = Mat4Transform.translate(0,7.5f,0);
        mesh = Mat4.multiply(Mat4Transform.scale(0.5f,1.5f,0.5f), Mat4Transform.translate(-2.5f,0.5f,-2.5f));
        TransformNode leg4Transform =  new TransformNode("leg4 transform", mesh);
        ModelNode leg4Shape = new ModelNode("Desk Leg Shape", cube);


        //Hierarchal scene graph tree for the desktop
        deskStart.addChild(deskTranslate);
                deskTranslate.addChild(desktop);
                    desktop.addChild(transformDesk);
                        transformDesk.addChild(desktopMesh);
                    desktop.addChild(leg1);
                        leg1.addChild(leg1Transform);
                            leg1Transform.addChild(leg1Shape);
                    desktop.addChild(leg2);
                        leg2.addChild(leg2Transform);
                            leg2Transform.addChild(leg2Shape);
                    desktop.addChild(leg3);
                        leg3.addChild(leg3Transform);
                            leg3Transform.addChild(leg3Shape);
                    desktop.addChild(leg4);
                        leg4.addChild(leg4Transform);
                            leg4Transform.addChild(leg4Shape);
                    
        deskStart.update();

    }

    //Function to render the desk
    public void render(){
        deskStart.draw(gl);
    }
}
