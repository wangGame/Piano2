package kw.mulitplay.game.pianojson;

import com.badlogic.gdx.utils.Array;

public class MusicDataBean {
    private float baseBpm;
    private Array<Array<NoteDatas>> arrayArray;

    public void setArrayArray(Array<Array<NoteDatas>> arrayArray) {
        this.arrayArray = arrayArray;
    }

    public Array<Array<NoteDatas>> getArrayArray() {
        return arrayArray;
    }

    public void setBaseBpm(float baseBpm) {
        this.baseBpm = baseBpm;
    }

    public float getBaseBpm() {
        return baseBpm;
    }
}
