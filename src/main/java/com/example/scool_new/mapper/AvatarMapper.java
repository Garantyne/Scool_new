package com.example.scool_new.mapper;

import com.example.scool_new.dto.AvatarDto;
import com.example.scool_new.model.Avatar;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Commit;

@Component
public class AvatarMapper {
    public AvatarDto toDto(Avatar avatar){
        AvatarDto avatarDto = new AvatarDto();
        avatarDto.setId(avatar.getId());
        avatarDto.setFileSize(avatar.getFileSize());
        avatarDto.setMediaType(avatar.getMediaType());
        //avatarDto.setAvatarUrl(avatar.getFilePath());
        avatarDto.setAvatarUrl("http://localhost:8080/avatar/"+avatar.getId()+"/avatar");
        return avatarDto;
    }
}
