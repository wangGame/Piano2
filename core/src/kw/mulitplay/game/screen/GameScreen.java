package kw.mulitplay.game.screen;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.kw.gdx.BaseGame;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.constant.Constant;
import com.kw.gdx.screen.BaseScreen;
import com.kw.gdx.view.dialog.base.BaseDialog;

import kw.mulitplay.game.constant.LevelConfig;
import kw.mulitplay.game.group.ImageDemo;
import kw.mulitplay.game.pianojson.JsonUtils1;
import kw.mulitplay.game.pianojson.MusicDataBean;
import kw.mulitplay.game.pianojson.NoteData;
import kw.mulitplay.game.pianojson.NoteDatas;

public class GameScreen extends BaseScreen {
    private float baseY = 0;
    private Array<Array<NoteDatas>> nodeData;
    public GameScreen(BaseGame game) {
        super(game);
    }

    @Override
    public void initView() {
        initBg();
        float v = Constant.GAMEWIDTH / 4;
//        image.setX(v * integer);
        for (int i = 1; i <= 3; i++) {
            Image line = new Image(Asset.getAsset().getTexture("main/white.png"));
            addActor(line);
            line.setWidth(2);
            line.setHeight(Constant.GAMEHIGHT);
            line.setX(v * i);
        }
        JsonUtils1 utils = new JsonUtils1();
        MusicDataBean musicDataBean = utils.readFile();
        nodeData = musicDataBean.getArrayArray();
        LevelConfig.speed = 60.0f/ musicDataBean.getBaseBpm() * 60;
        LevelConfig.newSpeed = LevelConfig.speed;
        int arr[] = {0,1,2,3};
        Array<Integer> array = new Array<>();
        Group nodeView = new Group();
        stage.addActor(nodeView);
        nodeView.setSize(Constant.GAMEWIDTH,Constant.GAMEHIGHT);
        ImageDemo startNode = new ImageDemo(new NinePatch(Asset.getAsset().getTexture("gamescreen/black_0.png"), 0,0,10,230));
        startNode.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                LevelConfig.gameStatus = LevelConfig.running;
            }
        });
        if (array.size<=0) {
            for (int i : arr) {
                array.add(i);
            }
        }
        Integer integer = array.get((int) (Math.random() * (array.size-1)));
        array.removeValue(integer,false);
        nodeView.addActor(startNode);
        startNode.setY(baseY);
        startNode.setBpm(musicDataBean.getBaseBpm());
        baseY += 450;
        startNode.setSize(v,450);
        for (Array<NoteDatas> noteDataBeans : nodeData) {
            for (NoteDatas noteDatas : noteDataBeans) {
                ImageDemo image = new ImageDemo(
                        new NinePatch(
                                Asset.getAsset().getTexture("gamescreen/black_0.png"),
                                0,0,10,230)
                );
                nodeView.addActor(image);
                if (array.size<=0) {
                    for (int i : arr) {
                        array.add(i);
                    }
                }
                for (NoteData node : noteDatas.getNodes()) {
                    image.setNodeInfo(node);
                }
                if (noteDatas.getNodes().size<=0){
                    image.setVisible(false);
                    image.setTouchable(Touchable.disabled);
                }
                integer = array.get((int) (Math.random() * (array.size-1)));
                array.removeValue(integer,false);
                image.setX(v * integer);
                image.setSize(v, noteDatas.getLen()*450);
                image.setY(baseY);
                image.setBpm(noteDatas.getBpm());
                baseY += noteDatas.getLen()*450;
            }
        }
    }

    private void initBg() {
        Image bg = new Image(Asset.getAsset().getTexture("mainscreen/bg.jpg"));
        addActor(bg);
        bg.setOrigin(Align.center);
        float max = Math.max(Constant.GAMEWIDTH / bg.getWidth(), Constant.GAMEHIGHT / bg.getHeight());
        bg.setScale(max);
        bg.setPosition(Constant.GAMEWIDTH/2,Constant.GAMEHIGHT/2,Align.center);
    }

    //450


    @Override
    public void render(float delta) {
        super.render(delta);
        if (LevelConfig.speed!=LevelConfig.newSpeed){
            LevelConfig.speed = LevelConfig.newSpeed;
        }
        System.out.println(LevelConfig.speed);
    }

    @Override
    protected BaseDialog back() {
        setScreen(new MainScreen(game));
        return super.back();
    }
}
