package kw.mulitplay.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.reflect.Field;
import com.kw.gdx.BaseGame;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.constant.Constant;
import com.kw.gdx.screen.BaseScreen;
import com.kw.gdx.view.dialog.base.BaseDialog;

import kw.mulitplay.game.constant.LevelConfig;
import kw.mulitplay.game.group.ImageDemo;
import kw.mulitplay.game.group.ItemGroup;

public class MainScreen extends BaseScreen {
    public MainScreen(BaseGame game) {
        super(game);
    }

    @Override
    protected void initView() {
        Image image = new Image(Asset.getAsset().getTexture("mainscreen/bg.jpg"));
        addActor(image);
        Image theme = new Image(Asset.getAsset().getTexture("mainscreen/theme.png"));
        addActor(theme);
        theme.setPosition(100,Constant.GAMEHIGHT-75,Align.center);
        Table table = new Table(){{
            FileHandle song = Gdx.files.internal("song");
            int index = 0;
            for (FileHandle handle : song.list()) {
                add(new ItemGroup(handle, new ItemButtonListener() {
                    @Override
                    public void callback() {
                        setScreen(new GameScreen(game));
                    }
                },index)).padBottom(20);
                row();
            }
            setPosition(Constant.GAMEWIDTH/2,Constant.GAMEHIGHT/2,Align.center);
        }};
        ScrollPane pane = new ScrollPane(table,new ScrollPane.ScrollPaneStyle());
        addActor(pane);
        pane.setSize(Constant.GAMEWIDTH,Constant.GAMEHIGHT-150);
    }


    @Override
    protected BaseDialog back() {
        return super.back();
    }
}
