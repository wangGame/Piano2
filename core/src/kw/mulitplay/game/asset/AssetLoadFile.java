package kw.mulitplay.game.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.kw.gdx.asset.Asset;

import java.util.HashMap;

public class AssetLoadFile {
    public static HashMap<String, Sound> hashMap = new HashMap<>();
    public static void loadSound(){
        FileHandle piano3 = Gdx.files.internal("piano3");
        for (FileHandle handle : piano3.list()) {
//            System.out.println(handle.path());
            Asset.getAsset().loadSound(handle.path());
        }
    }

//    public static void main(String[] args) {
//        loadSound();
//    }

    public static void loadFile(){
        Asset.assetManager.load("Bahnschrift-Regular_40_1.fnt", BitmapFont.class);
    }

    public static BitmapFont getBR40(){
        Asset.getAsset().assetManager.load("Bahnschrift-Regular_40_1.fnt",BitmapFont.class);
        Asset.getAsset().assetManager.finishLoading();
        return Asset.getAsset().assetManager.get("Bahnschrift-Regular_40_1.fnt",BitmapFont.class);
    }
}
