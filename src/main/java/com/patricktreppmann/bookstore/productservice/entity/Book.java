package com.patricktreppmann.bookstore.productservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.net.URI;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book implements Serializable {
    public static final Set<String> SORTABLE_FIELDS = Set.of(
            "name","author","price"
    );

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private String isbn;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private String coverImgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    @JsonIgnore
    private Set<Rating> ratings;

    @Transient
    private String href;

    @PostLoad
    public void createLinkToResource() {
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentContextPath();
        builder.scheme("http");
        URI uri = builder.build().toUri();
        href = uri + "/api/v1/books/" + bookId;
    }
}
