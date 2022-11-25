package kw.mulitplay.game.group;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

import kw.mulitplay.game.constant.Constant;

public class ImageDemo extends Image {
    private Array<Sound> array;
    private float bpm;
    public ImageDemo(Texture texture){
        super(texture);
        array = new Array<>();
    }

    private String strName;
    public void setSoundName(String str){
        this.strName = str;
        if (strName.contains(".")){
            String[] split = strName.split("\\.");
            for (String s : split) {
                s = s.replace("(","");
                s = s.replace(")","");
                if (Gdx.files.internal("piani3/"+s+".mp3").exists()) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("piani3/"+s+".mp3"));
                    array.add(sound);
                }else {
                    System.out.println("not found "+ s);
                }
            }
        }else {
            if (Gdx.files.internal("piani3/"+str+".mp3").exists()){
                Sound sound = Gdx.audio.newSound(Gdx.files.internal("piani3/"+str+".mp3"));
                array.add(sound);
            }
        }
    }

    private boolean isTouch;
    @Override
    public void act(float delta) {
        super.act(delta);
        setY(getY() - Constant.speed);
        System.out.println(Constant.speed);
        if (getY()<300&&!isTouch) {
            isTouch = true;
            Constant.speed = 60.0f/bpm * 30.0f;
            System.out.println("-----------------"+strName);
            for (Sound sound : array) {
                sound.play();
            }
            addAction(Actions.fadeOut(0.3f));
        }
    }

    public void setBpm(float bpm) {
        System.out.println(bpm+"-------------------");
        this.bpm = bpm;
    }
}
