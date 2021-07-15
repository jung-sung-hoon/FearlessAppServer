package com.fans.bravegirls.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.fans.bravegirls.common.BaseRestController;
import com.fans.bravegirls.service.PhotoService;
import com.fans.bravegirls.vo.model.PageInfoVo;
import com.fans.bravegirls.vo.model.PhotoPageable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/app-api/v2", produces = MediaType.APPLICATION_JSON_VALUE)
public class PhotoController extends BaseRestController {

    private final PhotoService photoService;


    @GetMapping(value = "/photos")
    public ResponseEntity<?> getPhotos(HttpServletRequest request,
            @RequestParam(value = "folderId", required = false) String folderId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {

        PhotoPageable pageable = new PhotoPageable();
        pageable.setFolderId(folderId);
        pageable.setOffSet((page - 1) * size);
        pageable.setPageSize(size);

        PageInfoVo pageInfo = new PageInfoVo();
        pageInfo.setPage(page);
        pageInfo.setSize(size);
        pageInfo.setTotal(photoService.countPhotosInFolder(pageable));

        Map<String, Object> result_map = new HashMap<>();
        result_map.put("list", photoService.getPhotosInFolder(pageable));
        result_map.put("pageInfo", pageInfo);

        return success(result_map);
    }


    @GetMapping(value = "/folders")
    public ResponseEntity<?> getFolders(HttpServletRequest request,
            @RequestParam(value = "parentFolderId", required = false) String parentFolderId) {
        Map<String, Object> result_map = new HashMap<>();

        result_map.put("list", photoService.getFoldersInParentFolder(parentFolderId));

        return success(result_map);
    }
}
