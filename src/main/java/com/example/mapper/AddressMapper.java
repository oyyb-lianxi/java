package com.example.mapper;



import com.example.model.domain.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AddressMapper {

//    <!-- 根据用户ID查询用户地址信息 -->
    @Select("SELECT * FROM address  WHERE userId = #{userId}")
    Address getUserAddressById(@Param("userId") String userId);

    int saveUserAddress(Address address);

    int updateAddressById(Address address);

    int deleteUserAddressById(Address address);

    List<Address> getUsersAddressByConditions(Address address);
}
