package com.sayurbox.sequel2api.app.service;

import com.sayurbox.sequel2api.app.config.PathParameter;
import com.sayurbox.sequel2api.app.config.QueryParameter;
import com.sayurbox.sequel2api.app.persistence.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReaderService {

    @Autowired
    private ReaderRepository readerRepository;

    public List<Map<String, Object>> list(String sql,
                                          List<Pair<QueryParameter, String>> queryParameterValues,
                                          List<Pair<PathParameter, String>> pathParameterValues) {
        MapSqlParameterSource args = provideArguments(queryParameterValues, pathParameterValues);
        return readerRepository.list(sql, args);
    }

    public Map<String, Object> get(String sql,
                                          List<Pair<QueryParameter, String>> queryParameterValues,
                                          List<Pair<PathParameter, String>> pathParameterValues) {
        MapSqlParameterSource args = provideArguments(queryParameterValues, pathParameterValues);
        return readerRepository.get(sql, args);
    }

    private MapSqlParameterSource provideArguments(List<Pair<QueryParameter, String>> queryParameters,
                                                   List<Pair<PathParameter, String>> pathParameters) {
        MapSqlParameterSource args = new MapSqlParameterSource();
        queryParameters.forEach(p -> {
            QueryParameter qp = p.getFirst();
            args.addValue(qp.getBinding(), qp.newParamValue(p.getSecond()));
        });
        pathParameters.forEach(p -> {
            PathParameter pp = p.getFirst();
            args.addValue(pp.getBinding(), pp.newParamValue(p.getSecond()));
        });
        return args;
    }
}
