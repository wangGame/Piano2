package kw.mulitplay.game.pianojson;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kw.mulitplay.game.constant.LevelConfig;

public class JsonUtils1 {
    private String[] pt2Note = {"A-3", "#A-3", "B-3", "C-2", "#C-2", "D-2", "#D-2", "E-2", "F-2", "#F-2", "G-2", "#G-2", "A-2",
            "#A-2", "B-2", "C-1", "#C-1", "D-1", "#D-1", "E-1", "F-1", "#F-1", "G-1", "#G-1", "A-1", "#A-1", "B-1", "c", "#c",
            "d", "#d", "e", "f", "#f", "g", "#g", "a", "#a", "b", "c1", "#c1", "d1", "#d1", "e1", "f1", "#f1", "g1", "#g1",
            "a1", "#a1", "b1", "c2", "#c2", "d2", "#d2", "e2", "f2", "#f2", "g2", "#g2", "a2", "#a2", "b2", "c3", "#c3", "d3",
            "#d3", "e3", "f3", "#f3", "g3", "#g3", "a3", "#a3", "b3", "c4", "#c4", "d4", "#d4", "e4", "f4", "#f4", "g4", "#g4",
            "a4", "#a4", "b4", "c5", "mute"};
    private boolean[] table;

    public static void main(String[] args) {
        readFile();
    }

    private static Array<NoteDatas> stringToTiles(String str, float bpm) {
        float timeAll = 0;
        Array<NoteDatas> noteDataArray = new Array<>();
        Array<String> arrayTemp = new Array<>();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c==','||c == ';'){
                arrayTemp.add(builder.toString());
                builder.setLength(0);
            }else {
                if (c == '<'){
                    int index = 0;
                    for (int i1 = i; i1 < str.length(); i1++) {
                        char c1 = str.charAt(i1);
                        if (c1=='>'){
                            index = i1;
                            break;
                        }
                    }
                    builder.append(str.substring(i,index+1));
                    i = index;
                }else {
                    builder.append(c);
                }
            }
        }


        for (String tile : arrayTemp) {
            int type = 1;
            if (tile.startsWith(">", tile.length() - 2)) {
                type = Integer.valueOf(tile.substring(0, tile.indexOf("<")));
                tile = tile.substring(tile.indexOf("<") + 1, tile.length() - 2);
            } else {

            }

            String[] split = tile.split("[,;]");
            for (String s1 : split) {
                if (s1 == null || s1.equals(""))continue;
                Pattern pattern1 = Pattern.compile("(?<=\\[).*(?=\\])");
                Matcher matcher1 = pattern1.matcher(s1);
                if (matcher1.find()) {
                    String lenstr = matcher1.group(0);
                    float len = lenToNum(lenstr.charAt(0)+"", 1);
                    Pattern compile = Pattern.compile(".+(?=\\[)");
                    Matcher matcher2 = compile.matcher(s1);
                    NoteDatas bean = new NoteDatas();
                    noteDataArray.add(bean);
                    bean.setBpm(bpm);
                    bean.setType(type);
                    bean.setLen(len);
                    bean.setNodes(new Array<>());
                    if (matcher2.find()) {
                        String notearr = matcher2.group(0);
                        NoteData data = new NoteData();
                        data.setStart(0);
                        data.setEnd(len);
                        timeAll += bean.getLen();
                        data.setNodeName(notearr);
                        bean.getNodes().add(data);
                    }
                }else {
                    NoteDatas bean = new NoteDatas();
                    noteDataArray.add(bean);
                    bean.setBpm(bpm);
                    bean.setType(type);
                    bean.setLen(lenToNum(s1, 0));
                    timeAll += bean.getLen();
                    bean.setNodes(new Array<>());
//                    System.out.println("占位");
                }
            }
        }
        System.out.println(timeAll +"   -------------------------        timeAll");
        return noteDataArray;
    }


    private static float lenToNum(String len, int type) {
        float num = 0;
        for (int length = 0; length < len.length(); length++) {
            char i  = len.charAt(length);
            switch (i) {
                case ('H'):
                case ('Q'):
                    num += 8;
                    break;
                case ('R'):
                case ('I'):
                    num += 4;
                    break;
                case ('J'):
                case ('S'):
                    num += 2;
                    break;
                case ('K'):
                case ('T'):
                    num += 1;
                    break;
                case ('L'):
                case ('U'):
                    num += 0.5;
                    break;
                case ('M'):
                case ('V'):
                    num += 0.25;
                    break;
                case ('N'):
                case ('W'):
                    num += 0.125;
                    break;
                case ('O'):
                case ('X'):
                    num += 0.0625;
                    break;
                case ('Y'):
                case ('P'):
                    num += 0.03125;
                    break;
                default:
                    return 0;
            }
        }
        return num;
    }

    public static MusicDataBean readFile() {
        MusicDataBean musicDataBean = new MusicDataBean();
        Array<Array<NoteDatas>> arrayArray = new Array<>();
        Json json = new Json();
//        FileHandle exampleJson = new FileHandle("Assets/song/Little Star.json");
//        FileHandle exampleJson = Gdx.files.internal("song/Little Star.json");
        FileHandle exampleJson = LevelConfig.levelHandle;
        String JsonString = exampleJson.readString();
        PythonDict jsonRoot = json.fromJson(PythonDict.class, JsonString);
        PythonArray musics = (PythonArray) jsonRoot.get("musics");
        Array<JsonData> jsonDataArray = new Array<>();
        float baseBpm = (float) jsonRoot.get("baseBpm");
        musicDataBean.setBaseBpm(baseBpm);
        musicDataBean.setArrayArray(arrayArray);


        for (int i = 0; i < musics.size; i++) {
            PythonDict data = (PythonDict) musics.get(i);
            Float id = data.get("id",Float.class);
            Float bpm = data.get("bpm", Float.class);
            Float baseBeats = data.get("baseBeats", Float.class);
            PythonArray scores = data.get("scores", PythonArray.class);
            Array<String> array = new Array<>();
            for (int i1 = 0; i1 < scores.size; i1++) {
                array.add(scores.get(i1).toString());
            }
            JsonData jsonData = new JsonData();
            if (bpm == null){
                bpm = 120.0f;
            }
            jsonData.setBpm(bpm);
            float tempId = id;
            jsonData.setId((int)tempId);
            jsonData.setBaseBeats(baseBeats);
            jsonData.setScores(array);
            jsonDataArray.add(jsonData);
        }

        for (int i1 = 0; i1 < jsonDataArray.size; i1++) {
            JsonData jsonData = jsonDataArray.get(i1);
            float baseBeats = jsonData.getBaseBeats();
            float bpm = jsonData.getBpm();
            if (bpm == 0){
                System.out.println( "- -------------");
            }
            //music
            Array<String> scores = jsonData.getScores();
            Array<NoteDatas> base = stringToTiles(scores.get(0),jsonData.getBpm());
            for (int i = 1; i < scores.size; i++) {
                float baseDur = 0;
                int baseIdx = 0;
                float branchDur = 0;
                int branchIdx = 0;
                //将第二个插入
                Array<NoteDatas> branch = stringToTiles(scores.get(i), jsonData.getBpm());
                while (baseIdx < base.size && branchIdx < branch.size) {
                    if (branchDur < baseDur + base.get(baseIdx).getLen()) {
                        if (branch.get(branchIdx).getNodes().size>0) {
                            NoteDatas data = base.get(baseIdx);
                            data.setBpm(jsonData.getBpm());
                            NoteData data1 = new NoteData();
                            data1.setNodeName(branch.get(branchIdx).getNodes().get(0).getNodeName());
                            data1.setStart(branchDur - baseDur);
                            data1.setEnd(branch.get(branchIdx).getNodes().get(0).getEnd());
                            data.getNodes().add(data1);
                        }
                        branchDur += branch.get(branchIdx++).getLen();
                    } else{
                        baseDur += base.get(baseIdx++).getLen();
                    }
                }
            }
            for (NoteDatas bean : base) {
                if (bean.getType()!=0){
                    if (bean.getType() == 1){
                        if (bean.getLen()>1.0f){
                            bean.setType(6);
                        }else {
                            bean.setType(2);
                        }
                    }
                }
                bean.setLen(bean.getLen()/baseBeats);
            }
            arrayArray.add(base);
        }
        return musicDataBean;
    }
}