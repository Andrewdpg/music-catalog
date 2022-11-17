package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Ad {
    public static final Map<String, String> ads;

    /**
     * Initializing the ads
     */
    static {
        ads = new HashMap<String, String>();
        ads.put("Nike", "Just do it");
        ads.put("Coca-Cola", "Open happiness");
        ads.put("M&M", "Melts in your mouth, not in your hands");
    }

    /**
     * 
     * @return A random ad
     */
    public static String getAdd() {
        Random random = new Random();
        String key = (String) ads.keySet().toArray()[random.nextInt(ads.size() - 1)];
        return "\n" + key + " - " + ads.get(key);
    }
}
