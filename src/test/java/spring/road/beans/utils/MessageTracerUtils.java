package spring.road.beans.utils;

import java.util.LinkedList;
import java.util.List;

/**
 * User: lijinpeng
 * Created by Shanghai on 2019/4/20.
 */
public class MessageTracerUtils {
    private static List<String> messages = new LinkedList<String>();

    public static void addMessage(String message) {
        synchronized (MessageTracerUtils.class) {
            messages.add(message);
        }
    }

    public static List<String> getMessages() {
        synchronized (MessageTracerUtils.class) {
            return messages;
        }
    }
}
