package com.example.springboot.common;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Page<T> {
    private Integer current;
    private Integer total;
    private List<T> list;
}
