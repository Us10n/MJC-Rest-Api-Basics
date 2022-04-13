package com.epam.esm.web.controller;

import com.epam.esm.service.crud.TagCRU;
import com.epam.esm.service.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Tag rest controller.
 */
@RestController
@RequestMapping("/tags")
public class TagController {
    private TagCRU tagCRU;

    /**
     * Instantiates a new Tag controller.
     *
     * @param tagCRU the tag cru
     */
    @Autowired
    public TagController(TagCRU tagCRU) {
        this.tagCRU = tagCRU;
    }

    /**
     * Read all tags.
     *
     * @return the list
     */
    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public List<TagDto> readAllTags() {
        List<TagDto> tagList = tagCRU.readAll();
        return tagList;
    }

    /**
     * Read tad by id.
     *
     * @param id the id
     * @return the tag dto
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public TagDto readTadById(@PathVariable long id) {
        TagDto tagDto = tagCRU.readById(id);
        return tagDto;
    }

    /**
     * Create tag.
     *
     * @param tagDto the tag dto
     * @return the tag dto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto createTag(@RequestBody TagDto tagDto) {
        return tagCRU.create(tagDto);
    }

    /**
     * Delete tag.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteTag(@PathVariable long id) {
        tagCRU.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
