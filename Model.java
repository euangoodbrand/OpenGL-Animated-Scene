import gmaths.*;
import java.nio.*;
import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;

import java.util.List;
import java.util.Collections;

public class Model {
  
  private final Mesh mesh;
  private final int[] textureId1;
  private final int[] textureId2;
  private final Material material;
  private final Shader shader;
  private Mat4 modelMatrix;
  private Camera camera;
  private final List<Light> allLight;

  
  public Model(GL3 gl, Camera camera, List<Light> allLight, Shader shader, Material material, Mat4 modelMatrix, Mesh mesh, int[] textureId1, int[] textureId2) {
    this.mesh = mesh;
    this.material = material;
    this.modelMatrix = modelMatrix;
    this.shader = shader;
    this.camera = camera;
    this.allLight = allLight;


    this.textureId1 = textureId1;
    this.textureId2 = textureId2;


  }
  
  //Changed class input to accept multiple lights
  public Model(GL3 gl, Camera camera, List<Light> allLight, Shader shader, Material material, Mat4 modelMatrix, Mesh mesh, int[] textureId1) {
    this(gl, camera, allLight, shader, material, modelMatrix, mesh, textureId1, null);
  }
  
  public Model(GL3 gl, Camera camera, List<Light> allLight, Shader shader, Material material, Mat4 modelMatrix, Mesh mesh) {
    this(gl, camera, allLight, shader, material, modelMatrix, mesh, null, null);
  }
  
  public void setModelMatrix(Mat4 m) {
    modelMatrix = m;
  }
  
  public void setCamera(Camera camera) {
    this.camera = camera;
  }
  
  public void setLight(Light allLight) {
    this.allLight.add(allLight);
  }

  public void render(GL3 gl, Mat4 modelMatrix) {
    Mat4 mvpMatrix = Mat4.multiply(camera.getPerspectiveMatrix(), Mat4.multiply(camera.getViewMatrix(), modelMatrix));
    shader.use(gl);
    shader.setFloatArray(gl, "model", modelMatrix.toFloatArrayForGLSL());
    shader.setFloatArray(gl, "mvpMatrix", mvpMatrix.toFloatArrayForGLSL());
    
    shader.setVec3(gl, "viewPos", camera.getPosition());

    //Added shader vectors for each light in scene
    //Global Light 1
    shader.setVec3(gl, "globalLight1.position", allLight.get(0).getPosition());
    shader.setVec3(gl, "globalLight1.ambient", allLight.get(0).getMaterial().getAmbient());
    shader.setVec3(gl, "globalLight1.diffuse", allLight.get(0).getMaterial().getDiffuse());
    shader.setVec3(gl, "globalLight1.specular", allLight.get(0).getMaterial().getSpecular());    
    shader.setFloat(gl,"globalLight1.onOffState", allLight.get(0).getIntensity());

    //Global Light 2
    shader.setVec3(gl, "globalLight2.position", allLight.get(1).getPosition());
    shader.setVec3(gl, "globalLight2.ambient", allLight.get(1).getMaterial().getAmbient());
    shader.setVec3(gl, "globalLight2.diffuse", allLight.get(1).getMaterial().getDiffuse());
    shader.setVec3(gl, "globalLight2.specular", allLight.get(1).getMaterial().getSpecular());
    shader.setFloat(gl,"globalLight2.onOffState", allLight.get(1).getIntensity());

    //Lamp 1
    shader.setVec3(gl, "lampLight1.position", allLight.get(2).getPosition());
    shader.setVec3(gl, "lampLight1.ambient", allLight.get(2).getMaterial().getAmbient());
    shader.setVec3(gl, "lampLight1.diffuse", allLight.get(2).getMaterial().getDiffuse());
    shader.setVec3(gl, "lampLight1.specular", allLight.get(2).getMaterial().getSpecular());
    shader.setVec3(gl, "lampLight1.direction", allLight.get(2).getDirection());
    shader.setFloat(gl,"lampLight1.onOffState", allLight.get(2).getIntensity());
    shader.setFloat(gl,"lampLight1.cutOff1", (float)Math.cos(Math.toRadians(12.5f)));
    shader.setFloat(gl,"lampLight1.cutOff2", (float)Math.cos(Math.toRadians(17.5f)));

    //Lamp2
    shader.setVec3(gl, "lampLight2.position", allLight.get(3).getPosition());
    shader.setVec3(gl, "lampLight2.ambient", allLight.get(3).getMaterial().getAmbient());
    shader.setVec3(gl, "lampLight2.diffuse", allLight.get(3).getMaterial().getDiffuse());
    shader.setVec3(gl, "lampLight2.specular", allLight.get(3).getMaterial().getSpecular());
    shader.setVec3(gl, "lampLight2.direction", allLight.get(3).getDirection());
    shader.setFloat(gl,"lampLight2.onOffState", allLight.get(3).getIntensity());
    shader.setFloat(gl,"lampLight2.cutOff1", (float)Math.cos(Math.toRadians(12.5f)));
    shader.setFloat(gl,"lampLight2.cutOff2", (float)Math.cos(Math.toRadians(17.5f)));

    shader.setVec3(gl, "material.ambient", material.getAmbient());
    shader.setVec3(gl, "material.diffuse", material.getDiffuse());
    shader.setVec3(gl, "material.specular", material.getSpecular());
    shader.setFloat(gl, "material.shininess", material.getShininess());  
    
    if (textureId1!=null) {
      shader.setInt(gl, "first_texture", 0);  // be careful to match these with GL_TEXTURE0 and GL_TEXTURE1
      gl.glActiveTexture(GL.GL_TEXTURE0);
      gl.glBindTexture(GL.GL_TEXTURE_2D, textureId1[0]);
    }
    if (textureId2!=null) {
      shader.setInt(gl, "second_texture", 1);
      gl.glActiveTexture(GL.GL_TEXTURE1);
      gl.glBindTexture(GL.GL_TEXTURE_2D, textureId2[0]);
    }
    mesh.render(gl);
  } 
  
  public void render(GL3 gl) {
    render(gl, modelMatrix);
  }

  public Material getMaterial(){
    return this.material;
  }
  
  public void dispose(GL3 gl) {
    mesh.dispose(gl);
    if (textureId1!=null) gl.glDeleteBuffers(1, textureId1, 0);
    if (textureId2!=null) gl.glDeleteBuffers(1, textureId2, 0);
  }
  
}