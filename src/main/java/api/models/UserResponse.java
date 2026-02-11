package api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {
    private int page;
    private int per_page;
    private int total;
    private int total_pages;
    private List<UserData> data;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UserData {
        private int id;
        private String email;
        private String first_name;
        private String last_name;
        private String avatar;
    }
}
