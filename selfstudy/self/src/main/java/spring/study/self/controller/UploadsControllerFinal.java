package spring.study.self.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;
import spring.study.self.up.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UploadsControllerFinal {

    private final UPThingRepository upThingRepository;
    private final FileStore fileStore;

    @GetMapping("/items/new")
    public String newItem(@ModelAttribute UpThingForm form) {
        return "item-form";
    }

    @PostMapping("/items/new")
    public String saveItem(@ModelAttribute UpThingForm form, RedirectAttributes redirectAttributes) throws IOException {
        UploadFile attachFile = fileStore.storeFile(form.getAttachFile());
        List<UploadFile> storeImageFiles = fileStore.storeFiles(form.getImageFiles());

        UpThing upThing = new UpThing();
        upThing.setItemName(form.getItemName());
        upThing.setAttachFile(attachFile);
        upThing.setImageFiles(storeImageFiles);
        upThingRepository.save(upThing);

        redirectAttributes.addAttribute("itemId", upThing.getId());
        return "redirect:/items/{itemId}";

    }

    @GetMapping("/items/{id}")
    public String items(@PathVariable Long id, Model model) {
        UpThing upThing = upThingRepository.findById(id);
        model.addAttribute("item", upThing);
        return "item-view";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFulPath(filename));
    }

    @GetMapping("/attach/{itemId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long itemId) throws MalformedURLException {

        UpThing upThing = upThingRepository.findById(itemId);
        String storeFileName = upThing.getAttachFile().getStoreFileName();
        String uploadFileName = upThing.getAttachFile().getUploadFileName();

        UrlResource resource = new UrlResource("file: " + fileStore.getFulPath(storeFileName));


        log.info("uploadFileName ={}", uploadFileName);

        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename =\"" +
                encodedUploadFileName + "\"";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

}
