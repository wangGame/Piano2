package kw.mulitplay.game;

import com.kw.gdx.asset.Asset;

public class TimeLine {
    private String name;
    private float startTime;
    private boolean isPlayed;
    private float countTime;



    public void setName(String name) {
        this.name = name;
    }

    public void setStartTime(float startTime) {
        this.startTime = startTime;
    }

    public String getName() {
        return name;
    }

    public float getStartTime() {
        return startTime;
    }

    public boolean update(float delta){
        if (!isPlayed){
            countTime += delta;
            System.out.println(countTime +"   "+startTime);
            if (countTime > startTime){
                System.out.println("chan yin "+countTime);
                isPlayed = true;
                return true;
            }
        }
        return false;
    }
}
