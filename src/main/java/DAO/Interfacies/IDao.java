package DAO.Interfacies;

import DAO.DAOException;
import Entities.IEntity;

/**
 * Created by Артем on 01.05.2016.
 */
public interface IDao<T extends IEntity> {
    int Insert(T item) throws DAOException;
    T Select(int id) throws DAOException;
    void Update(T item) throws DAOException;
    void Delete(int id) throws DAOException;
}
