package com.epam.esm.web.controller;

import com.epam.esm.service.criteria.GiftCertificateCriteria;
import com.epam.esm.service.crud.GiftCertificateCRUD;
import com.epam.esm.service.dto.GiftCertificateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/giftCertificates")
public class GiftCertificateController {

    private GiftCertificateCRUD giftCertificateCRUD;

    @Autowired
    public GiftCertificateController(GiftCertificateCRUD giftCertificateCRUD) {
        this.giftCertificateCRUD = giftCertificateCRUD;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public GiftCertificateDto readById(@PathVariable long id) {
        return giftCertificateCRUD.readById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto create(@RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateCRUD.create(giftCertificateDto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto update(@PathVariable long id, @RequestBody GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.setGiftCertificateId(id);
        return giftCertificateCRUD.update(giftCertificateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        giftCertificateCRUD.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> getAllGiftCertificates(
            @RequestParam(name="tag", required=false) String tagName,
            @RequestParam(name="name", required=false) String partName,
            @RequestParam(name="description", required=false) String partDesc,
            @RequestParam(name="sort", required=false) String sortBy,
            @RequestParam(name="order", required=false) String sortOrder) {

        GiftCertificateCriteria criteria = new GiftCertificateCriteria();
        criteria.setTagName(tagName);
        criteria.setPartName(partName);
        criteria.setPartDesc(partDesc);
        criteria.setSortBy(sortBy);
        criteria.setSortOrder(sortOrder);

        return giftCertificateCRUD.readByCriteria(criteria);
    }
}
