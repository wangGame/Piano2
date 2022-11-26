package kw.mulitplay.game.screen;

import com.badlogic.gdx.utils.Array;
import com.kw.gdx.BaseGame;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.constant.Constant;
import com.kw.gdx.screen.BaseScreen;

import kw.mulitplay.game.constant.LevelConfig;
import kw.mulitplay.game.group.ImageDemo;
import kw.mulitplay.game.pianojson.MusicDataBean;
import kw.mulitplay.game.pianojson.JsonUtils;
import kw.mulitplay.game.pianojson.NoteData;
import kw.mulitplay.game.pianojson.NoteDatas;

public class GameScreen extends BaseScreen {

    public GameScreen(BaseGame game) {
        super(game);
    }

    @Override
    public void initView() {
        JsonUtils utils = new JsonUtils();
        MusicDataBean musicDataBean = utils.readFile();
        Array<Array<NoteDatas>> arrayArray = musicDataBean.getArrayArray();
        LevelConfig.speed = 60.0f/ musicDataBean.getBaseBpm() * 60;
        LevelConfig.newSpeed = LevelConfig.speed;
        float baseY = 400;
        int arr[] = {0,1,2,3};
        Array<Integer> array = new Array<>();
        for (Array<NoteDatas> noteDataBeans : arrayArray) {
            for (NoteDatas noteDatas : noteDataBeans) {
                ImageDemo image = new ImageDemo(Asset.getAsset().getTexture("main/white.png"));
                stage.addActor(image);
                if (array.size<=0) {
                    for (int i : arr) {
                        array.add(i);
                    }
                }
                for (NoteData node : noteDatas.getNodes()) {
                    image.setSoundName(node.getNodeName());
                }
                Integer integer = array.get((int) (Math.random() * (array.size-1)));
                array.removeValue(integer,false);
                float v = Constant.GAMEWIDTH / 4;
                image.setX(v * integer);
                image.setSize(v, noteDatas.getLen()*450);
                image.setY(baseY);
                image.setBpm(noteDatas.getBpm());
                baseY += noteDatas.getLen()*450;
            }
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (LevelConfig.speed!=LevelConfig.newSpeed){
            LevelConfig.speed = LevelConfig.newSpeed;
        }
    }
}
