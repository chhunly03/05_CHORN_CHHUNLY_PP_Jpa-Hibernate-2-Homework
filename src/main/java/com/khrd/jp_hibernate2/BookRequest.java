package com.khrd.jp_hibernate2;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BookRequest {
    private String title;
    private String description;
    private String author;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date publicationYear;
}
