package shoppingmall.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import shoppingmall.ReviewServiceApplication;

@Entity
@Table(name = "Review_table")
@Setter
@Getter
//<<< DDD / Aggregate Root
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long orderId;

    private Long customerId;

    private Long productId;

    private String reviewContent;

    private Integer score;

    @PostPersist
    public void onPostPersist() {}

    public static ReviewRepository repository() {
        ReviewRepository reviewRepository = ReviewServiceApplication.applicationContext.getBean(
            ReviewRepository.class
        );
        return reviewRepository;
    }

    //<<< Clean Arch / Port Method
    public void reviewCreate(ReviewCreateCommand reviewCreateCommand) {
        //implement business logic here:

        ReviewCreated reviewCreated = new ReviewCreated(this);
        reviewCreated.setCustomerId(this.customerId);
        reviewCreated.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method

    //<<< Clean Arch / Port Method
    public static void addReviewList(OrderConfirmed orderConfirmed) {
        Review review = new Review();
        review.setOrderId(orderConfirmed.getId());
        repository().save(review);

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
