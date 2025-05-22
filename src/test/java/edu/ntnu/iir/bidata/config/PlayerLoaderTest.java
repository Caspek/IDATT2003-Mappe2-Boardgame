package edu.ntnu.iir.bidata.config;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerLoaderTest {

    @Test
    void testLoadPlayerNamesFromFile() {
        List<String> names = PlayerLoader.loadPlayerNamesFromFile
                ("src/main/resources/Players.json");
        assertNotNull(names);
        assertTrue(names.contains("Racecar"));
        assertEquals(5, names.size());
    }
}

