package kw.mulitplay.game.group;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.utils.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kw.mulitplay.game.constant.LevelConfig;

public class ImageDemo extends Image {
    private float bpm;
    public boolean userTouchPlay;
    private Array<String> array;

    public ImageDemo(Texture texture){
        super(texture);
        setDebug(true);
        array = new Array<>();
        addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(userTouchPlay)return false;
                userTouchPlay = true;
//                bf(strName,1);
                addAction(Actions.fadeOut(0.3f));
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    public void setSoundName(String str){
        if (str == null){
            System.out.println("---err----");
        }
        array.add(str);
    }


    public static void main(String[] args) {
        String str = "(#a1~#d1~#a1)";
//        bf(str,1);
    }

    private float bf(String str,int len) {
        System.out.println(str);
        Pattern pattern = Pattern.compile("(?<=\\().+(?=\\))");
        if (str == null){
            return 0;
        }
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            String group = matcher.group(0);
            Pattern compile = Pattern.compile("[~@&^$%!]");
            Matcher matcher1 = compile.matcher(group);
            if (matcher1.find()) {
                String group1 = matcher1.group();
                System.out.println(group1);
                char sh = group1.charAt(0);
                String []zh = str.split(sh+"");
                int num = zh.length;
                float ms = 0;
                boolean tr = false;
                switch (sh) {
                    case '~':
                        ms /= num;
                        break;
                    case '@':
                        ms *= (num * 0.4 - 1) / (num * 3 - 8) / (num - 1);
                        break;
                    case '&':
                    case '^':
                        tr = true;
                        break;
                    case '$':
                        ms *= 0.99 / num;
                        break;
                    case '%':
                        ms *= 0.3 / (num - 1);
                        break;
                    case '!':
                        ms *= 0.1485 / (num - 1);
                        break;
                }

                if (!tr){
                    for (int i = 0; i < zh.length; i++) {
                        String s = zh[i];
                        s = s.replace("(","");
                        s = s.replace(")","");
                        if (Asset.getAsset().assetManager.isLoaded("piano3/"+s+".mp3")) {
                            addAction(Actions.delay(i / 0.015f,Actions.run(()->{
//                                Sound o = Asset.getAsset().assetManager.get("piano3/" + s + ".mp3");
//                                o.play();
                            })));
                        }else {
                            System.out.println("-----------------");
                        }
                    }
                }else if(num != 2){
                    System.out.println("error");
                }else {
                    float ts = (float) Math.floor(ms * 0.015);
                    int flag = 0;
                    for (int i = 0; i < ts; i++) {
                        String s = zh[flag % 2];
                        if (Asset.getAsset().assetManager.isLoaded("piano3/"+s+".mp3")) {
                            addAction(Actions.delay(i / 0.015f,Actions.run(()->{
                                Sound o = Asset.getAsset().assetManager.get("piano3/" + s + ".mp3");
                                o.play();
                            })));
                        }else {
                            System.out.println("-------------");
                        }
                        flag++;
                    }
                }
            }else {
                System.out.println("------------22222222222222222---------------------");
                if (str.contains(".")){
                    String[] split = str.split("\\.");
                    for (String s : split) {
                        s = s.replace("(","");
                        s = s.replace(")","");
                        if (Asset.assetManager.isLoaded("piano3/"+s+".mp3")) {
                            Sound sound = Asset.getAsset().assetManager.get("piano3/"+s+".mp3");
                            sound.play();
                        }else {
                            System.out.println("not found "+ s);
                        }
                    }
                }else {
                    str = str.replace("(","");
                    str = str.replace(")","");

                    if (Asset.assetManager.isLoaded("piano3/"+str+".mp3")){
                        Sound sound = Asset.assetManager.get("piano3/"+str+".mp3");
                        sound.play();
                    }else {
                        System.out.println("----------------");
                    }
                }
            }
        }else {
            str = str.replace("(","");
            str = str.replace(")","");

            System.out.println(""+str);
            if (Asset.assetManager.isLoaded("piano3/"+str+".mp3")){
                Sound sound = Asset.assetManager.get("piano3/"+str+".mp3");
                sound.play();
            }else {
                System.out.println("----------------");
            }
        }
        return 0;
    }



    private boolean isPass = false;
    @Override
    public void act(float delta) {
        super.act(delta);
        setY(getY() - LevelConfig.speed);
        LevelConfig.newSpeed = 60.0f/bpm * 60.0f;
        if (getY()<0 && !isPass){
            isPass = true;
            for (String s : array) {
                bf(s,1);
            }
        }
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        return x >= -20 && x < width+20 && y >= -20 && y < height + 20? this : null;
    }

    public void setBpm(float bpm) {
        this.bpm = bpm;
    }
}
