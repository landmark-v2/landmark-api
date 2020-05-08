package com.landmark.app.controller;

import com.landmark.app.service.support.QnaService;
import com.landmark.app.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.landmark.app.utils.constants.Constants.QNA_API;

@RestController
@RequestMapping(value = QNA_API)
public class QnaController extends LoggerUtils {
    private QnaService qnaService;

    @Autowired
    public QnaController(QnaService qnaService) { this.qnaService = qnaService; }
}
