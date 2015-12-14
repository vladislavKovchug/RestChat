package com.teamdev.chat.repository;

import com.teamdev.chat.database.ChatDatabase;
import com.teamdev.chat.database.Tables;
import com.teamdev.chat.entity.DatabaseEntity;

import java.util.List;

public abstract class AbstractRepository<Entity extends DatabaseEntity> {

    public Entity findOne(long id) {
        final List<DatabaseEntity> databaseEntities = ChatDatabase.INSTANCE.selectTable(getTable());
        for (DatabaseEntity entity : databaseEntities) {
            if (entity.getId() == id) {
                return (Entity) entity;
            }
        }
        return null;
    }

    public List<Entity> findAll() {
        return (List<Entity>) ChatDatabase.INSTANCE.selectTable(getTable());
    }

    public void save(Entity entity) {
        if (entity.getId() == -1) { //if id not defined insert, else update
            ChatDatabase.INSTANCE.insertIntoTable(getTable(), entity);
        } else {
            ChatDatabase.INSTANCE.updateInTable(getTable(), entity, entity.getId());
        }
    }

    public void delete(long id) {
        throw new RuntimeException("not implemented");
    }

    protected abstract Tables getTable();

}
