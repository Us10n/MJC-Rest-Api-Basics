package com.epam.esm.web.controller;

import com.epam.esm.service.crud.TagCRU;
import com.epam.esm.service.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private TagCRU tagCRU;

    @Autowired
    public TagController(TagCRU tagCRU) {
        this.tagCRU = tagCRU;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public List<TagDto> readAllTags() {
        List<TagDto> tagList = tagCRU.readAll();
        return tagList;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public TagDto readTadById(@PathVariable long id) {
        TagDto tagDto = tagCRU.readById(id);
        return tagDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto createTag(@RequestBody TagDto tagDto) {
        return tagCRU.create(tagDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> createTag(@PathVariable long id) {
        tagCRU.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
