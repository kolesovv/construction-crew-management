package com.epam.constructioncrewmanagement.converter;

import com.epam.constructioncrewmanagement.dto.PositionDto;
import com.epam.constructioncrewmanagement.entity.Position;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PositionConverter {

  public PositionDto mapToDto(Position position) {

    return new PositionDto(
        position.getId(),
        position.getName());
  }

  public Position mapToEntity(PositionDto positionDto) {

    return Position.builder()
        .id(positionDto.getId())
        .name(positionDto.getName())
        .build();
  }

  public List<PositionDto> mapToDtos(List<Position> positions) {

    return positions
        .stream()
        .map(this::mapToDto)
        .collect(Collectors.toList());
  }
}
