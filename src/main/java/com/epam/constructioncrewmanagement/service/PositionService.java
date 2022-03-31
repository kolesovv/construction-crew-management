package com.epam.constructioncrewmanagement.service;

import com.epam.constructioncrewmanagement.entity.Position;
import java.util.List;

public interface PositionService {

  void add(Position position);

  Position getById(Long positionId);

  List<Position> getAll();

  void update(Position position);

  void delete(Long positionId);
}
