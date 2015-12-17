package com.teamdev.chat.repository;


import com.teamdev.chat.database.ChatDatabase;
import com.teamdev.chat.entity.DatabaseEntity;

import java.util.List;

public interface Repository<Entity extends DatabaseEntity> {
    Entity findOne(long id);

    List<Entity> findAll();

    void save(Entity entity);

    void delete(Entity entity);
}
