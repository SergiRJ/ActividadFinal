package com.nttdata.actividadfinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.actividadfinal.repository.entity.Rol;

public interface RolRepoJPA extends JpaRepository<Rol,String> {

}
