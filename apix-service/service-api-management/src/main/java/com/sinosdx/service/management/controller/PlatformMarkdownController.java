/*
 * Copyright © 2022 SinoSDX (biz@sinosdx.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sinosdx.service.management.controller;//package com.sinosdx.middle.application.controller;
//
//import com.sinosdx.middle.application.dao.entity.PlatformMarkdown;
//import com.sinosdx.middle.application.result.R;
//import com.sinosdx.middle.application.service.PlatformMarkdownService;
////import com.sinosdx.starter.api.oss.base.OssFactory;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.Map;
//
///**
// * @author wendy
// * @date 2020/12/24
// */
//@RestController
//@RequestMapping("/app/platform/markdown")
//public class PlatformMarkdownController {
//
//    @Autowired
//    private PlatformMarkdownService platformMarkdownService;
//
//    /**
//     * 创建新平台文档
//     *
//     * @param platformMarkdown
//     * @return
//     */
//    @PostMapping("/create")
//    public R<PlatformMarkdown> createNewMarkdown(@RequestBody PlatformMarkdown platformMarkdown) {
//        return platformMarkdownService.createNewMarkdown(platformMarkdown);
//    }
//
//    /**
//     * 修改平台文档
//     *
//     * @param platName
//     * @param map
//     * @return
//     */
//    @PutMapping("/modify/{platName}")
//    public R<PlatformMarkdown> modifyMarkdown(@PathVariable("platName") String platName,
//                                              @RequestBody Map<String, String> map) {
//        return platformMarkdownService.modifyMarkdown(platName, map.get("markdown"));
//    }
//
//    /**
//     * 查询平台文档
//     *
//     * @param platName
//     * @return
//     */
//    @GetMapping("/query/{platName}")
//    public R<PlatformMarkdown> queryMarkdown(@PathVariable("platName") String platName) {
//        return platformMarkdownService.queryMarkdown(platName);
//    }
//
//    /**
//     * 上传文件
//     * @param file
//     * @return
//     */
//    @PostMapping("/upload")
//    public R<Object> uploadFile(@RequestBody MultipartFile file){
//        String path = OssFactory.getOss().fileUpload(file, "markdown", true);
//        return R.success(path);
//    }
//
//    /**
//     * 上传文件url
//     * @param fileName
//     * @param versionId
//     * @param expireTime 单位秒
//     * @return
//     */
//    @GetMapping("/getUrl")
//    public R<Object> getUrl(@RequestParam String fileName,@RequestParam(required = false) String versionId,@RequestParam(required = false) Long expireTime){
//        String path = OssFactory.getOss().getUrl(fileName,versionId,expireTime);
//        return R.success(path);
//    }
//}
