package com.thinkmore.forum.controller.v1;

import com.thinkmore.forum.dto.followerUsers.FollowerUsersGetDto;
import com.thinkmore.forum.service.FollowerUsersService;
import com.thinkmore.forum.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/users")
@RequiredArgsConstructor
public class FollowerUsersController {
    private final FollowerUsersService followerUsersService;

    //  view fans
    @GetMapping(path = "/follower/{username}")
    public ResponseEntity<List<FollowerUsersGetDto>> view_follower(@PathVariable("username") String target_username) {
        return ResponseEntity.ok(followerUsersService.getFollowersByUsername(target_username));
    }

    @GetMapping(path = "/followed/{username}")
    public ResponseEntity<List<FollowerUsersGetDto>> view_followed_user(@PathVariable("username") String target_username) {
        return ResponseEntity.ok(followerUsersService.getFriendsByUsername(target_username));
    }

    @GetMapping(path = "/followedStatus/{username}")
    public ResponseEntity<Boolean> checkFollowedStatus(@PathVariable("username") String username) {
        UUID usersId = UUID.fromString(Util.getJwtContext().get(0));
        return ResponseEntity.ok(followerUsersService.followStatus(username, usersId));
    }

    @PostMapping(path = "/follow/{username}")
    public ResponseEntity<FollowerUsersGetDto> follow_user(@PathVariable("username") String username) {
        UUID usersId = UUID.fromString(Util.getJwtContext().get(0));
        return ResponseEntity.ok(followerUsersService.followUsers(usersId, username));
    }

    @DeleteMapping(path = "/follow/{username}")
    public ResponseEntity<?> unfollow_user(@PathVariable("username") String target_username) {
        String username = Util.getJwtContext().get(1);
        try {
            followerUsersService.unfollowUsers(username, target_username);
            return ResponseEntity.ok().body("Deleted");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Not found");
        }
    }
}
