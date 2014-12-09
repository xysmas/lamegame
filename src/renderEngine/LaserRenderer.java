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

import shaders.LaserShader;
import textures.ModelTexture;
import toolbox.MathUtil;
import entities.Laser;

public class LaserRenderer
{

  private LaserShader shader;

  public LaserRenderer(LaserShader shader, Matrix4f projectionMatrix)
  {
    this.shader = shader;

    shader.start();
    shader.loadProjectionMatrix(projectionMatrix);
    shader.stop();

  }

  public void render(Map<TexturedModel, List<Laser>> entities)
  {
    for (TexturedModel model : entities.keySet())
    {
      prepareTexturedModel(model);
      List<Laser> batch = entities.get(model);
      for (Laser Laser : batch)
      {
        prepareInstance(Laser);
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel()
            .getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
      }
      unbindTexturedModel();

    }

  }

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

  private void unbindTexturedModel()
  {
    GL20.glDisableVertexAttribArray(0);
    GL20.glDisableVertexAttribArray(1);
    GL20.glDisableVertexAttribArray(2);
    GL30.glBindVertexArray(0);
  }

  private void prepareInstance(Laser laser)
  {
    Matrix4f transformationMatrix = MathUtil.createTransformationMatrix(laser);
    shader.loadTransformationMatrix(transformationMatrix);
  }
}
