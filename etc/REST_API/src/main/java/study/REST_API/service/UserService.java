package study.REST_API.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.REST_API.domain.Post;
import study.REST_API.domain.User;
import study.REST_API.exception.UserNotFoundException;
import study.REST_API.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<Post> getPostsByUserId(int id) {
        User user = userRepository.findByIdWithPosts(id)
                .orElseThrow(() -> new UserNotFoundException("id = " + id));
        return user.getPosts();
    }
}
