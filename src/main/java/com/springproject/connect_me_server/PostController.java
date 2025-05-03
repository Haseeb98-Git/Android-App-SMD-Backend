package com.springproject.connect_me_server;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")
public class PostController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostController(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<Post> posts = postRepository.findAll();

        List<PostResponse> response = posts.stream().map(post -> {
            User user = userRepository.findById(post.getUserId()).orElse(null);
            return new PostResponse(
                    post.getPostId(),
                    post.getUserId(),
                    user != null ? user.getUserName() : "Unknown",
                    post.getMediaUrl(),
                    post.getCaption(),
                    user != null ? user.getProfilePicture() : ""
            );
        }).toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/upload-post")
    public ResponseEntity<String> uploadPost(
            @RequestParam("image") MultipartFile file,
            @RequestParam("userId") String userId,
            @RequestParam("caption") String caption) {
        System.out.println("Upload request received.");
        try {
            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            String fileName = UUID.randomUUID().toString() + fileExtension;

            // Save file to static/images/
            Path uploadPath = Paths.get("uploads/images/");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Save post to DB
            Post post = new Post();
            post.setUserId(userId);
            post.setMediaUrl(fileName);
            post.setCaption(caption);
            //post.setTimestamp((int) (System.currentTimeMillis() / 1000)); // UNIX timestamp

            postRepository.save(post);

            return ResponseEntity.ok("Upload successful");
        } catch (Exception e) {
            System.out.println("Upload request denied.");
            e.printStackTrace();
            return ResponseEntity.status(500).body("Upload failed");
        }
    }

}



