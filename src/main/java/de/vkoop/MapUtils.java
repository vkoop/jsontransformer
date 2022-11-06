package de.vkoop;

import java.util.HashMap;
import java.util.Map;

public class MapUtils {
    public static Map<String, Object> unflatten(Map<String, ?> input) {
        Map<String, Object> result = new HashMap<>();

        for (String key : input.keySet()) {
            final String[] keyFragments = key.split("_");
            Map<String, Object> mapPointer = result;

            for (int i = 0; i < keyFragments.length; i++) {
                String keyFragment = keyFragments[i];
                boolean leaf = i == keyFragments.length - 1;

                if (leaf) {
                    mapPointer.put(keyFragment, input.get(key));
                } else {
                    if (!mapPointer.containsKey(keyFragment)) {
                        final Map<String, Object> value = new HashMap<>();
                        mapPointer.put(keyFragment, value);
                        mapPointer = value;
                    } else {
                        mapPointer = (Map<String, Object>) mapPointer.get(keyFragment);
                    }
                }
            }
        }

        return result;
    }

    public static Map<String, Object> flatten(Map<String, ?> inputMap) {
        Map<String, Object> result = new HashMap<>();

        for (var inputMapEntry : inputMap.entrySet()) {
            if (inputMapEntry.getValue() instanceof Map nestedMap) {
                Map<String, Object> innerMap = flatten(nestedMap);

                for (var nestedMapEntry : innerMap.entrySet()) {
                    result.put(inputMapEntry.getKey() + "_" + nestedMapEntry.getKey(), nestedMapEntry.getValue());
                }
            } else {
                result.put(inputMapEntry.getKey(), inputMapEntry.getValue());
            }
        }

        return result;
    }

}
