package com.tutu2.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 记录用户最后月度的book-part信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookPartDTO implements Serializable {
    private Long userId;
    private Long bookId;
    private Long partId;
}
