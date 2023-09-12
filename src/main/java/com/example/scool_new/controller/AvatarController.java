package com.example.scool_new.controller;

import com.example.scool_new.dto.AvatarDto;
import com.example.scool_new.model.Avatar;
import com.example.scool_new.service.AvatarService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("avatar")
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/{id}/avatar",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long id,
                                               @RequestParam MultipartFile avatar)throws IOException {
        if(avatar.getSize()>=1024*300){
            return ResponseEntity.badRequest().body("File is not begin");
        }
        avatarService.uploadAvatar(id,avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/avatar/priview")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable long id){

        Avatar avatar = avatarService.findAvatar(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .body(avatar.getData());
    }

    @GetMapping(value = "{id}/avatar")
    public void downloadAvatar(@PathVariable long id, HttpServletResponse response) throws IOException{
        Avatar avatar = avatarService.findAvatar(id);

        Path path = Path.of(avatar.getFilePath());

        try(InputStream is = Files.newInputStream(path);
            OutputStream os = response.getOutputStream()){
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int)avatar.getFileSize());
            is.transferTo(os);
        }
    }
    @GetMapping("{page}/{size}/avatar")
    public ResponseEntity<Collection<Avatar>> getAllAvatar(@RequestParam("page")Integer numPage,
                                                          @RequestParam("size") Integer numSize){
        Collection<Avatar> avatars = avatarService.getAllAvatars(numPage,numSize);


        return ResponseEntity.ok(avatars);
    }

    @GetMapping
    public List<AvatarDto> page(@RequestParam(value = "page",required = false,defaultValue = "0") int pageNum,
                                @RequestParam(value = "size",required = false,defaultValue = "10") int sizeNum){
        return avatarService.page(pageNum,sizeNum);

    }
}
