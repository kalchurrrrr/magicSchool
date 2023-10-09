package com.hogwarts.magicSchool.repository;

import com.hogwarts.magicSchool.model.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
}
