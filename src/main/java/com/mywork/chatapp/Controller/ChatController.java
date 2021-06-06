package com.mywork.chatapp.Controller;



import com.mywork.chatapp.Exception.GroupNotFoundException;
import com.mywork.chatapp.Exception.UserNotFoundException;
import com.mywork.chatapp.Model.Group;
import com.mywork.chatapp.Model.Message;
import com.mywork.chatapp.Model.User;
import com.mywork.chatapp.Service.GroupService;
import com.mywork.chatapp.Service.MessageService;
import com.mywork.chatapp.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private MessageService messageService;

    Logger logger = LoggerFactory.getLogger(ChatController.class);

    @GetMapping(value = {"/","/dashboard"})
    public String getDashboard(HttpServletRequest request,
                               Model model) throws UserNotFoundException {
        HttpSession session = request.getSession();
        String userid = (String) session.getAttribute("userid");

        if(userid ==null || userid.length()==0){
            return "redirect:/login";
        }
        User user = userService.getUser(Integer.valueOf(userid));
        logger.info("User :",user);
        model.addAttribute("user",user);

        return "dashboard";
    }

    @MessageMapping("/message/{groupId}")
    public void chatAppMessage(@DestinationVariable("groupId") String groupId, @Payload String message) throws GroupNotFoundException, UserNotFoundException {

        JsonParser springParser = JsonParserFactory.getJsonParser();
        Map<String, Object> map = springParser.parseMap(message);

        Message message1 = new Message();
        message1.setMessage((String)map.get("message"));
        Date date = new Date();
        message1.setMessageDate(date);
        User user = userService.getUser(Integer.valueOf((String) map.get("userid")));
        message1.setUser(user);
        message1.setGroupN(groupService.getGroup(Integer.valueOf(groupId)));

        Message message2= messageService.addMessage(message1);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");

        Map<String ,String> response = new HashMap<>();
        response.put("username",user.getUserName());
        response.put("message",(String)map.get("message"));
        response.put("date",simpleDateFormat.format(date));
        response.put("messageId",String.valueOf(message2.getId()));
        simpMessagingTemplate.convertAndSend("/topic/"+groupId,response);
    }


    @PostMapping("create-group")
    @ResponseBody
    public Group createGroup(@RequestParam String groupName,HttpServletRequest request) throws UserNotFoundException {

        Group group = new Group();

        String userId =(String) request.getSession().getAttribute("userid");
        int userid = Integer.valueOf(userId);

        User user = userService.getUser(userid);

        group.setGroupName(groupName);
        Set<Group> groups;
        if(user.getGroups().size()==0) {
            groups = new HashSet<Group>();
        }else{
            groups = user.getGroups();
        }

        groups.add(group);

        user.setGroups(groups);

        userService.updateUser(user);
        return group;
    }


    @GetMapping("/chat-group/{groupid}")
    public String groupGetRequest(@PathVariable("groupid") int groupid, HttpServletRequest request, ModelMap map) throws UserNotFoundException {

        String userid =(String) request.getSession().getAttribute("userid");

        if(userid== null){
            request.getSession().setAttribute("userid",null);
            return "redirect:/login";
        }
        User user = userService.getUser(Integer.valueOf(userid));

        for(Group g :user.getGroups()){
            if(g.getId() == groupid){
                map.addAttribute("user",user);
                Set<Message> ms = g.getMessages().stream().sorted(new Comparator<Message>() {
                    @Override
                    public int compare(Message o1, Message o2) {
                        return o1.getMessageDate().compareTo(o2.getMessageDate());
                    }
                }).collect(Collectors.toCollection(LinkedHashSet::new));

                g.setMessages(ms);

                map.addAttribute("group",g);
                return "group";
            }
        }

        return "redirect:/";
    }


    @PostMapping("add-group")
    @ResponseBody
    public boolean addGroup(@Payload int groupid,HttpServletRequest request) throws UserNotFoundException {
        String userid =(String) request.getSession().getAttribute("userid");
        User user;
        Group group;
        if(userid== null){
            request.getSession().setAttribute("userid",null);
            return false;
        }
        try {
            user = userService.getUser(Integer.valueOf(userid));
        }catch (UserNotFoundException u){
            u.printStackTrace();
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        try {
            group = groupService.getGroup(groupid);
        }catch (GroupNotFoundException g){
            g.printStackTrace();
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        user.getGroups().add(group);

        userService.updateUser(user);

        return true;
    }


    @GetMapping("/group/{groupId}/{userId}/remove")
    public String removeFromGroup(@PathVariable("groupId") Integer groupId,@PathVariable("userId") Integer userId,HttpServletRequest request) throws Exception {

        String uid = (String) request.getSession().getAttribute("userid");
        if(uid ==null){
            throw new Exception("Invalid Request");
        }
        if(Integer.valueOf(uid)== userId) {
            groupService.deleteGroup(groupId,userId );
        }else{
            throw new Exception("User deleting authorize group");
        }

        return "redirect:/dashboard";
    }




}
