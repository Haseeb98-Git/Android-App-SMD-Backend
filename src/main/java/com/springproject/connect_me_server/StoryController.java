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
@RequestMapping("/api/stories")
@CrossOrigin(origins = "*")
public class StoryController {

    private final StoryRepository storyRepository;
    private final UserRepository userRepository;

    public StoryController(StoryRepository storyRepository, UserRepository userRepository) {
        this.storyRepository = storyRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<StoryResponse>> getAllStories() {
        List<Story> stories = storyRepository.findAll();

        List<StoryResponse> response = stories.stream().map(story -> {
            User user = userRepository.findById(story.getUserId()).orElse(null);
            return new StoryResponse(
                    story.getStoryId(),
                    story.getUserId(),
                    user != null ? user.getUserName() : "Unknown",
                    story.getMediaUrl(),
                    user != null ? user.getProfilePicture() : ""
            );
        }).toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/upload-story")
    public ResponseEntity<String> uploadStory(
            @RequestParam("image") MultipartFile file,
            @RequestParam("userId") String userId) {
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
            Story story = new Story();
            story.setUserId(userId);
            story.setMediaUrl(fileName);
            //post.setTimestamp((int) (System.currentTimeMillis() / 1000)); // UNIX timestamp

            storyRepository.save(story);
            System.out.println("It worked.");
            return ResponseEntity.ok("Upload successful");
        } catch (Exception e) {
            System.out.println("It worked.");
            e.printStackTrace();
            String errorMessage = e.getMessage();
            System.out.println("Error: " + errorMessage);
            return ResponseEntity.status(500).body("Upload failed: " + errorMessage);
        }

    }
}



