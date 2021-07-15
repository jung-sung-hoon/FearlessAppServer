package com.fans.bravegirls.dao;

import java.util.List;
import com.fans.bravegirls.vo.model.PhotoPageable;
import com.fans.bravegirls.vo.model.PhotoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PhotoDao {
    List<PhotoVO> selectPhotosInFolder(PhotoPageable pageable);
    int countPhotosInFolder(String folderId);
}
