package at.jkvn.eventlib;

import at.jkvn.eventlib.annotation.EventHandler;
import at.jkvn.eventlib.annotation.Priority;
import at.jkvn.eventlib.enumeration.EventPriority;
import at.jkvn.eventlib.registry.Configuration;
import at.jkvn.eventlib.registry.ListenerRegistryType;
import lombok.Getter;
import lombok.SneakyThrows;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class EventLib {
    private Configuration configuration;
    private final List<Listener> listeners = new ArrayList<>();

    /**
     * Initializes the EventLib with a given configuration.
     *
     * @param configuration the configuration to use
     */
    public void init(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * Calls an event. If the configuration is set to automatic, it will invoke all methods
     * annotated with @EventHandler. Otherwise, it will invoke the methods of the registered listeners.
     *
     * @param event the event to call
     */
    @SneakyThrows
    public void call(Event event) {
        if (isAutomatic()) {
            invokeMethods(getMethods(), event);
        } else {
            listeners.forEach(listener -> invokeMethods(getListenerMethods(listener), event));
        }
    }

    /**
     * Invokes the given methods for the specified event.
     *
     * @param methods the methods to invoke
     * @param event   the event to pass to the methods
     */
    private void invokeMethods(List<Method> methods, Event event) {
        methods.stream()
                .filter(method -> isMethodCompatibleWithEvent(method, event))
                .sorted(this::compareMethodsByPriority)
                .forEach(method -> invokeSafely(method, event));
    }

    /**
     * Safely invokes a method for a given event.
     *
     * @param method the method to invoke
     * @param event  the event to pass to the method
     */
    private void invokeSafely(Method method, Event event) {
        try {
            method.invoke(method.getDeclaringClass().newInstance(), event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns all methods in the classpath that are annotated with @EventHandler.
     *
     * @return a list of methods annotated with @EventHandler
     */
    private List<Method> getMethods() {
        var reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forJavaClassPath())
                .setScanners(new MethodAnnotationsScanner()));

        return reflections.getMethodsAnnotatedWith(EventHandler.class).stream().toList();
    }

    /**
     * Returns all methods of the given listener that are annotated with @EventHandler.
     *
     * @param listener the listener to get the methods from
     * @return a list of methods annotated with @EventHandler
     */
    private List<Method> getListenerMethods(Listener listener) {
        return Arrays.stream(listener.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(EventHandler.class))
                .toList();
    }

    /**
     * Checks if a method is compatible with an event, i.e., if the method has one parameter
     * and the parameter type is assignable from the event's class.
     *
     * @param method the method to check
     * @param event  the event to check compatibility with
     * @return true if the method is compatible with the event, false otherwise
     */
    private boolean isMethodCompatibleWithEvent(Method method, Event event) {
        return method.getParameterCount() == 1
                && method.getParameterTypes()[0].isAssignableFrom(event.getClass());
    }

    /**
     * Compares two methods by their priority. The priority is determined by the @Priority annotation.
     * If a method does not have a @Priority annotation, it is considered to have normal priority.
     *
     * @param method1 the first method to compare
     * @param method2 the second method to compare
     * @return a negative integer, zero, or a positive integer if the priority of the first method
     *         is less than, equal to, or greater than the priority of the second method
     */
    private int compareMethodsByPriority(Method method1, Method method2) {
        Priority priority1 = method1.getAnnotation(Priority.class);
        Priority priority2 = method2.getAnnotation(Priority.class);

        int priorityValue1 = priority1 != null ? priority1.value().ordinal() : EventPriority.NORMAL.ordinal();
        int priorityValue2 = priority2 != null ? priority2.value().ordinal() : EventPriority.NORMAL.ordinal();

        return Integer.compare(priorityValue1, priorityValue2);
    }

    /**
     * Returns whether the configuration type is set to automatic.
     *
     * @return true if the configuration type is automatic, false otherwise
     */
    private boolean isAutomatic() {
        return configuration.getType().equals(ListenerRegistryType.AUTOMATIC);
    }

    /**
     * Registers a listener. If the configuration type is set to automatic, it throws an exception.
     *
     * @param listener the listener to register
     * @throws Exception if the configuration type is automatic
     */
    public void registerListener(Listener listener) throws Exception {
        if (isAutomatic()) {
            throw new Exception("Cannot register listener on automatic mode"); //Todo: create custom exception
        }
        listeners.add(listener);
    }

    /**
     * Registers multiple listeners.
     *
     * @param allListeners the listeners to register
     */
    public void registerListeners(Listener... allListeners) {
        listeners.addAll(Arrays.stream(allListeners).toList());
    }

    /**
     * Unregister a listener.
     *
     * @param listener the listener to unregister
     */
    public void unregisterListener(Listener listener) {
        listeners.remove(listener);
    }

    /**
     * Unregister multiple listeners.
     *
     * @param allListeners the listeners to unregister
     */
    public void unregisterListeners(Listener... allListeners) {
        listeners.removeAll(Arrays.stream(allListeners).toList());
    }
}