package edu.ntnu.iir.bidata.player;

import edu.ntnu.iir.bidata.board.BoardGame;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerLoader {

    private static final Logger LOGGER = Logger.getLogger(PlayerLoader.class.getName());

    public static List<Player> loadPlayersFromFile(String filePath, BoardGame game) {
        List<Player> players = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));

            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String playingPiece = jsonObject.getString("playingPiece");

                Player player = new Player(name, playingPiece, game);
                players.add(player);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load players from file: " + filePath, e);
        }
        return players;
    }
}
