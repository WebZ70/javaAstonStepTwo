import hw1.CHashMap;
import org.junit.jupiter.api.Test;

public class TestHW1 {
    @Test
    void putMap() {
        CHashMap<Integer, String> cmap = new CHashMap<>();
        cmap.put(0, "Zero");
        cmap.put(1, "One");
        cmap.put(2, "Two");
        cmap.put(3, "Three");
        cmap.put(4, "Four");
        cmap.put(5, "Five");
        cmap.put(6, "Six");
        cmap.put(7, "Seven");
        cmap.put(8, "Eight");
        cmap.put(9, "Nine");
        cmap.put(10, "Ten");
        cmap.put(11, "Eleven");
        cmap.put(12, "Twelve");
        cmap.put(13, "Thirteen");
        cmap.put(14, "Fourteen");

        System.out.println(cmap);
    }

    @Test
    void getMap() {
        CHashMap<Integer, Integer> cmap = new CHashMap<>();
        for (int i = 0; i < 15; i++) {
            cmap.put(i, i);
        }

        for (int i = 0; i < 15; i++) {
            System.out.printf("%s : %s \n", i, cmap.get(i));
        }

    }

    @Test
    void removeMap() {
        CHashMap<Integer, Integer> cmap = new CHashMap<>();
        for (int i = 0; i < 15; i++) {
            cmap.put(i, i);
        }

        cmap.remove(14);
        cmap.remove(11);
        cmap.remove(9);
        cmap.remove(1);

        System.out.println(cmap);
    }
}
