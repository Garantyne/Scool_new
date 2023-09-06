package com.example.scool_new.service;

import com.example.scool_new.dto.AvatarDto;
import com.example.scool_new.mapper.AvatarMapper;
import com.example.scool_new.model.Avatar;
import com.example.scool_new.model.Student;
import com.example.scool_new.repositorys.AvatarRepository;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.CREATE_NEW;


@Service
@Transactional
public class AvatarService {

    @Value("${path.to.avatars.folder}")
    private String avatarDir;
    private final AvatarRepository avatarRepository;
    private final StudentService studentService;
    private final AvatarMapper avatarMapper;

    public AvatarService(AvatarRepository avatarRepository, StudentService studentService, AvatarMapper avatarMapper) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
        this.avatarMapper = avatarMapper;
    }

    public void uploadAvatar(Long id, MultipartFile file) throws IOException {
        Student student = studentService.findStudent(id);
            //это сохраняет на диск
        Path filePath = Path.of(avatarDir, id + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath,CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is,1024);
             BufferedOutputStream bos = new BufferedOutputStream(os,1024);
        ){
            bis.transferTo(bos);
        }//это сохраняет в базу данных
        Avatar avatar = findAvatar(id);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(generateImageData(filePath));
        avatarRepository.save(avatar);
    }

    private byte[] generateImageData(Path filePath) throws IOException {
        try(InputStream is = Files.newInputStream(filePath);
            BufferedInputStream bis = new BufferedInputStream(is,1024);
            ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight()/(image.getWidth()/100);
            BufferedImage data = new BufferedImage(100,height,image.getType());
            Graphics2D graphics2D = data.createGraphics();
            graphics2D.drawImage(image,0,0,100, height,null);
            graphics2D.dispose();
            //1)Зачем тащить файл мультипартдата в сервс если он относится к веб интерфейсу
            //2)зачем картинка хранится и на диске и в базе?
            //3)
            ImageIO.write(data,getExtension(filePath.getFileName().toString()),baos);
            return baos.toByteArray();
        }
    }

    public Avatar findAvatar(Long id) {
        return avatarRepository.findByStudentId(id).orElse(new Avatar());
    }


    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private byte[] generateDataForDB(Path filePath)throws IOException{
        try(
            InputStream is = Files.newInputStream(filePath);
            BufferedInputStream bis = new BufferedInputStream(is,1024);
            ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            BufferedImage image = ImageIO.read(bis);
            int height = image.getHeight()/(image.getWidth()/100);
            BufferedImage preview = new BufferedImage(100,height,image.getType());
            Graphics2D graphics2D = preview.createGraphics();
            graphics2D.drawImage(image,0,0,100,height,null);
            graphics2D.dispose();

            ImageIO.write(preview,getExtension(filePath.getFileName().toString()),baos);
            return baos.toByteArray();
            }
    }

    public Collection<Avatar> getAllAvatars(Integer numPage, Integer numSize) {
        PageRequest pageRequest = PageRequest.of(numPage - 1,numSize);
        return avatarRepository.findAll(pageRequest).stream().collect(Collectors.toList());
    }

    public Collection<Avatar> getPage(){
        return null;
    }

    public List<AvatarDto> page(int pageNum, int sizeNum) {
        return avatarRepository.findAll(PageRequest.of(pageNum,sizeNum)).stream()
                .map(avatarMapper::toDto).collect(Collectors.toList());
    }
}