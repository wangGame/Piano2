package kw.mulitplay.game.group;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.utils.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kw.mulitplay.game.TimeLine;
import kw.mulitplay.game.constant.LevelConfig;

public class ImageDemo extends Group {
    private float bpm;
    public boolean userTouchPlay;
    private Array<String> array;
    private Array<TimeLine> lines = new Array<>();
    private Image black;
    private Image image;
    private boolean touchDown;
    private float touchDownY;
    private float touchY;

    public ImageDemo(Texture texture){
        image = new Image(texture);
        addActor(image);
        image.setTouchable(Touchable.disabled);
        black = new Image(texture);
        black.setSize(3,0);
        black.setTouchable(Touchable.disabled);
        black.setColor(Color.BLACK);
        addActor(black);
        array = new Array<>();
        addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(userTouchPlay)return false;
                userTouchPlay = true;
                touchDown = true;
                black.setHeight(y);
//                bf(strName,1);
//                addAction(Actions.fadeOut(0.3f));
                touchDownY = y;
                touchY = getY(Align.bottom);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                touchDown = false;
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
                float ms = 0.5f;
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
                        TimeLine line = new TimeLine();
                        line.setName(s);
                        line.setStartTime(ms * i);
                        lines.add(line);
                    }
                }else if(num != 2){
                    System.out.println("error");
                }else {
                    float ts = (float) Math.floor(ms * 0.015);
                    int flag = 0;
                    for (int i = 0; i < ts; i++) {
                        String s = zh[flag % 2];
                        s = s.replace("(","");
                        s = s.replace(")","");
                        TimeLine line = new TimeLine();
                        line.setName(s);
                        line.setStartTime(ms * i);
                        lines.add(line);
//                        if (Asset.getAsset().assetManager.isLoaded("piano3/"+s+".mp3")) {
//                            addAction(Actions.delay(i / 0.015f,Actions.run(()->{
//                                Sound o = Asset.getAsset().assetManager.get("piano3/" + s + ".mp3");
//                                o.play();
//                            })));
//                        }else {
//                            System.out.println("-------------");
//                        }
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


    private Array<TimeLine> dispose = new Array<>();

    private boolean isPass = false;
    @Override
    public void act(float delta) {
        super.act(delta);

        if (touchDown){
            float y = getY(Align.bottom);
            float v = touchY - y + touchDownY;
            black.setHeight(v);
        }


        setY(getY() - LevelConfig.speed);
        LevelConfig.newSpeed = 60.0f/bpm * 30.0f;
        if (getY()<0 && !isPass){
            isPass = true;
            for (String s : array) {
                bf(s,1);
            }
        }

        for (TimeLine line : lines) {
            boolean update = line.update(delta);
            if (update) {
                dispose.add(line);
                if (Asset.assetManager.isLoaded("piano3/"+line.getName()+".mp3")){
                    Sound sound = Asset.assetManager.get("piano3/"+line.getName()+".mp3");
                    sound.play();
                }else {
                    System.out.println("----------------"+line.getName());
                }
            }
        }
        for (TimeLine line : dispose) {
            lines.removeValue(line,false);
        }
        lines.clear();
    }


    @Override
    public Actor hit(float x, float y, boolean touchable) {
        return x >= -20 && x < width+20 && y >= -20 && y < height + 20? this : null;
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        image.setSize(width,height);
        black.setSize(width,0);
    }

    public void setBpm(float bpm) {
        this.bpm = bpm;
    }
}
