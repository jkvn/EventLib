# **EventLib**
## ⚠️ UNDER DEVELOPMENT ⚠️

Welcome to EventLib, your user-friendly Java event library using sockets for seamless communication between various applications.

## **Quick Start** 🚀

### Maven

Add the following repository to your `pom.xml`:

```xml
<repository>
    <id>maven-releases</id>
    <url>https://nexus.synclyn.com/repository/maven-public/</url>
</repository>
```

Then, include the dependency:

```xml
<dependency>
    <groupId>at.jkvn.eventlib</groupId>
    <artifactId>EventLib</artifactId>
    <version>LATEST</version>
</dependency>
```

### Gradle

For Gradle, add this repository to your `build.gradle`:

```groovy
maven {
    url = uri("https://nexus.synclyn.com/repository/maven-public/")
}
```

Then, include the dependency:

```groovy
implementation("at.jkvn.eventlib:EventLib:LATEST")
```

## Integration 🛠️

Integrating EventLib into your project is straightforward:

### Configuration:

```java
EventLib.configure(Configuration.builder()
                .type(ListenerRegistryType.AUTOMATIC)
                .build());
```

### Listener Registration:

No need to register listeners if you use ListenerRegistryType.AUTOMATIC.
If you opt for ListenerRegistryType.MANUAL, implement your class with the **Listener** interface.

**ListenerRegistryType.MANUAL**
```java
class MyListener implements Listener {
    public void onStartup(StartupEvent event) {
      System.out.println("Server started");
    }
}
```

**ListenerRegistryType.AUTOMATIC**
```java
class MyListenerNoRegistration {
    @EventHandler
    public void onStartup(StartupEvent event) {
      System.out.println("Server started");
    }
}
```

Then register your listeners:

```java
EventLib.registerListener(new MyListener());

EventLib.registerListeners(new MyListener(), new YourFavoriteListener());
```

### Example Event

```java
class StartupEvent extends Event {}
```

### Triggering Events:

```java
EventLib.call(new StartupEvent());
```

## Priorities

EventLib supports the following event priorities:

- `EventPriority.LOWEST`
- `EventPriority.LOW`
- `EventPriority.NORMAL` (default)
- `EventPriority.HIGH`
- `EventPriority.HIGHEST`

## Extras & Features

EventLib provides additional features such as event cancellation and resumption:

```java
@EventHandler
public void onListenYourFavoriteEvent(YourFavoriteEvent event) {
    int i = 0;
    if (i > 0) {
        event.cancel(); // Event is cancelled
    }
    
    System.out.print("All listeners of these event are stopped when i is bigger as 0");
}
```

To resume an event:

```java
@EventHandler
public void onListenYourFavoriteEvent(YourFavoriteEvent event) {
    int i = 0;
    if (i > 0) {
        event.cancel(); // Event is cancelled
        if (event.isCancelled()) {
            i = 0;
            event.uncancel(); // Event is uncancelled because i = 0
        }
    }
}
```

## Future Plans 🛌

- [ ] Asynchronous events
- [ ] Event cancellation
- [x] Event listener registration
- [ ] Event listener deregistration
- [ ] Event listener priority
- [ ] Socket connection
- [ ] Socket authentication (password, private key, etc.)
