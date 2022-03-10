package com.gumisie.persistence.interfaces;

import com.gumisie.containers.Game;

public interface IGameDao {

    void save(Game game);
    Game getById(int id);

}
