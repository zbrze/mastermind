package com.gumisie.persistence.interfaces;

import com.gumisie.containers.Game;
import com.gumisie.containers.ScoreRecord;
import com.gumisie.containers.User;

import java.util.List;

public interface IUserDao {

    void save(User game);
    User getByEmail(String email);

    long getBestScoreFor(String email);
    long getWonGamesFor(String email);
    long getTotalGamesFor(String email);

    List<ScoreRecord<Long>> getBestScores(int amount);
    List<ScoreRecord<Long>> getMostWonGames(int amount);

}
