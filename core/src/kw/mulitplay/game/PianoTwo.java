package kw.mulitplay.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kw.gdx.BaseGame;

import kw.mulitplay.game.constant.LevelConfig;
import kw.mulitplay.game.screen.GameScreen;
import kw.mulitplay.game.screen.LoadingScreen;

public class PianoTwo extends BaseGame {
    @Override
    protected void loadingView() {
        super.loadingView();
        setScreen(new LoadingScreen(this));
    }
}
