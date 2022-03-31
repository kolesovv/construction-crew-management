package com.epam.constructioncrewmanagement.service;

import com.epam.constructioncrewmanagement.entity.Position;
import com.epam.constructioncrewmanagement.exception.EntityNotFoundException;
import com.epam.constructioncrewmanagement.repository.PositionRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PositionServiceImpl implements PositionService {

  private PositionRepository positionRepository;

  @Override
  public void add(Position position) {

    positionRepository.save(position);
  }

  @Override
  public Position getById(Long positionId) {

    return positionRepository.getById(positionId);
  }

  @Override
  public List<Position> getAll() {

    return positionRepository.findAll();
  }

  @Override
  public void update(Position position) {

    if (!positionRepository.existsById(position.getId())) {
      throw new EntityNotFoundException("Position with id" + position.getId() + "does not exist");
    }
    positionRepository.save(position);
  }

  @Override
  public void delete(Long positionId) {

    if (!positionRepository.existsById(positionId)) {
      throw new EntityNotFoundException("Position with id" + positionId + "does not exist");
    }
    positionRepository.deleteById(positionId);
  }
}
