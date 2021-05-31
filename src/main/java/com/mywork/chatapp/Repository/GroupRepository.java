package com.mywork.chatapp.Repository;

import com.mywork.chatapp.Model.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends CrudRepository<Group,Integer> {
}
