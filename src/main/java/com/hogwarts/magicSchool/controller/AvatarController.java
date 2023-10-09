package com.hogwarts.magicSchool.controller;

import com.hogwarts.magicSchool.model.Avatar;
import com.hogwarts.magicSchool.service.AvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/avatars")
public class AvatarController {
    private final AvatarService avatarService;
    private final String avatarsDir;

    @Autowired
    public AvatarController(AvatarService avatarService, @Value("${path.to.avatars.folder}") String avatarsDir) {
        this.avatarService = avatarService;
        this.avatarsDir = avatarsDir;
    }

    @PostMapping("/upload")
    public ResponseEntity<Avatar> uploadAvatar(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Файл не предоставлен", HttpStatus.BAD_REQUEST);
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            Avatar avatar = new Avatar();
            avatar.setFilePath(fileName);
            avatar.setFileSize(file.getSize());
            avatar.setMediaType(file.getContentType());
            avatar.setData(file.getBytes());

            Avatar savedAvatar = avatarService.saveAvatar(avatar);

            Path uploadDir = Path.of(avatarsDir);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            Path filePath = uploadDir.resolve(savedAvatar.getId() + "_" + fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return new ResponseEntity<>(savedAvatar, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Не удалось загрузить файл", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAvatarById(@PathVariable Long id) {
        Avatar avatar = avatarService.getAvatarById(id);
        if (avatar == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + avatar.getFilePath() + "\"")
                .contentType(org.springframework.http.MediaType.parseMediaType(avatar.getMediaType()))
                .contentLength(avatar.getFileSize())
                .body(avatar.getData());
    }
}