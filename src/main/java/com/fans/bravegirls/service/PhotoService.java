package com.fans.bravegirls.service;

import java.util.Comparator;
import java.util.List;
import com.fans.bravegirls.dao.FolderDao;
import com.fans.bravegirls.dao.PhotoDao;
import com.fans.bravegirls.vo.model.FolderVO;
import com.fans.bravegirls.vo.model.PhotoDateVO;
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

    private static String ROOT_FOLDER_ID = "1IhcQ1_-1_tujGhPmJpwb69LJVIAPL_nX";


    public List<PhotoVO> getPhotosInFolder(PhotoPageable pageable) {

        return photoDao.selectPhotosInFolder(pageable);
    }


    public int countPhotosInFolder(PhotoPageable pageable) {

        return photoDao.countPhotosInFolder(pageable.getFolderId());
    }


    public List<FolderVO> getFoldersInParentFolder(String folderId) {
        if(folderId == null) {
            folderId = ROOT_FOLDER_ID;
        }

        return folderDao.selectFoldersInParentFolder(folderId);
    }


    public void getFolderPhotosInParentFolder(String folderId, List<FolderVO> holder) {
        List<FolderVO> folderVOS = folderDao.selectFoldersInParentFolder(folderId);
        Comparator<PhotoVO> comparatorByEventDate = Comparator.comparing(PhotoVO::getEventDate).reversed();

        for(FolderVO folderVO : folderVOS) {
            if(folderVO.getPhotoCnt() > 0) {
                List<PhotoVO> photoVOS =
                        this.getPhotosInFolder(new PhotoPageable(folderVO.getId(), 1000, 0));
                folderVO.setPhotos(photoVOS);
                folderVO.setLatestEventDate(
                        photoVOS.stream().max(comparatorByEventDate).orElseThrow().getEventDate());
                holder.add(folderVO);
            }

            getFolderPhotosInParentFolder(folderVO.getId(), holder);
        }
    }


    public List<PhotoDateVO> getPhotoDates(String folderId) {
        return photoDao.countPhotoDates(folderId);
    }


}
