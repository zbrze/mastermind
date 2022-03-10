package com.gumisie.persistence.hibernate;

import com.gumisie.containers.Game;
import com.gumisie.containers.User;
import com.gumisie.persistence.interfaces.IGameDao;
import com.gumisie.persistence.interfaces.IUserDao;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class GameDao extends GenericDao<Game, Integer> implements IGameDao {

    public GameDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    @Override
    public Game getById(int id) {
        return super.getById(Game.class, id);
    }

}
