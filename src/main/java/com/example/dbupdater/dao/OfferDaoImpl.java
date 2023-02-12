package com.example.dbupdater.dao;

import com.example.dbupdater.model.Offer;
import com.example.dbupdater.service.OfferProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class OfferDaoImpl implements OfferDao {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public OfferDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Offer> getChunkFromDb(int rowsSize, int offset) {
        String sql = "SELECT offer_id, exposable, client_fio FROM offer WHERE exposable = 1 LIMIT "
                + rowsSize + " OFFSET " + offset;
        List<Offer> rows = jdbcTemplate.query(sql, new OfferRowMapper());
        System.out.println("This is chunk from offset = " + offset + " to " + (offset + rowsSize));
        return rows;
    }

    @Override
    public int getSizeOfOffers() {
        String sqlRowsCount = "SELECT count(offer_id) FROM offer";
        Optional<Integer> optRowsCount = Objects.requireNonNull(jdbcTemplate.queryForObject(sqlRowsCount, Integer.class))
                .describeConstable();
        int rowsCount = optRowsCount.orElseThrow();
        System.out.println("rowsCount in table = " + rowsCount);
        return rowsCount;
    }

    private static class OfferRowMapper implements RowMapper<Offer> {
        @Override
        public Offer mapRow(ResultSet resultSet, int i) throws SQLException {
            Offer offer = new Offer();
            offer.setOfferId(resultSet.getLong("offer_id"));
            offer.setExposable(resultSet.getInt("exposable"));
            offer.setClientFio(resultSet.getString("client_fio"));
            return offer;
        }
    }
}
