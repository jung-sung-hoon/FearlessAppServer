package com.fans.bravegirls.dao;

import java.util.List;
import com.fans.bravegirls.vo.model.FolderVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FolderDao {
    List<FolderVO> selectFoldersInParentFolder(String folderId);
}
