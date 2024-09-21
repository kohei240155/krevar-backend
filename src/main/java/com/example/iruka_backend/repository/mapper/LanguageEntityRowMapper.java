package com.example.iruka_backend.repository.mapper;

import com.example.iruka_backend.entity.LanguageEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

public class LanguageEntityRowMapper implements RowMapper<LanguageEntity> {

    @Override
    public LanguageEntity mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {

        long id = rs.getLong("id");
        String code = rs.getString("language_code");
        String name = rs.getString("language_name");

        LanguageEntity languageEntity = new LanguageEntity(id, code, name);

        return languageEntity;
    }
}
