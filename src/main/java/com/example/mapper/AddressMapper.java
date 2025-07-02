
package com.example.mapper;


import com.example.model.domain.Admin;
import com.example.model.domain.User;
import com.example.model.domain.Adress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AddressMapper {


    @Select("SELECT * FROM adress  WHERE userId = #{userId}")
    Adress getUserAdressById(@Param("userId") String userId);

    int saveUserAdress(Address adress);

    int updateAdressById(Address adress);

    int deleteUserAdressById(Address adress);

    ListL<Address> getUsersAdressByConditions(Address adress);
}
