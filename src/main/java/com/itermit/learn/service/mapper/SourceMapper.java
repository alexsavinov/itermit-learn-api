package com.itermit.learn.service.mapper;

import com.itermit.learn.model.dto.SourceDto;
import com.itermit.learn.model.dto.request.CreateSourceRequest;
import com.itermit.learn.model.dto.request.UpdateSourceRequest;
import com.itermit.learn.model.entity.Source;
import lombok.AllArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class SourceMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    {
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
    }

    public SourceDto toDto(Source source) {
        return modelMapper.map(source, SourceDto.class);
    }

    public SourceDto toIdDto(Source source) {
        return SourceDto.builder().id(source.getId()).build();
    }

    public SourceDto toBasicDto(Source source) {
        return SourceDto.builder()
                .id(source.getId())
                .name(source.getName())
                .build();
    }

    public Source toSource(CreateSourceRequest request) {
        return modelMapper.map(request, Source.class);
    }

    public void toSource(UpdateSourceRequest request, Source source) {
        modelMapper.map(request, source);
    }
}
