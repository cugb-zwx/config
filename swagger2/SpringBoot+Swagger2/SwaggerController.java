package com.example.demo.controller;

import com.example.demo.Entity.SwaggerTestEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Title:
 * Project: WebserviceTest
 * Description:
 * Date: 2019/7/9
 * Copyright: Copyright (c) 2020
 * Company: 北京中科院软件中心有限公司 (SEC)
 *
 * @author zwx
 * @version 1.0
 */
@RestController
@RequestMapping("api/demo")
@Api(value = "测试接口", tags = "TestController", description = "测试接口相关")
public class SwaggerController {

    @ApiOperation(value = "demo", httpMethod = "GET", response = SwaggerTestEntity.class, notes = "demo接口的描述性文字")
    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    public Object demo(@ModelAttribute SwaggerTestEntity req) {

        SwaggerTestEntity result = new SwaggerTestEntity();
        result.setAuditReason("11111111111");
        result.setExchangeBatchId(123);
        result.setStatus((byte) 1);
        result.setVersion(256);
//        boolean error = false;
//        if (error) {
//            throw new Exception("SwaggerController has error");
//        }
        return result;
    }

    /**
     * 权限异常
     */
    @ExceptionHandler({Exception.class})
    public String authorizationException(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println();
        return "null";
    }
}
