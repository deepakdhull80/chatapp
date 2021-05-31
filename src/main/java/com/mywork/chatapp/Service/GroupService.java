package com.mywork.chatapp.Service;

import com.mywork.chatapp.Exception.GroupNotFoundException;
import com.mywork.chatapp.Model.Group;
import com.mywork.chatapp.Repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    GroupRepository groupRepository;

    public Group addGroup(Group group){
        return groupRepository.save(group);
    }

    public Group getGroup(int groupID) throws GroupNotFoundException {
        Optional<Group> group = groupRepository.findById(groupID);
        if(group.isPresent()){
            return group.get();
        }
        throw new GroupNotFoundException("Group not exist :"+groupID);

    }
    public Group updateGroup(Group group) throws GroupNotFoundException {
        Optional<Group> groupOptional = groupRepository.findById(group.getId());
        if(groupOptional.isPresent()){
            return groupRepository.save(group);
        }
        throw new GroupNotFoundException("Group not exist :"+group.getId());
    }

    public void deleteGroup(int groupId){
        groupRepository.deleteById(groupId);
    }


}
