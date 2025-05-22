package edu.ntnu.iir.bidata.config;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerLoaderTest {

    @Test
    void testLoadPlayerNamesFromFile() {
        List<String> names = PlayerLoader.loadPlayerNamesFromFile("Players.json");
        assertNotNull(names);
        assertTrue(names.contains("Racecar") || !names.isEmpty());
    }
}

