package com.example.krevar_backend.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.example.krevar_backend.entity.SubscriptionEntity;
import io.micrometer.common.lang.NonNull;

public class SubscriptionEntityRowMapper implements RowMapper<SubscriptionEntity> {

    @Override
    public SubscriptionEntity mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {

        long id = rs.getLong("id");
        String plan = rs.getString("plan");
        Integer maxImageGeneration = rs.getInt("max_image_generation");

        SubscriptionEntity subscription = new SubscriptionEntity(id, plan, maxImageGeneration);

        return subscription;
    }
}
