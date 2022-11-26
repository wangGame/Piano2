package kw.mulitplay.game.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.BaseGame;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.constant.Constant;
import com.kw.gdx.screen.BaseScreen;

import kw.mulitplay.game.asset.AssetLoadFile;
import kw.mulitplay.game.constant.LevelConfig;

public class LoadingScreen extends BaseScreen {
    public LoadingScreen(BaseGame game) {
        super(game);
    }

    @Override
    protected void initView() {
        super.initView();
        Image loadingBg = new Image(Asset.getAsset().getTexture("loading/cmlogo1.png"));
        loadingBg.setOrigin(Align.center);
        float max = Math.max(Constant.GAMEWIDTH / loadingBg.getWidth(), Constant.GAMEHIGHT / loadingBg.getHeight());
        loadingBg.setScale(max);
        stage.addActor(loadingBg);
        loadingBg.setPosition(Constant.GAMEWIDTH/2,Constant.GAMEHIGHT/2,Align.center);
        AssetLoadFile.loadSound();
        AssetLoadFile.loadFile();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (Asset.getAsset().assetManager.update()) {
            setScreen(new MainScreen(game));
        }
    }
}
