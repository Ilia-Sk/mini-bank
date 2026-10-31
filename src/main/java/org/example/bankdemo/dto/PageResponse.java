package org.example.bankdemo.dto;

import java.util.List;

public record PageResponse<T>(List<T> items, int page, int pageSize,long totalElements, int totalPages) {


}
