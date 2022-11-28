package kw.mulitplay.game.group;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.utils.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kw.mulitplay.game.TimeLine;
import kw.mulitplay.game.constant.LevelConfig;
import kw.mulitplay.game.pianojson.NoteData;

public class ImageDemo extends Group {
    private float bpm;
    public boolean userTouchPlay;
    private Array<TimeLine> array;
    private Array<TimeLine> lines = new Array<>();
    private Image black;
    private Image image;
    private boolean touchDown;
    private float touchDownY;
    private float touchY;
    private StringBuilder builder = new StringBuilder();

    public ImageDemo(NinePatch texture){
        array = new Array<>();
        image = new Image(texture);
        addActor(image);
        image.setTouchable(Touchable.disabled);
        black = new Image(new NinePatch(Asset.getAsset().getTexture("gamescreen/touch_0.png"),
                0,0,230,10));
        black.setTouchable(Touchable.disabled);
        black.setVisible(false);
        addActor(black);
        addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(userTouchPlay)return false;
                userTouchPlay = true;
                touchDown = true;
                black.setHeight(y);
                System.out.println(builder.toString());
                builder.setLength(0);
                touchDownY = y;
                touchY = getY(Align.bottom);
                isPass = true;
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                touchDown = false;
            }
        });
    }

    public void setNodeInfo(NoteData nodeInfo){
        if (nodeInfo == null){
            System.out.println("---err----");
            return;
        }
        TimeLine line = new TimeLine();
        line.setStartTime(nodeInfo.getStart());
        line.setName(nodeInfo.getNodeName());
        array.add(line);
        bf(nodeInfo.getNodeName(),nodeInfo.getEnd(),nodeInfo.getStart());
    }

    private float bf(String str,float len,float start) {

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
                char sh = group1.charAt(0);
                String []zh = str.split(sh+"");
                int num = zh.length;
                float ms = len;
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
                        line.setStartTime(start+ms * i);
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
                        flag++;
                    }
                }
            }else {
                if (str.contains(".")){
                    String[] split = str.split("\\.");
                    for (String s : split) {
                        s = s.replace("(","");
                        s = s.replace(")","");
                        TimeLine line = new TimeLine();
                        line.setStartTime(start);
                        line.setName(s);
                        lines.add(line);
                    }
                }else {
                    str = str.replace("(","");
                    str = str.replace(")","");
                    TimeLine line = new TimeLine();
                    line.setStartTime(start);
                    line.setName(str);
                    lines.add(line);
                }
            }
        }else {
            str = str.replace("(","");
            str = str.replace(")","");
            TimeLine line = new TimeLine();
            line.setName(str);
            line.setStartTime(start);
            lines.add(line);
        }
        return 0;
    }


    private Array<TimeLine> dispose = new Array<>();

    private boolean isPass = false;
    private float minusTime;
    private final float delta = 0.01667f;
    private float timeScale = 1.0f;
    @Override
    public void act(float delta1) {
        super.act(delta1);
        minusTime+=delta1;
        if (minusTime>=0.01667f) {
            minusTime -= 0.01667f;
            if (touchDown) {

                black.setVisible(true);
                float y = getY(Align.bottom);
                float v = touchY - y + touchDownY + 100;
                if (v < 260) {
                    v = 260;
                }
                if (v > getHeight()) {
                    v = getHeight();
                }
                black.setHeight(v);
            }

            if (LevelConfig.gameStatus == LevelConfig.running) {
                setY(getY() - LevelConfig.speed);
                if (getY() < 0 && !isPass) {
                    LevelConfig.passNum++;
                    if (LevelConfig.passNum <15){
                        timeScale = 1.0f;
                    }else if (LevelConfig.passNum < 30){
                        timeScale = 1.2f;
                    }else if (LevelConfig.passNum < 45){
                        timeScale = 1.4f;
                    }else if (LevelConfig.passNum <60){
                        timeScale = 1.5f;
                    }else {
                        timeScale = 1.55f;
                    }
                    isPass = true;
                    touchDown = true;
                    LevelConfig.newSpeed = 210 * 6 * delta * timeScale;
                }
                if (
                        lines.size <= 0
                ) {
                    if (getY(Align.top) < 0) {
                        remove();
                    }
                }
            }
            if (isPass) {
                for (TimeLine line : lines) {
                    boolean update = line.update(delta*timeScale);
                    if (update) {
                        dispose.add(line);
                        if (Asset.assetManager.isLoaded("piano3/" + line.getName() + ".mp3")) {
                            Sound sound = Asset.assetManager.get("piano3/" + line.getName() + ".mp3");
                            sound.play();
                        } else {
                            System.out.println("----------------" + line.getName());
                        }
                    }
                }
            }


            for (TimeLine line : dispose) {
                lines.removeValue(line, false);
            }
            dispose.clear();
        }
    }


    @Override
    public Actor hit(float x, float y, boolean touchable) {
        return x >= -20 && x < width+20 && y >= -20 && y < height + 20? this : null;
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        image.setHeight(height);
        black.setHeight(0);
        black.setX(getWidth()/2,Align.center);
    }

    public void setBpm(float bpm) {
        this.bpm = bpm;
    }
}
