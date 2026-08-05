package org.example.commonsservice.mapper;

public interface BaseMapper<E, R, C>  {
    E toEntity(C request);
    R toResponse(E entity);
}
