package fishing.event;

import fishing.fish.Fish;
import fishing.fish.Species;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CatchLogFile implements CatchListener {

    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final Path file;

    public CatchLogFile(Path file) {
        this.file = file;
    }

    @Override
    public void onCatch(Fish fish) {
        write("FOGÁS     " + fish);
    }

    @Override
    public void onEscape(Species species, String reason) {
        write("ELSZÖKÖTT " + species.displayName() + " - " + reason);
    }

    private void write(String line) {
        String entry = "[" + LocalDateTime.now().format(TIME) + "] " + line + System.lineSeparator();
        try {
            Files.writeString(file, entry, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Nem sikerült a naplóba írni: " + e.getMessage());
        }
    }
}
