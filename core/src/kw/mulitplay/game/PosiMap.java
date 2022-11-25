package kw.mulitplay.game;

import java.util.HashMap;

public class PosiMap {

    private final static int WHITE_KEY_COUNT = 52;
    private final static int BLACK_KEY_COUNT = 36;
    public HashMap<String,String> piano_keys = new HashMap<>();
    public void test(){
        for (int i = 0; i < WHITE_KEY_COUNT; i++) {
            int key_padding = 2 * i - (i + 5) / 7 - (i + 2) / 7;
            int midi_position = 21 + key_padding;
            piano_keys.put(midi_position+"", midi_position+"");
            System.out.print(midi_position+"    ");
        }

        System.out.println();
        System.out.println();
        System.out.println();

        for (int i = 0; i < BLACK_KEY_COUNT; i++) {
            int key_padding = (i + 4) / 5 + (i + 2) / 5;
            int midi_position = 22 + 2 * i + key_padding;
            piano_keys.put(midi_position+"", midi_position+"");
            System.out.print(midi_position+"    ");
        }
    }

    public static void main(String[] args) {
        PosiMap map = new PosiMap();
        map.test();
    }
}

