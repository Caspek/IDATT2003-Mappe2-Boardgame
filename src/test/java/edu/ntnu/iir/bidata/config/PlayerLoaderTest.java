package edu.ntnu.iir.bidata.config;

import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class PlayerLoaderTest {

    @Test
    void testLoadPlayerNamesFromFile() throws URISyntaxException {
        String filePath = Paths.get(Objects.requireNonNull(getClass().getClassLoader()
                .getResource("Players.json")).toURI()).toString();

        List<String> names = PlayerLoader.loadPlayerNamesFromFile(filePath);
        assertNotNull(names);
        assertTrue(names.contains("Racecar"));
        assertEquals(5, names.size());
    }
}

