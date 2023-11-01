package com.hogwarts.magicSchool.service;

import com.hogwarts.magicSchool.model.Avatar;
import com.hogwarts.magicSchool.model.Student;
import com.hogwarts.magicSchool.repository.AvatarRepository;
import com.hogwarts.magicSchool.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {
    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;
    private static final Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);


    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    public AvatarServiceImpl(AvatarRepository avatarRepository, StudentRepository studentRepository) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("Был вызван метод для загрузки аватара. Идентификатор студента: {}", studentId);

        Student student = studentRepository.getById(studentId);
        Path filePath = Path.of(avatarsDir, student + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(generateDataForDB(filePath));
        avatarRepository.save(avatar);

        logger.info("Аватар успешно загружен для студента с идентификатором: {}", studentId);
    }

    private byte[] generateDataForDB(Path filePath) throws IOException {
        logger.info("Был вызван метод для генерации данных изображения для сохранения в базе данных. Путь к файлу: {}", filePath);

        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);
            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics2D = preview.createGraphics();
            graphics2D.drawImage(image, 0, 0, 100, height, null);
            graphics2D.dispose();
            ImageIO.write(preview, getExtensions(filePath.getFileName().toString()), baos);
            byte[] data = baos.toByteArray();

            logger.info("Данные изображения успешно сгенерированы");

            return data;
        }
    }

    public Avatar findAvatar(Long studentId) {
        logger.info("Был вызван метод для поиска аватара студента. Идентификатор студента: {}", studentId);

        Optional<Avatar> avatarOptional = avatarRepository.findByStudentId(studentId);
        Avatar avatar = avatarOptional.orElse(new Avatar());

        if (avatarOptional.isPresent()) {
            logger.info("Аватар найден для студента с идентификатором: {}", studentId);
        } else {
            logger.info("Аватар не найден для студента с идентификатором: {}", studentId);
        }

        return avatar;
    }

    private String getExtensions(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

        logger.debug("Извлечено расширение файла: {}", extension);

        return extension;
    }
}

