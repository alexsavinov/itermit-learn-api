package com.itermit.learn.controller;

import com.itermit.learn.exception.ResourceReferencedFromTables;
import com.itermit.learn.model.dto.SourceDto;
import com.itermit.learn.model.dto.SourcesDto;
import com.itermit.learn.model.dto.request.CreateSourceRequest;
import com.itermit.learn.model.dto.request.UpdateSourceRequest;
import com.itermit.learn.model.entity.Source;
import com.itermit.learn.service.SourceService;
import com.itermit.learn.service.mapper.SourceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequiredArgsConstructor
@RequestMapping("/sources")
public class SourceController {

    private final SourceService sourceService;
    private final SourceMapper sourceMapper;
    private final SourcesDto sourcesDto;
    private final PagedResourcesAssembler<Source> pagedResourcesAssembler;

    @GetMapping("/{id}")
    public SourceDto getSourceById(@PathVariable Long id) {
        Source foundSource = sourceService.findById(id);
        SourceDto sourceDto = sourceMapper.toDto(foundSource);

        sourceDto.add(linkTo(methodOn(SourceController.class).getSourceById(sourceDto.getId())).withSelfRel());
        return sourceDto;
    }

    @GetMapping
    public PagedModel<SourceDto> getSources(
            Pageable pageable,
            @RequestParam(required = false) Map<String, String> params) {
        Page<Source> foundSources = sourceService.findAll(pageable, params);

        return pagedResourcesAssembler.toModel(foundSources, sourcesDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SourceDto addSource(@RequestBody CreateSourceRequest createRequest) {
        Source createdSource = sourceService.create(createRequest);
        SourceDto sourceDto = sourceMapper.toDto(createdSource);

        sourceDto.add(linkTo(methodOn(SourceController.class).getSourceById(sourceDto.getId())).withSelfRel());
        return sourceDto;
    }

    @PatchMapping
    public SourceDto updateSource(@RequestBody UpdateSourceRequest updateRequest) {
        Source updatedSource = sourceService.update(updateRequest);
        SourceDto sourceDto = sourceMapper.toDto(updatedSource);

        sourceDto.add(linkTo(methodOn(SourceController.class).getSourceById(sourceDto.getId())).withSelfRel());
        return sourceDto;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSourceById(@PathVariable Long id) {
        try {
            sourceService.delete(id);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceReferencedFromTables("Source exists in another tables. %s"
                    .formatted(e.getCause().getCause().getMessage().split(System.lineSeparator())[1]));
        }
    }
}
