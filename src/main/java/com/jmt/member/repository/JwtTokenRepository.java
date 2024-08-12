package com.jmt.member.repository;

import com.jmt.member.entities.JwtToken;
import org.springframework.data.repository.CrudRepository;
public interface JwtTokenRepository extends CrudRepository<JwtToken, String> {
}
