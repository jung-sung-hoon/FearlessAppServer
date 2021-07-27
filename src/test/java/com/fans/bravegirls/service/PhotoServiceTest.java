package com.fans.bravegirls.service;

import java.util.List;
import com.fans.bravegirls.vo.model.FolderVO;
import com.fans.bravegirls.vo.model.PhotoPageable;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class PhotoServiceTest {
    @Autowired
    private PhotoService photoService;

    @Test
    public void test_getPhotosInFolder() {
        PhotoPageable pageable = new PhotoPageable();
        pageable.setPageSize(20);
        pageable.setOffSet(0);
        pageable.setFolderId("19yuPSrRLHoZW1sV_T1l0Mxei7jzNVjgX");
        assertNotNull(photoService.getPhotosInFolder(pageable));
        System.out.println(photoService.getPhotosInFolder(pageable));
    }

    @Test
    public void test_getFoldersInFolder() {

        assertNotNull(photoService.getFoldersInParentFolder(null));
        System.out.println(photoService.getFoldersInParentFolder(null));
    }

    @Test
    public void test_getFolderPhotosInParentFolder() {
        List<FolderVO> holder = Lists.newArrayList();

        photoService.getFolderPhotosInParentFolder("1Fgkg-MHO-TTc1ichsi6mKOJfVFMrU1yC", holder);

        System.out.println(holder.size());
        System.out.println(holder);
    }
}
