package com.winnie.drools.ruleconfig;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;

/**
 * @author : winnie [wangliu023@qq.com]
 * @date : 2020/6/3
 * @desc
 */
@SpringBootTest
public class FilePathTest {

    private Logger logger = LoggerFactory.getLogger(FilePathTest.class);

    @Test
    void testFilePath() throws IOException {
        Resource resource = new FileSystemResource("");
        File file = resource.getFile().getAbsoluteFile();
        File file1 = new File(file, "").getAbsoluteFile();
        logger.info("filePath=" + file1.getAbsolutePath());
    }
}
