package kw.mulitplay.game.screen;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.kw.gdx.asset.Asset;

import kw.mulitplay.game.constant.Constant;
import kw.mulitplay.game.group.ImageDemo;
import kw.mulitplay.game.pianojson.JsonDataBean;
import kw.mulitplay.game.pianojson.JsonUtils;
import kw.mulitplay.game.pianojson.NoteData;
import kw.mulitplay.game.pianojson.NoteDataBean;
import kw.mulitplay.game.screen.base.BaseScreen;

public class TTScreen extends BaseScreen {

    @Override
    protected void initView() {
        JsonUtils utils = new JsonUtils();
        JsonDataBean jsonDataBean = utils.readFile();
        Array<Array<NoteDataBean>> arrayArray = jsonDataBean.getArrayArray();
        Constant.speed = 60.0f/jsonDataBean.getBaseBpm() * 150.0f;
        float baseY = 400;
        int arr[] = {1,2,3,4};
        Array<Integer> array = new Array<>();
        for (Array<NoteDataBean> noteDataBeans : arrayArray) {
            for (NoteDataBean noteDataBean : noteDataBeans) {
                ImageDemo image = new ImageDemo(Asset.getAsset().getTexture("main/white.png"));
                stage.addActor(image);
                if (array.size<=0) {
                    for (int i : arr) {
                        array.add(i);
                    }
                }
                for (NoteData node : noteDataBean.getNodes()) {
                    image.setSoundName(node.getNodeName());
                }
                Integer integer = array.get((int) (Math.random() * (array.size-1)));
                array.removeValue(integer,false);
                image.setX(100.0f * integer);
                image.setY(baseY);
                image.setBpm(noteDataBean.getBpm());
                baseY += noteDataBean.getLen()*150;
                image.setSize(100,noteDataBean.getLen()*150);
//                image.addAction(Actions.forever(Actions.moveBy(0,-36,0.1f)));
            }
        }
    }

    int key = 5; //轨道数量
    int wpos = -1;
    private int nextPos(int num, int type) {
        switch (type) {
            case 5:
                //if (num < 0) return Math.floor(Math.random() * 2) + key;
                return (num + 1) % 2 + key;
            default:
                //if (num < 0) return Math.floor(Math.random() * key);
                if (num >= key) return (int) (Math.floor(Math.random() * 2) * 2 + (num + 1) % 2);
                return (int) ((num + Math.floor(Math.random() * (key - 1)) + 1) % key);
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void back() {

    }
}
