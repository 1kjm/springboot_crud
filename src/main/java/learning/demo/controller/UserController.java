package learning.demo.controller;

import learning.demo.entity.User;
import learning.demo.exception.ResourceNotFoundException;
import learning.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable(value = "id") Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not found with id : " + userId));
    }


    @PostMapping
    public User createUser(@RequestBody User user){
        if(user!=null&&user.getId()==null){
           return userRepository.save(user);
        }else return  null;
    }


    @PutMapping("/{id}")
public User updateUser(@PathVariable(value = "id") Long userId,
        @RequestBody User requestBody){
       User user= userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not found"));
       user.setFirstName(requestBody.getFirstName());
       user.setLastName(requestBody.getLastName());
       user.setEmail(requestBody.getEmail());

    return userRepository.save(user);
}

@DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable(value ="id") Long userId){
User user=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found with id : "+userId));
userRepository.delete(user);
return ResponseEntity.ok().build();
}

}
