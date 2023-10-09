package com.hogwarts.magicSchool.service;

import com.hogwarts.magicSchool.model.Avatar;
import com.hogwarts.magicSchool.repository.AvatarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvatarService {
    private final AvatarRepository avatarRepository;

    @Autowired
    public AvatarService(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    public Avatar saveAvatar(Avatar avatar) {
        return avatarRepository.save(avatar);
    }

    public Avatar getAvatarById(Long id) {
        return avatarRepository.findById(id).orElse(null);
    }

    public void deleteAvatar(Long id) {
        avatarRepository.deleteById(id);
    }
}