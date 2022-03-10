package com.gumisie.persistence.hibernate;

import com.gumisie.containers.Game;
import com.gumisie.containers.ScoreRecord;
import com.gumisie.containers.User;
import com.gumisie.persistence.interfaces.IUserDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class UserDao extends GenericDao<User, String> implements IUserDao {

    public UserDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User getByEmail(String email) {
        return super.getById(User.class, email);
    }

    @Override
    public long getBestScoreFor(String email) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        try {
            return session.createQuery(
                    "SELECT g.score " +
                            "FROM Game g " +
                            "WHERE g.isWon = true AND g.user.email = :email " +
                            "ORDER BY g.score DESC ",
                    Long.class)
                    .setParameter("email", email)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException e){
            return -1;
        } finally {
            tx.commit();
        }
    }

    @Override
    public long getWonGamesFor(String email) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        try {
            return session.createQuery(
                    "SELECT count(g.id) " +
                            "FROM Game g " +
                            "WHERE g.isWon = true AND g.user.email = :email ",
                    Long.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e){
            return -1;
        } finally {
            tx.commit();
        }
    }

    @Override
    public long getTotalGamesFor(String email) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        try {
            return session.createQuery(
                    "SELECT count(g.id) " +
                            "FROM Game g " +
                            "WHERE g.user.email = :email ",
                    Long.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e){
            return -1;
        } finally {
            tx.commit();
        }
    }

    @Override
    public List<ScoreRecord<Long>> getBestScores(int amount){
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        List<Game> bestGames = session.createQuery(
                "SELECT g " +
                        "FROM Game g " +
                        "WHERE g.isWon = true " +
                        "ORDER BY g.score DESC",
                Game.class)
                .setMaxResults(amount)
                .list();

        tx.commit();

        return bestGames.stream().map((game) -> new ScoreRecord<>(
                game.getUser().getFirstName(),
                game.getUser().getLastName(),
                game.getScore()
        )).toList();
    }

    @Override
    public List<ScoreRecord<Long>> getMostWonGames(int amount){
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        List<Object[]> mostGames = session.createQuery(
                "SELECT u, count(u.email) as c " +
                        "FROM Game g " +
                        "JOIN g.user u " +
                        "WHERE g.isWon = true " +
                        "GROUP BY u.email " +
                        "ORDER BY c DESC ",
                Object[].class)
                .setMaxResults(amount)
                .list();

        tx.commit();

        return mostGames.stream().map((arr) -> {
                    User user = (User) arr[0];
                    Long count = (Long) arr[1];
                    return new ScoreRecord<>(user.getFirstName(), user.getLastName(), count);
                }).toList();
    }



}
