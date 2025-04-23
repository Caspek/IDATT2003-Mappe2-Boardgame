package edu.ntnu.iir.bidata;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerLoader {

    private static final Logger LOGGER = Logger.getLogger(PlayerLoader.class.getName());

    public static List<Player> loadPlayersFromFile(String filePath, BoardGame game) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            Type playerListType = new TypeToken<List<Player>>() {
            }.getType();
            List<Player> players = gson.fromJson(reader, playerListType);
            for (Player player : players) {
                player.setGame(game);
            }
            return players;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load players from file: " + filePath, e);
            return null;

        }

    }
}
