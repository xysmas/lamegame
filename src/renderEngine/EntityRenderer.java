package renderEngine;

import java.util.List;
import java.util.Map;

import models.RawModel;
import models.TexturedModel;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import shaders.StaticShader;
import textures.ModelTexture;
import toolbox.MathUtil;
import entities.Entity;

public class EntityRenderer
{

  private StaticShader shader;

  /**
   * Create an entity renderer with a shader and load a projection matrix into it
   * @param shader
   * @param projectionMatrix
   */
  public EntityRenderer(StaticShader shader, Matrix4f projectionMatrix)
  {
    this.shader = shader;

    shader.start();
    shader.loadProjectionMatrix(projectionMatrix);
    shader.stop();
  }

  /**
   * render all models in the entities list to the screen
   * @param entities
   */
  public void render(Map<TexturedModel, List<Entity>> entities)
  {
    for (TexturedModel model : entities.keySet())
    {
      prepareTexturedModel(model);
      List<Entity> batch = entities.get(model);
      for (Entity entity : batch)
      {
        prepareInstance(entity);
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel()
            .getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
      }
      unbindTexturedModel();
    }
  }

  /**
   * Bind a textured model
   * @param model
   */
  private void prepareTexturedModel(TexturedModel model)
  {
    RawModel rawModel = model.getRawModel();
    GL30.glBindVertexArray(rawModel.getVaoID());

    GL20.glEnableVertexAttribArray(0);
    GL20.glEnableVertexAttribArray(1);
    GL20.glEnableVertexAttribArray(2);
    ModelTexture texture = model.getTexture();
    shader.loadShineVariables(texture.getShadeDamper(),
        texture.getReflectivity());

    GL13.glActiveTexture(GL13.GL_TEXTURE0);
    GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureID());
  }

  /**
   * Unbind a textured model
   */
  private void unbindTexturedModel()
  {
    GL20.glDisableVertexAttribArray(0);
    GL20.glDisableVertexAttribArray(1);
    GL20.glDisableVertexAttribArray(2);
    GL30.glBindVertexArray(0);
  }

  /**
   * Load an entity into the transformation matrix
   * @param entity
   */
  private void prepareInstance(Entity entity)
  {
    shader.loadDrawShadow(entity.drawShadow);
    Matrix4f transformationMatrix = MathUtil.createTransformationMatrix(entity);
    shader.loadTransformationMatrix(transformationMatrix);
  }
}
