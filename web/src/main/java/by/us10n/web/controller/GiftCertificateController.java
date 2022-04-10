package by.us10n.web.controller;

import by.us10n.service.crud.GiftCertificateCRUD;
import by.us10n.service.dto.GiftCertificateDto;
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

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public List<GiftCertificateDto> readAll() {
        return giftCertificateCRUD.readAll();
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
}
