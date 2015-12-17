package com.teamdev.chat.repository;


import com.teamdev.chat.database.ChatDatabase;
import com.teamdev.chat.entity.DatabaseEntity;

import java.util.List;

public interface Repository<Entity extends DatabaseEntity> {
    public Entity findOne(long id);

    public List<Entity> findAll();

    public void save(Entity entity);

    public void delete(Entity entity);
}
