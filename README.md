# ArcadeLobby

ArcadeLobby is spigot utility plugin for managing lobbies, parties, queues to game. It's not is a full-fledged plugin, it's library for other plugin with arcade minigames.

## Installation

Add the library to your Spigot plugin project:

### Maven
```xml
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>
```

```xml
<dependencies>
    <dependency>
        <groupId>com.github.MrBedrockpy</groupId>
        <artifactId>ArcadeLobby</artifactId>
        <version>1.2</version>
    </dependency>
</dependencies>
```

### Gradle (Groovy)
```groovy
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}
```

```groovy
dependencies {
    implementation 'com.github.MrBedrockpy:ArcadeLobby:1.2'
}
```

### Gradle (Kotlin)
```kotlin
repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}
```

```kotlin
dependencies {
    implementation("com.github.MrBedrockpy:ArcadeLobby:1.2")
}
```

### Important
> 1. To work, you need to add the code of this library to the jar file of the build. I am using [shadowjar](https://github.com/GradleUp/shadow).
> 2. Alse to work this plugin requered Mltiverse-Core: https://mvplugins.org/core/developers/developer-api-starter/#Adding-Multiverse-to-your-project

## API

### Creating your minigame

For create your minegame, you should create class with implemetation Minigame interface

```java
public class TestMinigame implements Minigame {

    @Override
    public int getCountPlayersInTeam() {
        return 2;
    }

    @Override
    public Lobby getLobby(String id) {
        return new DefaultLobby(
                id,
                this,
                world, // world where will teleport players in waiting time, don't write here lobby templates
                List.of(
                        new Team(TeamColor.RED),
                        new Team(TeamColor.BLUE),
                        new Team(TeamColor.GREEN),
                        new Team(TeamColor.YELLOW)
                )
        );
    }

    @Override
    public void startGame(Lobby lobby) {
        // When call this method, lobby ready now, and you can launch session of your minigame
    }

    @Override
    public String getId() {
        return "test-minigame"; // It's unique system name of your minigame
    }
}
```

### Registring minigame

```java
ArcadeLobby arcadeLobby = ArcadeLobby.getPlugin(ArcadeLobby.class)
MinigameManager manager = arcadeLobby.getMinigameManager();
manager.register(new BedWarsMinigame());
```

### Creating your minigame with several with several submodes

If you creating minigame with several submodes I suggest you write of:

```java
public class BedWarsMinigame implements Minigame {

    private final int countOfPlayersInTeam;
    private final int countOfTeams;

    public BedWarsMinigame(int countOfPlayersInTeam, int countOfTeams) {
        this.countOfPlayersInTeam = countOfPlayersInTeam;
        this.countOfTeams = countOfTeams;
    }

    @Override
    public int getCountPlayersInTeam() {
        return countOfPlayersInTeam;
    }

    @Override
    public Lobby getLobby(String id) {
        return new DefaultLobby(id, this, world, createTeams());
    }

    public List<Team> createTeams() {
        TeamColor[] colors = TeamColor.values();
        if (countOfTeams < colors.length) colors = Arrays.copyOf(colors, countOfTeams);
        return Arrays.stream(colors).map(Team::new).toList();
    }

    @Override
    public void startGame(Lobby lobby) {
        // logic of launch session
    }

    @Override
    public String getId() {
        return countOfPlayersInTeam + "x" + countOfTeams;
    }

    public static void register() {
        MinigameManager manager = ArcadeLobby.getPlugin(ArcadeLobby.class).getMinigameManager();
        manager.register(new BedWarsMinigame(1, 8));
        manager.register(new BedWarsMinigame(2, 8));
        manager.register(new BedWarsMinigame(3, 4));
        manager.register(new BedWarsMinigame(4, 4));
    }
}
```
