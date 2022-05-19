package com.sayurbox.sequel2api.app.controller;

import com.sayurbox.sequel2api.app.client.Response;
import com.sayurbox.sequel2api.app.config.Api;
import com.sayurbox.sequel2api.app.config.PathParameter;
import com.sayurbox.sequel2api.app.config.QueryParameter;
import com.sayurbox.sequel2api.app.config.Root;
import com.sayurbox.sequel2api.app.service.ReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@SuppressWarnings("unchecked")
public class ApiController {

    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Autowired
    private ReaderService readerService;

    @Autowired
    private Root root;

    @PostConstruct
    public void init() throws NoSuchMethodException {
        RequestMappingInfo.BuilderConfiguration options = new RequestMappingInfo.BuilderConfiguration();
        //options.setPatternParser(new PathPatternParser()); // for spring boot version 2.6.6
        for (Api p : root.getApi()) {
            initApi(p, options);
        }
    }

    void initApi(Api api, RequestMappingInfo.BuilderConfiguration options) throws NoSuchMethodException {
        requestMappingHandlerMapping.registerMapping(
                RequestMappingInfo.paths(api.getUrl())
                        .methods(RequestMethod.GET)
                        .produces(MediaType.APPLICATION_JSON_VALUE)
                        .options(options)
                        .build(),
                this,
                ApiController.class.getDeclaredMethod("serviceHandler", HttpServletRequest.class));
    }

    public ResponseEntity<Response> serviceHandler(HttpServletRequest httpServletRequest)
            throws NoSuchMethodException {
        Map<String, Object> partVars = (Map<String, Object>) httpServletRequest
                .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        logger.info("path variables {}", partVars);
        String apiName = httpServletRequest
                .getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString();
        Api api = getApiByName(apiName);

        try {
            List<Pair<PathParameter, String>> pathParams = populateParams(api.getPathParameters(),
                    partVars);
            List<Pair<QueryParameter, String>> queryParams = populateParams(api.getQueryParameters(),
                    httpServletRequest);

            if (api.isSingleResult()) {
                Map<String, Object> values = readerService.get(api.getQuery(),
                        queryParams, pathParams);
                return Response.okResponse(values);
            }

            List<Map<String, Object>> values = readerService.list(api.getQuery(),
                    queryParams, pathParams);
            return Response.okResponse(values);
        } catch (HttpClientErrorException e) {
            return Response.badRequestResponse(e.getMessage());
        } catch (BadSqlGrammarException e) {
            logger.error("sql exception: {} - {}", e.getSql(), e.getSQLException().getMessage());
            return Response.internalErrorResponse("SQL error:" + e.getSQLException().getMessage());
        }
    }

    private Api getApiByName(String name) throws NoSuchMethodException {
        Optional<Api> optionalApi =
                root.getApi().stream().filter(p -> p.getUrl().equals(name)).findFirst();
        return optionalApi.orElseThrow(() -> new NoSuchMethodException("Api not found"));
    }

    public List<Pair<PathParameter, String>> populateParams(List<PathParameter> pathParams,
                                                            Map<String, Object> partVars) {
        List<Pair<PathParameter, String>> params = new ArrayList<>();
        for (PathParameter p : pathParams) {
            if (!partVars.containsKey(p.getName())) {
                throw new HttpClientErrorException("Path parameter " + p.getName() + " is required",
                        HttpStatus.BAD_REQUEST, "", HttpHeaders.EMPTY, null, null);
            }
            params.add(Pair.of(p, partVars.get(p.getName()).toString()));
        }
        return params;
    }

    public List<Pair<QueryParameter, String>> populateParams(List<QueryParameter> queryParams,
                                                             HttpServletRequest httpServletRequest) {
        List<Pair<QueryParameter, String>> params = new ArrayList<>();
        for (QueryParameter p : queryParams) {
            if (!httpServletRequest.getParameterMap().containsKey(p.getName()) && p.getRequired()) {
                throw new HttpClientErrorException("Query parameter " + p.getName() + " is required",
                        HttpStatus.BAD_REQUEST, "", HttpHeaders.EMPTY, null, null);
            }
            String value = httpServletRequest.getParameter(p.getName());
            if (value.isEmpty() && StringUtils.hasText(p.getDefaultValue())) {
                value = p.getDefaultValue();
            }
            params.add(Pair.of(p, value));
        }
        return params;
    }

}
