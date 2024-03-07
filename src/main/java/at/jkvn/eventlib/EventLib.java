package at.jkvn.eventlib;

import at.jkvn.eventlib.annotation.EventHandler;
import at.jkvn.eventlib.annotation.Priority;
import at.jkvn.eventlib.enumeration.EventPriority;
import at.jkvn.eventlib.registry.Configuration;
import at.jkvn.eventlib.registry.ListenerRegistryType;
import at.jkvn.eventlib.exception.AutomaticListenerException;
import lombok.Getter;
import lombok.SneakyThrows;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public class EventLib {
    @Getter
    private static Configuration configuration;
    @Getter
    private static final List<Listener> listeners = new ArrayList<>();
    @Getter
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    @SneakyThrows
    public static void call(Event event) {
        callEvent(event);
    }

    @SneakyThrows
    public static void callAsync(Event event) {
        executor.execute(() -> callEvent(event));
    }

    private static void callEvent(Event event) {
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
                .sorted(Comparator.comparingInt(method -> {
                    Priority priority = method.getAnnotation(Priority.class);
                    return priority != null ? priority.value().ordinal() : EventPriority.NORMAL.ordinal();
                }))
                .forEach(method -> {
                    if (!event.isCancelled()) {
                        invokeSafely(method, event);
                    }
                });
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

    public static void registerListener(Listener listener) throws Exception {
        if(isAutomatic()) {
            throw new AutomaticListenerException("Cannot register listener on automatic mode");
        }
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