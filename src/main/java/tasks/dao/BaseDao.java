package tasks.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tasks.domain.ENTITY;

import java.io.Serializable;

public interface BaseDao<T extends ENTITY> extends JpaRepository<T, Serializable> {
}
