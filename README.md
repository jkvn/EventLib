# EventLib

EventLib is a simple event library that uses sockets in Java to facilitate communication between different applications.

## Quick Start

### Maven

Add the following repository to your `pom.xml`:

```xml
<repository>
    <id>maven-releases</id>
    <url>https://nexus.synclyn.com/repository/maven-public/</url>
</repository>
```

And include the following dependency:

```xml
<dependency>
    <groupId>at.jkvn.eventlib</groupId>
    <artifactId>EventLib</artifactId>
    <version>LATEST</version>
</dependency>
```

### Gradle

Add the following repository to your `build.gradle`:

```groovy
maven {
    url = uri("https://nexus.synclyn.com/repository/maven-public/")
}
```

And include the following dependency:

```groovy
implementation("at.jkvn.eventlib:EventLib:LATEST")
```

## Integration

To integrate EventLib into your project, simply follow these steps:

### Configuration:

```java
EventLib.configure(Configuration configuration);
```

### Listener Registration:

```java
EventLib.registerListener(new YourFavoriteListener());

EventLib.registerListeners(new YourFavoriteListener(), new YourNicesListener());
```

### Example Listener Method:

```java
@EventHandler
@Priority(EventPriority.NORMAL)
public void onStartup(StartupEvent event) {
    System.out.println("Server started");
}
```

### Triggering Events:

```java
EventLib.callEvent(new StartupEvent());
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

## Future Plans

- [ ] Asynchronous events
- [ ] Event cancellation
- [x] Event listener registration
- [ ] Event listener deregistration
- [ ] Event listener priority
- [ ] Socket connection
- [ ] Socket authentication (password, private key, etc.)