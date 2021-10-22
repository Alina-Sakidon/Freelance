package API.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Job {
    private int id;
    private String title;
    private String description;
    private int price;
    private String user;
    private int noOfComments;

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", user='" + user + '\'' +
                ", noOfComments=" + noOfComments +
                '}';
    }
}
