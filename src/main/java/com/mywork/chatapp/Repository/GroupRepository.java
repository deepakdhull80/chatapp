package com.mywork.chatapp.Repository;

import com.mywork.chatapp.Model.Group;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface GroupRepository extends CrudRepository<Group,Integer> {

    @Modifying
    @Transactional
    @Query(value = "delete from user_group where user_id=?2 and group_id=?1",nativeQuery = true)
    void removeUserFromGroup(Integer groupId,Integer userId);

}
