package engineTester;


import java.util.HashMap;
import java.util.Map;

import models.RawModel;
import models.TexturedModel;
import renderEngine.FontLoader;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import shaders.StaticShader;
import textures.ModelTexture;

public class ModelMap {
	//generate a hashmap of all textured models that we could use in the scene and 
	//associate key them to an int as an id
	/*the current key mapping is as follows
	
	*1-> square rock asteroid
	*2->high poly rock asteroid
	*3->asteroid
	*4->ship,SciFi_FighterMK
	*5->space_frigate_6
	*
	*/
	private Map<String,TexturedModel> texturedModelList = new HashMap<>();
	
	Loader loader = new Loader();
	StaticShader shader = new StaticShader();
	RawModel text;
	ModelTexture textTx;
	
	public ModelMap(){
	    RawModel ship = OBJLoader.loadObjModel("SpaceShip3", loader, true);
	    RawModel ship2 = OBJLoader.loadObjModel("SpaceShip31", loader, true);
	    RawModel modelAsteroid = OBJLoader.loadObjModel("SquareRock3", loader,  true);
	    RawModel stoneAsteroid = OBJLoader.loadObjModel("SquareRock4", loader,  false);
	    //change text label here
	    text = FontLoader.loadFontModel("Hud test", loader,  true, 1);
	    RawModel text2 = FontLoader.loadFontModel("Asteroids!!", loader,  true, 20);
	        
	    ModelTexture shipTexture = new ModelTexture(loader.loadTexture("SciFi_FighterMK_diffuse"));
	    ModelTexture ship2Texture = new ModelTexture(loader.loadTexture("space_frigate_6_color"));
	    ModelTexture asteroidTexture = new ModelTexture(loader.loadTexture("stone_texture"));
	    ModelTexture stoneTexture = new ModelTexture(loader.loadTexture("RockRed2"));
	    //!test!
	    textTx = new ModelTexture(loader.loadTexture("font"));
	    ModelTexture textTx2 = new ModelTexture(loader.loadTexture("font"));
	    
	    TexturedModel texturedModelAsteroid = new TexturedModel(modelAsteroid,asteroidTexture);
	    TexturedModel texturedModelAsteroid2 = new TexturedModel(stoneAsteroid,asteroidTexture);
	    TexturedModel texturedModelStone = new TexturedModel(modelAsteroid,stoneTexture);
	    TexturedModel texturedShip = new TexturedModel(ship, shipTexture);
	    TexturedModel texturedShip2 = new TexturedModel(ship2, ship2Texture);
	    //!test!
	    TexturedModel texturedText = new TexturedModel(text, textTx);
	    TexturedModel texturedText2 = new TexturedModel(text2, textTx2);
      
    texturedModelList.put("A001", texturedModelAsteroid);
    texturedModelList.put("A002", texturedModelAsteroid2);
    texturedModelList.put("A003", texturedModelStone);
    texturedModelList.put("S001", texturedShip);
    texturedModelList.put("S002", texturedShip2);
    
    //!test!
    texturedModelList.put("T001", texturedText);
    texturedModelList.put("T002", texturedText2);
    
    
	}
    

	
	public Map<String, TexturedModel> getTexturedModelList() {
		return texturedModelList;
	}

	//Potential test funtion
	public TexturedModel getCompletedText(String input)
	{
	  this.text = FontLoader.loadFontModel(input, loader,  true, 20);
    return (new TexturedModel(this.text, this.textTx));
    
	}


}
