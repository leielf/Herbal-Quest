package com.example.elixirapp;

import com.example.elixirapp.GameEntity.Player;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.elixirapp.GameEntity.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataSaver {

    private static final Logger logger = Logger.getLogger(DataSaver.class.getName());
    public DataSaver(){
    }

    public void saveData(Player player){
        try {
            FileWriter data = new FileWriter("data.json");
            JSONObject dataArr = new JSONObject();
            dataArr.put("herbs", saveHerbs(player.getHerbs()));
            dataArr.put("coins", saveCoins(player.getCoins()));
            data.write(dataArr.toJSONString());
            data.flush();
            logger.log(Level.INFO, "Data was saved");
        }catch (IOException e){
        }
    }

    public JSONArray saveHerbs(ArrayList<Herb> playerHerbs){
        JSONArray herbs = new JSONArray();
        for(Herb herb : playerHerbs){
            JSONObject jsonHerb = new JSONObject();
            jsonHerb.put("name", herb.getName());
            jsonHerb.put("cost", herb.getCost());
            herbs.add(jsonHerb);
        }
        return herbs;
    }

    public JSONArray saveCoins(ArrayList<Coin> playerCoins){
        JSONArray coins = new JSONArray();
        for(Coin coin : playerCoins){
            JSONObject jsonHerb = new JSONObject();
            jsonHerb.put("value", coin.getValue());
            coins.add(jsonHerb);
        }
        return coins;
    }

}
