package at.jkvn.eventlib;

import at.jkvn.eventlib.annotation.EventHandler;
import at.jkvn.eventlib.registry.Configuration;
import at.jkvn.eventlib.registry.ListenerRegistryType;
import lombok.Getter;
import lombok.SneakyThrows;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Method;
import java.util.*;

@Getter
public class EventLib {
    public static Configuration configuration;
    private static final List<Listener> listeners = new ArrayList<>();

    @SneakyThrows
    public static void call(Event event) {
        if (isAutomatic()) {
            invokeMethods(getMethods(), event);
            return;
        }

        (new ArrayList<>(listeners)).forEach(listener -> {
            invokeMethods(Arrays.stream(listener.getClass().getMethods()).toList(), event);
        });
    }

    private static void invokeMethods(List<Method> methods, Event event) {
        methods.stream()
                .filter(method -> method.getParameterCount() == 1
                        && method.getParameterTypes()[0].isAssignableFrom(event.getClass()))
                .forEach(method -> invokeSafely(method, event));
    }

    private static void invokeSafely(Method method, Event event) {
        try {
            method.invoke(method.getDeclaringClass().newInstance(), event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static List<Method> getMethods() {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forJavaClassPath())
                .setScanners(new MethodAnnotationsScanner()));

        return reflections.getMethodsAnnotatedWith(EventHandler.class).stream().toList();
    }

    private static boolean isAutomatic() {
        return configuration.getType().equals(ListenerRegistryType.AUTOMATIC);
    }

    public static void registerListener(Listener listener) {
        listeners.add(listener);
    }

    public static void registerListeners(Listener... allListeners) {
        listeners.addAll(Arrays.stream(allListeners).toList());
    }

    public static void unregisterListener(Listener listener) {
        listeners.remove(listener);
    }

    public static void unregisterListeners(Listener... allListeners) {
        listeners.removeAll(Arrays.stream(allListeners).toList());
    }

    public static void configure(Configuration configuration) {
        EventLib.configuration = configuration;
    }
}