package generator.mapper;

import generator.domain.User;

/**
* @author wei
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-10-31 10:59:20
* @Entity generator.domain.User
*/
public interface UserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

}
