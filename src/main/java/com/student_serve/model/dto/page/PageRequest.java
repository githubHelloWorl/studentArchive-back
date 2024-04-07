package com.student_serve.model.dto.page;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageRequest <T> implements Serializable {
    Integer pageNumber;
    Integer pageSize;
    T data;
}