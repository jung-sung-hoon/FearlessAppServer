package com.fans.bravegirls.service;

import java.util.List;
import com.fans.bravegirls.dao.FolderDao;
import com.fans.bravegirls.dao.PhotoDao;
import com.fans.bravegirls.vo.model.FolderVO;
import com.fans.bravegirls.vo.model.PhotoPageable;
import com.fans.bravegirls.vo.model.PhotoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhotoService {

    private final FolderDao folderDao;
    private final PhotoDao photoDao;

    private static String ROOT_FOLDER_ID = "1IhcQ1_-1_tujGhPmJpw";


    public List<PhotoVO> getPhotosInFolder(PhotoPageable pageable) {

        if(pageable.getFolderId() == null) {
            pageable.setFolderId(ROOT_FOLDER_ID);
        }

        return photoDao.selectPhotosInFolder(pageable);
    }

    public int countPhotosInFolder(PhotoPageable pageable) {

        if(pageable.getFolderId() == null) {
            pageable.setFolderId(ROOT_FOLDER_ID);
        }

        return photoDao.countPhotosInFolder(pageable.getFolderId());
    }

    public List<FolderVO> getFoldersInParentFolder(String folderId) {
        if(folderId == null) {
            folderId = ROOT_FOLDER_ID;
        }

        return folderDao.selectFoldersInParentFolder(folderId);
    }
}
