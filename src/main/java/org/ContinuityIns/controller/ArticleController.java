package org.ContinuityIns.controller;

import jakarta.validation.constraints.Pattern;
import org.ContinuityIns.entity.Result;
import org.ContinuityIns.service.ArticleService;
import jakarta.validation.constraints.NotNull;
import org.ContinuityIns.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/article")
@Validated
public class ArticleController {
    /**
     *@Author:  Oii Woof
     *@Date: 2025/1/16 23:00<p>
     *@Description: 进行参数验证,并实现对文章的增删改查
     **/

}