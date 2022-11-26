package kw.mulitplay.game.group;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.asset.Asset;

import kw.mulitplay.game.asset.AssetLoadFile;
import kw.mulitplay.game.constant.LevelConfig;
import kw.mulitplay.game.screen.ItemButtonListener;

public class ItemGroup extends Group {
    public ItemGroup(FileHandle fileName, ItemButtonListener itemButtonListener){
        Image image = new Image(Asset.getAsset().getTexture("levelitem/color_select_bg.png"));
        addActor(image);
        setSize(image.getWidth(),image.getHeight());
        Label songLabel = new Label(fileName.name(),new Label.LabelStyle(){{
            font = AssetLoadFile.getBR40();
        }});
        addActor(songLabel);
        songLabel.setColor(Color.BLACK);
        songLabel.setPosition(20,getHeight()/2, Align.left);
        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                LevelConfig.levelHandle = fileName;
                itemButtonListener.callback();
            }
        });
    }
}
